<!DOCTYPE html>
<html>
<head>
    <title>服务器主动推送</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" charset="utf-8">
    <script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.js"></script>
</head>
<body>
    <div id="msgFromPush"></div>
    <script type="text/javascript">
        if (!!window.EventSource) {
            console.log("支持！")
            var source = new EventSource("getDemo1"); //访问路径
            s = "";

            source.addEventListener("message", function (e) {
                console.log("接收到消息");
                s += e.data + "<br/>";
                $("#msgFromPush").html(s);
            }, false);

            source.addEventListener("open", function (e) {
                console.log("打开连接");
                console.log(e);
            }, false);

            source.addEventListener("error", function (e) {
                console.log("连接出错！");
                console.log(e);

                if (e.readyState == EventSource.CLOSED) {
                    console("连接关闭！");
                } else {
                    console("AAA" + e.readyState);
                }
            }, false);
        } else {
            alert("你的浏览器不支持！");
        }
    </script>
</body>
</html>
