<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 탑 메뉴 영역 -->
<%@ include file="/Include/topmenu.jsp" %>
<html>
<head>
<title>회원 로그인</title>
<style type="text/css">
    body {
        font-family: 'Noto Sans KR', 돋움, sans-serif;
        background-color: #f5f5f5;
        margin: 0;
        padding: 0;
    }
    
    /* 화면 중앙 정렬을 위한 컨테이너 */
    .login-wrapper {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 80vh; /* 화면 높이의 80% */
    }

    /* 로그인 박스 스타일 */
    .login-box {
        background-color: white;
        width: 400px;
        padding: 40px;
        border-radius: 10px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        text-align: center;
    }

    .login-header {
        margin-bottom: 30px;
        color: #7AAAD5;
        font-size: 24px;
        font-weight: bold;
    }

    /* 입력창 스타일 */
    .input-group {
        margin-bottom: 15px;
        text-align: left;
    }
    
    .input-group label {
        display: block;
        margin-bottom: 5px;
        font-size: 14px;
        color: #666;
    }

    .input-field {
        width: 100%;
        padding: 12px;
        border: 1px solid #ddd;
        border-radius: 5px;
        box-sizing: border-box; /* 패딩 포함 너비 계산 */
        font-size: 14px;
    }

    /* 버튼 스타일 */
    .btn-login {
        width: 100%;
        padding: 13px;
        background-color: #7AAAD5;
        color: white;
        border: none;
        border-radius: 5px;
        font-size: 16px;
        font-weight: bold;
        cursor: pointer;
        margin-top: 10px;
        transition: background 0.3s;
    }

    .btn-login:hover {
        background-color: #5a8cb9;
    }

    /* 하단 링크 (아이디 찾기 등) */
    .login-footer {
        margin-top: 20px;
        font-size: 13px;
        color: #888;
    }
    
    .login-footer a {
        color: #666;
        text-decoration: none;
        margin: 0 10px;
    }
    
    .login-footer a:hover {
        text-decoration: underline;
    }
    
</style>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
	function loginCheck(){
		if(!user_login.user_id.value){
			alert("아이디를 입력하세요.");
			user_login.user_id.focus();
			return;
		}
		if(!user_login.user_pass.value){
			alert("비밀번호를 입력하세요.");
			user_login.user_pass.focus();
			return;
		}
		user_login.submit();
	}
</script>
</head>


<body style="background:url('<%= request.getContextPath() %>/img/garden_banner.png') 
             no-repeat center top; 
             background-size:cover;
             margin:0; padding:0;">

<div class="login-wrapper">
    <div class="login-box">

        <div class="login-header">
            LOGIN
        </div>
        
        <form name="user_login" id="user_login" method="post" action="<%= request.getContextPath() %>/user_login.do">
            <div class="input-group">
                <label for="user_id">아이디</label>
                <input type="text" id="user_id" name="user_id" class="input-field" placeholder="ID를 입력하세요" maxlength="20">
            </div>
            
            <div class="input-group">
                <label for="user_pass">비밀번호</label>
                <input type="password" id="user_pass" name="user_pass" class="input-field" placeholder="비밀번호를 입력하세요" maxlength="20">
            </div>
            
            <button type="button" id="btnLogin" class="btn-login" onclick="loginCheck()">로 그 인</button>
            
            <div class="login-footer"> 
			    <a href="<%= request.getContextPath() %>/User/user_find_id.do">아이디 찾기</a> |
			    <a href="<%= request.getContextPath() %>/User/user_find_pass.do">비밀번호 찾기</a> |
			    <a href="<%= request.getContextPath() %>/User/user_join_agree.jsp">회원가입</a>
			</div>
        </form>
    </div>
</div>

</body>
</html>