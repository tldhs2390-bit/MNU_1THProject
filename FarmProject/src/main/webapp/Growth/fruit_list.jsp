<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/Include/topmenu.jsp" %>

<html>
<head>
<title>🍎 과일 이야기 - 목록</title>

<style>
body {
    background:#f7f1ef;
    font-family:'Noto Sans KR', sans-serif;
    margin:0; padding:0;
}

.container {
    width:85%;
    max-width:900px;
    margin:30px auto;
    position:relative;
}

/* 돌아가기 버튼 */
.back-btn {
    position:absolute;
    right:0;
    top:-10px;
    padding:10px 18px;
    background:#e28c7c;
    color:white;
    border:none;
    border-radius:10px;
    font-weight:700;
    cursor:pointer;
    transition:0.2s;
}
.back-btn:hover {
    background:#d27d6e;
    transform:scale(1.05);
}

/* 제목 */
.page-title {
    text-align:center;
    font-size:32px;
    font-weight:900;
    color:#d8746a;
    margin-bottom:25px;
}

/* 검색 박스 */
.search-container {
    display:flex;
    align-items:center;
    gap:12px;
    background:#fbeeea;
    border:2px solid #f0d6d1;
    padding:15px 20px;
    border-radius:18px;
    margin-bottom:22px;
}

.search-select {
    padding:10px 14px;
    border:2px solid #e4c3be;
    border-radius:10px;
    background:white;
    font-size:14px;
    font-weight:700;
    color:#d8746a;
}

.search-input {
    flex:1;
    padding:10px 14px;
    border:2px solid #e4c3be;
    border-radius:10px;
    font-size:14px;
}

/* 버튼 */
.fruit-btn {
    padding:10px 20px;
    background:#e28c7c;
    color:white;
    border:none;
    border-radius:12px;
    font-weight:800;
    cursor:pointer;
    transition:0.2s;
}
.fruit-btn:hover {
    background:#d27d6e;
    transform:scale(1.05);
}

/* 테이블 */
.list-table {
    width:100%;
    border-collapse:collapse;
    background:white;
    border-radius:15px;
    overflow:hidden;
    border:3px solid #f0d6d1;
}

.list-table th {
    background:#f0d6d1;
    padding:12px;
    font-size:15px;
}

.list-table td {
    padding:14px;
    border-bottom:1px solid #eee;
    text-align:center;
}

.list-table tr:hover {
    background:#f8ebe7;
}

/* 글쓰기 버튼 */
.write-btn-box {
    width:100%;
    display:flex;
    justify-content:flex-end;
    margin:18px 0 5px;
}

.write-btn {
    padding:10px 20px;
    background:#e28c7c;
    color:white;
    border-radius:10px;
    font-weight:700;
    transition:0.25s;
    text-decoration:none;
}
.write-btn:hover {
    background:#d27d6e;
    transform:scale(1.07);
}

/* ⭐ 새 파스텔 페이징 */
.page-wrap {
    margin:25px 0 40px;
    text-align:center;
}

.page-num {
    display:inline-flex;
    justify-content:center;
    align-items:center;

    width:42px;
    height:42px;

    background:white;
    border:2px solid #f0d6d1;
    border-radius:14px;
    color:#d8746a;
    font-weight:800;
    text-decoration:none;
    margin:0 5px;

    transition:0.25s;
}

.page-num:hover {
    background:#f8ebe7;
    border-color:#dfc2bd;
    transform:translateY(-2px);
}

/* 현재 페이지 애니메이션 (숫자만 표시) */
.page-num.active {
    background:#e28c7c;
    border-color:#e28c7c;
    color:white;
    animation:jump 0.55s ease-in-out infinite alternate;
}

@keyframes jump {
    0% { transform:translateY(0); }
    100% { transform:translateY(-5px); }
}
</style>

</head>

<body>

<div class="container">

    <!-- 돌아가기 버튼 -->
    <button class="back-btn" onclick="location.href='/growth_list.do'">🔙 전체 목록</button>

    <!-- 제목 -->
    <div class="page-title">🍎 과일 이야기</div>

    <!-- 검색 -->
    <form action="/growth_list.do" method="get" class="search-container">

        <input type="hidden" name="ctype" value="fruit">
        <input type="hidden" name="category" value="fruit">

        <select name="key" class="search-select">
            <option value="subject" ${keyValue == 'subject' ? 'selected' : ''}>제목</option>
            <option value="n_name" ${keyValue == 'n_name' ? 'selected' : ''}>작성자</option>
        </select>

        <input type="text" name="search" value="${searchValue}" class="search-input">

        <button type="submit" class="fruit-btn">검색</button>
        <button type="button" onclick="location.href='/growth_list.do?ctype=fruit'" class="fruit-btn">
            초기화
        </button>

    </form>

    <!-- 목록 -->
    <table class="list-table">
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>조회수</th>
            <th>감정</th>
            <th>날짜</th>
        </tr>

        <c:forEach items="${list}" var="dto" varStatus="st">
            <c:if test="${dto.category == 'fruit'}">
                <tr onclick="location.href='/growth_read.do?idx=${dto.idx}'" style="cursor:pointer">

                    <td>${(page - 1) * 10 + st.index + 1}</td>
                    <td>${dto.subject}</td>
                    <td>${dto.n_name}</td>
                    <td>${dto.readcnt}</td>

                    <td>
                        ❤️ ${dto.like_cnt}
                        👍 ${dto.sym_cnt}
                        😢 ${dto.sad_cnt}
                    </td>

                    <td>${fn:substring(dto.regdate, 0, 10)}</td>
                </tr>
            </c:if>
        </c:forEach>

        <c:if test="${empty list}">
            <tr>
                <td colspan="6" style="padding:15px;">등록된 글이 없습니다.</td>
            </tr>
        </c:if>
    </table>

    <!-- 글쓰기 버튼 -->
    <div class="write-btn-box">
        <a href="/growth_write.do" class="write-btn">✏️ 글쓰기</a>
    </div>

    <!-- ⭐ 숫자만 나오는 패스텔 페이징 (최종 정상버전) -->
    <div class="page-wrap">

        <c:forEach var="i" begin="${startPage}" end="${endPage}">
            <a href="?ctype=fruit&page=${i}&key=${keyValue}&search=${searchValue}"
               class="page-num ${page == i ? 'active' : ''}">
               ${i}
            </a>
        </c:forEach>

    </div>

</div>

</body>
</html>
