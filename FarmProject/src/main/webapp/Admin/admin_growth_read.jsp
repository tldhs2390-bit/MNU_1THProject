<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<title>🌱 성장 게시글 상세 (관리자)</title>

<style>
body {
    background:#f4fbe9;
    font-family:'Noto Sans KR', sans-serif;
    margin:0;
    padding:0;
}

.container {
    width:80%;
    max-width:900px;
    margin:30px auto;
    background:white;
    border-radius:12px;
    padding:25px;
    box-shadow:0 2px 8px rgba(0,0,0,0.1);
}

h1 {
    font-size:26px;
    color:#2e7d32;
    margin-bottom:10px;
}

.nav-btn {
    display:inline-block;
    padding:8px 16px;
    background:#388e3c;
    color:white;
    border-radius:6px;
    text-decoration:none;
    font-weight:bold;
    margin-right:8px;
    margin-bottom:20px;
}
.nav-btn:hover { background:#2e7d32; }

.info-box {
    margin:15px 0;
    padding:12px;
    background:#e8f5e9;
    border-left:5px solid #43a047;
    border-radius:6px;
}

.label { font-weight:bold; color:#2e7d32; }

.contents-box {
    margin-top:20px;
    padding:15px;
    background:#fafafa;
    border-radius:8px;
    border:1px solid #ddd;
    white-space:pre-wrap;
}

img.post-img {
    width:300px;
    border-radius:10px;
    margin:15px 0;
}

.reply-box { margin-top:30px; }
.reply-item {
    background:white;
    border:1px solid #ddd;
    border-radius:8px;
    padding:12px;
    margin-bottom:12px;
}
.reply-hidden { background:#ffe5e5; color:#777; }

.btn {
    padding:6px 10px;
    cursor:pointer;
    font-size:13px;
    border:none;
    border-radius:6px;
    font-weight:bold;
}

.btn-hide { background:#ff9800; color:white; }
.btn-show { background:#4caf50; color:white; }
.btn-del { background:#e53935; color:white; }
.btn-edit { background:#2196f3; color:white; }
</style>

<script>
// -----------------------------------
// AJAX : 댓글 숨김/보임
// -----------------------------------
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

            let item = btn.closest(".reply-item");

            if (newStatus === 0) {
                btn.innerText = "보임";
                btn.className = "btn btn-show";
                item.classList.add("reply-hidden");

            } else {
                btn.innerText = "숨김";
                btn.className = "btn btn-hide";
                item.classList.remove("reply-hidden");
            }

            btn.setAttribute("onclick",
                `toggleReplyStatus(this, ${r_idx}, ${newStatus})`);
        }
    });
}

// -----------------------------------
// AJAX : 댓글 삭제
// -----------------------------------
function deleteReply(r_idx, btn) {

    if (!confirm("정말 삭제하시겠습니까?")) return;

    fetch("/admin_reply_delete_json.do", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: "r_idx=" + r_idx
    })
    .then(res => res.json())
    .then(data => {
        if (data.result === "OK") {
            btn.closest(".reply-item").remove();
        }
    });
}
</script>

</head>
<body>

<div class="container">

<a class="nav-btn" href="/admin_index.do">← 관리자 메인</a>
<a class="nav-btn" href="/admin_growth_read.do">← 목록으로</a>

<h1>${dto.subject}</h1>

<div class="info-box">
    <div><span class="label">작성자:</span> ${dto.n_name}</div>
    <div><span class="label">카테고리:</span> ${dto.category}</div>
    <div><span class="label">작성일:</span> ${fn:substring(dto.regdate,0,10)}</div>
    <div><span class="label">조회수:</span> ${dto.readcnt}</div>
    <div><span class="label">해시태그:</span> ${dto.hashtags}</div>
</div>

<c:if test="${not empty dto.img}">
    <img class="post-img" src="/asset/growth/${dto.img}">
</c:if>

<div class="contents-box">
    ${dto.contents}
</div>

<h2 style="margin-top:35px; color:#2e7d32;">💬 댓글 관리</h2>

<div class="reply-box">
<c:forEach var="r" items="${replyList}">
    <div class="reply-item ${r.status == 0 ? 'reply-hidden' : ''}">

        <p><b>${r.n_name}</b> (${fn:substring(r.regdate,0,16)})</p>

        <p style="white-space:pre-wrap;">${r.contents}</p>

        <c:if test="${not empty r.img}">
            <img src="/asset/reply/${r.img}" style="width:120px; border-radius:6px;">
        </c:if>

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

        <button class="btn btn-edit"
            onclick="location.href='/admin_reply_modify.do?r_idx=${r.r_idx}'">
            수정
        </button>

        <button class="btn btn-del"
            onclick="deleteReply(${r.r_idx}, this)">삭제</button>

    </div>
</c:forEach>
</div>

</div>
</body>
</html>
