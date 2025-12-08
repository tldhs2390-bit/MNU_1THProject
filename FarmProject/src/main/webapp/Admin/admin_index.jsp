<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="/Admin/Include/admin_topmenu.jsp" %>

<html>
<head>
    <title>FarmProject 메인(관리자용)</title>
    <link rel="stylesheet" type="text/css" href="/css/main.css">
</head>

<body>

<table width="100%" border="0">
<tr>

<<<<<<< HEAD
    

    <!-- 오른쪽 본문 -->
    <td class="content">

        <h1 class="main-title">🌿 FarmProject(관리자용)</h1>
        <p class="main-sub">가장 쉬운 초심자 텃밭 가이드와 성장일기 공유 커뮤니티</p>

        <!-- 가이드 카드 3개 -->
        <div class="guide-wrap">

            <div class="guide-card">
                <h3>🥬 채소 가이드 (관리자용)</h3>
                <p>실내,실외에서도 <br> 쉽게 키울 수 있는 12종 채소</p>
                <a class="guide-btn" href="admin_guide_veg_list.do">바로가기</a>
            </div>

            <div class="guide-card">
                <h3>🌿 허브 가이드 (관리자용)</h3>
                <p>아파트에서도 쉽게 키울 수 있는 <br> 향기 좋은 초보자 허브 5종</p>
                <a class="guide-btn" href="admin_guide_herb_list.do">바로가기</a>
            </div>

            <div class="guide-card">
                <h3>🍓 과일 가이드 (관리자용)</h3>
                <p>텃밭과 실내에서   <br> 직접 키워보는 13종 과일</p>
                <a class="guide-btn" href="admin_guide_fruit_list.do">바로가기</a>
            </div>

        </div>

        <!-- 성공 최신 글 -->
        <div class="latest-box">
            <h3>🌱 쑥쑥 성장 이야기 (관리자용)</h3>
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
            <h3>💧 아쉬운 성장 노트 (관리자용)</h3>
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
            <h3>📢 자유게시판 최신 글 (관리자용)</h3>
=======
    <!-- 왼쪽 로그인 -->
    <td class="left-menu">
        <%@ include file="/Include/login_form.jsp" %>
    </td>

    <!-- 오른쪽 본문 -->
    <td class="content">

        <h1 class="main-title">🌿 FarmProject(관리자용)</h1>
        <p class="main-sub">가장 쉬운 초심자 텃밭 가이드와 성장일기 공유 커뮤니티</p>

        <!-- 가이드 카드 3개 -->
        <div class="guide-wrap">

            <div class="guide-card">
                <h3>🥬 채소 가이드</h3>
                <p>실내,실외에서도 <br> 쉽게 키울 수 있는 12종 채소</p>
                <a class="guide-btn" href="admin_guide_veg_list.do">바로가기</a>
            </div>

            <div class="guide-card">
                <h3>🌿 허브 가이드</h3>
                <p>아파트에서도 쉽게 키울 수 있는 <br> 향기 좋은 초보자 허브 5종</p>
                <a class="guide-btn" href="admin_guide_herb_list.do">바로가기</a>
            </div>

            <div class="guide-card">
                <h3>🍓 과일 가이드</h3>
                <p>텃밭과 실내에서   <br> 직접 키워보는 13종 과일</p>
                <a class="guide-btn" href="admin_guide_fruit_list.do">바로가기</a>
            </div>

        </div>

        <!-- 성공 최신 글 -->
        <div class="latest-box">
            <h3>🌱 쑥쑥 성장 이야기</h3>
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
            <h3>💧 아쉬운 성장 노트</h3>
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
>>>>>>> refs/remotes/origin/kso
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