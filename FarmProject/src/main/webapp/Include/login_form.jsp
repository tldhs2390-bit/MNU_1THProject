<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style>
    .side-login-box {
        width: 100%;
        padding: 15px;
        background: #ffffff;
        border: 1px solid #cfcfcf;
        border-radius: 8px;
        font-family: 돋움, Verdana;
        font-size: 11pt;
        box-sizing: border-box;
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

    /* ----------------------------------------- */
    /* 랭킹 박스 */
    /* ----------------------------------------- */
    .side-rank-box {
        width: 100%;
        padding: 20px;
        background: #ffffff;
        border: 1px solid #dfe5d8;
        border-radius: 14px;
        margin-top: 20px;
        box-sizing: border-box;
        text-align: center;
        box-shadow: 0px 3px 10px rgba(0,0,0,0.08);
    }
    
    .side-rank-title {
        font-weight: bold;
        color: #4CAF50;
        margin-bottom: 16px;
        text-align: center;
        font-size: 14pt;
    }
    
    .side-rank-table {
        width: 100%;
        font-size: 11pt;
        border-collapse: collapse;
        table-layout: fixed; 
    }

    /* ---------------------------------------------------- */
    /* ⭐ 핵심: 모든 td를 inline-flex 로 맞춰 통일된 높이 유지 */
    /* ---------------------------------------------------- */
    .side-rank-table td,
    .side-rank-table th {
        height: 42px;
        padding: 0;
        white-space: nowrap;
        display: inline-flex;       /* ← 전체 통일 */
        justify-content: center;
        align-items: center;
        vertical-align: middle;
        text-align: center;
    }

    /* 메달 */
    .medal-icon {
        width: 20px;
        height: auto;
    }

    /* 닉네임 + 배지 */
    .badge-icon {
        font-size: 12pt;
        margin-right: 4px;
        line-height: 1;
    }

    .side-rank-rank { width: 20%; }
    .side-rank-name { width: 45%; }
    .side-rank-point { width: 35%; padding-right:10px; }
    .side-banner-box {
    width: 100%;
    margin-top: 15px;
    text-align: center;
	}
	
	.side-banner-img {
	    width: 100%;
	    border-radius: 8px;
	    border: 1px solid #cfcfcf;
	    box-sizing: border-box;
	    cursor: pointer;
	}
</style>



<!-- 로그인 영역 -->
<c:if test="${empty user}">
    <div class="side-login-box">
        <h3 class="side-login-title">Member Login</h3>

        <form method="post" action="user_login.do">
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

		<p><b>${fn:substring(user.user_name,0,1)}**</b> 님</p>
        <p>닉네임 : ${user.n_name}</p>
		
		<p>등급 : 
		<c:choose>
			<c:when test="${user.point <=1999}">초보 농부</c:when>
			<c:when test="${user.point <=2999}">병아리 농부</c:when>
			<c:when test="${user.point <=3999}">들판 지기</c:when>
			<c:when test="${user.point <=4999}">농장 주인</c:when>
			<c:otherwise>대농장 마스터</c:otherwise>
		</c:choose>
		</p>
        
        <p>포인트 : ${user.point }</p>
        

        <p><a href="/user_modify.do">회원정보수정</a></p>
        <p><a href="/user_logout.do">로그아웃</a></p>
    </div>
</c:if>


<%@ page import="model.user.UserDAO,model.user.UserDTO" %>
<%
    UserDAO dao = UserDAO.getInstance();
    request.setAttribute("rankList", dao.getPointRankTop5());
%>


<!-- 랭킹 -->
<div class="side-rank-box">
    <div class="side-rank-title">포인트 랭킹</div>

    <table class="side-rank-table">
        <tr>
            <th class="side-rank-rank">랭킹</th>
            <th class="side-rank-name">닉네임</th>
            <th class="side-rank-point">포인트</th>
        </tr>

        <c:forEach var="r" items="${rankList}" varStatus="st">
            <tr>
                <td class="side-rank-rank">
                    <c:choose>
                        <c:when test="${st.index == 0}">
                            <img src="${pageContext.request.contextPath}/img/medal/gold.png" class="medal-icon">
                        </c:when>
                        <c:when test="${st.index == 1}">
                            <img src="${pageContext.request.contextPath}/img/medal/silver.png" class="medal-icon">
                        </c:when>
                        <c:when test="${st.index == 2}">
                            <img src="${pageContext.request.contextPath}/img/medal/bronze.png" class="medal-icon">
                        </c:when>
                        <c:otherwise>${st.index + 1}</c:otherwise>
                    </c:choose>
                </td>

                <td class="side-rank-name">
                    <span class="badge-icon">${r.badge}</span>
                    ${r.n_name}
                </td>

                <td class="side-rank-point">${r.point}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<!-- 옥션 사이트 배너 -->
<div class="side-banner-box">
    <a href="https://www.auction.co.kr/" onclick="window.open(this.href,'popup','width=800,height=600'); return false;">
        <img src="./img/auction.png" alt="Auction 배너" class="side-banner-img">
    </a>
</div>
<!-- 쿠팡 사이트 배너 -->
<div class="side-banner-box">
    <a href="https://www.coupang.com/" onclick="window.open(this.href,'popup','width=800,height=600'); return false;">
        <img src="./img/coupang.png" alt="Auction 배너" class="side-banner-img">
    </a>
</div>
