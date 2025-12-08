<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
    <title>과일 가이드 목록</title>
    <link rel="stylesheet" type="text/css" href="/css/main.css">

    <style>
        .page-wrapper { display: flex; }
        .left-menu { width: 200px; }
        .content { flex: 1; padding: 20px; }

        /* 검색 영역 */
        .search-box {
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 6px;
        }
        .search-box select,
        .search-box input[type="text"] {
            height: 34px;
            border: 1px solid #C8E6C9;
            border-radius: 6px;
            padding: 0 8px;
            font-size: 11pt;
        }
        .search-btn {
            height: 34px;
            padding: 0 12px;
            border-radius: 6px;
            background: #4CAF50;
            color: white;
            font-weight: bold;
            text-decoration: none;
            display: flex;
            align-items: center;
            justify-content: center;
            transition: background .2s;
        }
        .search-btn:hover {
            background: #43A047;
        }

        table.guide-table { width: 100%; border-collapse: collapse; }
        table.guide-table td { padding: 15px; text-align: center; }

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
        .detail-btn:hover {
            background: #43A047;
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
<div class="page-wrapper">

    <!-- 왼쪽 로그인 -->
    <td class="left-menu" width="20%" valign="top">
        <%@ include file="/Include/login_form.jsp" %>
    </td>

    <!-- 오른쪽 -->
    <div class="content">
        <h1 class="main-title">🍓 과일 가이드</h1>
        <p>직접 키워보는 과일</p>

        <!-- 검색창 -->
        <form name="guide" method="get" action="fruit_list.do">
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

        <!-- 카드 리스트 -->
        <table class="guide-table">
            <tr>
                <c:set var="count" value="0"/>
                <c:forEach var="fruit" items="${fruitList}">
                    <c:if test="${fruit.category eq '과일'}">
                        <c:set var="count" value="${count + 1}" />

                        <td>
                            <a href="${fruit.link}" target="_blank">
                                <div class="guide-card">
                                    <img src="${pageContext.request.contextPath}/img/guide/${fn:escapeXml(fruit.image_filename)}" alt="${fruit.name}">
                                </div>
                            </a>

                            <h3>${fruit.name}</h3>
                            <p>카테고리: ${fruit.category}</p>
                            <p>파종 시기: ${fruit.best_date}</p>
                            <p>난이도: ${fruit.level}</p>
                            <p>급수: ${fruit.water}</p>
                            <p>비료: ${fruit.medicine}</p>
                            <p>수확 기간: ${fruit.last_date}</p>
                            <p>재배 장소: ${fruit.place}</p>

                            <a href="${fruit.link}" target="_blank" class="detail-btn">🔍 자세히 보기</a>
                        </td>

                        <c:if test="${count % 5 == 0}">
                            </tr><tr>
                        </c:if>

                    </c:if>
                </c:forEach>
            </tr>
        </table>

    </div>
</div>
</body>
</html>