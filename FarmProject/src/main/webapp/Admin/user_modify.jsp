<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/Admin/Include/admin_topmenu.jsp" %>

<html>
<head>
<title>회원 정보 수정</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style>
/* ★★★ 방법 1: 스타일을 content-area 내부에만 적용 ★★★ */
.content-area body, 
.content-area td, 
.content-area tr, 
.content-area table {
    font-size:9pt; 
    font-family:tahoma; 
    color:#000;
    line-height:160%;
}

.content-area .inputBox {
    width: 90%;
    padding: 5px;
    border: 1px solid #ccc;
}

.content-area .selectBox { padding: 4px; }

.content-area .btn {
    padding: 6px 15px;
    background:#4CAF50;
    border:none;
    color:white;
    font-weight:bold;
    cursor:pointer;
}
</style>

</head>

<body>

<div class="content-area"> <!-- ★ 전체 화면을 감싸 topmenu와 CSS 분리 -->

<br><br>

<form action="admin_user_modify_pro.do" method="post">

<input type="hidden" name="user_id" value="${dto.user_id}">
<input type="hidden" name="n_name" value="${dto.n_name}">

<!-- 제목 박스 -->
<table width="30%" border="1" cellspacing="0" cellpadding="3"
       bgcolor="#FFCC66" bordercolor="#FFFFFF" bordercolorlight="#000000" align="center">
    <tr>
        <td height=40 align="center" style="font-size: 15px;"><b>회원 정보 수정</b></td>
    </tr>
</table>

<br>

<table width="50%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td>
            <table width="100%" border="0" cellpadding="6" cellspacing="1" bgcolor="DDDDDD">

                <tr bgcolor="#FFFFFF">
                    <td width="30%" bgcolor="#EcECEC" align="center"><strong>이름</strong></td>
                    <td>${dto.user_name}</td>
                </tr>

                <tr bgcolor="#FFFFFF">
                    <td bgcolor="#EcECEC" align="center"><strong>아이디</strong></td>
                    <td>${dto.user_id}</td>
                </tr>

                <tr bgcolor="#FFFFFF">
                    <td bgcolor="#EcECEC" align="center"><strong>닉네임</strong></td>
                    <td>${dto.n_name}</td>
                </tr>

                <tr bgcolor="#FFFFFF">
                    <td bgcolor="#EcECEC" align="center"><strong>포인트</strong></td>
                    <td><input type="text" name="point" value="${dto.point}" class="inputBox"></td>
                </tr>

                <tr bgcolor="#FFFFFF">
                    <td colspan="2" align="center">
                        <input type="submit" value="수정완료" class="btn">
                        &nbsp;&nbsp;
                        <input type="button" value="리스트로 돌아가기" class="btn"
                               onclick="location.href='/admin_user_list.do'">
                    </td>
                </tr>

            </table>
        </td>
    </tr>
</table>

</form>

</div> <!-- content-area 끝 -->

</body>
</html>