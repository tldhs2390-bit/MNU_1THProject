<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<html>
<head>
<title>회원 정보 수정</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
body,td,tr,table{
    font-size:9pt; 
    font-family:tahoma; 
    color:#000;
    line-height:160%;
}
.inputBox {
    width: 90%;
    padding: 5px;
    border: 1px solid #ccc;
}
.selectBox { padding: 4px; }
.btn {
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

<jsp:include page="/Include/topmenu.jsp" />

<br><br>

<!-- 전체 form을 table 바깥으로 뺌 -->
<form action="/Admin/user_modify_pro.do" method="post">

<input type="hidden" name="user_id" value="${dto.user_id}">

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
                    <td bgcolor="#EcECEC" align="center"><strong>등급</strong></td>
                    <td>
                        <select name="user_rank" class="selectBox">
                            <option value="초심자" ${dto.user_rank=='초심자'?'selected':''}>초심자</option>
                            <option value="고수"   ${dto.user_rank=='고수'?'selected':''}>고수</option>
                        </select>
                    </td>
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
				               onclick="location.href='/Admin/user_list.do'">
				    </td>
				</tr>
                

            </table>
        </td>
    </tr>
</table>

</form>

</body>
</html>