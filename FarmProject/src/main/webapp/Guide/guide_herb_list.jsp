<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
    <title>허브 가이드 목록</title>
    <link rel="stylesheet" type="text/css" href="/css/main.css">

    <style>
        .page-wrapper { display:flex; gap:20px; justify-content:flex-start; }
        .left-menu { width:20%; }
        .content { flex:1; padding:20px; }

        .main-title { text-align:center; font-size:28px; font-weight:700; margin-bottom:20px; }

        .search-container {
            display:flex; align-items:center; gap:12px;
            background:#f5ffe9; border:2px solid #d8eec5;
            padding:15px 20px; border-radius:18px;
            margin:0 auto 22px auto; width:66%;
        }
        .search-select { padding:10px 14px; border:2px solid #cfe8c8; border-radius:10px; background:white; font-size:14px; font-weight:700; color:#4CAF50; cursor:pointer; }
        .search-input { flex:1; padding:10px 14px; border:2px solid #cfe8c8; border-radius:10px; font-size:14px; }
        .search-btn { padding:10px 20px; background:#4CAF50; color:white; border:none; border-radius:12px; font-weight:800; cursor:pointer; transition:.25s; }
        .search-btn:hover { background:#43A047; }

        .guide-table { width:auto; margin:0 auto; border-collapse:collapse; }
        .guide-table td { padding:15px; text-align:center; vertical-align:top; }

        .guide-card { width:160px; background:#fff4e0; border:1px solid #C8E6C9; border-radius:10px; padding:10px; box-shadow:0 3px 6px rgba(0,0,0,0.05); transition:transform 0.2s; }
        .guide-card:hover { transform:scale(1.05); }
        .guide-card img { width:100%; height:120px; object-fit:cover; border-radius:8px; margin-bottom:8px; }

        .detail-btn { display:inline-block; margin-top:10px; padding:6px 10px; background:#4CAF50; color:white; font-size:12pt; border-radius:6px; text-decoration:none; transition:background 0.2s; }
        .detail-btn:hover { background:#43A047; }
    </style>

<script>
function guide_search(){
    if(guide.key.value==""){ alert("검색어를 입력하세요"); guide.key.focus(); return; }
    guide.submit();
}

//팝업 열기 함수
function openPopup(url){
    window.open(
        url,
        "guidePopup",
        "width=900,height=700,scrollbars=yes,resizable=yes"
    );
}
</script>
</head>

<body>
<div class="page-wrapper">
    <div class="left-menu">
        <%@ include file="/Include/login_form.jsp" %>
    </div>

    <div class="content">
        <h1 class="main-title">🌿 허브 가이드</h1>

        <form name="guide" method="get" action="guide_herb_list.do">
            <div class="search-container">
                <select name="search" class="search-select">
                    <option value="name" <c:if test="${search=='name'}">selected</c:if>>이름</option>
                    <option value="place" <c:if test="${search=='place'}">selected</c:if>>재배 장소</option>
                </select>
                <input type="text" name="key" class="search-input" value="${key}">
                <button type="button" class="search-btn" onclick="guide_search()">검색</button>
            </div>
        </form>

        <table class="guide-table">
            <tr>
                <c:set var="count" value="0"/>
                <c:forEach var="herb" items="${herbList}">
                    <c:if test="${herb.category eq '허브'}">
                        <c:set var="count" value="${count + 1}" />
                        <td>
                            <div class="guide-card">
                                <img src="${pageContext.request.contextPath}/img/guide/${fn:escapeXml(herb.image_filename)}"
                                     alt="${herb.name}"
                                     style="cursor:pointer"
                                     onclick="openPopup('${herb.link}')">
                                <h3>${herb.name}</h3>
                                <p>카테고리: ${herb.category}</p>
                                <p>파종 시기: ${herb.best_date}</p>
                                <p>난이도: ${herb.level}</p>
                                <p>급수: ${herb.water}</p>
                                <p>비료: ${herb.medicine}</p>
                                <p>수확 기간: ${herb.last_date}</p>
                                <p>재배 장소: ${herb.place}</p>
                                <!-- 자세히 보기 → 팝업 -->
                                <a href="javascript:void(0)"
                                   class="detail-btn"
                                   onclick="openPopup('${herb.link}')">
                                   🔍 자세히 보기
                                </a>
                            </div>
                        </td>
                        <c:if test="${count % 5 == 0}"></tr><tr></c:if>
                    </c:if>
                </c:forEach>
            </tr>
        </table>
    </div>
</div>
</body>
</html>