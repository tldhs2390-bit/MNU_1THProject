<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
<title>🌱 쑥쑥 성장 이야기 - 목록</title>

<style>
/* --------------------------- */
/* 기본 스타일 */
/* --------------------------- */
body {
    background:#f4fbe9;
    font-family:'Noto Sans KR',sans-serif;
    margin:0;
    padding:0;
}

.list-container {
    width:85%;
    max-width:850px;
    margin:30px auto;
}

.board-title {
    text-align:center;
    font-size:32px;
    font-weight:900;
    color:#4CAF50;
    margin-bottom:3px;
}

.board-sub {
    text-align:center;
    color:#666;
    font-size:15px;
    margin-bottom:20px;
}

/* --------------------------- */
/* ⭐ Top 3 박스 */
/* --------------------------- */
@keyframes fadePop {
    0% { opacity:0; transform:scale(0.92); }
    100% { opacity:1; transform:scale(1); }
}

.top3-wrap {
    display:flex;
    justify-content:space-between;
    margin-bottom:25px;
    gap:14px;
}

.top3-box {
    flex:1;
    background:white;
    border-radius:18px;
    padding:22px 18px 28px;
    border:3px solid #d8eec5;
    box-shadow:0 8px 22px rgba(0,0,0,0.07);
    text-align:center;
    transition:0.3s;
    animation:fadePop .5s ease;
}

.top3-box:hover {
    transform:translateY(-6px) scale(1.03);
    box-shadow:0 12px 30px rgba(0,0,0,0.12);
}

.top3-title {
    font-size:22px;
    font-weight:900;
    display:flex;
    justify-content:center;
    align-items:center;
    gap:8px;
    margin-bottom:12px;
}

.crown-icon { font-size:28px; }

