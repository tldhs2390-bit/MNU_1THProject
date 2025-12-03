<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/Include/topmenu.jsp" %>
<html>
<head>
<title>회원등록</title>
<style>
/* 전체 배경을 아이디 찾기처럼 */
body { 
    font-family: 'Noto Sans KR', 돋움, Verdana, sans-serif;
    background:#f5f5f5;
    margin:0;
    padding:0;
}

/* 회원가입 전체 박스를 감싸는 부분 */
.join-container {
    width: 900px;
    margin: 40px auto;
    padding: 30px;
}

/* 아이디 찾기와 같은 카드형 박스 */
.join-box {
    background:white;
    width:100%;
    border-radius: 12px;
    padding: 30px 35px 40px 35px;
    box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}

/* 제목 바 */
.join-title {
    background:#7AAAD5;
    color:white;
    text-align:center;
    padding: 12px 0;
    border-radius: 8px;
    font-size:18px;
    font-weight:bold;
}

/* 테이블 스타일은 크게 건드리지 않고 색만 조금 개선 */
td.form-label {
    background:#f3f7fb;
    font-weight:bold;
    padding-left:18px;
    height:40px;
}

td.form-input {
    background:white;
    padding:10px 18px;
}

/* input 전체 공통 */
.join-box input[type="text"],
.join-box input[type="password"],
.join-box select {
    width:280px;
    height:32px;
    border:1px solid #ccc;
    border-radius:6px;
    padding-left:8px;
    font-size:12px;
    box-sizing:border-box;
}

/* 이메일 인증 버튼 */
#emailSendBtn,
#emailVerifyBtn {
    background:#7AAAD5;
    border:none;
    color:white;
    padding:5px 12px;
    border-radius:5px;
    cursor:pointer;
    font-size:12px;
}

/* 회원가입 / 취소 버튼 */
.btn-submit {
    background:#4CAF50 !important;
    color:white !important;
    border:none;
    border-radius:6px;
    width:120px;
    height:40px;
    font-weight:bold;
    cursor:pointer;
}
.btn-submit:hover { background:#43A047 !important; }

.btn-cancel {
    background:#ccc !important;
    color:black !important;
    border:none;
    border-radius:6px;
    width:120px;
    height:40px;
    font-weight:bold;
    cursor:pointer;
}
.btn-cancel:hover { background:#b5b5b5 !important; }

</style><script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

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
			alert("회원가입 성공!");
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
		
		// 이메일 인증코드 전송
		$("#emailSendBtn").click(function(){
		    let email = $("#email").val();

		    if(email.trim() == ""){
		        alert("이메일을 입력하세요");
		        return;
		    }

		    $.ajax({
		        url: "/User/emailSend.do",   // ★ 서블릿 호출
		        type: "get",
		        data: { email: email },
		        success: function(result){
		            if(result.trim() == "OK"){
		                alert("인증코드가 발송되었습니다.");
		            } else {
		                alert("메일 발송 실패! 서버 콘솔을 확인하세요.");
		            }
		        },
		        error: function(){
		            alert("서버 통신 오류");
		        }
		    });
		});
		
		// 인증코드 확인
		$("#emailVerifyBtn").click(function(){
		    let code = $("#emailCode").val();

		    $.ajax({
		        url: "/User/emailVerify.do",
		        type: "get",
		        data: { code: code },
		        success: function(result){
		            if(result.trim() == "OK"){
		                $("#emailMsg").text("이메일 인증 완료").css("color","blue");
		                $("#emailVerified").val("1"); // 필요하면 hidden값으로 사용
		            } else {
		                $("#emailMsg").text("인증코드가 틀립니다.").css("color","red");
		            }
		        }
		    });
		});
	});
	
	
	
</script>
</head>

<body bgcolor="#FFFFFF" leftmargin=0 topmargin=0 >
 
