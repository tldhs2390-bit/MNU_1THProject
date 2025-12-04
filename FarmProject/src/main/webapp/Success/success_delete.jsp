<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/Include/topmenu.jsp" %>

<link rel="stylesheet" href="/css/farm_board.css">

<table width="100%">
<tr>

<!-- 왼쪽 로그인 -->
<td width="20%" valign="top" bgcolor="#ecf1ef">
    <jsp:include page="/Include/login_form.jsp" />
</td>

<td width="80%" valign="top">

<div class="board-container">

    <h2 class="board-title">🗑 글 삭제</h2>

    <c:if test="${param.err == 1}">
        <p class="error-msg">❌ 비밀번호가 틀렸습니다.</p>
    </c:if>

    <form action="/Success/delete.do" method="post" class="farm-form">

        <input type="hidden" name="idx" value="${param.idx}">

        <label>비밀번호를 입력하세요</label>
        <input type="password" name="pass" required>

        <div class="board-btn-area">
            <button type="submit" class="btn-red">삭제하기</button>
            <button type="button" class="btn-gray"
                onclick="location.href='/Success/read.do?idx=${param.idx}'">취소</button>
        </div>

    </form>

</div>

</td>
</tr>
</table>