<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<title>📝 댓글 관리</title>

<style>
body {
    background:#f4fbe9;;
    font-family:'Noto Sans KR', sans-serif;
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
    display:flex;
    align-items:center;
    gap:10px;
}

/* 돌아가기 버튼 */
.nav-btn {
    display:inline-block;
    padding:8px 16px;
    background:#388e3c;
    color:white;
    border-radius:6px;
    text-decoration:none;
    font-weight:bold;
    margin-right:10px;
    transition:0.2s;
}
.nav-btn:hover { background:#2e7d32; }

/* 테이블 */
.table-wrap { margin-top:20px; }

table {
    width:100%;
    border-collapse:collapse;
    background:white;
    border-radius:10px;
    overflow:hidden;
    box-shadow:0 2px 6px rgba(0,0,0,0.1);
}

thead {
    background:#1b5e20;
    color:white;
    font-size:18px;
}

th, td {
    padding:14px;
    border-bottom:1px solid #ddd;
    text-align:center;
    font-size:16px;
}

.reply-hidden {
    background:#ffe5e5 !important;
    color:#777;
}

.btn {
    padding:6px 12px;
    border:none;
    border-radius:6px;
    color:white;
    font-weight:bold;
    cursor:pointer;
}

.btn-edit { background:#2196f3; }
.btn-del  { background:#e53935; }
.btn-hide { background:#fb8c00; }
.btn-show { background:#43a047; }
</style>

<script>
// ---------------------------------------------
// AJAX : 댓글 숨김/보임
// ---------------------------------------------
function toggleReplyStatus(btn, r_idx, curStatus) {

    let newStatus = (curStatus == 1 ? 0 : 1);

    fetch("/admin_reply_status_json.do", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: "r_idx=" + r_idx + "&status=" + newStatus
    })
    .then(res => res.json())
    .then(data => {

        if (data.result === "OK") {

            let row = btn.closest("tr");

            if (newStatus === 0) {
                btn.innerText = "보임";
                btn.className = "btn btn-show";
                row.classList.add("reply-hidden");

            } else {
                btn.innerText = "숨김";
                btn.className = "btn btn-hide";
                row.classList.remove("reply-hidden");
            }

            // 버튼 상태 갱신
            btn.setAttribute("onclick",
                `toggleReplyStatus(this, ${r_idx}, ${newStatus})`);
        }
    });
}

// ---------------------------------------------
// AJAX : 댓글 삭제
// ---------------------------------------------
function deleteReply(btn, r_idx) {

    if (!confirm("정말 삭제하시겠습니까?")) return;

    fetch("/admin_reply_delete_json.do", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: "r_idx=" + r_idx
    })
    .then(res => res.json())
    .then(data => {
        if (data.result === "OK") {
            btn.closest("tr").remove(); // 화면에서 즉시 제거
        }
    });
}
</script>

</head>
<body>

<div class="container">

<!-- 돌아가기 버튼 -->
<div style="margin-bottom:20px;">
    <a href="/admin_index.do" class="nav-btn">← 관리자 메인으로</a>
    <a href="/admin_growth_list.do" class="nav-btn">← 성장 게시판으로</a>
</div>

<h1>📝 댓글 전체 관리</h1>

<div class="table-wrap">

<table>
<thead>
<tr>
    <th>No</th>
    <th>게시글 번호</th>
    <th>작성자</th>
    <th>내용</th>
    <th>작성일</th>
    <th>상태</th>
    <th>수정</th>
    <th>삭제</th>
</tr>
</thead>

<tbody>
<c:forEach var="r" items="${list}" varStatus="st">
<tr class="${r.status == 0 ? 'reply-hidden' : ''}">

    <td>${st.index + 1}</td>
    <td>${r.post_idx}</td>
    <td>${r.n_name}</td>

    <td style="max-width:250px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">
        ${r.contents}
    </td>

    <td>${fn:substring(r.regdate,0,16)}</td>

    <td>
        <c:choose>
            <c:when test="${r.status == 1}">
                <button class="btn btn-hide"
                        onclick="toggleReplyStatus(this, ${r.r_idx}, 1)">숨김</button>
            </c:when>
            <c:otherwise>
                <button class="btn btn-show"
                        onclick="toggleReplyStatus(this, ${r.r_idx}, 0)">보임</button>
            </c:otherwise>
        </c:choose>
    </td>

    <td>
        <button class="btn btn-edit"
                onclick="location.href='/admin_reply_modify.do?r_idx=${r.r_idx}'">
            수정
        </button>
    </td>

    <td>
        <button class="btn btn-del"
                onclick="deleteReply(this, ${r.r_idx})">삭제</button>
    </td>

</tr>
</c:forEach>
</tbody>

</table>

</div>

</div>

</body>
</html>