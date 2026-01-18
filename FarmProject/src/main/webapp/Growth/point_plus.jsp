<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.user.UserDTO" %>

<%
UserDTO user = (UserDTO) session.getAttribute("user");
int beforePoint = user.getPoint();
int afterPoint = beforePoint + 100;
%>

<html>
<head>
<title>포인트 획득!</title>

<style>
body {
    background:#f4fbe9;
    font-family:'Noto Sans KR', sans-serif;
    display:flex;
    justify-content:center;
    align-items:center;
    height:100vh;
    margin:0;
}

.box {
    width:350px;
    background:white;
    padding:30px;
    border-radius:22px;
    text-align:center;
    border:3px solid #d8eec5;
    animation:pop .3s ease-out;
}

.point {
    font-size:28px;
    font-weight:900;
    color:#4CAF50;
    margin-bottom:15px;
}

.anim {
    font-size:40px;
    font-weight:900;
    color:#ff6b55;
    animation:up 1s ease-out forwards;
}

@keyframes up {
    0% { opacity:0; transform:translateY(20px) scale(0.8); }
    100% { opacity:1; transform:translateY(0) scale(1.1); }
}

@keyframes pop {
    0% { transform:scale(0.6); opacity:0; }
    100% { transform:scale(1); opacity:1; }
}
</style>

<script>
// 1.5초 뒤 리스트 페이지로 이동
setTimeout(function(){
    location.href = "/growth_list.do";
}, 1500);
</script>
</head>

<body>

<div class="box">
    <div class="point">포인트 획득!</div>
    <div class="anim">+100P</div>
    <div style="margin-top:15px; font-size:16px;">
        현재 포인트: <b><%= afterPoint %>P</b>
    </div>
</div>

</body>
</html>