<%@ page contentType="text/html; charset=UTF-8" %>

<!-- 메인 CSS -->
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/css/main.css">

<style>
/* 전체 탑 메뉴 영역 */
.top-wrapper {
    width: 100%;
    background: #4CAF50;
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
    padding: 15px 0;
    font-size: 12pt;
    font-weight: bold;
    cursor: pointer;
    transition: 0.2s;
}

/* 상단 메뉴 셀 색상 */
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

/* 로고 영역 */
.logo-area {
    background: #A5D6A7 !important;
    color: #1B5E20;
    padding: 15px 0;
}

/* 방문자 수 글씨 */
.logo-area small {
    display: block;
    font-size: 10pt;
    color: #2E7D32;
}
</style>


<!-- 메뉴 실제 출력 부분 -->
<div class="top-wrapper">

<table class="top-table">
    <tr>
        <!-- 로고 영역 -->
        <td class="logo-area" style="width:22%;">
            <a href="${pageContext.request.contextPath}/"
               style="font-size:15pt; font-weight:bold; color:#1B5E20;">
               🌿 FarmProject
            </a>
            <small>Total 136,489 | Now 178</small>
        </td>

        <!-- 메뉴 -->
        <td><a href="${pageContext.request.contextPath}/User/user_login.jsp">로그인</a></td>
        <td><a href="">로그아웃</a></td>
        <td><a href="${pageContext.request.contextPath}/User/user_join.jsp">회원가입</a></td>
        <td><a href="">정보수정</a></td>

        <td><a href="${pageContext.request.contextPath}/Guide/guide_list.jsp">초심자가이드</a></td>

        <td><a href="${pageContext.request.contextPath}/Success/list.do">쑥쑥 성장이야기</a></td>

        <td><a href="${pageContext.request.contextPath}/Fail/fail_list.jsp">아쉬운 성장이야기</a></td>
        <td><a href="${pageContext.request.contextPath}/Board/board_list.jsp">자유게시판</a></td>
        <td><a href="">관리자</a></td>
    </tr>
</table>

</div>