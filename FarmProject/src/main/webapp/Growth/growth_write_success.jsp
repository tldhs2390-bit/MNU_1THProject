<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
<title>포인트 증가!</title>

<style>
body {
    background:#f4fbe9;
    font-family:'Noto Sans KR', sans-serif;
    text-align:center;
    margin:0;
    padding-top:120px;
}

.box {
    background:white;
    width:450px;
    margin:0 auto;
    padding:40px;
    border-radius:25px;
    border:3px solid #d8eec5;
    box-shadow:0 5px 15px rgba(0,0,0,0.15);
    animation: fadeIn .7s ease;
}

@keyframes fadeIn {
    from { opacity:0; transform:translateY(20px); }
    to   { opacity:1; transform:translateY(0); }
}

.point {
    font-size:50px;
    font-weight:700;
    color:#ff9800;
    animation: pop 0.7s ease-out;
}

@keyframes pop {
    0% { transform: scale(0.2); opacity:0; }
    60% { transform: scale(1.3); opacity:1; }
    100% { transform: scale(1); }
}

.msg {
    font-size:20px;
    color:#4CAF50;
    margin-top:10px;
    margin-bottom:25px;
    font-weight:600;
}

.btn {
    padding:12px 25px;
    background:#4CAF50;
    color:white;
    border-radius:12px;
    text-decoration:none;
    font-size:16px;
}

.btn:hover {
    background:#43a047;
}

</style>
</head>

<body>

<div class="box">
    <div class="point">+ ${pointPlus} 포인트!</div>
    <div class="msg">🎉 성장 기록이 저장되었어요! 계속해서 성장해봐요 🌱</div>

    <a href="/growth_list.do" class="btn">목록으로 돌아가기</a>
</div>

</body>
</html>