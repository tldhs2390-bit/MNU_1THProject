<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
<title>오늘 등록 가능 횟수 종료</title>

<style>
body {
    background: #f4fbe9;
    font-family: 'Noto Sans KR', sans-serif;
    text-align:center;
    margin:0;
    padding-top:100px;
}

.box {
    width: 420px;
    margin: 0 auto;
    background: white;
    padding: 35px;
    border-radius: 20px;
    border: 3px solid #d8eec5;
    box-shadow: 0 5px 15px rgba(0,0,0,0.15);
    animation: fadeIn .6s ease;
}

@keyframes fadeIn {
    from { opacity:0; transform:translateY(20px); }
    to   { opacity:1; transform:translateY(0); }
}

.icon {
    font-size: 60px;
    margin-bottom: 10px;
}

.title {
    font-size: 24px;
    font-weight: 700;
    color:#4CAF50;
    margin-bottom: 10px;
}

.msg {
    font-size: 16px;
    color:#555;
    margin-bottom: 25px;
}

.btn {
    display:inline-block;
    padding:12px 25px;
    background:#4CAF50;
    color:white;
    font-size:15px;
    border-radius:10px;
    text-decoration:none;
    transition:0.2s;
}

.btn:hover {
    background:#43a047;
}
</style>
</head>

<body>

<div class="box">
    <div class="icon">🌱</div>
    <div class="title">오늘 등록은 종료!</div>
    <div class="msg">하루 2회까지만 글을 등록할 수 있어요.<br>내일 다시 작성해주세요 😄</div>

    <a href="/growth_list.do" class="btn">목록으로 돌아가기</a>
</div>

</body>
</html>