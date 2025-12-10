<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
<title>회원정보수정</title>

<style>
/* 전체 배경 */
body { 
    font-family: 'Noto Sans KR', 돋움, Verdana, sans-serif;
    background:#f5f5f5;
    margin:0;
    padding:0;
}

/* 화면 중앙 정렬 */
.signup-wrapper {
    width:100%;
    min-height:100vh;
    display:flex;
    justify-content:center;
    align-items:flex-start;
    padding-top:0px;
    box-sizing:border-box;
}

/* 중앙 흰 카드 */
.signup-box {
    width:1000px;
    background:#ffffff;
    border-radius:8px;
    padding:32px 32px 28px;
    box-shadow:0 4px 15px rgba(0,0,0,0.06);
    box-sizing:border-box;
}

/* 상단 제목 */
.signup-logo {
    text-align:center;
    font-size:24px;
    font-weight:700;
    color:#03c75a;
    margin-bottom:24px;
}

/* 각 입력 블록 */
.field {
    margin-bottom:16px;
}

/* 라벨 */
.field-label {
    font-size:13px;
    color:#333;
    margin-bottom:6px;
}

/* input-row */
.input-row {
    border:1px solid #dadada;
    border-radius:4px;
    padding:10px 12px;
    display:flex;
    align-items:center;
    box-sizing:border-box;
    background:#fff;
}
.input-row:focus-within {
    border-color:#03c75a;
}

.input-row input[type="text"],
.input-row input[type="password"],
.input-row input[type="tel"] {
    border:none;
    outline:none;
    width:100%;
    font-size:13px;
    padding:0;
    font-family:inherit;
    background:transparent;
}

/* helper text */
.helper-text {
    font-size:11px;
    color:#888;
    margin-top:4px;
}

/* 버튼 */
.btn-submit-main {
    width:100%;
    height:48px;
    border:none;
    border-radius:4px;
    background:#03c75a;
    color:#fff;
    font-size:16px;
    font-weight:600;
    cursor:pointer;
    margin-top:12px;
}
.btn-submit-main:hover {
    background:#02b258;
}

.cancel-link {
    margin-top:10px;
    text-align:center;
    font-size:12px;
    color:#666;
    cursor:pointer;
}
.cancel-link:hover {
    text-decoration:underline;
}

.btn-inline {
    padding:8px 14px;
    background:#e53935;
    color:white;
    border:none;
    border-radius:4px;
    cursor:pointer;
    font-size:13px;
    font-weight:600;
}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script>
$(function(){

    // 수정 확인
    $("#btn1").click(function(){
        if($("#n_name").val()==''){
            alert("닉네임을 입력하세요");
            $("#n_name").focus();
            return;
        }
        if($("#user_pass").val()==''){
            alert("비밀번호를 입력하세요");
            $("#user_pass").focus();
            return;
        }
        if($("#user_repass").val()==''){
            alert("비밀번호 확인을 입력하세요");
            $("#user_repass").focus();
            return;
        }
        if($("#user_pass").val() != $("#user_repass").val()){
            alert("비밀번호가 일치하지 않습니다.");
            $("#user_repass").focus();
            return;
        }
        if($("#tel").val()==''){
            alert("전화번호를 입력하세요");
            $("#tel").focus();
            return;
        }
        alert("회원정보수정 성공!");
        $("#user").submit();
    });

    // 취소
    $("#btn2").click(function(){
        if(confirm("수정을 취소하시겠습니까?")){
            history.back();
        }
    });

    // 비밀번호 일치 체크
    $("#user_repass").keyup(function(){
        var pass = $("#user_pass").val();
        var repass = $(this).val();
        
        if(pass == repass){
            $("#repasswd_c").text("비밀번호가 일치합니다.").css("color", "blue");
        } else {
            $("#repasswd_c").text("비밀번호가 일치하지 않습니다.").css("color", "red");
        }
    });

    // 회원탈퇴
    $("#btn3").click(function(){
        if(confirm("정말로 회원탈퇴하시겠습니까?\n모든 정보가 삭제됩니다.")){
            location.href = "<%=request.getContextPath()%>/User/user_delete.do?user_id=" + $("#user_id").val();
        }
    });

});
</script>

</head>
<body>
<!-- 왼쪽 로그인 배너 -->
<table border="0" width="100%">
	<tr>
  	 <td class="left-menu" width="20%" valign="top">
        <%@ include file="/Include/login_form.jsp" %>
    </td>
  <td width="80%" valign="top">&nbsp;<br>    
		<div class="signup-wrapper">
		    <div class="signup-box">

        <div class="signup-logo">회원정보수정</div>

        <form name="user" id="user" method="post" action="/User/user_modify.do">

            <input type="hidden" id="user_id" name="user_id" value="${dto.user_id}">

            <!-- 이름 -->
            <div class="field">
                <div class="field-label">회원 성명</div>
                <div class="input-row">
                    <input type="text" value="${dto.user_name}" readonly>
                </div>
                <div class="helper-text">변경할 수 없습니다</div>
            </div>

            <!-- 닉네임 -->
            <div class="field">
                <div class="field-label">닉네임</div>
                <div class="input-row">
                    <input type="text" id="n_name" name="n_name" value="${dto.n_name}">
                </div>
            </div>

            <!-- 아이디 -->
            <div class="field">
                <div class="field-label">아이디</div>
                <div class="input-row">
                    <input type="text" value="${dto.user_id}" readonly>
                </div>
                <div class="helper-text">변경할 수 없습니다</div>
            </div>

            <!-- 비밀번호 -->
            <div class="field">
                <div class="field-label">비밀번호 *</div>
                <div class="input-row">
                    <input type="password" id="user_pass" name="user_pass">
                </div>
            </div>

            <!-- 비밀번호 확인 -->
            <div class="field">
                <div class="field-label">비밀번호 확인 *</div>
                <div class="input-row">
                    <input type="password" id="user_repass" name="user_repass">
                </div>
                <div id="repasswd_c" class="helper-text">비밀번호를 다시 입력해주세요.</div>
            </div>

            <!-- 전화번호 -->
            <div class="field">
                <div class="field-label">전화번호</div>
                <div class="input-row">
                    <input type="text" id="tel" name="tel" value="${dto.tel}">
                </div>
            </div>

            <!-- 이메일 -->
            <div class="field">
                <div class="field-label">E-mail</div>
                <div class="input-row">
                    <input type="text" value="${dto.email}" readonly>
                </div>
                <div class="helper-text">변경할 수 없습니다</div>
            </div>

            <!-- 거주지 -->
            <div class="field">
                <div class="field-label">거주지</div>
                <div class="input-row" style="gap:10px;">
                    <label><input type="radio" name="address" value="아파트"
                        ${dto.address=='아파트'?'checked':''}> 아파트</label>
                    <label><input type="radio" name="address" value="주택"
                        ${dto.address=='주택'?'checked':''}> 주택</label>
                </div>
            </div>

            <button type="button" id="btn1" class="btn-submit-main">수정하기</button>

            <div class="cancel-link" id="btn2">수정 취소</div>

            <div style="text-align:right; margin-top:20px;">
                <button type="button" id="btn3" class="btn-inline">회원 탈퇴</button>
            </div>

        </form>

    </div>
</div>

</body>
</html>