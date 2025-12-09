<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
    <title>허브 가이드 목록</title>
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

    /* 검색 버튼 스타일 */
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
    <!-- 왼쪽 로그인 영역 -->
    <td class="left-menu" width="20%" valign="top">
        <%@ include file="/Include/login_form.jsp" %>
    </td>

    <!-- 오른쪽 본문 -->
    <td class="content">

        <h1 class="main-title">🌿 허브 가이드</h1>
        <p>향기 좋은 초보자 허브</p>

        <!-- 검색창 추가 -->
        <form name="guide" method="get" action="guide_herb_list.do">
            <table>
                <tr>
                    <td>
                        <select name="search">
                            <option value="name" <c:if test="${search=='name'}">selected</c:if>>이름</option>
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

        <!-- 카드 목록 -->
        <table class="guide-table">
            <tr>
                <c:set var="count" value="0"/>
                <c:forEach var="herb" items="${herbList}">

                    <!-- category = 허브 인 데이터만 출력 -->
                    <c:if test="${herb.category eq '허브'}">

                        <c:set var="count" value="${count + 1}" />

                        <td>
                            <div class="guide-card">
                                <a href="${herb.link}" target="_blank">
                                    <img src="${pageContext.request.contextPath}/img/guide/${fn:escapeXml(herb.image_filename)}"
                                         alt="${herb.name}">
                                </a>

                                <h3>${herb.name}</h3>
                                <p>카테고리: ${herb.category}</p>
                                <p>파종 시기: ${herb.best_date}</p>
                                <p>난이도: ${herb.level}</p>
                                <p>급수: ${herb.water}</p>
                                <p>비료: ${herb.medicine}</p>
                                <p>수확 기간: ${herb.last_date}</p>
                                <p>재배 장소: ${herb.place}</p>

                                <a href="${herb.link}" target="_blank" class="detail-btn">🔍 자세히 보기</a>
                            </div>
                        </td>

                        <c:if test="${count % 5 == 0}">
                            </tr><tr>
                        </c:if>

                    </c:if>

                </c:forEach>
            </tr>
        </table>

    </td>
</tr>

</table>

</body>
</html>