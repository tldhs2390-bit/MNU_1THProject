<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
<title>🌱 성장 이야기 - 전체 목록</title>

<style>
body {
    background:#f4fbe9;
    font-family:'Noto Sans KR', sans-serif;
    margin:0;
    padding:0;
}

/* ⭐ 화면 전체 구조: 왼쪽 메뉴 + 오른쪽 컨텐츠 */
.page-wrapper { 
    width:100%;
    display:flex;
    gap:20px;
}

.left-menu {
    width:220px;
    flex-shrink:0;
    padding:20px 10px;
    background:#f4fbe9;
    box-sizing:border-box;
}

.content-area {
    flex:1;
}

.container {
    width:95%;
    max-width:900px;
    margin:30px auto;
    padding:0 20px;
}

/* ----------------------------- */
/* 카테고리 버튼 */
/* ----------------------------- */
.category-wrap {
    display:flex;
    justify-content:space-between;
    margin-bottom:25px;
}

.category-box {
    width:32%;
    padding:18px 10px;
    background:white;
    border-radius:15px;
    border:3px solid #d8eec5;
    text-align:center;
    font-size:18px;
    font-weight:700;
    color:#4CAF50;
    cursor:pointer;
    transition:.25s;
    display:flex;
    justify-content:center;
    align-items:center;
    gap:8px;
}
.category-box:hover {
    background:#eef9e6;
    transform:scale(1.05);
}

/* ----------------------------- */
/* 검색 박스 */
/* ----------------------------- */
.search-container {
    display:flex;
    align-items:center;
    gap:12px;
    background:#f5ffe9;
    border:2px solid #d8eec5;
    padding:15px 20px;
    border-radius:18px;
    margin-bottom:22px;
}

.search-select, .search-input {
    padding:10px 14px;
    border:2px solid #cfe8c8;
    border-radius:10px;
    font-size:14px;
    font-weight:700;
}

.search-select {
    background:white;
    color:#4CAF50;
    cursor:pointer;
}

.search-input {
    flex:1;
}

