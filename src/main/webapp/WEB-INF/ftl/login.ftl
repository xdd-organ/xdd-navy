<!DOCTYPE html>
<html>
<head>
    <title>用户登录</title>
    <meta charset="utf-8">
    <link rel="icon" href="/static/icon/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="/static/js/bootstrap-3.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/js/bootstrap-3.3.0/dist/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/static/js/bootstrapValidator/dist/css/bootstrapValidator.min.css">
    <link rel="stylesheet" href="/static/css/xdd-navy/main/login.css">
    <script type="text/javascript" src="/static/js/jQuery/jquery-1.11.1.js"></script>
    <script type="text/javascript" src="/static/js/common/common.js"></script>
    <script type="text/javascript" src="/static/js/bootstrap-3.3.0/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/js/bootstrapValidator/dist/js/bootstrapValidator.min.js"></script>
    <script type="text/javascript" src="/static/js/bootstrapValidator/dist/js/language/zh_CN.js"></script>
    <script type="text/javascript" src="/static/js/xdd-navy/main/login.js"></script>
</head>
<body>
<div class="tree-default tree-file"></div>
<div class="tree-default tree-folder"></div>
<div class="container">
    <form class="form-signin" id="form" role="form" action="/login" method="post">
        <div class="form-group">
            <input type="text" name="username" class="form-control form-username" placeholder="用户名/邮箱/手机号" autofocus>
        </div>
        <div class="form-group">
            <input type="password" name="password" class="form-control form-password" placeholder="密码" required>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" name="rememberMe"> 记住密码 <!--<a href="#">忘记密码？</a>-->
            </label>
            <a class="register" href="/toRegister">注册</a>
        </div>
        <div class="form-group">
            <input class="btn btn-lg btn-success btn-block" type="submit" id="submit" value="登录"/>
        </div>
    </form>
</div>
</body>
</html>
