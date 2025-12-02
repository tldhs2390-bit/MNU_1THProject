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

        .guide-wrap {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            margin-top: 20px;
        }

        .guide-card {
            width: calc((100% - 40px)/3);
            background: white;
            border-radius: 10px;
            padding: 15px;
            border: 1px solid #C8E6C9;
            box-shadow: 0 3px 6px rgba(0,0,0,0.05);
            text-align: center;
            box-sizing: border-box;
            transition: transform 0.2s;
        }

        .guide-card:hover {
            transform: scale(1.05);
        }

        .guide-card img {
            width: 100%;
            border-radius: 8px;
            margin-bottom: 10px;
        }

        .guide-btn {
            display: inline-block;
            background: #4CAF50;
            color: white;
            padding: 6px 12px;
            text-decoration: none;
            border-radius: 4px;
            margin-top: 8px;
        }

        .guide-btn:hover {
            background: #43A047;
        }
    </style>
</head>

<body>

<div class="page-wrapper">

    <div class="left-menu">
        <%@ include file="/Include/login_form.jsp" %>
    </div>

    <div class="content">
        <h1 class="main-title">🌿 초심자 가이드</h1>
        <p class="main-sub">가장 쉬운 초심자 텃밭 가이드와 성장일기 공유 커뮤니티</p>

        <div class="guide-wrap">
            <c:forEach var="g" items="${guideList}">
                <div class="guide-card">
                    <img src="${pageContext.request.contextPath}/img/guide/${fn:escapeXml(g.name)}.png">
                    <h3>${g.name}</h3>
                    <p>카테고리: ${g.category}</p>
                    <p>파종 시기: ${g.best_date}</p>
                    <p>난이도: ${g.level}</p>
                    <p>급수: ${g.water}</p>
                    <p>비료: ${g.medicine}</p>
                    <p>수확 기간: ${g.last_date}</p>
                </div>
            </c:forEach>
        </div>
    </div>

</div>

</body>
</html>