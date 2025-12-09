<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/Admin/Include/admin_topmenu.jsp" %>

<html>
<head>
    <title>초심자 가이드 목록(관리자 용)</title>
    <link rel="stylesheet" type="text/css" href="/css/main.css">

    <style>
    .main-title {
    text-align: center;  /* 가운데 정렬 */
    font-size: 28px;     /* 필요시 크기 조정 */
    font-weight: 700;
    margin-bottom: 20px; /* 제목과 검색폼 사이 간격 */
}
        .page-wrapper {
            display: flex;
            justify-content: center;
        }
        .content {
            flex: none;
            padding: 20px;
        }

        /* ----------------------------- */
        /*   검색 박스 (요청 디자인)       */
        /* ----------------------------- */
        .search-container {
		    display: flex;
		    align-items: center;
		    gap: 12px;
		    background: #f5ffe9;
		    border: 2px solid #d8eec5;
		    padding: 15px 20px;
		    border-radius: 18px;
		    margin: 0 auto 22px auto; /* 위/아래 0, 좌우 자동 중앙 */
		    width: 66%; /* 너비 2/3 */
		}

        .search-select {
            padding:10px 14px;
            border:2px solid #cfe8c8;
            border-radius:10px;
            background:white;
            font-size:14px;
            font-weight:700;
            color:#4CAF50;
            cursor:pointer;
        }

        .search-input {
            flex:1;
            padding:10px 14px;
            border:2px solid #cfe8c8;
            border-radius:10px;
            font-size:14px;
        }

        .search-btn {
            padding:10px 20px;
            background:#4CAF50;
            color:white;
            border:none;
            border-radius:12px;
            font-weight:800;
            cursor:pointer;
            transition:.25s;
        }
        .search-btn:hover { background:#43a047; }

        /* ----------------------------- */
        /* 카드 테이블 & 카드 스타일 그대로 */
        /* ----------------------------- */
        .guide-table {
            width: auto;
            margin: 0 auto;
            border-collapse: collapse;
        }

        .guide-table td {
            padding: 15px;
            text-align: center;
            vertical-align: top;
        }

        .guide-card {
		    width: 160px;
		    background: #fff4e0; /* 연한 베이지 + 살짝 갈색 느낌 */
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
    <div class="content">
        <h1 class="main-title">🌿 초심자 가이드(관리자용)</h1>

        <!-- 검색폼 (요청 디자인) -->
        <form name="guide" method="get" action="admin_guide_list.do">
            <div class="search-container">
                <select name="search" class="search-select">
                    <option value="name" <c:if test="${search=='name'}">selected</c:if>>이름</option>
                    <option value="category" <c:if test="${search=='category'}">selected</c:if>>카테고리</option>
                    <option value="place" <c:if test="${search=='place'}">selected</c:if>>재배 장소</option>
                </select>

                <input type="text" name="key" class="search-input" value="${key}">

                <button type="button" class="search-btn" onclick="guide_search()">검색</button>
            </div>
        </form>

        <!-- 등록 버튼 (원래 그대로) -->
        <div style="margin-top: 30px; text-align: left;">
            <a href="admin_guide_write.do" 
               style="padding:10px 20px; background:#4CAF50; color:white; border-radius:8px; text-decoration:none; margin-right:10px;">
                카드 등록
            </a>
        </div>

        <!-- 카드 테이블 -->
        <table class="guide-table">
            <tr>
            <c:set var="count" value="0"/>
            <c:forEach var="g" items="${guideList}">
                <c:set var="count" value="${count + 1}" />
                <td>
                    <a href="${g.link}" target="_blank">
                        <div class="guide-card">
                            <img src="${pageContext.request.contextPath}/img/guide/${fn:escapeXml(g.image_filename)}" alt="${g.name}"></a>
                            <h3>${g.name}</h3>
                            <p>카테고리: ${g.category}</p>
                            <p>파종 시기: ${g.best_date}</p>
                            <p>난이도: ${g.level}</p>
                            <p>급수: ${g.water}</p>
                            <p>비료: ${g.medicine}</p>
                            <p>수확 기간: ${g.last_date}</p>
                            <p>재배 장소: ${g.place}</p>

                            <a href="${g.link}" target="_blank" class="detail-btn">🔍 자세히 보기</a>
                            <div style="margin-top:10px;">
                                <a href="admin_guide_modify.do?id=${g.id}" 
                                   style="padding:6px 10px; background:#FFC107; color:white; border-radius:6px; text-decoration:none; margin-right:5px;">수정</a>
                                <a href="/admin_guide_delete.do?id=${g.id}" 
                                   style="padding:6px 10px; background:#F44336; color:white; border-radius:6px; text-decoration:none;" 
                                   onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
                            </div>
                        </div>
                    
                </td>
                <c:if test="${count % 5 == 0}"></tr><tr></c:if>
            </c:forEach>
            </tr>
        </table>

    </div>
</div>
</body>
</html>