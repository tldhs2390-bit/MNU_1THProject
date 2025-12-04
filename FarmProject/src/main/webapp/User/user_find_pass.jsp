<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<title>비밀번호 찾기</title>
<style>
    body { font-family: 'Noto Sans KR', sans-serif; }
    .find-box { width: 400px; margin: 80px auto; padding: 30px; border: 1px solid #ddd; border-radius: 10px; }
    h2 { text-align:center; color:#4A90E2; }
    .input-field { width:100%; padding:12px; margin-top:10px; border:1px solid #ccc; border-radius:5px; }
    .btn { width:100%; padding:12px; background:#4A90E2; color:white; border:none; border-radius:5px; margin-top:15px; cursor:pointer; font-weight:bold; }
	.btn-back {width:100%; padding:12px; background:#cccccc; color:#333; border:none; border-radius:5px; margin-top:10px; cursor:pointer; font-weight:bold;}
</style>
<script>
	function checkFindPass(){
		if(!findpass.user_id.value){
			alert("아이디를 입력하세요.");
			findpass.user_id.focus();
			return;
		}
		if(!findpass.email.value){
			alert("이메일을 입력하세요.");
			findpass.email.focus();
			return;
		}
		findpass.submit();
	}
</script>
</head>

<body>
<div class="find-box">
    <h2>비밀번호 찾기</h2>

    <form name="findpass" method="post" action="<%=request.getContextPath()%>/User/user_find_pass.do">
        <input type="text" name="user_id" class="input-field" placeholder="아이디 입력">
        <input type="text" name="email" class="input-field" placeholder="가입한 이메일 입력">

        <button type="button" class="btn" onclick="checkFindPass()">비밀번호 찾기</button>
        <button type="button" class="btn-back"
        onclick="location.href='<%=request.getContextPath()%>/User/user_login.do'">로그인 페이지로 이동</button>
    </form>
</div>
</body>
</html>