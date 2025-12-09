<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
<title>🌱 글 수정하기</title>

<style>
body {
    background:#f4fbe9;
    font-family:'Noto Sans KR', sans-serif;
}

.modify-container {
    width:80%;
    max-width:900px;
    margin:40px auto;
    background:white;
    border-radius:20px;
    padding:40px;
    border:3px solid #d8eec5;
    box-shadow:0 8px 25px rgba(0,0,0,0.08);
}

.modify-title {
    font-size:30px;
    font-weight:900;
    color:#4CAF50;
    margin-bottom:25px;
    text-align:center;
}

.input-box { margin-bottom:20px; }
.input-box label {
    display:block;
    font-weight:bold;
    margin-bottom:8px;
    color:#4CAF50;
}
.input-box input, 
.input-box textarea {
    width:100%;
    border:2px solid #d8eec5;
    border-radius:12px;
    padding:12px;
    font-size:15px;
}

.btn-wrap {
    text-align:center;
    margin-top:30px;
}
.btn-wrap button {
    padding:12px 25px;
    background:#4CAF50;
    color:white;
    font-weight:bold;
    border:none;
    border-radius:12px;
    cursor:pointer;
}
</style>

</head>
<body>

<div class="modify-container">

    <div class="modify-title">🌱 글 수정하기</div>

    <!-- 수정 폼 -->
    <form method="post" action="/Success/modifyPro.do">

        <!-- 글 번호 -->
        <input type="hidden" name="idx" value="${dto.idx}">

        <!-- 제목 -->
        <div class="input-box">
            <label>제목</label>
            <input type="text" name="subject" value="${dto.subject}" required>
        </div>

        <!-- 내용 -->
        <div class="input-box">
            <label>내용</label>
            <textarea name="contents" rows="10" required>${dto.contents}</textarea>
        </div>

        <!-- 비밀번호 확인 -->
        <div class="input-box">
            <label>비밀번호</label>
            <input type="password" name="pass" required>
        </div>

        <div class="btn-wrap">
            <button type="submit">수정하기</button>
        </div>

    </form>

</div>

</body>
</html>
