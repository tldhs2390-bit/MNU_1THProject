<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
<title>🌱 쑥쑥 성장 이야기</title>
<link rel="stylesheet" type="text/css" href="/css/farm_board.css">

<style>
    .search-box {
        background: #e9f5df;
        border: 1px solid #c5e3b1;
        padding: 15px;
        border-radius: 12px;
        display: flex;
        align-items: center;
        gap: 10px;
        width: 80%;
        margin: 0 auto 20px auto;
    }
    .search-box select,
    .search-box input[type="text"] {
        padding: 7px;
        border-radius: 6px;
        border: 1px solid #a4c48c;
        font-size: 14px;
    }
    .search-btn {
        background: #4CAF50;
        color: white;
        padding: 8px 16px;
        border-radius: 6px;
        border: none;
        cursor: pointer;
        font-weight: bold;
    }
    .write-btn {
        background: #2e7d32;
        color: white;
        padding: 8px 16px;
        border-radius: 6px;
        border: none;
        cursor: pointer;
        font-weight: bold;
        margin-left: auto;
    }
    .list-top-space {
        margin-top: 40px;
    }
    .nodata {
        text-align: center;
        padding: 40px 0;
        font-size: 18px;
        color: #555;
    }
</style>
</head>

<body>

<table width="100%" border="0">
<tr>

<!-- 왼쪽 로그인 -->
<td width="20%" valign="top" bgcolor="#ecf1ef">
    <jsp:include page="/Include/login_form.jsp" />
</td>

<!-- 오른쪽 본문 -->
<td width="80%" valign="top">

    <h2 style="text-align:center; margin-top:15px;">🌿 쑥쑥 성장 이야기</h2>

    <!-- 검색 박스 -->
    <div class="search-box">

        <form method="get" action="/Success/list.do" style="display:flex; gap:10px;">

            <select name="search">
                <option value="subject" <c:if test="${search eq 'subject'}">selected</c:if>>제목</option>
                <option value="n_name" <c:if test="${search eq 'n_name'}">selected</c:if>>닉네임</option>
            </select>

            <input type="text" name="key" value="${key}" placeholder="검색어 입력">

            <button type="submit" class="search-btn">검색</button>
        </form>

        <button class="write-btn" onclick="location.href='/Success/write.do'">글쓰기</button>

    </div>

    <div class="list-top-space"></div>

    <!-- 목록 테이블 -->
    <table class="farm-table">
        <tr>
            <th width="10%">번호</th>
            <th width="40%">제목</th>
            <th width="15%">닉네임</th>
            <th width="10%">조회수</th>
            <th width="10%">좋아요</th>
            <th width="15%">등록일</th>
        </tr>

        <!-- 데이터 없을 때 -->
        <c:if test="${empty list}">
            <tr><td colspan="6" class="nodata">등록된 게시물이 없습니다.</td></tr>
        </c:if>

        <!-- 데이터 있을 때 -->
        <c:forEach var="dto" items="${list}">
            <tr onclick="location.href='/Success/read.do?idx=${dto.idx}'" style="cursor:pointer;">
                <td>${dto.idx}</td>
                <td>${dto.subject}</td>
                <td>${dto.n_name}</td>
                <td>${dto.readcnt}</td>
                <td>${dto.likes}</td>
                <td>${dto.regdate}</td>
            </tr>
        </c:forEach>

    </table>

</td>
</tr>
</table>

</body>
</html>