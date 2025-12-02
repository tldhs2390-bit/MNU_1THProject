<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 탑 메뉴 영역 등 include 파일 경로 확인 필요 -->
<%@ include file="/Include/topmenu.jsp" %>
<html>
<head>
<title>회원등록</title>
<style type="text/css">
/* CSS 주석 수정 및 스타일 정리 */
body { font-family: 돋움, Verdana; font-size: 9pt}
td   { font-family: 돋움, Verdana; font-size: 9pt; text-decoration: none; color: #000000; background-position: left top; background-repeat: no-repeat;}
.formbox {
	background-color: #F0F0F0; font-family: "Verdana", "Arial", "Helvetica", "돋움"; font-size:9pt
} 
</style>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script>
	$(function(){
		// 가입 버튼 클릭 이벤트
		$("#btn1").click(function(){
			// 이름
			if($("#user_name").val()==''){
				alert("이름을 입력하세요");
				$("#user_name").focus();
				return;
			}
			// 닉네임
			if($("#n_name").val()==''){
				alert("닉네임을 입력하세요");
				$("#n_name").focus();
				return;
			}
			// 아이디
			if($("#user_id").val()==''){
				alert("아이디를 입력하세요");
				$("#user_id").focus();
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
			$("#user").submit();		
		});

		$("#btn2").click(function(){
            if(confirm("회원가입을 취소하시겠습니까?")){
                history.back();
            }
        });
		
		// 아이디 중복 검사
		$("#user_id").keyup(function(){
			var user_id = $("#user_id").val();
			
			if(user_id.length < 5 || user_id.length > 16) {
				$("#userID_c").text("5~16자 이내로 입력해주세요.");
				return;
			}

			$.ajax({
				url:'user_idCheck.do',
				type:'post',
				data:{'user_id':user_id},
				success:function(result){
					if(result.trim() == "0"){ 
						$("#userID_c").text("사용 가능한 아이디입니다.").css("color", "blue");
					}else{
						$("#userID_c").text("중복된 아이디입니다.").css("color", "red");
					}
				},
				error: function() {
					console.log("아이디 체크 에러");
				}
			});
		});
		
		// 비밀번호 일치 확인 (실시간)
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
 
<table border="0" width="800">
<tr>
  <td width="20%"  bgcolor="#ecf1ef" valign="top" style="padding-left:0;">

	<%@ include file="/Include/login_form.jsp" %>
  </td>
  <td width="80%" valign="top">&nbsp;<br>    

	<form name="user" id="user" method="post" action="user_join.do">
	<table border=0 cellpadding=0 cellspacing=0 width=730 valign=top>
		<tr><td align=center><br>                            
			<table cellpadding=0 cellspacing=0 border=0 width=650 align=center>       
				<tr>
					<td bgcolor="#7AAAD5">            
						<table cellpadding=0 cellspacing=0 border=0 width=100%>
							<tr bgcolor=#7AAAD5>
								<td align=left border="0" hspace="0" vspace="0"></td>
								<td align=center bgcolor="#7AAAD5"><font color="#FFFFFF"><b>사용자등록&nbsp;</b><font color=black>(</font><font color=red>&nbsp;*&nbsp;</font><font color=black>표시항목은 반드시 입력하십시요.)</font></font></td>
								<td align=right border="0" hspace="0" vspace="0"></td>
							</tr>
						</table>
						<table cellpadding=3 cellspacing=1 border=0 width=100%>
							<tr>
								<td width=110 bgcolor=#EFF4F8>&nbsp;회원 성명<font color=red>&nbsp;*</font></td>
								<td bgcolor=WHITE>
									<input type=text id=user_name name=user_name size=16 maxlength=20 value="">&nbsp;성명은 빈칸없이 입력하세요.
								</td>
							</tr>
							<tr>
								<td width=110 bgcolor=#EFF4F8>&nbsp;닉네임<font color=red>&nbsp;*</font></td>
								<td bgcolor=WHITE>
									<input type=text id=n_name name=n_name size=16 maxlength=20 value="">
								</td>
							</tr>
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;회원 ID<font color=red>&nbsp;*</font></td>
								<td bgcolor=WHITE>
									<table cellspacing=0 cellpadding=0>
										<tr>
											<td align=absmiddle>
												<input type=text id=user_id name=user_id size=12 maxlength=16 style="width:120px">
											</td>
											<td id="userID_c" style="padding-left: 10px; font-size: 11px; color: gray;">
                   									&nbsp; 5~16자 이내의 영문이나 숫자만 가능합니다.
                  							</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;비밀번호<font color=red>&nbsp;*</font></td>
								<td bgcolor=WHITE>
								    <input type=password id=user_pass name=user_pass size=8 maxlength=12 style="width:120px">
									 &nbsp; 6~12자 이내의 영문이나 숫자만 가능합니다.
								</td>
							</tr>
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;비밀번호확인<font color=red>&nbsp;*</font></td>
								<td bgcolor=WHITE><input type=password id=user_repass name=user_repass size=8 maxlength=12 value="" style="width:120px">
								 <font id="repasswd_c" color="red" style="font-size: 11px;">&nbsp; 비밀번호 확인을 위해서 비밀번호를 한번 더 입력해주세요.</font> 
								</td>
							</tr>
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;전화번호<font color=red>&nbsp;*</font></td>
								<td bgcolor=WHITE>
									<input type=text id=tel name=tel size=13 maxlength=13 value="">&nbsp; "-" 포함
								</td>
							</tr>
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;E-mail</td>
								<td bgcolor=WHITE valign=middle>
									<input type="text" id=email name="email" maxlength="50">
									<input type="button" value="인증하기">
								</td>
							</tr>

							<tr>
								<td bgcolor="#EFF4F8">&nbsp;거주지</td>
								<td bgcolor=WHITE valign=middle>
								<input type="radio" name="address" value="아파트" checked>아파트
								<input type="radio" name="address" value="주택">주택
								</td>
							</tr>
							
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;등급</td>
								<td bgcolor=WHITE valign=middle>
								<select name="user_rank">
									<option>등급을 선택하세요</option>
									<option value="고수">고수</option>
									<option value="초심자">초심자</option>
									</select></td>
							</tr>
	<!-- 					
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;포인트</td>
								<td bgcolor=WHITE valign=middle>
								<input type="text" id=point name="point" maxlength="50"></td>
							</tr>
 	-->						
						</table>
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr bgcolor="#ffffff">
                                <td colspan="2" style="text-align: center; padding: 20px 0;">
									<input type="button" value="회원가입" id="btn1" class="btn-style btn-submit">
                                    &nbsp;&nbsp; <input type="button" value="가입취소" id="btn2" class="btn-style btn-cancel">
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