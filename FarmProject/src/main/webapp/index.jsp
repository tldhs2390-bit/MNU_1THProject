<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
    <title>FarmProject 메인</title>
    <link rel="stylesheet" type="text/css" href="/css/main.css">
</head>

<body>

<table width="100%" border="0">
<tr>

    <!-- 왼쪽 로그인 -->
    <td class="left-menu" width="20%" valign="top">
        <%@ include file="/Include/login_form.jsp" %>
    </td>

    <!-- 오른쪽 본문 -->
    <td class="content" width="80%" valign="top">

        <h1 class="main-title">🌿 FarmProject</h1>
        <p class="main-sub">가장 쉬운 초심자 텃밭 가이드와 성장일기 공유 커뮤니티</p>

        <!-- 가이드 카드 3개 -->
        <div class="guide-wrap">

            <div class="guide-card">
                <h3>🥬 채소 가이드</h3>
                <p>아파트에서도 쉽게 키우는 15종 채소</p>
                <a class="guide-btn" href="/guide/guide_list.do?cate=veg">바로가기</a>
            </div>

            <div class="guide-card">
                <h3>🌿 허브 가이드</h3>
                <p>향기 좋은 초보자 허브 5종</p>
                <a class="guide-btn" href="/guide/guide_list.do?cate=herb">바로가기</a>
            </div>

            <div class="guide-card">
                <h3>🍓 과일 가이드</h3>
                <p>집에서 키우는 10종 과일</p>
                <a class="guide-btn" href="/guide/guide_list.do?cate=fruit">바로가기</a>
            </div>

        </div>

        <!-- 성공 최신 글 -->
        <div class="latest-box">
            <h3>🌱 최근 성공 이야기</h3>
            <table class="latest-mini">
                <c:forEach var="s" items="${successList}">
                    <tr>
                        <td>✔</td>
                        <td><a href="/success/success_read.do?idx=${s.idx}">${s.subject}</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <!-- 실패 최신 글 -->
        <div class="latest-box">
            <h3>💧 최근 실패 노트</h3>
            <table class="latest-mini">
                <c:forEach var="f" items="${failList}">
                    <tr>
                        <td>•</td>
                        <td><a href="/fail/fail_read.do?idx=${f.idx}">${f.subject}</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <!-- 자유 최신 글 -->
        <div class="latest-box">
            <h3>📢 자유게시판 최신 글</h3>
            <table class="latest-mini">
                <c:forEach var="b" items="${boardList}">
                    <tr>
                        <td>•</td>
                        <td><a href="/board/board_read.do?idx=${b.idx}">${b.subject}</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

    </td>

</tr>
</table>

</body>
</html>