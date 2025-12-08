<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
<<<<<<< HEAD
    /* modify.jsp 왼쪽 20% td 안에서 항상 일정한 크기로 보이도록 고정 */
    .side-login-box {
        width: 100%;                 /* td 영역 안에서 꽉 차도록 */
        max-width: 260px;            /* 화면 크기 커져도 이보다 넓어지지 않음 */
        min-width: 220px;            /* 화면 작아져도 이보다 줄지 않음 */
        background: #ffffff;
        border: 1px solid #cfcfcf;
        border-radius: 8px;
        padding: 18px;
        font-family: 돋움, Verdana;
        font-size: 11pt;
        box-sizing: border-box;      /* padding 포함한 고정 width 유지 */
        margin: 0 auto;              /* td 안에서 가운데 정렬 */
=======
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
>>>>>>> refs/remotes/origin/kso
    }

    .side-login-title {
        font-weight: bold;
        color: #4CAF50;
        font-size: 13pt;
<<<<<<< HEAD
        margin-bottom: 18px;
=======
        margin-bottom: 15px;
>>>>>>> refs/remotes/origin/kso
        text-align: center;
    }

<<<<<<< HEAD
    /* 입력창 고정 크기 유지 */
    .side-login-box input[type=text],
    .side-login-box input[type=password] {
        width: 100%;
        height: 30px;
        font-size: 10pt;
        padding-left: 6px;
        border-radius: 4px;
        border: 1px solid #aaa;
        margin-bottom: 10px;
        box-sizing: border-box;
=======
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
>>>>>>> refs/remotes/origin/kso
    }

<<<<<<< HEAD
    /* 로그인 버튼 */
    .side-login-btn {
        width: 100%;
        height: 34px;
        background: #4CAF50;
        border: none;
        color: white;
        font-weight: bold;
        border-radius: 4px;
        cursor: pointer;
        margin-top: 5px;
    }
    .side-login-btn:hover {
        background: #43A047;
=======
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
>>>>>>> refs/remotes/origin/kso
    }

<<<<<<< HEAD
    /* 링크 */
    .side-login-box a {
        color: #4CAF50;
        text-decoration: none;
        font-size: 11pt;
    }
    .side-login-box a:hover {
        text-decoration: underline;
=======
    .side-login-btn:hover {
        background: #43A047;
>>>>>>> refs/remotes/origin/kso
    }

<<<<<<< HEAD
    .side-login-box p {
        text-align: center;
        margin: 12px 0;
    }
</style>
=======
    .side-login-box a {
        color: #4CAF50;
        font-size: 11pt;
        text-decoration: none;
    }
>>>>>>> refs/remotes/origin/kso

<<<<<<< HEAD
=======
    .side-login-box p {
        margin: 11px 0;
        text-align: center;
    }
</style>
>>>>>>> refs/remotes/origin/kso

<!-- 로그인 안 된 상태 -->
<c:if test="${empty user}">
    <div class="side-login-box">
        <h3 class="side-login-title">Member Login</h3>

        <form method="post" action="/User/user_login.do">
<<<<<<< HEAD
            <input type="text" name="user_id" placeholder="아이디">
            <input type="password" name="user_pass" placeholder="비밀번호">
            <input type="submit" value="로그인" class="side-login-btn">
        </form>
=======
>>>>>>> refs/remotes/origin/kso

<<<<<<< HEAD
        <p><a href="/User/user_join.do">회원가입</a></p>
    </div>
</c:if>
=======
            <input type="text" name="user_id" placeholder="아이디">
>>>>>>> refs/remotes/origin/kso

<<<<<<< HEAD
<!-- 로그인 된 상태 -->
<c:if test="${!empty user}">
    <div class="side-login-box">
        <h4 class="side-login-title">이용자 정보</h4>
=======
            <input type="password" name="user_pass" placeholder="비밀번호">
>>>>>>> refs/remotes/origin/kso

<<<<<<< HEAD
        <p><b>${user.user_name}</b> 님</p>
        <p>닉네임 : ${user.n_name}</p>
        <p>등급 : ${user.user_rank}</p>
        <p>포인트 : ${user.point}</p>
=======
            <input type="submit" value="로그인" class="side-login-btn">
        </form>
>>>>>>> refs/remotes/origin/kso

<<<<<<< HEAD
=======
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
        

>>>>>>> refs/remotes/origin/kso
        <p><a href="/User/user_modify.do">회원정보수정</a></p>
        <p><a href="/User/user_logout.do">로그아웃</a></p>
    </div>
</c:if>