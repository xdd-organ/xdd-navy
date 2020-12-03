local tokens_key = KEYS[1] -- 令牌桶需要两个Redis密钥
local timestamp_key = KEYS[2]

local rate = tonumber(ARGV[1]) -- 每秒产生多少个令牌
local capacity = tonumber(ARGV[2]) -- 令牌桶的容量
local now = tonumber(ARGV[3]) -- 时间戳 当前时间的秒数
local requested = tonumber(ARGV[4]) -- 需要获取多少个令牌

local fill_time = capacity/rate
local ttl = math.floor(fill_time*2)

local last_tokens = tonumber(redis.call("get", tokens_key))
if last_tokens == nil then
  last_tokens = capacity
end
--redis.log(redis.LOG_WARNING, "last_tokens " .. last_tokens)

local last_refreshed = tonumber(redis.call("get", timestamp_key))
if last_refreshed == nil then
  last_refreshed = 0
end
--redis.log(redis.LOG_WARNING, "last_refreshed " .. last_refreshed)

local delta = math.max(0, now-last_refreshed)
local filled_tokens = math.min(capacity, last_tokens+(delta*rate))
local allowed = filled_tokens >= requested
local new_tokens = filled_tokens
local allowed_num = 0
if allowed then
  new_tokens = filled_tokens - requested
  allowed_num = 1
end


redis.call("setex", tokens_key, ttl, new_tokens)
redis.call("setex", timestamp_key, ttl, now)

-- 返回本次申请成功标志allowed_num和剩余令牌数new_tokens (注意区分剩余令牌数和本次申请的令牌数)
return { allowed_num, new_tokens }