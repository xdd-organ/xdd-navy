<!DOCTYPE html>
<html>
<head>
    <title>聊天页面</title>
    <meta charset="utf-8">
    <link rel="icon" href="/static/icon/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="/static/js/bootstrap-3.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/js/bootstrap-3.3.0/dist/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/static/css/xdd-navy/websocket/websocket.css">
    <script type="text/javascript" src="/static/js/common/common.js"></script>
    <script type="text/javascript" src="/static/js/jQuery/jquery-1.11.1.js"></script>
    <script type="text/javascript" src="/static/js/bootstrap-3.3.0/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/js/bootstrapValidator/dist/js/bootstrapValidator.min.js"></script>
    <script type="text/javascript" src="/static/js/bootstrapValidator/dist/js/language/zh_CN.js"></script>
    <script type="text/javascript" src="/static/js/xdd-navy/websocket/websocket.js"></script>
</head>

<body>
<div class="chat">
    <div class="friends">
        <div class="item" id="0">
            <a href="javascript:;">同事</a>
            <ul id="colleague">
            </ul>
        </div>
        <div class="item" id="1">
            <a href="javascript:;">朋友</a>
            <ul>
                <li>功能正在开发</li>
            </ul>
        </div>
        <div class="item" id="2">
            <a href="javascript:;">群</a>
            <ul>
                <li id="111">功能正在开发</li>
            </ul>
        </div>
    </div>
    <div class="messageControl">
        <div class="control">
            <div id="username">联系人</div>
            <div class="head" id="sendTo"></div>
        </div>
        <div style="height: 270px;overflow: auto">
            <div id="message">
            </div>
        </div>
    </div>
    <div class="messageMessage">
        <div class="toolbar">
            工具栏
            <input type="button" value="发送" id="send">
        </div>
        <div>
            <textarea class="msgTextarea" id="content" placeholder="请输入消息..."></textarea>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(function(){
        $('.item a').click(function(){
            $(this).siblings('ul').slideToggle();
        })
    })
</script>
</html>