<%@ page contentType="text/html; charset=UTF-8" %>

<html>
 <head><title>Web Programming Test</title>
 <link rel="stylesheet" type="text/css" href="/css/main.css">

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
    background: #A5D6A7 !important; /* 연녹색 */
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

<div class="top-wrapper">

<table class="top-table">
    <tr>

        <!-- 연녹색 로고 / 로그인 블록 -->
        <td class="logo-area" style="width:20%;">
            <a href="admin_index.do" style="font-size:14pt; font-weight:bold; color:#1B5E20;">
    		🌿 FarmProject(관리자용)
		 	</a>
            <small>Total 136,489 | Now 178</small>
        </td>

        <!-- 메뉴들 -->
        <td><a href="/index.do">관리자 로그아웃</a></td>
        <td><a href="admin_user_list.do">회원 관리</a></td>
        <td><a href="admin_board_list.do">공지사항 관리</a></td>
        <td><a href="admin_guide_list.do">초심자가이드</a></td>
        <td><a href="admin_growth_list.do">성장 게시판 관리</a></td>
        

    </tr>
</table>

</div>