<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/Include/topmenu.jsp" %>
<html>
<head>
<title>회원가입 - 약관동의</title>

<style>
/* ======= 전체 레이아웃 네이버 스타일 ======= */
body { 
    font-family: 'Noto Sans KR', 돋움, Verdana, sans-serif;
    background:#F1F8E9;
    margin:0;
    padding:0;
}

.signup-wrapper {
    width:100%;
    min-height:100vh;
    display:flex;
    justify-content:center;
    align-items:flex-start;
    padding-top:60px;
    box-sizing:border-box;
}

.signup-box {
    width:700px;
    background:#ffffff;
    border-radius:8px;
    padding:32px 32px 28px;
    box-shadow:0 4px 15px rgba(0,0,0,0.06);
    box-sizing:border-box;
}

.signup-logo {
    text-align:center;
    font-size:24px;
    font-weight:700;
    color:#03c75a;
    margin-bottom:24px;
}

/* ======= 약관 박스 ======= */
.agree-title {
    font-size:15px;
    font-weight:bold;
    margin-bottom:8px;
}

.agree-box {
    width:100%;
    height:160px;
    overflow-y:scroll;
    padding:12px;
    border:1px solid #dadada;
    border-radius:4px;
    background:#fafafa;
    font-size:12px;
    line-height:1.5;
    margin-bottom:8px;
}

label {
    font-size:13px;
}

.btn-main {
    width:100%;
    height:48px;
    background:#03c75a;
    color:white;
    font-size:16px;
    font-weight:600;
    border:none;
    border-radius:4px;
    cursor:pointer;
    margin-top:20px;
}
.btn-main:disabled {
    background:#bebebe;
    cursor:not-allowed;
}
.btn-main:hover:not(:disabled) {
    background:#02b258;
}

.cancel-link {
    margin-top:12px;
    text-align:center;
    font-size:12px;
    color:#666;
    cursor:pointer;
}
.cancel-link:hover {
    text-decoration:underline;
}
</style>

<script>
function checkAgree() {
    let a1 = document.getElementById("agree1").checked;
    let a2 = document.getElementById("agree2").checked;
    let btn = document.getElementById("btnAgreeNext");

    btn.disabled = !(a1 && a2);
}

function goCancel() {
    if(confirm("회원가입을 취소하시겠습니까?")) history.back();
}
</script>

</head>
<body>

<div class="signup-wrapper">
    <div class="signup-box">

        <div class="signup-logo">회원가입 약관동의</div>

        <!-- 이용약관 -->
        <div class="agree-title">이용약관</div>
        <div class="agree-box">여러분을 환영합니다.
			저희서비스를 즐겨보세요.
			회원으로 가입하시면 서비스를 보다 편리하게 이용할 수 있습니다.
			여러분이 제공한 콘텐츠를 소중히 다룰 것입니다.
			여러분의 개인정보를 소중히 보호합니다.
			타인의 권리를 존중해 주세요.
			서비스 이용과 관련하여 몇 가지 주의사항이 있습니다.
			저희가 제공하는 다양한 포인트를 요긴하게 활용해 보세요.
			부득이 서비스 이용을 제한할 경우 합리적인 절차를 준수합니다.
			저희 잘못은 저희가 책임집니다.
			언제든지 저희 서비스 이용계약을 해지하실 수 있습니다.
			서비스 중단 또는 변경 시 꼭 알려드리겠습니다.
			주요 사항을 잘 안내하고 여러분의 소중한 의견에 귀 기울이겠습니다.
			여러분이 쉽게 알 수 있도록 약관 및 운영정책을 게시하며 사전 공지 후 개정합니다.<br><br>
        </div>
        <label><input type="checkbox" id="agree1" onclick="checkAgree()"> 이용약관에 동의합니다.</label>

        <br><br>

        <!-- 개인정보 수집 -->
        <div class="agree-title">개인정보 수집 및 이용동의</div>
        <div class="agree-box">
            개인정보보호법에 따라 회원가입 신청하시는 분께 수집하는 개인정보의 항목, 개인정보의 수집 및 이용목적, 개인정보의 보유 및 이용기간, 동의 거부권 및 동의 거부 시 불이익에 관한 사항을 안내 드리오니 자세히 읽은 후 동의하여 주시기 바랍니다.<br><br>
        </div>
        <label><input type="checkbox" id="agree2" onclick="checkAgree()"> 개인정보 수집·이용에 동의합니다.</label>

        <form action="/user_join.do" method="get">
            <button type="submit" id="btnAgreeNext" class="btn-main" disabled>
                동의하고 회원가입 진행
            </button>
        </form>

        <div class="cancel-link" onclick="goCancel()">가입 취소하고 이전으로 돌아가기</div>
    </div>
</div>

</body>
</html>