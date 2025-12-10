<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>관리자 - 게시글 수정</title>

<style>
/* ============================= */
/* 관리자 페이지 스타일 */
/* ============================= */
body {
    background:#f4fbe9;
    font-family:'Noto Sans KR', sans-serif;
    margin:0;
    padding:0;
}

.container {
    width:75%;
    max-width:850px;
    background:white;
    margin:40px auto;
    padding:30px;
    border-radius:12px;
    box-shadow:0 3px 10px rgba(0,0,0,0.12);
}

/* 제목 */
h1 {
    font-size:28px;
    margin-bottom:25px;
    color:#2e7d32;
    border-left:6px solid #43a047;
    padding-left:12px;
}

/* 라벨 */
label {
    font-weight:700;
    margin-bottom:6px;
    display:block;
    color:#2e7d32;
}

/* 입력 UI 공통 */
input[type="text"],
select,
textarea,
input[type="file"] {
    width:100%;
    padding:12px;
    border-radius:10px;
    border:1px solid #bbb;
    margin-bottom:20px;
    font-size:15px;
    background:#fafafa;
}

/* 텍스트 영역 */
textarea {
    height:200px;
    resize:none;
}

/* 현재 이미지 */
.current-img-box {
    margin-bottom:20px;
}
.current-img {
    max-width:250px;
    border-radius:10px;
    border:1px solid #ddd;
}
.preview-img {
    max-width:250px;
    border-radius:10px;
    margin-top:10px;
    display:none;
}

/* 버튼 영역 */
.btn-wrap {
    text-align:center;
    margin-top:30px;
}

.btn {
    padding:12px 22px;
    border:none;
    border-radius:10px;
    font-size:16px;
    font-weight:700;
    cursor:pointer;
}

.btn-submit {
    background:#2e7d32;
    color:white;
}

.btn-cancel {
    background:#999;
    color:white;
    margin-left:10px;
}
</style>

<script>
// 이미지 미리보기 기능
function previewImage(event) {
    let preview = document.getElementById("preview");
    let file = event.target.files[0];

    if (file) {
        preview.style.display = "block";
        preview.src = URL.createObjectURL(file);
    }
}
</script>

</head>

<body>

<div class="container">

<h1>🌱 관리자 - 게시글 수정</h1>

<form method="post" action="/admin_growth_modify_ok.do" enctype="multipart/form-data">

    <!-- hidden -->
    <input type="hidden" name="idx" value="${dto.idx}">
    <input type="hidden" name="oldImg" value="${dto.img}">

    <!-- 카테고리 -->
    <label>카테고리</label>
    <select name="category">
        <option value="채소" ${dto.category == '채소' ? 'selected' : ''}>🥬 채소</option>
        <option value="과일" ${dto.category == '과일' ? 'selected' : ''}>🍎 과일</option>
        <option value="허브" ${dto.category == '허브' ? 'selected' : ''}>🌿 허브</option>
    </select>

    <!-- 제목 -->
    <label>제목</label>
    <input type="text" name="subject" value="${dto.subject}" required>

    <!-- 내용 -->
    <label>내용</label>
    <textarea name="contents" required>${dto.contents}</textarea>

    <!-- 현재 이미지 -->
    <label>현재 이미지</label>
    <div class="current-img-box">
        <c:if test="${not empty dto.img}">
            <img src="/asset/growth/${dto.img}" class="current-img">
        </c:if>
        <c:if test="${empty dto.img}">
            <p style="color:#777;">등록된 이미지 없음</p>
        </c:if>
    </div>

    <!-- 이미지 변경 -->
    <label>이미지 변경</label>
    <input type="file" name="imgFile" onchange="previewImage(event)">
    <img id="preview" class="preview-img">

    <!-- 해시태그 -->
    <label>해시태그 (쉼표로 구분)</label>
    <input type="text" name="hashtags" value="${dto.hashtags}">

    <!-- 버튼 -->
    <div class="btn-wrap">
        <button type="submit" class="btn btn-submit">수정 완료</button>
        <button type="button" class="btn btn-cancel" onclick="history.back()">취소</button>
    </div>

</form>

</div>

</body>
</html>