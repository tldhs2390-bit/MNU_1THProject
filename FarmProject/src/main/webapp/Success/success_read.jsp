<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
<title>🌱 성장 기록 보기</title>
<link rel="stylesheet" type="text/css" href="/css/farm_board.css">
</head>

<body>

<table width="100%" border="0">
<tr>

<td width="20%" valign="top" bgcolor="#ecf1ef">
    <jsp:include page="/Include/login_form.jsp" />
</td>

<td width="80%" valign="top">

    <h2>${dto.subject}</h2>

    <p>닉네임 : ${dto.n_name}</p>
    <p>조회수 : ${dto.readcnt}</p>
    <p>좋아요 : ${dto.likes}</p>
    <p>등록일 : ${dto.regdate}</p>

    <hr>

    <div class="contents-box">
        ${dto.contents}
    </div>

    <br>

    <button onclick="location.href='/Success/good.do?idx=${dto.idx}'">❤️ 좋아요</button>
    <button onclick="location.href='/Success/modify.do?idx=${dto.idx}'">수정</button>
    <button onclick="location.href='/Success/delete.do?idx=${dto.idx}'">삭제</button>
    <button onclick="location.href='/Success/list.do'">목록</button>

</td>
</tr>
</table>

</body>
</html>
