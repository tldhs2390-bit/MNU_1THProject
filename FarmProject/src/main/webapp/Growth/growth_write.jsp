<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
<title>🌱 성장 이야기 - 글쓰기</title>

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

.input-text, .input-select, .input-file, .input-pass {
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

    <div class="title">✏️ 성장 이야기 - 새 글 작성</div>

    <form action="/growth_write_ok.do" method="post" enctype="multipart/form-data">

        <!-- ⭐ 카테고리 -->
        <div class="input-box">
            <label>카테고리</label>
            <select name="category" class="input-select">
                <option value="vegetable">🥬 채소</option>
                <option value="fruit">🍎 과일</option>
                <option value="herb">🌿 허브</option>
            </select>
        </div>

        <!-- ⭐ 제목 -->
        <div class="input-box">
            <label>제목</label>
            <input type="text" name="subject" class="input-text" placeholder="제목을 입력하세요" required>
        </div>

        <!-- ⭐ 내용 -->
        <div class="input-box">
            <label>내용</label>
            <textarea name="contents" class="textarea" placeholder="내용을 입력하세요" required></textarea>
        </div>

        <!-- ⭐ 이미지 -->
        <div class="input-box">
            <label>이미지 첨부</label>
            <input type="file" name="img" class="input-file">
        </div>

        <!-- ⭐ 해시태그 -->
        <div class="input-box">
            <label>해시태그 (쉼표로 구분)</label>
            <input type="text" name="hashtags" class="input-text" placeholder="예: 채소성장, 물주기, 허브키우기">
        </div>

        <!-- ⭐ 비밀번호 -->
        <div class="input-box">
            <label>삭제 비밀번호</label>
            <input type="password" name="pass" class="input-pass" placeholder="비밀번호 입력" required>
        </div>

        <!-- ⭐ 작성자 hidden 삭제 (이미 서블릿에서 처리됨) -->

        <div class="btn-area">
            <button class="btn">등록하기</button>
        </div>

    </form>

</div>

</body>
</html>