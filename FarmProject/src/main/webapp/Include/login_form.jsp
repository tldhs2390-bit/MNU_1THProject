<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="/css/main.css">
</head>

<body>

<!--  로그인 상단 배너 이미지 -->
<!--  <div class="login-banner">
    <img src="/img/garden_banner.png">
</div> -->

    

<div class="login-box">

    <h3 class="login-title">Member Login</h3>

    <form action="/User?cmd=user_login" method="post">

        <!-- 아이디 -->
        <div class="input-wrap">
            <img src="/img/leaf_icon.png" class="icon">
            <input type="text" name="userid" placeholder="아이디">
        </div>

        <!-- 비밀번호 -->
        <div class="input-wrap">
            <img src="/img/leaf_icon.png" class="icon">
            <input type="password" name="passwd" placeholder="비밀번호">
        </div>

        <input type="image" src="/img/login_btn.png" class="login-btn">

    </form>

</div>

</body>
</html>