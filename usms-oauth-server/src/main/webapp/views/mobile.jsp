<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base href="/">
    <meta charset="UTF-8">
    <title>平潭综合实验区</title>
</head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="/static/css/mobile-style.css">
<link rel="stylesheet" type="text/css" href="/static/css/theme.css">
<link rel="stylesheet" type="text/css" href="/static/css/jquery.mobile-1.3.2.min.css">

<body>
<div data-role="page">
    <div data-role="content" class="login-wrap">
        <div class="login-input-box">
            <div class="logo">
                <p>平潭综合实验区</p>
            </div>
            <div class="tip">
                <p style="color: red;"></p>
            </div>
            <div>
                <div class="line1">
                    <img src="/static/images/user.png">
                    <input type="text" class="user" value="用户名"
                           onfocus="if(value=='用户名') {value=''}"
                           onblur="if (value=='') {value='用户名'}" data-role="none">
                </div>
                <div class="line2">
                    <img src="/static/images/lock.png">
                    <input type="text" class="lock" value="密码"
                           onfocus="if(value=='密码') {value=''}"
                           onblur="if (value=='') {value='密码'}" data-role="none">
                </div>
                <div class="chose">
                    <a href="#" class="login">授权并登录</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/static/js/jquery-1.8.3.min.js"></script>
<script src="/static/js/jquery.mobile-1.3.2.min.js"></script>
</body>
</html>