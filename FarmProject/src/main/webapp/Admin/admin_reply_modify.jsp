<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>댓글 수정</title>

<style>
body { 
    background:#f4fbe9;
    font-family:'Noto Sans KR'; 
    margin:0;
    padding:0;
}
.container {
    width:600px; 
    margin:40px auto; 
    background:white; 
    padding:25px;
    border-radius:12px; 
    border:1px solid #ddd;
    box-shadow:0 3px 10px rgba(0,0,0,0.12);
}

h2 {
    margin-bottom:20px;
    color:#2e7d32;
    border-left:6px solid #43a047;
    padding-left:10px;
    font-size:22px;
}

label { 
    display:block; 
    margin-top:12px; 
    font-weight:bold;
    color:#2e7d32;
}

textarea {
    width:100%; 
    height:120px; 
    border-radius:8px; 
    border:1px solid #ccc;
    padding:10px; 
    margin-top:5px;
}

input[type=text] {
    width:100%; 
    padding:8px; 
    border-radius:8px; 
    border:1px solid #ccc;
}

.btn-wrap { 
    margin-top:25px; 
    text-align:center; 
}

.btn { 
    padding:10px 18px; 
    border:none; 
    border-radius:8px; 
    cursor:pointer; 
    font-size:15px;
}

.btn-ok { background:#4caf50; color:white; }
.btn-cancel { background:#999; color:white; margin-left:10px; }

</style>

</head>
<body>

<div class="container">

<h2>📝 댓글 수정</h2>

<form method="post" action="/admin_reply_modify_ok.do">

    <!-- 댓글 PK -->
    <input type="hidden" name="r_idx" value="${dto.r_idx}">
    <!-- 원글 번호 -->
    <input type="hidden" name="post_idx" value="${dto.post_idx}">

    <label>내용</label>
    <textarea name="contents">${dto.contents}</textarea>

    <label>이모지</label>
    <input type="text" name="emoji" value="${dto.emoji}">

    <div class="btn-wrap">
        <button type="submit" class="btn btn-ok">수정 완료</button>
        <button type="button" class="btn btn-cancel" onclick="history.back()">취소</button>
    </div>

</form>

</div>

</body>
</html>