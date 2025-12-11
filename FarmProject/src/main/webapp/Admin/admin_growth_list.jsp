<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="/Admin/Include/admin_topmenu.jsp" %>

<html>
<head>
<title>🌱 성장 게시판 관리</title>

<style>
/* ============================= */
/* 관리자 페이지 메인 디자인 */
/* ============================= */

body {
    background:#f4fbe9;
    font-family:'Noto Sans KR', sans-serif;
    color:#222;
    margin:0;
    padding:0;
}

.container {
    width:90%;
    margin:30px auto;
}

/* 제목 */
h1 {
    color:#2e7d32;
    margin-bottom:25px;
    font-size:28px;
    border-left:6px solid #43a047;
    padding-left:12px;
}

/* 돌아가기 버튼 */
.back-btn {
    display:inline-block;
    margin-bottom:20px;
    padding:8px 16px;
    background:#388e3c;
    color:white;
    font-weight:bold;
    border-radius:6px;
    text-decoration:none;
    transition:0.2s;
}
.back-btn:hover {
    background:#2e7d32;
}

/* ============================= */
/* 검색 + 필터 */
/* ============================= */
.filter-box {
    display:flex;
    gap:10px;
    margin-bottom:20px;
}

.filter-box select,
.filter-box input {
    padding:8px 10px;
    border:1px solid #bbb;
    border-radius:6px;
    background:white;
    font-size:13px;
}

.filter-btn {
    padding:8px 14px;
    background:#2e7d32;
    border:none;
    border-radius:6px;
    color:white;
    cursor:pointer;
    font-weight:bold;
}

/* ============================= */
/* 테이블 */
/* ============================= */
table {
    width:100%;
    border-collapse:collapse;
    background:white;
    border-radius:10px;
    overflow:hidden;
    box-shadow:0 2px 6px rgba(0,0,0,0.1);
}

thead {
    background:#2e7d32;
    color:white;
}

th, td {
    padding:12px;
    border-bottom:1px solid #ddd;
    text-align:center;
}

/* 숨김 표시 */
.hidden-row {
    background:#ffe5e5 !important;
    color:#999;
}

/* ============================= */
/* 버튼 */
/* ============================= */
.btn {
    padding:6px 10px;
    border:none;
    border-radius:6px;
    font-weight:bold;
    cursor:pointer;
    font-size:13px;
}