.search-btn {
    padding:10px 20px;
    background:#4CAF50;
    color:white;
    border:none;
    border-radius:12px;
    font-weight:800;
    cursor:pointer;
}
.search-btn:hover { background:#43a047; }

.reset-btn {
    padding:10px 20px;
    background:white;
    border:2px solid #cfe8c8;
    border-radius:12px;
    font-weight:800;
    color:#4CAF50;
    cursor:pointer;
}
.reset-btn:hover { background:#e9f8dd; }

/* ----------------------------- */
/* 인기글 카드 (2x2 grid) */
/* ----------------------------- */
.pop-grid {
    display:grid;
    grid-template-columns:repeat(2,1fr);
    gap:18px;
    margin-bottom:28px;
}

.pop-card {
    padding:18px 20px;
    border-radius:15px;
    border:3px solid #d8eec5;
    background:white;
    box-shadow:0 4px 12px rgba(150,180,130,0.18);
    transition:0.28s;
    cursor:pointer;
}
.pop-card:hover {
    transform:translateY(-4px);
    background:#fafff3;
}

.pop-title { font-size:17px; font-weight:900; margin-bottom:8px; }

.pop-veg { color:#4CAF50; background:#f2fff2; border-color:#c9ebc9; }
.pop-fruit { color:#d35454; background:#fff5f7; border-color:#f3c4c4; }
.pop-herb { color:#2f9e79; background:#f2fffb; border-color:#b7e9d8; }
.pop-all { color:#d4a017; background:#fffdf2; border-color:#f2e7b8; }

.heart-bounce { display:inline-block; animation:heartBounce 1.6s ease-in-out infinite; }
@keyframes heartBounce { 0%{transform:translateY(0);}50%{transform:translateY(-3px);}100%{transform:translateY(0);} }

.icon-wiggle { display:inline-block; animation:wiggle 1.8s ease-in-out infinite; }
@keyframes wiggle { 0%{rotate:0deg;}50%{rotate:4deg;}100%{rotate:0deg;} }

/* ----------------------------- */
/* 목록 테이블 */
/* ----------------------------- */
.list-table {
    width:100%;
    border-collapse:collapse;
    background:white;
    border-radius:15px;
    overflow:hidden;
    border:3px solid #d8eec5;
}
.list-table th, .list-table td { text-align:center; }
.list-table th { background:#d8eec5; padding:12px; font-size:15px; }
.list-table td { padding:14px; border-bottom:1px solid #eee; }
.list-table tr:hover { background:#f9fff1; }

/* ----------------------------- */
/* 글쓰기 버튼 */
/* ----------------------------- */
.write-btn-box { width:100%; display:flex; justify-content:flex-end; margin:18px 0 5px; }
.write-btn { display:inline-block; padding:10px 20px; background:#4CAF50; color:white; border-radius:10px; font-weight:700; text-decoration:none; transition:.25s; }
.write-btn:hover { background:#45a049; transform:scale(1.07); }

/* ----------------------------- */
/* 페이징 버튼 */
/* ----------------------------- */
.page-wrap { margin:25px 0 40px; text-align:center; }
.page-num { display:inline-flex; justify-content:center; align-items:center; width:42px; height:42px; background:white; border:2px solid #b7e5c5; border-radius:14px; color:#4CAF50; font-weight:800; text-decoration:none; margin:0 5px; transition:0.25s; }
.page-num:hover { background:#edfae9; border-color:#9edfb3; transform:translateY(-2px); }
.page-num.active { background:#4CAF50; border-color:#4CAF50; color:white; animation:jump 0.55s ease-in-out infinite alternate; }
@keyframes jump { 0%{transform:translateY(0);} 100%{transform:translateY(-5px);} }

</style>

</head>
<body>

<!-- 페이지 번호 초기화 -->
<c:set var="pageNumber" value="${page == null ? 1 : page}" />

<div class="page-wrapper">

    <!-- 왼쪽 메뉴 -->
    <div class="left-menu">
        <%@ include file="/Include/login_form.jsp" %>
    </div>

    <!-- 오른쪽 컨텐츠 -->
    <div class="content-area">
        <div class="container">

            <!-- 카테고리 -->
            <div class="category-wrap">
                <div class="category-box" onclick="location.href='/growth_list.do?ctype=vegetable'">🥬 채소</div>
                <div class="category-box" onclick="location.href='/growth_list.do?ctype=fruit'">🍎 과일</div>
                <div class="category-box" onclick="location.href='/growth_list.do?ctype=herb'">🌿 허브</div>
            </div>

            <!-- 검색 -->
            <form action="/growth_list.do" method="get" class="search-container">
                <select name="key" class="search-select">
                    <option value="subject" ${keyValue == 'subject' ? 'selected' : ''}>제목</option>
                    <option value="n_name" ${keyValue == 'n_name' ? 'selected' : ''}>작성자</option>
                </select>
                <select name="category" class="search-select">
                    <option value="" ${categoryValue == '' ? 'selected' : ''}>전체</option>
                    <option value="vegetable" ${categoryValue == 'vegetable' ? 'selected' : ''}>채소</option>
                    <option value="fruit" ${categoryValue == 'fruit' ? 'selected' : ''}>과일</option>
                    <option value="herb" ${categoryValue == 'herb' ? 'selected' : ''}>허브</option>
                </select>
                <input type="text" name="search" value="${searchValue}" class="search-input" placeholder="검색어 입력">
                <button type="submit" class="search-btn">검색</button>
                <button type="button" onclick="location.href='/growth_list.do'" class="reset-btn">초기화</button>
            </form>

            <!-- 인기글 카드 -->
            <div class="pop-grid">
                 <!-- 채소 -->
    <c:choose>
        <c:when test="${topVeg.idx != 0}">
            <div class="pop-card pop-veg" onclick="location.href='/growth_read.do?idx=${topVeg.idx}'">
                <div class="pop-title"><span class="icon-wiggle">🥬</span> 채소 인기글</div>
                <div style="font-size:15px;">
                    <span class="heart-bounce">💖</span> <span id="vegLike">${topVeg.like_cnt}</span> | <span id="vegTitle">${topVeg.subject}</span>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="pop-card pop-veg">
                <div class="pop-title"><span class="icon-wiggle">🥬</span> 채소 인기글</div>
                <div style="font-size:15px;">💖 0 | 등록된 글이 없습니다.</div>
            </div>
        </c:otherwise>
    </c:choose>

    <!-- 과일 -->
    <c:choose>
        <c:when test="${topFruit.idx != 0}">
            <div class="pop-card pop-fruit" onclick="location.href='/growth_read.do?idx=${topFruit.idx}'">
                <div class="pop-title"><span class="icon-wiggle">🍎</span> 과일 인기글</div>
                <div style="font-size:15px;">
                    <span class="heart-bounce">💖</span> <span id="fruitLike">${topFruit.like_cnt}</span> | <span id="fruitTitle">${topFruit.subject}</span>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="pop-card pop-fruit">
                <div class="pop-title"><span class="icon-wiggle">🍎</span> 과일 인기글</div>
                <div style="font-size:15px;">💖 0 | 등록된 글이 없습니다.</div>
            </div>
        </c:otherwise>
    </c:choose>

    <!-- 허브 -->
    <c:choose>
        <c:when test="${topHerb.idx != 0}">
            <div class="pop-card pop-herb" onclick="location.href='/growth_read.do?idx=${topHerb.idx}'">
                <div class="pop-title"><span class="icon-wiggle">🌿</span> 허브 인기글</div>
                <div style="font-size:15px;">
                    <span class="heart-bounce">💖</span> <span id="herbLike">${topHerb.like_cnt}</span> | <span id="herbTitle">${topHerb.subject}</span>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="pop-card pop-herb">
                <div class="pop-title"><span class="icon-wiggle">🌿</span> 허브 인기글</div>
                <div style="font-size:15px;">💖 0 | 등록된 글이 없습니다.</div>
            </div>
        </c:otherwise>
    </c:choose>

    <!-- 전체 -->
    <c:choose>
        <c:when test="${top.idx != 0}">
            <div class="pop-card pop-all" onclick="location.href='/growth_read.do?idx=${top.idx}'">
                <div class="pop-title"><span class="icon-wiggle">⭐</span> 전체 인기글</div>
                <div style="font-size:15px;">
                    <span class="heart-bounce">💖</span> <span id="topLike">${top.like_cnt}</span> | <span id="topTitle">${top.subject}</span>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="pop-card pop-all">
                <div class="pop-title"><span class="icon-wiggle">⭐</span> 전체 인기글</div>
                <div style="font-size:15px;">💖 0 | 등록된 글이 없습니다.</div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

            <!-- 목록 테이블 -->
            <table class="list-table">
                <tr>
                    <th>번호</th><th>카테고리</th><th>제목</th><th>작성자</th><th>조회수</th><th>감정</th><th>날짜</th>
                </tr>
                <c:forEach items="${list}" var="dto" varStatus="st">
                    <tr id="list-row-${dto.idx}" onclick="location.href='/growth_read.do?idx=${dto.idx}'" style="cursor:pointer">
                        <td>${totalCount - ((pageNumber - 1) * 10 + st.index)}</td>
                        <td>
                            <c:choose>
                                <c:when test="${dto.category == 'vegetable'}">채소</c:when>
                                <c:when test="${dto.category == 'fruit'}">과일</c:when>
                                <c:when test="${dto.category == 'herb'}">허브</c:when>
                                <c:otherwise>기타</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${dto.subject}</td>
                        <td>${dto.n_name}</td>
                        <td>${dto.readcnt}</td>
                        <td>❤️ <span class="list-like">${dto.like_cnt}</span> 👍 <span class="list-sym">${dto.sym_cnt}</span> 😢 <span class="list-sad">${dto.sad_cnt}</span></td>
                        <td>${fn:substring(dto.regdate,0,10)}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty list}">
                    <tr><td colspan="7" style="padding:15px;">등록된 글이 없습니다.</td></tr>
                </c:if>
            </table>

            <!-- 글쓰기 -->
            <div class="write-btn-box">
                <a href="/growth_write.do" class="write-btn">✏️ 글쓰기</a>
            </div>

            <!-- 페이징 -->
            <div class="page-wrap">
                <c:forEach var="i" begin="${startPage}" end="${endPage}">
                    <a href="?page=${i}&key=${keyValue}&search=${searchValue}&category=${categoryValue}" class="page-num ${page == i ? 'active' : ''}">${i}</a>
                </c:forEach>
            </div>

        </div>
    </div>
</div>

</body>
</html>