$(function () {
    var websocket = null;

    function conn() {
        websocket = new WebSocket("ws://" + baseHost + ":" + basePort + "/websocket1");

        websocket.onopen = function () {
            //setMessageInnerHTML("连接成功！");
            console.log("执行onopen方法！");
        };
        websocket.onmessage = function (event) {
            console.log("接收内容为-->", event.data);
            var obj = JSON.parse(event.data);
            console.log("0000000"+obj.from + obj.content);
            var text = $("#username").text();
            setMessageInnerHTML(text + "-回复：" + obj.content);
            console.log("执行onmessage方法！");
        };
        websocket.onclose = function (event) {
            //setMessageInnerHTML("执行onclose方法！");
            console.log("执行onclose方法！");
        };
    }


    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }
    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }


    function send() {

    }

    //异步请求好友信息
    $.ajax({
        type: "POST",
        url: baseUrl + ":" + basePort + "/user/findByUser",
        success: function(data){
            console.log(data);
            $(data).each(function () {
                var li  = $("<li/>");
                li.attr("id", this.id);
                li.text(this.username);
                $("#colleague").append(li);
            });
            $("li[id]").click(function () {
                var userId = $(this).attr("id");
                console.log(userId);
                console.log($(this).text());
                $("#sendTo").attr("user-id", userId);
                $("#username").text($(this).text());
            });
            conn();//连接websocket
        },
        error : function () {
            console.log("异步请求朋友信息出错！");
            $("li[id]").click(function () {
                var userId = $(this).attr("id");
                console.log(userId);
                console.log($(this).text());
                $("#sendTo").attr("user-id", userId);
                $("#username").text($(this).text());
            });
        }
    });

    //发送消息
    $("#send").click(function (){
        var content = $("#content").val();
        if (content == null || content.trim() == '') return;
        var sendTo = $("#sendTo").attr("user-id");
        if (sendTo == null || sendTo.trim() == '') return;
        var msgObject = {
            content: content,
            sendTo: parseInt(sendTo)
        };
        var msg = JSON.stringify(msgObject);
        console.log("发送内容为-->", msg);
        setMessageInnerHTML("我：" + content);
        websocket.send(msg);
    });
});