<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
    <title>초심자 가이드 목록</title>
    <link rel="stylesheet" type="text/css" href="/css/main.css">

<style>
    /* 페이지 전체 구조 */
    .content { padding: 20px; }

    /* 가이드 카드 */
    .guide-table { width: 100%; border-collapse: collapse; }
    .guide-table td { padding: 15px; text-align: center; }

    .guide-card {
        width: 160px;
        background: white;
        border: 1px solid #C8E6C9;
        border-radius: 10px;
        padding: 10px;
        box-shadow: 0 3px 6px rgba(0,0,0,0.05);
        transition: transform 0.2s;
    }
    .guide-card:hover { transform: scale(1.05); }

    .guide-card img {
        width: 100%;
        height: 120px;
        object-fit: cover;
        border-radius: 8px;
        margin-bottom: 8px;
    }

    .detail-btn {
        display: inline-block;
        margin-top: 10px;
        padding: 6px 10px;
        background: #4CAF50;
        color: white;
        font-size: 12pt;
        border-radius: 6px;
        text-decoration: none;
        transition: background 0.2s;
    }
    .detail-btn:hover { background: #43A047; }

    /* 검색 버튼 */
    .search-btn {
        background: #5cb85c;
        color: white;
        border: none;
        padding: 8px 18px;
        font-size: 14px;
        border-radius: 20px;
        cursor: pointer;
        transition: 0.2s;
        font-weight: bold;
    }
    .search-btn:hover {
        background: #4cae4c;
        box-shadow: 0 2px 6px rgba(0,0,0,0.15);
    }
</style>

<script>
function guide_search(){
    if(guide.key.value == ""){
        alert("검색어를 입력하세요");
        guide.key.focus();
        return;
    }
    guide.submit();
}
</script>

</head>

<body>

<table width="100%" border="0">

<tr>
    <!-- 왼쪽 로그인 영역 (메인 페이지와 동일) -->
    <td class="left-menu" width="20%" valign="top">
        <%@ include file="/Include/login_form.jsp" %>
    </td>

    <!-- 오른쪽 본문 -->
    <td class="content">

        <h1 class="main-title">🌿 초심자 가이드</h1>

        <!-- 검색 -->
        <form name="guide" method="get" action="guide_list.do">
            <table>
                <tr>
                    <td>
                        <select name="search">
                            <option value="name" <c:if test="${search=='name'}">selected</c:if>>이름</option>
                            <option value="category" <c:if test="${search=='category'}">selected</c:if>>카테고리</option>
                            <option value="place" <c:if test="${search=='place'}">selected</c:if>>재배 장소</option>
                        </select>
                    </td>

                    <td>
                        <input type="text" size="20" name="key" value="${key}">
                    </td>

                    <td>
                        <button type="button" class="search-btn" onclick="guide_search()">검색</button>
                    </td>
                </tr>
            </table>
        </form>

        <!-- 가이드 카드 목록 -->
        <table class="guide-table">
            <tr>
                <c:set var="count" value="0"/>
                <c:forEach var="g" items="${guideList}">
                    <c:set var="count" value="${count + 1}" />

                    <td>
                        <div class="guide-card">
                            <a href="${g.link}" target="_blank">
                                <img src="${pageContext.request.contextPath}/img/guide/${fn:escapeXml(g.image_filename)}"
                                     alt="${g.name}">
                            </a>

                            <h3>${g.name}</h3>
                            <p>카테고리: ${g.category}</p>
                            <p>파종 시기: ${g.best_date}</p>
                            <p>난이도: ${g.level}</p>
                            <p>급수: ${g.water}</p>
                            <p>비료: ${g.medicine}</p>
                            <p>수확 기간: ${g.last_date}</p>
                            <p>재배 장소: ${g.place}</p>

                            <a href="${g.link}" target="_blank" class="detail-btn">
                                🔍 자세히 보기
                            </a>
                        </div>
                    </td>

                    <c:if test="${count % 5 == 0}">
                        </tr><tr>
                    </c:if>

                </c:forEach>
            </tr>
        </table>

    </td>
</tr>

</table>

</body>
</html> 