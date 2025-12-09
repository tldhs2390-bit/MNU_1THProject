<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
<title>공지 사항</title>
<link rel="stylesheet" type="text/css" href="/css/main.css">

<style>
    body { 
        margin:0; 
        background:#f0f7e8;   
        font-family:"Noto Sans KR"; 
    }

    /* 전체 레이아웃 */
     .page-wrapper { display:flex; gap:20px; justify-content:flex-start; }
        .left-menu { width:20%; }
        .content { flex:1; padding:20px; }



    /* 우측 컨텐츠 영역 */
    .content-area {
        flex:1;
        background:#f5ffe9;
        border:2px solid #d8eec5;
        padding:25px;
    }

    .board-title {
        text-align:center;
        font-size:28px;
        font-weight:700;
        color:#4CAF50;
        margin-bottom:15px;
    }

    /* 검색창 */
    .search-container {
        display:flex; align-items:center; gap:12px;
        background:#d8eec5;
        border:2px solid #b0d17c;
        padding:15px 20px; border-radius:18px;
        margin:0 auto 22px auto; width:66%;
        box-shadow:0 4px 10px rgba(0,0,0,0.12);
    }
    .search-select { padding:10px 14px; border:2px solid #cfe8c8; border-radius:10px; background:white; font-size:14px; font-weight:700; color:#4CAF50; cursor:pointer; }
    .search-input { flex:1; padding:10px 14px; border:2px solid #cfe8c8; border-radius:10px; font-size:14px; }
    .search-btn { padding:10px 20px; background:#4CAF50; color:white; border:none; border-radius:12px; font-weight:800; cursor:pointer; transition:.25s; }
    .search-btn:hover { background:#43a047; }

    /* 게시판 */
    .board-table {
        width:100%;
        border-collapse:collapse;
        background:white;
        border-radius:12px;
        overflow:hidden;
        box-shadow:0 3px 6px rgba(0,0,0,0.06);
    }
    .board-table th {
        background:#e3f2e1;
        padding:12px;
        font-size:14px;
        border-bottom:1px solid #cfe8c8;
    }
    .board-table td {
        padding:12px;
        font-size:14px;
        text-align:center;
        border-bottom:1px solid #eeeeee;
    }
    .board-row:hover { background:#f0fff2; transition:0.2s; }

    a.list { text-decoration:none; color:#2e7d32; font-weight:600; }
    a.list:hover { text-decoration:underline; }
</style>

<script>
function board_search(){
    if(board.key.value==""){
        alert("검색어를 입력하세요");
        board.key.focus();
        return;
    }
    board.submit();
}
</script>
</head>

<body>
<div class="page-wrapper">

    <!-- 좌측 로그인 영역 -->
    <div class="left-menu">
        <%@ include file="/Include/login_form.jsp" %>
    </div>

    <!-- 우측 컨텐츠 영역 -->
    <div class="content-area">

        <h1 class="board-title">🌿 공지 사항</h1>

        <!-- 검색창 -->
        <form name="board" method="get" action="board_list.do">
            <div class="search-container">
                <select name="search" class="search-select">
                    <option value="subject" <c:if test="${search=='subject'}">selected</c:if>>제목</option>
                    <option value="contents" <c:if test="${search=='contents'}">selected</c:if>>내용</option>
                </select>
                <input type="text" name="key" class="search-input" value="${key}">
                <button type="button" class="search-btn" onclick="board_search()">검색</button>
            </div>
        </form>

        <!-- 게시판 상단 정보 -->
        <div style="text-align:right; margin-bottom:10px;">
            전체 : <b>${totcount}</b>건 — ${page}/${totpage} pages
        </div>

        <!-- 게시판 리스트 -->
        <table class="board-table">
            <tr>
                <th width="10%">번호</th>
                <th width="50%">제목</th>
                <th width="15%">글쓴이</th>
                <th width="15%">작성일</th>
                <th width="10%">조회</th>
            </tr>

            <c:if test="${empty blist}">
                <tr>
                    <td colspan="5" style="padding:20px;">등록된 글이 없습니다.</td>
                </tr>
            </c:if>

            <c:if test="${!empty blist}">
                <c:forEach var="dto" items="${blist}">
                    <tr class="board-row">
                        <td>${listcount}</td>
                        <td style="text-align:left; padding-left:15px;">
                            <a class="list" href="board_view.do?idx=${dto.idx}&page=${page}">${dto.subject}</a>
                        </td>
                        <td>${dto.name}</td>
                        <td>${fn:substring(dto.regdate,0,10)}</td>
                        <td>${dto.readcnt}</td>
                    </tr>
                    <c:set var="listcount" value="${listcount-1}"/>
                </c:forEach>
            </c:if>
        </table>

        <!-- 페이지 이동 -->
        <div style="text-align:center; margin-top:20px;">
            ${pageSkip}
        </div>

    </div>
</div>
</body>
</html>