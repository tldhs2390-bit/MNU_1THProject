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
        background:#f0f7e8;   /* 전체 페이지 배경색 */
        font-family:"Noto Sans KR"; 
    }

    /* 전체 레이아웃 (Flexbox) */
     .page-wrapper { 
         display:flex; 
         gap:20px; 
         justify-content:flex-start; 
         width: 100%; /* 너비를 100%로 설정하여 왼쪽 가장자리에 붙도록 합니다. */
         margin: 0;
     }
     
    /* left-menu의 배경색을 body와 동일하게 설정하여 경계를 없앱니다. */
    .left-menu { 
        /* left_menu.css에서 설정된 width:20%를 오버라이드해야 할 수 있지만, 
           left_menu.css를 사용한다고 가정하고 배경색만 통일합니다. */
        /* ★ 배경색 통일 ★ */
        background: #f0f7e8 !important; 
        /* left-menu의 폭이 20%로 설정되어 있으나, 
           이전 대화 흐름에서 220px 고정 폭 Flexbox가 제안되었으므로, 
           이 클래스를 사용하는 모든 JSP는 통일된 CSS를 사용해야 합니다. 
           여기서는 사용자님이 주신 20%를 기반으로 스타일을 정리합니다. */
        width: 20%; 
    }
    
    /* 기존 CSS에서 불필요한 .content 정의 제거 */
    /* .content { flex:1; padding:20px; } */


    /* ⭐ [핵심 수정] 우측 컨텐츠 영역 틀 제거 ⭐ */
    .content-area {
        flex:1;
        /* 아래 배경색, 테두리, 패딩 속성 제거 */
        /* background:#f5ffe9; */
        /* border:2px solid #d8eec5; */
        /* padding:25px; */
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
        /* content-area 틀이 제거되었으므로, 검색창 중앙 정렬을 위해 max-width와 auto margin을 유지합니다. */
        margin:0 auto 22px auto; 
        width:90%; /* content-area 내에서 중앙 정렬을 위해 100% 대신 90% 사용 */
        max-width: 800px; /* 최대 너비 지정 */
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

    <div class="left-menu">
        <%@ include file="/Include/login_form.jsp" %>
    </div>

    <div class="content-area">

        <h1 class="board-title">🌿 공지 사항</h1>

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

        <div style="text-align:right; margin-bottom:10px;">
            전체 : <b>${totcount}</b>건 — ${page}/${totpage} pages
        </div>

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

        <div style="text-align:center; margin-top:20px;">
            ${pageSkip}
        </div>

    </div>
</div>
</body>
</html>