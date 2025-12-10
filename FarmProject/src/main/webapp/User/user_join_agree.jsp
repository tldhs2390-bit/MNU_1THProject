<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/Include/topmenu.jsp" %>
<html>
<head>
<title>회원등록</title>
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
    padding-top:60px;
    box-sizing:border-box;
}

/* 중앙 흰 카드 */
.signup-box {
    width:870px;
    background:#ffffff;
    border-radius:8px;
    padding:32px 32px 28px;
    box-shadow:0 4px 15px rgba(0,0,0,0.06);
    box-sizing:border-box;
}

/* 상단 제목(원하면 사이트명으로 변경) */
.signup-logo {
    text-align:center;
    font-size:24px;
    font-weight:700;
    color:#03c75a;   /* 네이버 느낌 색 */
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

/* 네이버처럼 테두리 박스 안에 input */
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

/* 일반 텍스트/패스워드 input */
.input-row input[type="text"],
.input-row input[type="password"] {
    border:none;
    outline:none;
    width:100%;
    font-size:13px;
    padding:0;
    font-family:inherit;
    background:transparent;
}

/* 보조 설명 텍스트 */
.helper-text {
    font-size:11px;
    color:#888;
    margin-top:4px;
}

/* 검증용 메시지 스타일 (아이디/비밀번호/이메일 등) */
#userID_c,
#repasswd_c,
#emailMsg {
    font-size:11px;
}

/* 비밀번호 안내 기본 색 */
#repasswd_c {
    color:#888;
}

/* 성별/주거형태 같은 버튼 그룹(간단 버전) */
.inline-options {
    display:flex;
    gap:8px;
}

.inline-options label {
    font-size:13px;
}

/* 등급 select */
.select-basic {
    width:100%;
    border:1px solid #dadada;
    border-radius:4px;
    padding:10px 12px;
    font-size:13px;
    box-sizing:border-box;
}

/* 이메일 인증 버튼 */
.btn-inline, .btn-email {
    min-width: 80px;
    height: 28px;
    padding: 0 16px;
    background: #03c75a;
    color: white;
    border: none;
    font-size: 13px;
    font-weight: 600;
    border-radius: 4px;
    cursor: pointer;
}

/* 아래쪽 큰 버튼 */
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

/* 취소 버튼은 텍스트 링크처럼 처리 */
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
.input-row input[type="text"],
.input-row input[type="password"] {
    height: 25px;      /* 버튼 높이와 동일하게 */
    line-height: 25px;
}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script>
$(function(){

    $("#btnMainSubmit").click(function(){
        if($("#user_name").val()==''){ alert("이름을 입력하세요"); $("#user_name").focus(); return; }
        if($("#n_name").val()==''){ alert("닉네임을 입력하세요"); $("#n_name").focus(); return; }
        if($("#user_id").val()==''){ alert("아이디를 입력하세요"); $("#user_id").focus(); return; }
        if($("#user_pass").val()==''){ alert("비밀번호를 입력하세요"); $("#user_pass").focus(); return; }
        if($("#user_repass").val()==''){ alert("비밀번호 확인을 입력하세요"); $("#user_repass").focus(); return; }
        if($("#user_pass").val() != $("#user_repass").val()){ 
            alert("비밀번호가 일치하지 않습니다."); 
            $("#user_repass").focus(); 
            return; 
        }
        if($("#tel").val()==''){ alert("전화번호를 입력하세요"); $("#tel").focus(); return; }

        alert("회원가입 성공!");
        $("#user").submit();  
    });

    $("#btnCancel").click(function(){
        if(confirm("회원가입을 취소하시겠습니까?")){
            history.back();
        }
    });

    // 아이디 중복 검사
    $("#user_id").keyup(function(){
        var user_id = $("#user_id").val();

        if(user_id.length < 5 || user_id.length > 16) {
            $("#userID_c").text("5~16자 이내로 입력해주세요.").css("color","#888");
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
            }
        });
    });

    // 비밀번호 일치 검사
    $("#user_repass").keyup(function(){
        var pass = $("#user_pass").val();
        var repass = $(this).val();
        
        if(pass == repass){
            $("#repasswd_c").text("비밀번호가 일치합니다.").css("color", "blue");
        } else {
            $("#repasswd_c").text("비밀번호가 일치하지 않습니다.").css("color", "red");
        }
    });

    // 이메일 인증
    $("#emailSendBtn").click(function(){
        let email = $("#email").val();
        if(email.trim() == ""){ alert("이메일을 입력하세요"); return; }

        $.ajax({
            url: "/User/emailSend.do",
            type: "get",
            data: { email: email },
            success: function(result){
                if(result.trim() == "OK"){ alert("인증코드가 발송되었습니다."); }
                else { alert("메일 발송 실패"); }
            }
        });
    });

    $("#emailVerifyBtn").click(function(){
        let code = $("#emailCode").val();

        $.ajax({
            url: "/User/emailVerify.do",
            type: "get",
            data: { code: code },
            success: function(result){
                if(result.trim() == "OK"){
                    $("#emailMsg").text("이메일 인증 완료").css("color","blue");
                } else {
                    $("#emailMsg").text("인증코드가 틀립니다.").css("color","red");
                }
            }
        });
    });

 // 닉네임 중복 검사
    $("#n_name").keyup(function(){
        var n_name = $("#n_name").val();


        $.ajax({
        	url: "<%=request.getContextPath()%>/User/user_nickCheck.do",
            type:'post',
            data:{'n_name':n_name},
            success:function(result){
                if(result.trim() == "0"){ 
                    $("#nick_c").text("사용 가능한 닉네임입니다.").css("color", "blue");
                }else{
                    $("#nick_c").text("중복된 닉네임입니다.").css("color", "red");
                }
            }
        });
    });
});
</script>
</head>

