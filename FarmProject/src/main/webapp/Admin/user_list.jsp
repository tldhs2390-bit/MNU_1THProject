<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/Admin/Include/admin_topmenu.jsp" %>
<html>
<head>
<title>관리자페이지</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<!-- 이 페이지 전용 스타일만 적용되도록 격리 -->
<style type="text/css"> 
#userListPage body,
#userListPage td,
#userListPage tr,
#userListPage table {
    font-size:9pt; 
    font-family:tahoma;
    color:#666666;
    line-height:160%;
}

#userListPage a:link {color:#666666;text-decoration:none;} 
#userListPage a:visited {color:#666666;text-decoration:none;} 
#userListPage a:active {color:#666666;text-decoration:none;} 
#userListPage a:hover {color:#009900;text-decoration:underline;} 
</style> 
</head>

<body>
<div id="userListPage">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td align="center" height="100%" valign="middle"><br>

	<!-- 제목 박스 -->
	<table width="30%" border="1" cellspacing="0" cellpadding="3" bgcolor="#FFCC66"
	       bordercolor="#FFFFFF" bordercolorlight="#000000">
		<tr>
			<td height="40" align="center" style="font-size: 15px;">
				<b>회원정보리스트</b>
			</td>
		</tr>
	</table>
	<br>

	<!-- 메인 내용 -->
	<table width="80%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td height="20">

            <div style="display:flex; justify-content:space-between; align-items:center; width:100%;">
                <div>* 총 회원수 : <font color="red">${totcount}</font> 명</div>

                <form action="admin_user_list.do" method="get" style="margin:0;">
                    <input type="text" name="keyword" value="${keyword}"
                           placeholder="닉네임을 입력하세요"
                           style="width:200px; height:28px; padding:3px;">
                    <input type="submit" value="검색"
                           style="height:28px; padding:3px 10px;">
                </form>
            </div>
<br>
        </td>
    </tr>

	
		<tr>
			<td>
			<!-- 회원 리스트 테이블 -->
			<table width="100%" border="0" cellpadding="6" cellspacing="1" bgcolor="DDDDDD">

				<tr bgcolor="EcECEC">
					<td width="5%" align="center"><strong>번호</strong></td>
					<td width="10%" align="center"><strong>이름</strong></td>
					<td width="10%" align="center"><strong>닉네임</strong></td>
					<td width="10%" align="center"><strong>아이디</strong></td>
					<td width="15%" align="center"><strong>전화번호</strong></td>
					<td width="20%" align="center"><strong>이메일</strong></td>
					<td width="10%" align="center"><strong>포인트</strong></td>
					<td width="15%" align="center"><strong>관리</strong></td>
				</tr>

				<c:forEach var="uDto" items="${uList}" varStatus="st">
				<tr bgcolor="#FFFFFF">
					<td align="center">${st.index + 1}</td>
					<td align="center">${uDto.user_name}</td>
					<td align="center">${uDto.n_name}</td>
					<td align="center">${uDto.user_id}</td>
					<td align="center">${uDto.tel}</td>
					<td align="center">${uDto.email}</td>
					<td align="center">${uDto.point}</td>

					<td align="center">
						<form action="admin_user_modify.do" method="get" style="display:inline;">
							<input type="hidden" name="user_id" value="${uDto.user_id}">
							<input type="submit" value="수정">
						</form>

						<form action="admin_user_delete.do" method="get"
						      onsubmit="return confirm('정말 삭제할까요?');"
						      style="display:inline;">
							<input type="hidden" name="idx" value="${uDto.idx}">
							<input type="submit" value="삭제">
						</form>
					</td>
				</tr>
				</c:forEach>

				<tr>
					<td height="35" colspan="10" bgcolor="#FFFFFF"></td>
				</tr>

			</table>
			</td>
		</tr>

		

	</table>

</td>
</tr>
</table>
</div>
</body>
</html>