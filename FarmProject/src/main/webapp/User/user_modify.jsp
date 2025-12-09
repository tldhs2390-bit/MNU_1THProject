<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/Include/topmenu.jsp" %>
<html>
<head>
<title>회원정보수정</title>
<link rel="stylesheet" type="text/css" href="/css/main.css">
<style type="text/css">
/* CSS 주석 수정 및 스타일 정리 */
body { font-family: 돋움, Verdana; font-size: 9pt}
td   { font-family: 돋움, Verdana; font-size: 9pt; text-decoration: none; color: #000000; background-position: left top; background-repeat: no-repeat;}
.formbox {
	background-color: #F0F0F0; font-family: "Verdana", "Arial", "Helvetica", "돋움"; font-size:9pt
} 
.form-table { 
    width: 100%; 
    border-collapse: separate; 
    border-spacing: 1px; 
    background-color: #cfcfcf; 
}
.form-label { 
    background-color: #EFF4F8; 
    width: 150px; /* 라벨 너비 고정 */
    padding-left: 15px; 
    font-weight: bold; 
    height: 40px; /* 행 높이 조금 여유있게 */
}
.form-input { 
    background-color: #FFFFFF; 
    padding-left: 10px; 
}
.input-field { 
    height: 28px; 
    border: 1px solid #ccc; 
    padding-left: 5px; 
    font-size: 12px;
}
</style>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script>
	$(function(){

		$("#btn1").click(function(){
			// 닉네임
			if($("#n_name").val()==''){
				alert("닉네임을 입력하세요");
				$("#n_name").focus();
				return;
			}
			// 비밀번호
			if($("#user_pass").val()==''){
				alert("비밀번호를 입력하세요");
				$("#user_pass").focus();
				return;
			}
			// 비밀번호 확인
			if($("#user_repass").val()==''){
				alert("비밀번호 확인을 입력하세요");
				$("#user_repass").focus();
				return;
			}
			// 비밀번호 일치 여부 최종 확인
			if($("#user_pass").val() != $("#user_repass").val()){
				alert("비밀번호가 일치하지 않습니다.");
				$("#user_repass").focus();
				return;
			}
			// 전화번호
			if($("#tel").val()==''){
				alert("전화번호를 입력하세요");
				$("#tel").focus();
				return;
			}
			
			// 전송
			alert("회원정보수정 성공!");
			$("#user").submit();		
		});

		$("#btn2").click(function(){
            if(confirm("수정을 취소하시겠습니까?")){
                history.back();
            }
        });

		// 비밀번호 일치
		$("#user_repass").keyup(function(){
			var pass = $("#user_pass").val();
			var repass = $(this).val();
			
			if(pass == repass){
				$("#repasswd_c").text("비밀번호가 일치합니다.").css("color", "blue");
			}else{
				$("#repasswd_c").text("비밀번호가 일치하지 않습니다.").css("color", "red");
			}
		});
	});
</script>
</head>

<body bgcolor="#FFFFFF" leftmargin=0 topmargin=0 >
 
<!-- 왼쪽 로그인 배너 -->
<table border="0" width="100%">
	<tr>
  	 <td class="left-menu" width="20%" valign="top">
        <%@ include file="/Include/login_form.jsp" %>
    </td>
  <td width="80%" valign="top">&nbsp;<br>    

	<form name="user" id="user" method="post" action="/User/user_modify.do">
	<input type="hidden" name="user_id" value="${dto.user_id}">
	<table border=0 cellpadding=0 cellspacing=0 width=800 valign=top>
		<tr><td align=center><br>                            
			<table cellpadding=0 cellspacing=0 border=0 width=100% align="center">       
				<tr>
					<td bgcolor="#7AAAD5">            
						<table cellpadding=0 cellspacing=0 border=0 width=100%>
							<tr bgcolor=#7AAAD5>
								<td align=left border="0" hspace="0" vspace="0"></td>
								<td align=center bgcolor="#7AAAD5" style="padding: 8px 0;">
									<font color="#FFFFFF"><b>회원정보수정&nbsp;</b><font color=black></font>(<font color=red>&nbsp;*&nbsp;</font>은 필수항목 입니다.)<font color=black></font></font>
								</td>
								<td align=right border="0" hspace="0" vspace="0"></td>
							</tr>
						</table>
						<table cellpadding=5 cellspacing=1 border=0 width=100%>
							<tr>
								<td width=200 bgcolor=#EFF4F8>&nbsp;회원 성명</td>
								<td bgcolor=WHITE>
									<input type=text id=user_name name=user_name maxlength=20 readonly value="${dto.user_name }" style="width:300px; height:30px; font-size:12px;">&nbsp;변경 할 수 없습니다.
								</td>
							</tr>
							<tr>
								<td bgcolor=#EFF4F8>&nbsp;닉네임</td>
								<td bgcolor=WHITE>
									<input type=text id=n_name name=n_name maxlength=20 value="${dto.n_name }" style="width:300px; height:30px; font-size:12px;">
								</td>
							</tr>
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;아이디</td>
								<td bgcolor=WHITE>
									<table cellspacing=0 cellpadding=0>
										<tr>
											<td align=absmiddle>
												<input type=text id=user_id name=user_id maxlength=16 style="width:300px; height:30px; font-size:12px;" readonly value="${dto.user_id }">
											</td>
											<td id="userID_c" style="padding-left: 10px; font-size: 11px; color: black;">
                   									&nbsp;변경할 수 없습니다.
                  							</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;비밀번호<font color=red>&nbsp;*</font></td>
								<td bgcolor=WHITE>
								    <input type=password id=user_pass name=user_pass maxlength=12 style="width:300px; height:30px; font-size:12px;">
									 &nbsp; 6~12자 이내의 영문이나 숫자만 가능합니다.
								</td>
							</tr>
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;비밀번호확인<font color=red>&nbsp;*</font></td>
								<td bgcolor=WHITE>
									<input type=password id=user_repass name=user_repass maxlength=12 value="" style="width:300px; height:30px; font-size:12px;">
									
                                    
									<font id="repasswd_c" color="red" style="font-size: 11px; margin-top: 5px; display: inline-block;">&nbsp; 비밀번호 확인을 위해서 비밀번호를 한번 더 입력해주세요.</font> 
								</td>
							</tr>
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;전화번호</td>
								<td bgcolor=WHITE>
									<input type=text id=tel name=tel maxlength=13 value="${dto.tel }" style="width:300px; height:30px; font-size:12px;">&nbsp; "-" 포함
								</td>
							</tr>
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;E-mail</td>
								<td bgcolor=WHITE valign=middle>
									<input type="text" id=email name="email" maxlength="50" style="width:300px; height:30px; font-size:12px;" readonly value="${dto.email }">변경할 수 없습니다.

								</td>
							</tr>

							<tr>
								<td bgcolor="#EFF4F8">&nbsp;거주지</td>
								<td bgcolor=WHITE valign=middle style="height:40px;">
									<input type="radio" name="address" value="아파트" checked>아파트
									&nbsp;&nbsp;&nbsp;
									<input type="radio" name="address" value="주택">주택
								</td>
							</tr>
							
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;등급</td>
								<td bgcolor=WHITE valign=middle>
									<select name="user_rank" style="width:150px; height:30px;">
										<option>등급을 선택하세요</option>
										<option value="고수" ${dto.user_rank == '고수' ? 'selected' : '' }>고수</option>
										<option value="초심자" ${dto.user_rank == '초심자' ? 'selected' : '' }>초심자</option>
									</select>
								</td>
							</tr>
						</table>
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr bgcolor="#ffffff">
                                <td colspan="2" style="text-align: center; padding: 30px 0;">
									<input type="button" value="수정 확인" id="btn1" class="btn-style btn-submit" style="width:120px; height:40px; font-weight:bold; cursor:pointer; background:#4CAF50; color:white; border:none;">
                                    &nbsp;&nbsp; 
									<input type="button" value="수정 취소" id="btn2" class="btn-style btn-cancel" style="width:120px; height:40px; font-weight:bold; cursor:pointer; background:#ccc; color:black; border:none;">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td></tr>
	</table>
	</form>
  </td>
</tr>
</table>
</body>
</html>