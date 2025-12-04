<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
<title>🌱 성장 기록 수정</title>
<link rel="stylesheet" type="text/css" href="/css/farm_board.css">
</head>

<body>

<table width="100%" border="0">
<tr>

<td width="20%" valign="top" bgcolor="#ecf1ef">
    <jsp:include page="/Include/login_form.jsp" />
</td>

<td width="80%" valign="top">

    <h2>🌿 성장 기록 수정</h2>

    <form method="post" action="/Success/modify_pro.do">

        <input type="hidden" name="idx" value="${dto.idx}">

        제목<br>
        <input type="text" name="subject" size="60" value="${dto.subject}"><br><br>

        내용<br>
        <textarea name="contents" rows="12" cols="60">${dto.contents}</textarea><br><br>

        <input type="submit" value="수정완료">
        <input type="button" value="취소"
               onclick="location.href='/Success/read.do?idx=${dto.idx}'">

    </form>

</td>

</tr>
</table>

</body>
</html>