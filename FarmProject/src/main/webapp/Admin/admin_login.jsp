<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자 로그인</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: "Noto Sans KR", sans-serif;
            background: linear-gradient(135deg, #d7f0d6, #c0e4ff);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-container {
            background: #ffffffee;
            width: 420px;
            padding: 40px 35px;
            border-radius: 20px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
            animation: fadeIn 0.8s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px);} 
            to { opacity: 1; transform: translateY(0);} 
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #2f5d1e;
            font-weight: 700;
        }

        .input-group {
            margin-bottom: 20px;
        }

        .input-group label {
            display: block;
            font-size: 14px;
            margin-bottom: 5px;
            color: #3a3a3a;
        }

        .input-group input {
            width: 100%;
            padding: 12px;
            border-radius: 10px;
            outline: none;
            border: 1px solid #b6c9b4;
            font-size: 15px;
            transition: 0.2s;
        }

        .input-group input:focus {
            border-color: #6e9d5f;
            box-shadow: 0 0 6px #bde3b5;
        }

        .login-btn {
            width: 100%;
            padding: 14px;
            background: #6e9d5f;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 12px;
            cursor: pointer;
            transition: 0.2s;
            font-weight: 600;
        }

        .login-btn:hover {
            background: #5a864e;
        }

        .info-text {
            text-align: center;
            margin-top: 15px;
            font-size: 14px;
            color: #4a4a4a;
        }

        .plant-icon {
            text-align: center;
            margin-bottom: 15px;
        }

        .plant-icon img {
            width: 60px;
            opacity: 0.9;
        }
    </style>
<script>
	function login_send(){
		if(!loginform.admin_id.value){
			alert("아이디를 입력하세요");
			loginform.admin_id.focus();
			return;
		}
		if(!loginform.admin_pw.value){
			alert("비밀번호를 입력하세요");
			loginform.admin_pw.focus();
			return;
		}
		loginform.submit();
		
	}    
    
    
</script>
</head>
<body>

    <div class="login-container">

        <div class="plant-icon">
            <img src="/Admin/img/leaf_icon.png" alt="icon">
        </div>

        <h2>관리자 로그인</h2>

        <form action="admin_login.do" method="post" name="loginform">

            <div class="input-group">
                <label for="admin_id">아이디</label>
                <input type="text" name="admin_id" id="admin_id" required>
            </div>

            <div class="input-group">
                <label for="admin_pw">비밀번호</label>
                <input type="password" name="admin_pw" id="admin_pw" required>
            </div>

             <button type="button" class="login-btn" onclick="login_send()">로그인</button>

        </form>

        <div class="info-text">
            홈페이지 관리 시스템<br>
            관리자 전용 페이지입니다.
        </div>

    </div>

</body>
</html>