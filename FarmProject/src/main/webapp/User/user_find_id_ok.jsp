<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<title>아이디 찾기 결과</title>

<style>
    body { 
        font-family: 'Noto Sans KR', sans-serif;
        background:#f8f8f8;
        margin:0; 
        padding:0;
    }
    .find-box { 
        width: 400px; 
        margin: 80px auto; 
        padding: 40px; 
        border: 1px solid #ddd; 
        border-radius: 12px;
        background:white;
        box-shadow: 0 4px 12px rgba(0,0,0,0.08);
        text-align:center;
    }
    h2 { 
        color:#4A90E2; 
        margin-bottom:25px;
        font-size:24px;
        font-weight:bold;
    }
    .result-text { 
        font-size:16px; 
        margin:25px 0 30px 0;
        color:#333;
    }
    .btn {
        width:100%; 
        padding:12px; 
        background:#5A8CD9; 
        color:white;
        border:none; 
        border-radius:5px; 
        cursor:pointer;
        font-size:16px;
        font-weight:bold;
    }
    .btn:hover {
        background:#4A76C4;
    }
</style>
</head>
<body>
<div class="find-box">
    <h2>아이디 찾기</h2>
    <%
    String userId = (String)request.getAttribute("userId");

    if(userId == null) {
    %>
        <p class="result-text">해당 이메일로 가입된 아이디가 없습니다.</p>
    <% } else { %>
        <p class="result-text">
            회원님의 아이디는<br><br>
            <strong style="font-size:20px;"><%= userId %></strong> 입니다.
        </p>
    <% } %>

    <button class="btn" onclick="location.href='<%=request.getContextPath()%>user_login.do'">
        로그인 하러 가기
    </button>
</div>
</body>
</html>