<body>

<div class="signup-wrapper">
    <div class="signup-box">
        <!-- 네이버 로고 자리 느낌 -->
        <div class="signup-logo">회원가입</div>

        <form name="user" id="user" method="post" action="user_join.do">

            <div class="field">
                <div class="field-label">아이디</div>
                <div class="input-row">
                    <input type="text" id="user_id" name="user_id" maxlength="16" placeholder="아이디를 입력하세요">
                </div>
                <div id="userID_c" class="helper-text">5~16자 이내의 영문 또는 숫자</div>
            </div>

            <div class="field">
                <div class="field-label">비밀번호</div>
                <div class="input-row">
                    <input type="password" id="user_pass" name="user_pass" maxlength="12" placeholder="비밀번호를 입력하세요">
                </div>
            </div>

            <div class="field">
                <div class="field-label">비밀번호 확인</div>
                <div class="input-row">
                    <input type="password" id="user_repass" name="user_repass" maxlength="12" placeholder="비밀번호를 다시 입력하세요">
                </div>
                <div id="repasswd_c" class="helper-text">비밀번호를 한 번 더 입력해주세요.</div>
            </div>

            <div class="field">
                <div class="field-label">이름</div>
                <div class="input-row">
                    <input type="text" id="user_name" name="user_name" maxlength="20" placeholder="이름을 입력하세요">
                </div>
            </div>

            <div class="field">
                <div class="field-label">닉네임</div>
                <div class="input-row">
                    <input type="text" id="n_name" name="n_name" maxlength="20" placeholder="닉네임을 입력하세요">
                </div>
            	<div id="nick_c" class="helper-text">닉네임을 입력하세요</div>
            </div>

            <div class="field">
                <div class="field-label">휴대전화</div>
                <div class="input-row">
                    <input type="text" id="tel" name="tel" maxlength="13" placeholder="하이픈(-) 포함하여 입력">
                </div>
            </div>

            <div class="field">
                <div class="field-label">이메일</div>
                <div class="input-row">
                    <input type="text" id="email" name="email" placeholder="이메일 주소 입력">
                    <button type="button" id="emailSendBtn" class="btn-inline">인증하기</button>
                </div>
                <div style="margin-top:6px;" class="input-row">
                    <input type="text" id="emailCode" placeholder="인증코드 입력">
                    <button type="button" id="emailVerifyBtn" class="btn-inline">확인</button>
                </div>
                <div id="emailMsg" class="helper-text"></div>
            </div>

            <div class="field">
                <div class="field-label">주거 형태</div>
                <div class="input-row">
                    <div class="inline-options">
                        <label><input type="radio" name="address" value="아파트" checked> 아파트</label>
                        <label><input type="radio" name="address" value="주택"> 주택</label>
                    </div>
                </div>
            </div>


            <!-- 제출 버튼 -->
            <button type="button" id="btnMainSubmit" class="btn-submit-main">가입하기</button>

            <div class="cancel-link" id="btnCancel">가입 취소</div>

        </form>
    </div>
</div>

</body>
</html>