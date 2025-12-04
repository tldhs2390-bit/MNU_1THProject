<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
    /* modify.jsp 왼쪽 20% 영역에 정확히 맞는 크기 */
    .side-login-box {
        width: 100%;               /* td(20%) 안에서 꽉 차도록 */
        padding: 15px;
        background: #ffffff;
        border: 1px solid #cfcfcf;
        border-radius: 8px;
        font-family: 돋움, Verdana;
        font-size: 11pt;
        box-sizing: border-box;    /* padding·border 포함해서 100% 유지 */
    }

    .side-login-title {
        font-weight: bold;
        color: #4CAF50;
        font-size: 13pt;
        margin-bottom: 15px;
        text-align: center;
    }

    .side-login-box input[type=text],
    .side-login-box input[type=password] {
        width: 100%;
        height: 28px;
        margin-bottom: 8px;
        border: 1px solid #aaa;
        border-radius: 4px;
        padding-left: 5px;
        box-sizing: border-box;
        font-size: 10pt;
    }

    .side-login-btn {
        width: 100%;
        height: 32px;
        background: #4CAF50;
        border: none;
        color: white;
        border-radius: 4px;
        cursor: pointer;
        font-weight: bold;
        margin-top: 5px;
    }

    .side-login-btn:hover {
        background: #43A047;
    }

    .side-login-box a {
        color: #4CAF50;
        font-size: 11pt;
        text-decoration: none;
    }

    .side-login-box p {
        margin: 11px 0;
        text-align: center;
    }
</style>

<!-- 로그인 안 된 상태 -->
<c:if test="${empty user}">
    <div class="side-login-box">
        <h3 class="side-login-title">Member Login</h3>

        <form method="post" action="/User/user_login.do">

            <input type="text" name="user_id" placeholder="아이디">

            <input type="password" name="user_pass" placeholder="비밀번호">

            <input type="submit" value="로그인" class="side-login-btn">
        </form>

        <p><a href="/User/user_join.do">회원가입</a></p>
    </div>
</c:if>

<!-- 로그인 된 상태 -->
<c:if test="${!empty user}">
    <div class="side-login-box">
        <h4 class="side-login-title">이용자 정보</h4>

        <p><b>${user.user_name}</b> 님</p>
        <p>닉네임 : ${user.n_name}</p>
        <p>등급 : ${user.user_rank}</p>
        <p>포인트 : ${user.point }</p>
        

        <p><a href="/User/user_modify.do">회원정보수정</a></p>
        <p><a href="/User/user_logout.do">로그아웃</a></p>
    </div>
</c:if>