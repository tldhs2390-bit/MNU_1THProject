<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>게시글 삭제</title>
    <script>
        // 비밀번호 틀림 메시지 출력
        window.onload = function() {
            var msg = "${msg}";
            if(msg != null && msg != "") {
                alert(msg);
            }
        }
    </script>
</head>
<body>
<h3>게시글 삭제</h3>
<form method="post" action="admin_board_delete.do">
    <input type="hidden" name="idx" value="${idx}">
    <input type="hidden" name="page" value="${page}">
    <label>비밀번호: <input type="password" name="pass" required></label>
    <button type="submit">삭제</button>
</form>
</body>
</html>