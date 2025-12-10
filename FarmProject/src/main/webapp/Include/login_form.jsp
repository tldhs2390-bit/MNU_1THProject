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
    .side-rank-box {
    width: 100%;
    padding: 12px;
    background: #fff;
    border: 1px solid #cfcfcf;
    border-radius: 8px;
    margin-top: 15px;
    box-sizing: border-box;
    text-align:center;
	}
	
	.side-rank-title {
	    font-weight: bold;
	    color: #4CAF50; ;
	    margin-bottom: 8px;
	    text-align: center;
	}
	
	.side-rank-table {
	    width: 100%;
	    font-size: 9pt;
	}
	/* 포인트 랭킹 테이블 정렬 */
	.side-rank-table {
	    width: 100%;
	    font-size: 11pt;
	    border-collapse: collapse;
	}
	
	.side-rank-table th {
	    padding: 6px 0;
	    font-weight: bold;
	    border-bottom: 1px solid #e5e5e5;
	    text-align: center;
	}
	
	.side-rank-table td {
	    padding: 6px 0;
	    text-align: center; /* 기본 가운데 정렬 */
	}
	
	/* 열 폭 설정 */
	.side-rank-rank {
	    width: 20%;
	    text-align:center;
	}
	
	.side-rank-name {
	    width: 45%;
	    text-align:center;
	}
	
	.side-rank-point {
	    width: 35%;
	    text-align:center; /* 숫자 오른쪽 정렬 */
	    padding-right: 10px;
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

        <p><a href="/User/user_join_agree.jsp">회원가입</a></p>
    </div>
</c:if>

<!-- 로그인 된 상태 -->
<c:if test="${!empty user}">
    <div class="side-login-box">
        <h4 class="side-login-title">이용자 정보</h4>

        <p><b>${user.user_name}</b> 님</p>
        <p>닉네임 : ${user.n_name}</p>

        <p>포인트 : ${user.point }</p>
        

        <p><a href="/User/user_modify.do">회원정보수정</a></p>
        <p><a href="/User/user_logout.do">로그아웃</a></p>
    </div>
</c:if>

<%@ page import="user.model.UserDAO,user.model.UserDTO" %>
<%
    UserDAO dao = UserDAO.getInstance();
    request.setAttribute("rankList", dao.getPointRankTop5());
%>
<!-- 상위랭킹 5명 -->
<div class="side-rank-box">
    <div class="side-rank-title">포인트 랭킹</div>

    <c:choose>
        <c:when test="${empty rankList}">
            <div style="text-align:center; color:#777; font-size:9pt;">
                랭킹 정보 없음
            </div>
        </c:when>
        <c:otherwise>
            <table class="side-rank-table">
                <tr>
                    <th class="side-rank-rank">랭킹</th>
                    <th class="side-rank-name">닉네임</th>
                    <th class="side-rank-point">포인트</th>
                </tr>

                <c:forEach var="r" items="${rankList}" varStatus="st">
                    <tr>
                        <td class="side-rank-rank">${st.index + 1}</td>
                        <td class="side-rank-name">${r.n_name}</td>
                        <td class="side-rank-point">${r.point}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</div>