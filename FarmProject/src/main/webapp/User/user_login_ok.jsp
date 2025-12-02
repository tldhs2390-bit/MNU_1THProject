<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${row==1 }">
		<script>
			alert("로그인 성공");
			location.href="${pageContext.request.contextPath}/index.do";
		</script>
	</c:if>
	<c:if test="${row==0 }">
		<script>
			alert("비밀번호가 일치하지 않습니다");
			history.back();
		</script>
	</c:if>
	<c:if test="${row==-1 }">
		<script>
			alert("등록된 아이디가 없습니다.");
			history.back();
		</script>
	</c:if>
</body>
</html>