<!-- 왼쪽 로그인 배너 -->
<table border="0" width="100%">
	<tr>
  	<td width="20%" bgcolor="#ecf1ef" valign="top" style="padding:0; margin:0; height:auto;">

	<%@ include file="/Include/login_form.jsp" %>
  </td>
  <td valign="top" style="background:#f5f5f5; padding:0; width:80%;">
    
    <!-- join-box를 가운데로 정렬하고 높이를 제한하는 wrapper -->
   <div style="width:94%; margin:0;">
	<div class="join-box">
	<form name="user" id="user" method="post" action="user_join.do">
	<table border=0 cellpadding=0 cellspacing=0 width=800 valign=top>
		<tr><td align=center>                      
			<table cellpadding=0 cellspacing=0 border=0 width=100% align="center">       
				<tr>
					<td bgcolor="#7AAAD5">            
						<table cellpadding=0 cellspacing=0 border=0 width=100%>
							<tr bgcolor=#7AAAD5> 
								<td align=left border="0" hspace="0" vspace="0"></td>
								<td align=center bgcolor="#7AAAD5" style="padding: 8px 0;">
									<font color="#FFFFFF"><b>사용자등록&nbsp;</b><font color=black>(</font><font color=red>&nbsp;*&nbsp;</font><font color=black>표시항목은 반드시 입력하십시요.)</font></font>
								</td>
								<td align=right border="0" hspace="0" vspace="0"></td>
							</tr>
						</table>
						<table cellpadding=5 cellspacing=1 border=0 width=100%>
							<tr>
								<td width=200 bgcolor=#EFF4F8>&nbsp;회원 성명<font color=red>&nbsp;*</font></td>
								<td bgcolor=WHITE>
									<input type=text id=user_name name=user_name maxlength=20 value="" style="width:250px; height:30px; font-size:11px;">&nbsp;성명은 빈칸없이 입력하세요.
								</td>
							</tr>
							<tr>
								<td bgcolor=#EFF4F8>&nbsp;닉네임<font color=red>&nbsp;*</font></td>
								<td bgcolor=WHITE>
									<input type=text id=n_name name=n_name maxlength=20 value="" style="width:250px; height:30px; font-size:11px;">
								</td>
							</tr>
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;아이디<font color=red>&nbsp;*</font></td>
								<td bgcolor=WHITE>
									<table cellspacing=0 cellpadding=0>
										<tr>
											<td align=absmiddle>
												<input type=text id=user_id name=user_id maxlength=16 style="width:250px; height:30px; font-size:13px;">
											</td>
											<td id="userID_c" style="padding-left: 10px; font-size: 11px; color: gray;">
                   									&nbsp;5~16자 이내의 영문이나 숫자만 가능합니다.
                  							</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;비밀번호<font color=red>&nbsp;*</font></td>
								<td bgcolor=WHITE>
								    <input type=password id=user_pass name=user_pass maxlength=12 style="width:250px; height:30px; font-size:10px;">
									 &nbsp; 6~12자 이내의 영문이나 숫자만 가능합니다.
								</td>
							</tr>
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;비밀번호확인<font color=red>&nbsp;*</font></td>
								<td bgcolor=WHITE>
									<input type=password id=user_repass name=user_repass maxlength=12 value="" style="width:250px; height:30px; font-size:11px;">
									
                                    
									<font id="repasswd_c" color="red" style="font-size: 10px; margin-top: 5px; display: inline-block;">&nbsp; 비밀번호 확인을 위해서 비밀번호를 한번 더 입력해주세요.</font> 
								</td>
							</tr>
							<tr>
								<td bgcolor="#EFF4F8">&nbsp;전화번호<font color=red>&nbsp;*</font></td>
								<td bgcolor=WHITE>
									<input type=text id=tel name=tel maxlength=13 value="" style="width:250px; height:30px; font-size:11px;">&nbsp; "-" 포함
								</td>
							</tr>
							<tr>
							    <td bgcolor="#EFF4F8">&nbsp;E-mail</td>
							    <td bgcolor=WHITE valign=middle>
							        <input type="text" id="email" name="email" maxlength="50" 
							               style="width:250px; height:30px; font-size:11px;">
							        <input type="button" value="인증하기" id="emailSendBtn" 
							               style="height:30px; cursor:pointer;">
							        <br>
							        <input type="text" id="emailCode" placeholder="인증코드 입력" 
							               style="width:150px; height:30px; font-size:11px; margin-top:5px;">
							        <input type="button" value="확인" id="emailVerifyBtn" 
							               style="height:30px; cursor:pointer;">
							        <span id="emailMsg" style="margin-left:10px; font-size:11px;"></span>
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
										<option value="고수">고수</option>
										<option value="초심자">초심자</option>
									</select>
								</td>
							</tr>
						</table>
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr bgcolor="#ffffff">
                                <td colspan="2" style="text-align: center; padding: 30px 0;">
									<input type="button" value="회원가입" id="btn1" class="btn-style btn-submit" style="width:120px; height:40px; font-weight:bold; cursor:pointer; background:#4CAF50; color:white; border:none;">
                                    &nbsp;&nbsp; 
									<input type="button" value="가입취소" id="btn2" class="btn-style btn-cancel" style="width:120px; height:40px; font-weight:bold; cursor:pointer; background:#ccc; color:black; border:none;">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td></tr>
	</table>
	</form>
	</div>
	</div>
  </td>
</tr>
</table>
</body>
</html>