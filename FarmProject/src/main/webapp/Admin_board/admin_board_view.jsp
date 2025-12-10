<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/Admin/Include/admin_topmenu.jsp" %>

<html>
<head>
<meta charset="UTF-8">
<title>공지사항 보기(관리자용)</title>

<style>
    body {
        margin: 0;
        padding: 0;
        font-family: "Noto Sans KR", sans-serif;
        background-color: #f3f8e4;
    }

    .container {
        width: 1000px;
        margin: 30px auto;
    }

    .board-wrapper {
        background: #ffffff;
        padding: 30px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.08);
    }

    .board-title {
        font-size: 22px;
        font-weight: 700;
        color: #558b2f;
        margin-bottom: 20px;
        display: flex;
        align-items: center;
        gap: 6px;
    }

    .info-box {
        background: #f5f7e8;
        border-radius: 12px;
        padding: 15px 20px;
        font-size: 14px;
        color: #666;
        margin-bottom: 20px;
        line-height: 1.6;
    }

    .content-box {
        padding: 20px;
        font-size: 15px;
        line-height: 1.8;
        background: #ffffff;
        border: 1px solid #dfe7c7;
        min-height: 200px;
        white-space: pre-line;
    }

    /* 버튼 영역 */
    .btn-area {
        margin-top: 25px;
        display: flex;
        justify-content: flex-end; 
        gap: 15px; 
    }

    .btn-area .btn {
        padding: 10px 16px;
        border-radius: 10px;
        border: none;
        cursor: pointer;
        font-size: 14px;
        font-weight: 600;
    }

    .btn-register { background: #4CAF50; color: #fff; }
    .btn-modify { background: #ffd54f; }
    .btn-delete { background: #ef5350; color: #fff; }
    .btn-list { background: #4db6ac; color: #fff; }

</style>

</head>
<body>
<div class="container">
    <div class="board-wrapper">

        <!-- 제목 -->
        <div class="board-title">
            공지사항 - 글읽기
        </div>

        <!-- 글 정보 -->
        <div class="info-box">
            <b>${dto.subject}</b><br>
            작성자 : ${dto.name}  
            &nbsp; | &nbsp;
            날짜 : ${fn:substring(dto.regdate,0,10)}  
            &nbsp; | &nbsp;
            조회수 : ${dto.readcnt}회
        </div>

        <!-- 게시글 본문 -->
        <div class="content-box">
            ${dto.contents}
        </div>

        <!-- 삭제 폼 (hidden, POST 전송용) -->
        <form id="deleteForm" method="post" action="admin_board_delete.do">
            <input type="hidden" name="idx" value="${dto.idx}">
            <input type="hidden" name="page" value="${page}">
            <input type="hidden" name="pass" value="">
        </form>

        <!-- 버튼 영역 -->
        <div class="btn-area">
            <button class="btn btn-register" onclick="location.href='admin_board_write.do'">등록</button>
            <button class="btn btn-modify" onclick="location.href='admin_board_modify.do?idx=${dto.idx}&page=${page}'">수정</button>
            <button class="btn btn-delete" onclick="deleteBoard(${dto.idx}, ${page})">삭제</button>
            <button class="btn btn-list" onclick="if(history.length>1){history.back();}else{location.href='admin_board_list.do?page=${page}';}">목록</button>
        </div>

    </div>
</div>

<script>
function deleteBoard(idx, page) {
    if(confirm('정말 삭제하시겠습니까?')) {
        let pass = prompt('비밀번호를 입력하세요.');
        if(pass != null) {
            let form = document.getElementById('deleteForm');
            form.pass.value = pass;
            form.submit();
        }
    }
}
</script>

</body>
</html>