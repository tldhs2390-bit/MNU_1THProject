<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
    <title>FarmProject 메인</title>
    <link rel="stylesheet" type="text/css" href="/css/main.css">
    <style>
    .notice-section {
        margin:20px 0;
        width:100%;
        background:#ffffff;     /* 배경을 흰색으로 변경 */
        border:1px solid #d8eec5;
        border-radius:6px;
        padding:15px;
    }

    .notice-title {
        font-size:16pt;
        font-weight:700;
        color:#4CAF50;
        margin-bottom:10px;
        display:flex;
        justify-content:space-between;
        align-items:center;
    }

    .notice-table {
        width:100%;
        border-collapse:collapse;
    }

    .notice-table th, .notice-table td {
        padding:12px 8px;       /* 상하 간격 확대 */
        font-size:11pt;
        border-bottom:1px solid #eeeeee;
        text-align:center;
        line-height:1.6;        /* 줄 높이 */
    }

    .notice-table th { background:#f5f7e8; font-weight:700; } /* 헤더만 연한 색 유지 */

    .notice-row:hover { background:#f0fff2; transition:0.2s; }

    a.list { text-decoration:none; color:#2e7d32; font-weight:600; }
    a.list:hover { text-decoration:underline; }

    .more-btn {
        padding:2px 8px;
        font-size:10pt;
        background:#4CAF50;
        color:white;
        font-weight:700;
        border-radius:4px;
        text-decoration:none;
    }
    .more-btn:hover { background:#43a047; }
</style>
</head>

<body>

<table width="100%" border="0">
<tr>
    <!-- 왼쪽 로그인 -->
    <td class="left-menu" width="20%" valign="top">
        <jsp:include page="/Include/login_form.jsp" /> 
    </td>

    <!-- 오른쪽 본문 -->
    <td class="content" valign="top">

        <h1 class="main-title">🌿 FarmProject</h1>
        <p class="main-sub">가장 쉬운 초심자 텃밭 가이드와 성장일기 공유 커뮤니티</p>

        <!-- 가이드 카드 3개 -->
        <div class="guide-wrap">
            <div class="guide-card">
                <h3>🥬 채소 가이드</h3>
                <p>실내,실외에서도 <br> 쉽게 키울 수 있는 채소</p>
                <a class="guide-btn" href="guide_veg_list.do">바로가기</a>
            </div>

            <div class="guide-card">
                <h3>🌿 허브 가이드</h3>
                <p>아파트에서도 쉽게 키울 수 있는 <br> 향기 좋은 초보자 허브</p>
                <a class="guide-btn" href="/guide_herb_list.do">바로가기</a>
            </div>

            <div class="guide-card">
                <h3>🍓 과일 가이드</h3>
                <p>텃밭과 실내에서   <br> 직접 키워보는 과일</p>
                <a class="guide-btn" href="/guide_fruit_list.do">바로가기</a>
            </div>
        </div>

        <!-- 최신 공지사항 섹션 -->
<div class="notice-section">
    <div class="notice-title">
        <span>📢 최신 공지사항</span>
        <a class="more-btn" href="board_list.do">더보기</a>
    </div>

    <table class="notice-table">
        <tr>
            <th width="10%">번호</th>
            <th width="50%">제목</th>
            <th width="15%">작성자</th>
            <th width="15%">작성일</th>
            <th width="10%">조회수</th>
        </tr>

        <c:set var="total" value="${fn:length(blist) }" />
        <c:forEach var="bDto" items="${blist}" varStatus="status">
            <c:if test="${status.index < 3}">
                <tr class="notice-row">
                    <!-- 번호를 밑에서부터 1,2,3 -->
                    <td>${3 - status.index}</td>
                    <td style="text-align:left; padding-left:8px;">
                        <a class="list" href="board_view.do?idx=${bDto.idx}&page=1">
                            ${bDto.subject}
                        </a>
                    </td>
                    <td>관리자</td>
                    <td>${fn:substring(bDto.regdate,0,10)}</td>
                    <td>${bDto.readcnt}</td>
                </tr>
            </c:if>
        </c:forEach>
    </table>
</div>

    </td>
</tr>
</table>


</body>
</html>