<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
<title>🌱 성장 이야기 - 글 수정</title>

<style>
body {
    background:#f4fbe9;
    margin:0; padding:0;
    font-family:'Noto Sans KR', sans-serif;
}

.write-container {
    width:80%;
    max-width:900px;
    margin:40px auto;
    background:white;
    border:3px solid #d8eec5;
    border-radius:25px;
    padding:40px;
    box-shadow:0 8px 25px rgba(0,0,0,0.05);
}

.title {
    font-size:28px;
    font-weight:900;
    color:#4CAF50;
    margin-bottom:25px;
}

/* 입력 폼 */
.input-box {
    margin-bottom:20px;
}

.input-box label {
    font-weight:800;
    margin-bottom:8px;
    display:block;
    color:#4CAF50;
}

.input-text, .input-select, .input-file {
    width:100%;
    padding:12px;
    font-size:15px;
    border-radius:12px;
    border:2px solid #cfe8c8;
}

.textarea {
    width:100%;
    height:180px;
    padding:15px;
    border-radius:12px;
    border:2px solid #cfe8c8;
    font-size:15px;
    resize:none;
}

.btn-area {
    text-align:center;
    margin-top:30px;
}

.btn {
    padding:12px 30px;
    background:#4CAF50;
    color:white;
    border:none;
    border-radius:14px;
    font-weight:900;
    cursor:pointer;
    font-size:16px;
    transition:0.2s;
}

.btn:hover {
    background:#45a049;
    transform:scale(1.05);
}
</style>

</head>
<body>

<div class="write-container">

    <div class="title">✏️ 성장 이야기 - 글 수정</div>

    <form action="/growth_modify_ok.do" method="post" enctype="multipart/form-data">

        <!-- ⭐ 수정할 글 번호 -->
        <input type="hidden" name="idx" value="${dto.idx}">

        <!-- ⭐ 기존 이미지 파일명 (이미지 유지 기능에 꼭 필요!!) -->
        <input type="hidden" name="oldImg" value="${dto.img}">

        <!-- ⭐ 카테고리 -->
        <div class="input-box">
            <label>카테고리</label>
            <select name="category" class="input-select">
                <option value="vegetable" ${dto.category == 'vegetable' ? 'selected' : ''}>🥬 채소</option>
                <option value="fruit"     ${dto.category == 'fruit' ? 'selected' : ''}>🍎 과일</option>
                <option value="herb"      ${dto.category == 'herb' ? 'selected' : ''}>🌿 허브</option>
            </select>
        </div>

        <!-- ⭐ 제목 -->
        <div class="input-box">
            <label>제목</label>
            <input type="text" name="subject" class="input-text"
                   value="${dto.subject}" required>
        </div>

        <!-- ⭐ 내용 -->
        <div class="input-box">
            <label>내용</label>
            <textarea name="contents" class="textarea" required>${dto.contents}</textarea>
        </div>

        <!-- ⭐ 기존 이미지 미리보기 -->
        <c:if test="${not empty dto.img}">
            <div style="margin-bottom:15px;">
                <label>현재 이미지</label><br>
                <img src="/asset/growth/${dto.img}" 
                     style="max-width:200px; border-radius:10px; border:2px solid #ddd;">
            </div>
        </c:if>

        <!-- ⭐ 새 이미지 업로드 -->
        <div class="input-box">
            <label>이미지 변경 (선택)</label>
            <input type="file" name="img" class="input-file">
        </div>

        <!-- ⭐ 해시태그 -->
        <div class="input-box">
            <label>해시태그 (쉼표로 구분)</label>
            <input type="text" name="hashtags" class="input-text"
                   value="${dto.hashtags}">
        </div>

        <div class="btn-area">
            <button class="btn">수정 완료</button>
        </div>

    </form>

</div>

</body>
</html>