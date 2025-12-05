<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    /* ★ 로그인 기능이 아직 완성되지 않았기 때문에
       좋아요 테스트가 가능하도록 임시 로그인 세션을 자동 생성합니다. */
    if (session.getAttribute("n_name") == null) {
        session.setAttribute("n_name", "초록이");   // ❤️ 임시 사용자 닉네임
    }
%>

<html>
<head>
    <title>Web Programming Test</title>
    <link rel="stylesheet" type="text/css" href="/css/main.css">
</head>

<style>
/* 전체 탑 메뉴 영역 */
.top-wrapper {
    width: 100%;
    background: #4CAF50; /* 상단 메뉴 메인 녹색 */
    color: white;
    margin: 0;
    padding: 0;
    font-family: 'Noto Sans KR';
}

/* 메뉴 테이블 */
.top-table {
    width: 100%;
    border-collapse: collapse;
}

.top-table td {
    text-align: center;
    padding: 10px 0;
    font-size: 11pt;
    font-weight: bold;
    cursor: pointer;
    transition: 0.2s;
}

/* 상단 메뉴 셀 */
.top-table td {
    background: #4CAF50;
}

/* hover */
.top-table td:hover {
    background: #43A047;
}

/* 메뉴 링크 */
.top-table a {
    color: white;
    text-decoration: none;
    display: block;
}

/* 로그인 영역(왼쪽) → 연한 녹색 */
.logo-area {
    background: #A5D6A7 !important;
    color: #1B5E20;
    padding: 15px 0;
}

/* 방문자 수 표시 */
.logo-area small {
    display: block;
    font-size: 9pt;
    color: #2E7D32;
}
</style>

<body>

<div class="top-wrapper">

<table class="top-table">
    <tr>

        <!-- 연녹색 로고 / 로그인 블록 -->
        <td class="logo-area" style="width:20%;">
            <a href="/index.do" style="font-size:14pt; font-weight:bold; color:#1B5E20;">🌿 FarmProject</a>
            <small>Total 136,489 | Now 178</small>
        </td>

        <!-- 로그인 전 메뉴 -->
        <c:if test="${empty user}">
            <td><a href="/User/user_login.do">로그인</a></td>
            <td><a href="/User/user_join.do">회원가입</a></td>
        </c:if>

        <!-- 로그인 후 메뉴 -->
        <c:if test="${!empty user}">
            <td><a href="/User/user_logout.do">로그아웃</a></td>
            <td><a href="/User/user_modify.do">정보수정</a></td>
        </c:if>

        <td><a href="/guide_list.do">초심자가이드</a></td>
        <td><a href="/Success/list.do">쑥쑥 성장이야기</a></td>
        <td><a href="/Fail/fail_list.do">아쉬운 성장이야기</a></td>
        <td><a href="/Board/board_list.jsp">자유게시판</a></td>
        <td><a href="/admin_login.do">관리자</a></td>

    </tr>
</table>

</div>

</body>
</html>