.gold { color:#d4a017; }
.silver { color:#9c9c9c; }
.bronze { color:#b87333; }

.top3-subject {
    margin:18px 0;
}

.top3-subject a {
    font-size:20px;
    font-weight:900;
    color:#0f4b17;
    text-decoration:none;
    transition:0.2s;
}

.top3-subject a:hover {
    color:#43a047;
    text-shadow:0 0 6px rgba(76,175,80,0.3);
}

/* ⭐ Top3 해시태그 */
.top3-hashtag {
    display:inline-block;
    padding:6px 14px;
    background:#ff85c0;
    color:white;
    font-weight:bold;
    border-radius:12px;
    margin:6px 3px 0;
    font-size:13px;
}

.hash-yellow { background:#ffd966 !important; }
.hash-blue   { background:#8fd3ff !important; }
.hash-green  { background:#98e698 !important; }
.hash-pink   { background:#ff85c0 !important; }
.hash-purple { background:#d1b3ff !important; }

/* --------------------------- */
/* ⭐ 귀여운 검색창 */
/* --------------------------- */
.search-container {
    width:100%;
    margin:20px 0 10px;
    display:flex;
    justify-content:center;
}

.search-box {
    width:100%;
    background:#ffffff;
    border:3px solid #d8eec5;
    border-radius:20px;
    padding:12px;
    display:flex;
    align-items:center;
    gap:10px;
    box-shadow:0 4px 12px rgba(0,0,0,0.05);
}

.search-box select {
    padding:10px 14px;
    border:2px solid #b5dcb0;
    border-radius:14px;
    font-size:15px;
    font-weight:bold;
    background:white;
}

.search-box input[type="text"] {
    flex:1;
    padding:10px 14px;
    border:2px solid #d8eec5;
    border-radius:14px;
    font-size:15px;
}

/* ⭐ 귀여운 검색 버튼 */
.search-btn {
    padding:10px 20px;
    background:#b7e3c0;
    color:#2c5e36;
    border-radius:14px;
    font-weight:800;
    border:2px solid #a3d9ad;
    cursor:pointer;
    transition:0.25s;
    font-size:15px;
    box-shadow:0 2px 6px rgba(0,0,0,0.08);
}
.search-btn:hover {
    background:#a6dfb1;
    transform:translateY(-2px);
    box-shadow:0 4px 10px rgba(0,0,0,0.12);
}

/* --------------------------- */
/* ⭐ 목록 테이블 */
/* --------------------------- */
.table-wrap table {
    width:100%;
    border-collapse:collapse;
    background:white;
    border-radius:14px;
    overflow:hidden;
}

.table-wrap th {
    background:#d8eec5;
    padding:12px;
    font-size:14px;
    text-align:center;
    font-weight:800;
    color:#2e5c35;
}

.table-wrap td {
    padding:12px;
    border-bottom:1px solid #eee;
    font-size:14px;
    text-align:center;
    font-weight:600;
    color:#3a3a3a;
}

.table-wrap tr:hover {
    background:#f3fbea;
    transition:0.2s;
}

/* 번호 색 */
.table-wrap td:first-child {
    color:#4CAF50;
    font-weight:900;
}

/* 작성자 */
.table-wrap td:nth-child(3) {
    color:#3a7d44;
}

/* 조회수 */
.table-wrap td:nth-child(4) {
    color:#777;
}

/* 좋아요 */
.table-wrap td:nth-child(5) {
    color:#ff5d9e;
    font-weight:900;
}

/* 제목 */
.title-cell a {
    color:#2e6a46;
    font-weight:800;
    transition:0.2s;
}

.title-cell a:hover {
    color:#4CAF50;
    text-shadow:0 0 5px rgba(76,175,80,0.4);
}

/* ⭐ 목록 해시태그 */
.list-tag {
    display:inline-block;
    padding:4px 9px;
    border-radius:10px;
    font-size:11px;
    margin-left:5px;
    color:white;
    font-weight:700;
}

.tag-green { background:#98e698; }
.tag-blue  { background:#8fd3ff; }
.tag-pink  { background:#ff85c0; }
.tag-yellow { background:#ffd966; }
.tag-purple { background:#d1b3ff; }

/* --------------------------- */
/* ⭐ 페이징 버튼 */
/* --------------------------- */
.pagination {
    text-align:center;
    margin-top:25px;
}

.pagination a {
    display:inline-block;
    padding:9px 15px;
    margin:0 5px;
    background:white;
    border:2px solid #b7e3c0;
    border-radius:20px;
    text-decoration:none;
    color:#4CAF50;
    font-weight:bold;
    font-size:14px;
    box-shadow:0 2px 6px rgba(0,0,0,0.05);
    transition:0.25s;
}

.pagination a:hover {
    background:#d6f5d6;
    border-color:#9ad9a5;
    transform:translateY(-2px);
}

.pagination a.active {
    background:#4CAF50;
    color:white;
    border-color:#4CAF50;
    transform:scale(1.1);
    box-shadow:0 4px 10px rgba(76,175,80,0.3);
}

/* --------------------------- */
/* ⭐ 글쓰기 버튼 */
/* --------------------------- */
.write-btn-wrap {
    width:100%;
    text-align:right;
    margin-top:15px;
}

.write-btn {
    display:inline-block;
    padding:10px 22px;
    background:#4CAF50;
    color:white;
    border-radius:20px;
    font-weight:800;
    text-decoration:none;
    box-shadow:0 3px 10px rgba(0,0,0,0.1);
    transition:0.25s;
}

.write-btn:hover {
    background:#43a047;
    transform:translateY(-2px);
}
</style>

<!-- ⭐ 랜덤 태그 2개 생성 JS (Top3 & 목록 공용) -->
<script>
    const topTags = [
        {text:"#성장중", class:"hash-green"},
        {text:"#오늘도성공", class:"hash-blue"},
        {text:"#새싹등장", class:"hash-pink"},
        {text:"#꿀팁공유", class:"hash-yellow"},
        {text:"#햇빛듬뿍", class:"hash-purple"}
    ];

    function putTopTags(targetId) {
        const box = document.getElementById(targetId);
        const shuffled = topTags.sort(() => Math.random() - 0.5);
        const pick2 = shuffled.slice(0, 2);

        pick2.forEach(t => {
            const span = document.createElement("span");
            span.className = "top3-hashtag " + t.class;
            span.innerText = t.text;
            box.appendChild(span);
        });
    }

    const tags = [
        {text:"#성장중", class:"tag-green"},
        {text:"#오늘도성공", class:"tag-blue"},
        {text:"#새싹등장", class:"tag-pink"},
        {text:"#꿀팁공유", class:"tag-yellow"},
        {text:"#햇빛듬뿍", class:"tag-purple"}
    ];

    function putRandomTags(targetId) {
        const box = document.getElementById(targetId);
        const shuffled = tags.sort(() => Math.random() - 0.5);
        const pick2 = shuffled.slice(0, 2);

        pick2.forEach(t => {
            const span = document.createElement("span");
            span.className = "list-tag " + t.class;
            span.innerText = t.text;
            box.appendChild(span);
        });
    }
</script>

</head>
<body>

<div class="list-container">

    <div class="board-title">🌱 쑥쑥 성장 이야기</div>
    <div class="board-sub">여러분의 식물 재배 성공 경험을 나누어보세요.</div>

    <!-- ⭐ Top 3 -->
    <div class="top3-wrap">
        <c:forEach var="t" items="${top3}" varStatus="s">
            <div class="top3-box">
                <div class="top3-title 
                    <c:choose>
                        <c:when test='${s.index == 0}'>gold</c:when>
                        <c:when test='${s.index == 1}'>silver</c:when>
                        <c:otherwise>bronze</c:otherwise>
                    </c:choose>
                ">
                    <span class="crown-icon">♕</span>
                    좋아요 ${s.index + 1}위
                </div>

                <div class="top3-subject">
                    <a href="/Success/read.do?idx=${t.idx}">${t.subject}</a>
                </div>

                <!-- ⭐ 랜덤 해시태그 2개 -->
                <div id="topTag-${s.index}" style="margin-top:10px;"></div>
                <script> putTopTags("topTag-${s.index}"); </script>

            </div>
        </c:forEach>
    </div>

    <!-- ⭐ 목록 테이블 -->
    <div class="table-wrap">
        <table>
            <tr>
                <th width="60">번호</th>
                <th>제목</th>
                <th width="100">작성자</th>
                <th width="70">조회수</th>
                <th width="90">좋아요</th>
            </tr>

            <c:forEach var="dto" items="${list}" varStatus="s">
                <tr>
                    <td>${dto.idx}</td>

                    <td class="title-cell">
                        <a href="/Success/read.do?idx=${dto.idx}">${dto.subject}</a>
                        <span id="tag-${s.index}"></span>
                        <script> putRandomTags("tag-${s.index}"); </script>
                    </td>

                    <td>${dto.n_name}</td>
                    <td>${dto.readcnt}</td>
                    <td>❤️ ${dto.likes}</td>
                </tr>
            </c:forEach>

        </table>
    </div>

    <!-- ⭐ 검색창 -->
    <form method="get" action="/Success/list.do">
        <div class="search-container">
            <div class="search-box">

                <select name="key">
                    <option value="subject" ${key == 'subject' ? 'selected' : ''}>제목</option>
                    <option value="n_name"  ${key == 'n_name' ? 'selected' : ''}>닉네임</option>
                </select>

                <input type="text" name="search" value="${search}" placeholder="검색어를 입력하세요">

                <button class="search-btn">검색</button>
            </div>
        </div>
    </form>

    <!-- ⭐ 페이징 -->
    <div class="pagination">
        <c:set var="pageCount" value="${totalCount / 10 + (totalCount % 10 > 0 ? 1 : 0)}" />

        <c:forEach var="i" begin="1" end="${pageCount}">
            <a href="/Success/list.do?page=${i}&key=${key}&search=${search}"
               class="${i == nowPage ? 'active' : ''}">
                ${i}
            </a>
        </c:forEach>
    </div>

    <!-- ⭐ 글쓰기 버튼 -->
    <div class="write-btn-wrap">
        <a href="/Success/write.do" class="write-btn">✏️ 글쓰기</a>
    </div>

</div>

</body>
</html>