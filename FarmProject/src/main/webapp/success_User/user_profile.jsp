<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
<title>🌱 사용자 프로필</title>

<style>
body {
    background:#f4fbe9;
    font-family:'Noto Sans KR', sans-serif;
}

.profile-container {
    width:80%;
    max-width:700px;
    margin:40px auto;
    background:white;
    border-radius:20px;
    padding:40px;
    border:3px solid #d8eec5;
    box-shadow:0 8px 25px rgba(0,0,0,0.08);
    text-align:center;
}

/* 닉네임 */
.profile-name {
    font-size:28px;
    font-weight:900;
    color:#4CAF50;
    margin-bottom:10px;
}

/* 정보 표 */
.info-box {
    margin-top:25px;
    font-size:18px;
    color:#333;
}

.info-row {
    margin:10px 0;
}

.back-btn {
    margin-top:30px;
    display:inline-block;
    padding:10px 18px;
    background:#4CAF50;
    color:white;
    border-radius:10px;
    text-decoration:none;
    font-weight:700;
}
</style>

</head>
<body>

<div class="profile-container">

    <div class="profile-name">🌿 ${dto.n_name} 님의 프로필</div>

    <div class="info-box">
        <div class="info-row"><b>아이디:</b> ${dto.userid}</div>
        <div class="info-row"><b>이메일:</b> ${dto.email}</div>
        <div class="info-row"><b>가입일:</b> ${dto.regdate}</div>
    </div>

    <a href="/Success/list.do" class="back-btn">목록으로 돌아가기</a>

</div>

</body>
</html>