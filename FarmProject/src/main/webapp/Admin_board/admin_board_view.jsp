<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/Admin/Include/admin_topmenu.jsp" %>

<html>
<head>
<meta charset="UTF-8">
<title>공지사항 보기(관리자용)</title>

<!-- 공통 CSS -->
<style>
    body {
        margin: 0;
        padding: 0;
        font-family: "Noto Sans KR", sans-serif;
        background-color: #f3f8e4; /* 초심자가이드와 같은 배경 */
    }

    .container {
        width: 1000px;
        margin: 30px auto;
    }

    .board-wrapper {
        background: #ffffff;
        border-radius: 16px;
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
        border-radius: 12px;
        border: 1px solid #dfe7c7;
        min-height: 200px;
        white-space: pre-line;
    }

    .btn-area {
        margin-top: 25px;
        text-align: right;
        display: flex;
        gap: 10px;
        justify-content: flex-end;
    }

    .btn {
        padding: 10px 16px;
        border-radius: 10px;
        border: none;
        cursor: pointer;
        font-size: 14px;
        font-weight: 600;
    }

    .btn-list {
        background: #8bc34a;
        color: #fff;
    }

    .btn-modify {
        background: #ffd54f;
    }

    .btn-delete {
        background: #ef5350;
        color: #fff;
    }

</style>

</head>
<body>
<div class="container">
    <div class="board-wrapper">

        <!-- 제목 -->
        <div class="board-title">
           
            자유게시판 - 글읽기
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

        <!-- 버튼 영역 -->
        <div class="btn-area">
            <button class="btn btn-list" onclick="location.href='admin_board_list.do?page=${page}'">목록</button>
        </div>

    </div>
</div>

</body>
</html>