.btn-hide { background:#ff9800; color:white; }
.btn-show { background:#4caf50; color:white; }
.btn-edit { background:#2196f3; color:white; }
.btn-del { background:#f44336; color:white; }

/* ============================= */
/* 페이징 */
/* ============================= */
.page-box {
    text-align:center;
    margin-top:25px;
}

.page-box a {
    margin:0 5px;
    padding:7px 11px;
    border:1px solid #bbb;
    border-radius:6px;
    color:#333;
    text-decoration:none;
}

.page-box a.active {
    background:#2e7d32;
    color:white;
    border-color:#2e7d32;
}
</style>

<script>
function toggleStatus(btn, idx, curStatus) {
    let newStatus = curStatus == 1 ? 0 : 1;

    fetch("${pageContext.request.contextPath}/admin_growth_hide.do?idx=" + idx + "&status=" + newStatus)
        .then(res => res.text())
        .then(data => {

            if (data.trim() === "OK") {

                let row = btn.closest("tr");

                if (newStatus === 0) {
                    btn.innerText = "보임";
                    btn.className = "btn btn-show";
                    btn.setAttribute("onclick", `toggleStatus(this, ${idx}, 0)`);
                    row.classList.add("hidden-row");

                } else {
                    btn.innerText = "숨김";
                    btn.className = "btn btn-hide";
                    btn.setAttribute("onclick", `toggleStatus(this, ${idx}, 1)`);
                    row.classList.remove("hidden-row");
                }
            } else {
                alert("상태 변경 실패");
            }
        });
}

function deletePost(idx) {
    if (confirm("정말 삭제하시겠습니까?")) {
        location.href = "${pageContext.request.contextPath}/admin_growth_delete.do?idx=" + idx;
    }
}
</script>

</head>
<body>

<div class="container">

<h1>🌱 성장 게시판 관리</h1>

<form method="get" action="${pageContext.request.contextPath}/admin_growth_list.do">
<div class="filter-box">

    <select name="key">
        <option value="subject" ${key=="subject"?"selected":""}>제목</option>
        <option value="n_name" ${key=="n_name"?"selected":""}>작성자</option>
        <option value="category" ${key=="category"?"selected":""}>카테고리</option>
    </select>

    <input type="text" name="word" value="${word}" placeholder="검색어 입력">

    <select name="sort">
        <option value="recent" ${sort=="recent"?"selected":""}>최신순</option>
        <option value="old" ${sort=="old"?"selected":""}>오래된순</option>
        <option value="read" ${sort=="read"?"selected":""}>조회순</option>
        <option value="like" ${sort=="like"?"selected":""}>좋아요순</option>
        <option value="sym" ${sort=="sym"?"selected":""}>공감순</option>
        <option value="sad" ${sort=="sad"?"selected":""}>아쉬워순</option>
    </select>

    <select name="statusFilter">
        <option value="show" ${statusFilter=="show"?"selected":""}>보임</option>
        <option value="hide" ${statusFilter=="hide"?"selected":""}>숨김</option>
        <option value="all" ${statusFilter=="all"?"selected":""}>전체</option>
    </select>

    <button class="filter-btn">적용</button>
</div>
</form>

<table>
<thead>
<tr>
    <th>번호</th>
    <th>카테고리</th>
    <th>제목</th>
    <th>작성자</th>
    <th>작성일</th>
    <th>조회수</th>
    <th>좋아요</th>
    <th>공감</th>
    <th>아쉬워</th>
    <th>상태</th>
    <th>수정</th>
    <th>삭제</th>
</tr>
</thead>

<tbody>
<c:forEach var="dto" items="${list}" varStatus="st">

<tr class="${dto.status == 0 ? 'hidden-row' : ''}">

    <!-- ⭐⭐⭐ 번호 역순 공식 적용 (성장게시판과 동일) -->
    <td>${totalCount - ((page - 1) * 10 + st.index)}</td>

    <td>${dto.category}</td>

    <td>
        <a href="${pageContext.request.contextPath}/admin_growth_read.do?idx=${dto.idx}"
           style="color:#1e88e5; font-weight:bold; text-decoration:none;">
            ${dto.subject}
        </a>
    </td>

    <td>${dto.n_name}</td>
    <td>${fn:substring(dto.regdate, 0, 10)}</td>
    <td>${dto.readcnt}</td>
    <td>${dto.like_cnt}</td>
    <td>${dto.sym_cnt}</td>
    <td>${dto.sad_cnt}</td>

    <td>
        <c:choose>
            <c:when test="${dto.status == 1}">
                <button class="btn btn-hide"
                        onclick="toggleStatus(this, ${dto.idx}, 1)">숨김</button>
            </c:when>
            <c:otherwise>
                <button class="btn btn-show"
                        onclick="toggleStatus(this, ${dto.idx}, 0)">보임</button>
            </c:otherwise>
        </c:choose>
    </td>

    <td>
        <button class="btn btn-edit"
                onclick="location.href='${pageContext.request.contextPath}/admin_growth_modify.do?idx=${dto.idx}'">수정</button>
    </td>

    <td>
        <button class="btn btn-del" onclick="deletePost(${dto.idx})">삭제</button>
    </td>

</tr>

</c:forEach>
</tbody>
</table>

<div class="page-box">
<c:forEach begin="1" end="${totalPage}" var="p">
    <a href="${pageContext.request.contextPath}/admin_growth_list.do?page=${p}&key=${key}&word=${word}&sort=${sort}&statusFilter=${statusFilter}"
       class="${page == p ? 'active' : ''}">
        ${p}
    </a>
</c:forEach>
</div>

</div>

</body>
</html>