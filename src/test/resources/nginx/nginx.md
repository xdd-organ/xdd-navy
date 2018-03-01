### nginx配置

#### 证书和私钥的生成
```
    注意：一般生成的目录,应该放在nginx/conf/ssl目录
    1.创建服务器证书密钥文件 server.key：
    openssl genrsa -des3 -out server.key 1024
    输入密码，确认密码，自己随便定义，但是要记住，后面会用到。
    
    2.创建服务器证书的申请文件 server.csr
    openssl req -new -key server.key -out server.csr
    
    输出内容为：
    Enter pass phrase for root.key: ← 输入前面创建的密码 
    Country Name (2 letter code) [AU]:CN ← 国家代号，中国输入CN 
    State or Province Name (full name) [Some-State]:BeiJing ← 省的全名，拼音 
    Locality Name (eg, city) []:BeiJing ← 市的全名，拼音 
    Organization Name (eg, company) [Internet Widgits Pty Ltd]:MyCompany Corp. ← 公司英文名 
    Organizational Unit Name (eg, section) []: ← 可以不输入 
    Common Name (eg, YOUR name) []: ← 此时不输入 
    Email Address []:admin@mycompany.com ← 电子邮箱，可随意填
    Please enter the following ‘extra’ attributes 
    to be sent with your certificate request 
    A challenge password []: ← 可以不输入 
    An optional company name []: ← 可以不输入
    
    4.备份一份服务器密钥文件
    cp server.key server.key.org
    
    5.去除文件口令
    openssl rsa -in server.key.org -out server.key
    6.生成证书文件server.crt
    
    openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
```
#### 配置https(ssl)
```
    vim nginx.conf
    
    server {
        listen       443;
        server_name  本机的IP地址;
        
        # 开始ssl，配置ssl的key文件位置
        ssl                  on;
        ssl_certificate      /etc/nginx/server.crt; 
        ssl_certificate_key  /etc/nginx/server.key;
        ssl_session_timeout  5m;
        
        location / {
             proxy_redirect off;
             proxy_set_header Host $host;
             proxy_set_header X-Real-IP $remote_addr;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }
    }
```
