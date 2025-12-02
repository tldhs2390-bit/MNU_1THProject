<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
    <title>초심자 가이드 목록</title>
    <link rel="stylesheet" type="text/css" href="/css/main.css">
    <style>
        .page-wrapper {
            display: flex;
        }

        .left-menu {
            width: 200px;
        }

        .content {
            flex: 1;
            padding: 20px;
        }

        table.guide-table {
            border-collapse: collapse;
            width: 100%;
        }

        table.guide-table td {
            vertical-align: top;
            padding: 10px;
            text-align: center;
        }

        table.guide-table a {
            text-decoration: none;
            color: inherit;
            display: block;
        }

        .guide-card {
            background: white;
            border-radius: 10px;
            padding: 10px;
            border: 1px solid #C8E6C9;
            box-shadow: 0 3px 6px rgba(0,0,0,0.05);
            transition: transform 0.2s;
            width: 160px; /* 카드 고정폭 */
            margin: auto;
        }

        .guide-card:hover {
            transform: scale(1.05);
        }

        .guide-card img {
            width: 100%;
            height: 120px;
            object-fit: cover;
            border-radius: 8px;
            margin-bottom: 8px;
        }

        .guide-card h3 {
            margin: 5px 0;
        }

        .guide-card p {
            margin: 2px 0;
            font-size: 11pt;
        }
    </style>
</head>

<body>
<div class="page-wrapper">

    <!-- 왼쪽 로그인 -->
    <div class="left-menu">
        <%@ include file="/Include/login_form.jsp" %>
    </div>

    <!-- 오른쪽 콘텐츠 -->
    <div class="content">
        <h1 class="main-title">🌿 초심자 가이드</h1>
        <p class="main-sub">가장 쉬운 초심자 텃밭 가이드와 성장일기 공유 커뮤니티</p>

        <table class="guide-table">
            <tr>
                <c:set var="count" value="0"/>
                <c:forEach var="g" items="${guideList}">
                    <c:set var="count" value="${count + 1}" />
                    <td>
                        <a href="${pageContext.request.contextPath}/guide/guide_detail.do?name=${fn:escapeXml(g.name)}">
                            <div class="guide-card">
                                <img src="${pageContext.request.contextPath}/img/guide/${fn:escapeXml(g.name)}.jpeg" alt="${g.name}">
                                <h3>${g.name}</h3>
                                <p>카테고리: ${g.category}</p>
                                <p>파종 시기: ${g.best_date}</p>
                                <p>난이도: ${g.level}</p>
                                <p>급수: ${g.water}</p>
                                <p>비료: ${g.medicine}</p>
                                <p>수확 기간: ${g.last_date}</p>
                            </div>
                        </a>
                    </td>


                    <c:if test="${count % 5 == 0}">
                        </tr><tr>
                    </c:if>
                </c:forEach>
            </tr>
        </table>

    </div>
</div>
</body>
</html>