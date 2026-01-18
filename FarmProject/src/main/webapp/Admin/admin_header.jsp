<%@ page contentType="text/html; charset=UTF-8" %>

<style>
/* 관리자 공통 헤더 스타일 */
.admin-header-bar {
    width:100%;
    background:#2e7d32;
    padding:12px 20px;
    display:flex;
    align-items:center;
    box-sizing:border-box;
    gap:10px;
}

.admin-header-bar button {
    padding:8px 14px;
    background:white;
    color:#2e7d32;
    border-radius:6px;
    font-weight:bold;
    border:none;
    cursor:pointer;
}

.admin-header-title {
    color:white;
    font-size:18px;
    font-weight:bold;
    margin-left:auto;
    margin-right:10px;
}
</style>

<div class="admin-header-bar">

    <button onclick="history.back()">← 이전 페이지</button>

    <button onclick="location.href='/admin_Index.do'">🏠 관리자 홈</button>

    <div class="admin-header-title">관리자 페이지</div>

</div>