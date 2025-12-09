<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/Admin/Include/admin_topmenu.jsp" %>

<html>
<head>
<title>관리자페이지</title>

<style type="text/css"> 
/* 본문 영역에만 링크 스타일 적용 */
.content-area a:link { font-size:9pt; color:#666666; text-decoration:none; }
.content-area a:visited { font-size:9pt; color:#666666; text-decoration:none; }
.content-area a:active { font-size:9pt; color:#666666; text-decoration:none; }
.content-area a:hover { font-size:9pt; color:#009900; text-decoration:underline; }

/* ⚠ topmenu에 영향을 주던 기존 스타일 수정 */
.content-area td,
.content-area tr,
.content-area table,
.content-area {
    font-size:9pt;
    font-family:tahoma;
    color:#666666;
    line-height:160%;
}
</style>

</head>

<body>

<!-- 본문을 감싸는 영역 (topmenu와 스타일 분리) -->
<div class="content-area">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center" height="100%" valign="middle"><br>

			<table width="30%" border="1" cellspacing="0" cellpadding="3" bgcolor="#FFCC66" bordercolor="#FFFFFF" bordercolorlight="#000000">
				<tr> 
					<td height="40" align="center" style="font-size: 15px;">
						<b>회원정보리스트</b>
					</td>
				</tr>
			</table>

			<br>

			<table width="80%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="20">
						* 총 회원수 : <font color="red">${totcount}</font> 명
					</td>
				</tr>

				<tr>
					<td>
						<table width="100%" border="0" cellpadding="6" cellspacing="1" bgcolor="DDDDDD">
							<tr bgcolor="EcECEC">
								<td width="5%" align="center"><strong>번호</strong></td>
								<td width="10%" align="center"><strong>이름</strong></td>
								<td width="10%" align="center"><strong>닉네임</strong></td>
								<td width="10%" align="center"><strong>아이디</strong></td>
								<td width="15%" align="center"><strong>전화번호</strong></td>
								<td width="20%" align="center"><strong>이메일</strong></td>
								<td width="10%" align="center"><strong>등급</strong></td>
								<td width="10%" align="center"><strong>포인트</strong></td>
								<td width="15%" align="center"><strong>관리</strong></td>
							</tr>

							<c:forEach var="uDto" items="${uList}" varStatus="st">
								<tr>
									<td align="center" bgcolor="#FFFFFF">${st.index + 1}</td>
									<td align="center" bgcolor="#FFFFFF">${uDto.user_name}</td>
									<td align="center" bgcolor="#FFFFFF">${uDto.n_name}</td>
									<td align="center" bgcolor="#FFFFFF">${uDto.user_id}</td>
									<td align="center" bgcolor="#FFFFFF">${uDto.tel}</td>
									<td align="center" bgcolor="#FFFFFF">${uDto.email}</td>
									<td align="center" bgcolor="#FFFFFF">${uDto.user_rank}</td>
									<td align="center" bgcolor="#FFFFFF">${uDto.point}</td>

									<td align="center" bgcolor="#FFFFFF">
										<form action="admin_user_modify.do" method="get" style="display:inline;">
											<input type="hidden" name="user_id" value="${uDto.user_id}">
											<input type="submit" value="수정">
										</form>

										<form action="admin_user_delete.do" method="get"
											  onsubmit="return confirm('정말 삭제할까요?');" style="display:inline;">
											<input type="hidden" name="idx" value="${uDto.idx}">
											<input type="submit" value="삭제">
										</form>
									</td>
								</tr>
							</c:forEach>

							<tr>
								<td height="35" colspan="10" align="center" bgcolor="#FFFFFF"></td>
							</tr>

						</table>
					</td>
				</tr>
			</table>

		</td>
	</tr>
</table>

</div> <!-- content-area 끝 -->

</body>
</html>