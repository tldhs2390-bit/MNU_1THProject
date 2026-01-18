<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<title>작성 제한 안내</title>

<style>
body {
    background:#f8fff1;
    font-family:'Noto Sans KR', sans-serif;
    display:flex;
    justify-content:center;
    align-items:center;
    height:100vh;
    margin:0;
}
.box {
    width:330px;
    background:white;
    padding:30px;
    border-radius:20px;
    text-align:center;
    border:3px solid #ffd5d5;
    animation:pop .25s ease-out;
}
.title {
    font-size:22px;
    font-weight:900;
    color:#ff6b6b;
    margin-bottom:10px;
}
.msg {
    font-size:15px;
    color:#555;
    margin-bottom:15px;
}
@keyframes pop {
    0% {transform:scale(0.6); opacity:0;}
    100% {transform:scale(1); opacity:1;}
}
</style>

<script>
// 1.5초 후 다시 글쓰기 페이지로 이동
setTimeout(function(){
    location.href = "/growth_write.do";
}, 1500);
</script>

</head>
<body>

<div class="box">
    <div class="title">작성 제한 안내</div>
    <div class="msg">오늘 작성 가능한 2회가 모두 사용되었습니다.</div>
    <div class="msg">내일 다시 이용해주세요!</div>
</div>

</body>
</html>
