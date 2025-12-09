<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
<title>🌱 글 삭제</title>

<style>
body {
    background:#f4fbe9;
    font-family:'Noto Sans KR', sans-serif;
}

.delete-container {
    width:80%;
    max-width:500px;
    margin:40px auto;
    background:white;
    border-radius:20px;
    padding:40px;
    border:3px solid #d8eec5;
    box-shadow:0 8px 25px rgba(0,0,0,0.08);
    text-align:center;
}

.delete-title {
    font-size:26px;
    font-weight:900;
    color:#4CAF50;
    margin-bottom:25px;
}

.input-box { margin-bottom:20px; }
.input-box label {
    display:block;
    font-weight:bold;
    margin-bottom:8px;
    color:#4CAF50;
}
.input-box input {
    width:100%;
    border:2px solid #d8eec5;
    border-radius:12px;
    padding:12px;
    font-size:15px;
}

.btn-wrap { margin-top:20px; }
.btn-wrap button {
    padding:12px 25px;
    background:#4CAF50;
    color:white;
    border:none;
    border-radius:12px;
    font-weight:bold;
    cursor:pointer;
}
</style>

</head>
<body>

<div class="delete-container">

    <div class="delete-title">🌱 글 삭제</div>

    <!-- 삭제 폼 -->
    <form method="post" action="/Success/deletePro.do">

        <input type="hidden" name="idx" value="${idx}">

        <!-- 비밀번호 -->
        <div class="input-box">
            <label>비밀번호</label>
            <input type="password" name="pass" required>
        </div>

        <div class="btn-wrap">
            <button type="submit">삭제하기</button>
        </div>

    </form>

</div>

</body>
</html>