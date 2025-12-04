<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
<title>🌱 성장 기록 작성</title>
<link rel="stylesheet" type="text/css" href="/css/farm_board.css">
</head>

<body>

<table width="100%" border="0">
<tr>

<td width="20%" valign="top" bgcolor="#ecf1ef">
    <jsp:include page="/Include/login_form.jsp" />
</td>

<td width="80%" valign="top">

    <h2>🌿 성장 기록 작성</h2>

    <form method="post" action="/Success/write_pro.do">

        닉네임<br>
        <input type="text" name="n_name" size="40"><br><br>

        제목<br>
        <input type="text" name="subject" size="60"><br><br>

        내용<br>
        <textarea name="contents" rows="12" cols="60"></textarea><br><br>
		<input type="password" name="pass" required><br><br>
        <input type="submit" value="등록">
        <input type="button" value="목록" onclick="location.href='/Success/list.do'">

    </form>

</td>

</tr>
</table>

</body>
</html>