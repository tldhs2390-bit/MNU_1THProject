<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/Include/topmenu.jsp" %>

<%
    String loginUser = (String)session.getAttribute("n_name");
%>

<html>
<head>
<title>🌱 쑥쑥 성장 이야기 - 글 보기</title>

<style>
body {
    background:#f4fbe9;
    font-family:'Noto Sans KR', sans-serif;
    margin:0; padding:0;
}

.read-container {
    width:85%;
    max-width:900px;
    margin:40px auto;
    background:white;
    border-radius:20px;
    padding:40px;
    border:3px solid #d8eec5;
    box-shadow:0 8px 25px rgba(0,0,0,0.08);
    animation:fadeIn 0.4s ease-in-out;
}

@keyframes fadeIn {
    from {opacity:0; transform:translateY(15px);}
    to {opacity:1; transform:translateY(0);}
}

.read-title {
    font-size:32px;
    font-weight:900;
    color:#4CAF50;
    margin-bottom:18px;
}

/* 정보 박스 */
.info-box {
    background:#f9fff4;
    border:2px solid #d8eec5;
    padding:18px 20px;
    border-radius:14px;
    margin-bottom:18px;
    display:flex;
    justify-content:space-between;
    align-items:center;
}

.info-left {
    color:#444;
    font-size:15px;
    line-height:1.7;
}

.info-left b {
    color:#2f7a2f;
    font-weight:900;
}

/* 해시태그 */
.tag-box {
    display:flex;
    gap:10px;
    flex-wrap:wrap;
    margin-bottom:28px;
    margin-top:10px;
}

.tag {
    padding:6px 14px;
    border-radius:20px;
    font-weight:700;
    font-size:14px;
}

.tag.mint { background:#a8f0b8; color:#035427; }
.tag.yellow { background:#ffe98e; color:#6a5500; }

/* 좋아요 */
.like-area {
    font-size:22px;
    display:flex;
    align-items:center;
}

.heart {
    font-size:32px;
    cursor:pointer;
    color:#bbb;
    transition:0.25s;
    margin-right:6px;
}
.heart.active {
    color:#ff4f8b;
    transform:scale(1.25);
}

/* 본문 */
.read-content {
    font-size:17px;
    white-space:pre-line;
    line-height:1.7;
    margin-bottom:30px;
    padding:35px 30px;
    border-radius:18px;
    background:linear-gradient(180deg, #ffffff 0%, #f4fff4 100%);
    border:2px solid #d8eec5;
    min-height:150px;
    position:relative;
}

.read-content::before {
    content:"🌱 오늘의 성장 이야기";
    position:absolute;
    top:-18px;
    left:20px;
    background:#f9fff4;
    padding:3px 10px;
    font-size:13px;
    color:#4CAF50;
    border-radius:10px;
    border:1px solid #d6eacb;
}

/* 버튼 */
.btn-box {
    text-align:right;
    margin-bottom:40px;
}

.btn-box a {
    display:inline-block;
    padding:10px 20px;
    border-radius:10px;
    font-weight:700;
    text-decoration:none;
    color:white;
    transition:0.25s;
    margin-left:10px;
}

/* 목록 민트 */
.btn-list { background:#9ce8c1; }
.btn-list:hover { background:#82d7ad; }

/* 수정 파스텔 블루 */
.btn-modify { background:#9cc9ff; }
.btn-modify:hover { background:#84b7f0; }

/* 삭제 파스텔 핑크 */
.btn-delete { background:#ffb4c8; }
.btn-delete:hover { background:#e79caf; }
</style>

<script>
let likeLock = false; 

function toggleLike(idx){
    if(likeLock) return;
    likeLock = true;

    fetch("/Success/like.do?idx=" + idx)
        .then(r => r.text())
        .then(data => {

            const heart = document.getElementById("heartIcon");
            const countTag = document.getElementById("likeCount");
            let count = parseInt(countTag.innerText);

            if (data === "not-login") {
                alert("로그인 후 이용해주세요!");
                likeLock = false;
                return;
            }

            if (data === "limit") {
                alert("오늘은 더 이상 좋아요를 할 수 없습니다!");
                likeLock = false;
                return;
            }

            if (data === "liked") {
                heart.classList.add("active");
                countTag.innerText = count + 1;
            } else if (data === "unliked") {
                heart.classList.remove("active");
                countTag.innerText = count - 1;
            }

            likeLock = false;
        });
}
</script>

</head>
<body>

<div class="read-container">

    <!-- 제목 -->
    <div class="read-title">${dto.subject}</div>

    <!-- 정보 박스 -->
    <div class="info-box">
        <div class="info-left">
            작성자 : <b>${dto.n_name}</b><br>
            날짜 : ${fn:substring(dto.regdate,0,10)}
            &nbsp; | &nbsp;
            조회수 : <b>${dto.readcnt}</b>
        </div>

        <div class="like-area">
            <span id="heartIcon" class="heart ${dto.userLiked ? 'active' : ''}"
                  onclick="toggleLike(${dto.idx})">❤️</span>
            <span id="likeCount">${dto.likes}</span>
        </div>
    </div>

    <!-- ⭐ 해시태그 영역 -->
    <c:if test="${not empty dto.hashtag}">
        <div class="tag-box">
            <c:set var="tags" value="${fn:split(dto.hashtag, ' ')}"/>
            <c:forEach var="t" items="${tags}" varStatus="s">
                <span class="tag ${s.index % 2 == 0 ? 'mint' : 'yellow'}">${t}</span>
            </c:forEach>
        </div>
    </c:if>

    <!-- 본문 -->
    <div class="read-content">${dto.contents}</div>

    <!-- 버튼 -->
    <div class="btn-box">
        <a href="/Success/list.do" class="btn-list">목록</a>
        <a href="/Success/modify.do?idx=${dto.idx}" class="btn-modify">수정</a>
        <a href="/Success/delete.do?idx=${dto.idx}" class="btn-delete">삭제</a>
    </div>
    <!-- --------------------------------------- -->
    <!-- 댓글 전체 영역 -->
    <!-- --------------------------------------- -->
    <style>
    .comment-wrap {
        margin-top:40px;
        padding:30px 35px;
        background:white;
        border-radius:20px;
        border:3px solid #d8eec5;
        box-shadow:0 6px 20px rgba(0,0,0,0.05);
    }

    .comment-title {
        font-size:26px;
        font-weight:900;
        color:#4CAF50;
        margin-bottom:25px;
        border-left:8px solid #4CAF50;
        padding-left:12px;
    }

    .comment-item {
        background:#f4fbe9;
        border:2px solid #d0e9c3;
        padding:20px 22px;
        border-radius:18px;
        margin-bottom:20px;
        position:relative;
        animation:fadeIn 0.3s ease-in-out;
    }

    .comment-item::before {
        content:"";
        position:absolute;
        top:-10px; left:22px;
        border-left:10px solid transparent;
        border-right:10px solid transparent;
        border-bottom:10px solid #d0e9c3;
    }
    .comment-item::after {
        content:"";
        position:absolute;
        top:-8px; left:24px;
        border-left:8px solid transparent;
        border-right:8px solid transparent;
        border-bottom:8px solid #f4fbe9;
    }

    .comment-header {
        display:flex;
        justify-content:space-between;
        font-size:14px;
    }

    .comment-content {
        margin-top:12px;
        white-space:pre-line;
        line-height:1.5;
    }

    /* 대댓글 */
    .reply-item {
        margin-left:40px;
        background:#eef9ff;
        border:2px solid #bde0ff;
        border-left:6px solid #8bc6ff !important;
    }

    /* 댓글 버튼 */
    .comment-btns {
        margin-top:14px;
        display:flex;
        align-items:center;
        gap:12px;
    }

    .comment-btns a {
        font-size:13px;
        cursor:pointer;
        color:#4CAF50;
    }

    /* 댓글 좋아요 */
    .c-heart {
        font-size:22px;
        cursor:pointer;
        transition:0.25s;
        color:#bbb;
    }
    .c-heart.active {
        color:#ff5b9e;
        transform:scale(1.25);
    }

    /* 댓글 입력 박스 */
    .comment-input-box {
        background:#f4fbe9;
        border:2px solid #d0e9c3;
        border-radius:18px;
        padding:25px;
        margin-top:35px;
    }

    .comment-input-box input,
    .comment-input-box textarea {
        width:100%;
        padding:12px;
        border-radius:10px;
        border:1px solid #c8ddb9;
        font-size:15px;
        margin-bottom:12px;
    }

    .comment-input-box button {
        padding:10px 20px;
        background:#4CAF50;
        color:white;
        border:none;
        border-radius:12px;
        font-size:15px;
        font-weight:700;
        cursor:pointer;
        transition:0.25s;
    }
    .comment-input-box button:hover {
        background:#43a047;
        transform:translateY(-2px);
    }

    /* 슬라이드 박스 */
    .reply-slide {
        max-height:0;
        overflow:hidden;
        opacity:0;
        transition:max-height 0.35s ease, opacity 0.35s ease;
    }
    .reply-slide.open {
        max-height:300px;
        opacity:1;
    }
    </style>

    <!-- 댓글 스크립트 -->
    <script>
    // 댓글 좋아요
    let commentLikeLock = {};

    function likeComment(c_idx, tag){
        if(commentLikeLock[c_idx]) return;
        commentLikeLock[c_idx] = true;

        fetch("/Success/Reply/like.do?c_idx=" + c_idx)
            .then(r=>r.text())
            .then(cnt=>{
                tag.classList.add("active");
                document.getElementById("cLike_" + c_idx).innerText = cnt;
                commentLikeLock[c_idx] = false;
            });
    }

    // 대댓글 열기
    function openReply(c_idx){
        document.querySelectorAll(".reply-slide").forEach(b=>b.classList.remove("open"));
        const box = document.getElementById("replyBox_" + c_idx);
        box.classList.add("open");
        setTimeout(()=> box.scrollIntoView({behavior:"smooth"}), 200);
    }

    // 댓글 수정 열기
    function openEdit(c_idx){
        document.querySelectorAll(".reply-slide").forEach(b=>b.classList.remove("open"));
        const box = document.getElementById("editBox_" + c_idx);
        box.classList.add("open");
        const textarea = box.querySelector("textarea");
        setTimeout(()=> textarea.focus(), 200);
    }
    </script>


    <!-- 댓글 리스트 출력 -->
    <div class="comment-wrap">
        <div class="comment-title">💬 댓글</div>

        <c:forEach var="c" items="${commentList}">

            <div class="comment-item">

                <div class="comment-header">
                    <b style="color:#4CAF50;">${c.n_name}</b>
                    <span style="color:#777;">${fn:substring(c.regdate,0,16)}</span>
                </div>

                <div class="comment-content">${c.contents}</div>

                <div class="comment-btns">
                    <span class="c-heart" onclick="likeComment(${c.c_idx}, this)">❤</span>
                    <span id="cLike_${c.c_idx}">${c.likes}</span>

                    <a onclick="openReply(${c.c_idx})">답글</a>

                    <c:if test="${loginUser == c.n_name}">
                        <a onclick="openEdit(${c.c_idx})">수정</a>
                        <a href="/Success/Reply/delete.do?c_idx=${c.c_idx}&idx=${dto.idx}">삭제</a>
                    </c:if>
                </div>

                <!-- 댓글 수정 박스 -->
                <div id="editBox_${c.c_idx}" class="reply-slide">
                    <form action="/Success/Reply/modify.do" method="post">
                        <input type="hidden" name="idx" value="${dto.idx}">
                        <input type="hidden" name="c_idx" value="${c.c_idx}">
                        <textarea rows="3" name="contents">${c.contents}</textarea>
                        <button>수정 완료</button>
                    </form>
                </div>

                <!-- 대댓글 박스 -->
                <div id="replyBox_${c.c_idx}" class="reply-slide">
                    <form action="/Success/Reply/write.do" method="post">
                        <input type="hidden" name="idx" value="${dto.idx}">
                        <input type="hidden" name="parent_idx" value="${c.c_idx}">
                        <input type="text" name="n_name" value="${loginUser}" readonly>
                        <textarea rows="3" name="contents" placeholder="대댓글 입력..."></textarea>
                        <button>등록</button>
                    </form>
                </div>

            </div>

            <!-- 대댓글 출력 -->
            <c:forEach var="r" items="${c.replyList}">
                <div class="comment-item reply-item">

                    <div class="comment-header">
                        <b style="color:#4CAF50;">${r.n_name}</b>
                        <span style="color:#777;">${fn:substring(r.regdate,0,16)}</span>
                    </div>

                    <div class="comment-content">${r.contents}</div>

                    <div class="comment-btns">
                        <span class="c-heart" onclick="likeComment(${r.c_idx}, this)">❤</span>
                        <span id="cLike_${r.c_idx}">${r.likes}</span>

                        <c:if test="${loginUser == r.n_name}">
                            <a onclick="openEdit(${r.c_idx})">수정</a>
                            <a href="/Success/Reply/delete.do?c_idx=${r.c_idx}&idx=${dto.idx}">삭제</a>
                        </c:if>
                    </div>

                    <!-- 대댓글 수정 -->
                    <div id="editBox_${r.c_idx}" class="reply-slide">
                        <form action="/Success/Reply/modify.do" method="post">
                            <input type="hidden" name="idx" value="${dto.idx}">
                            <input type="hidden" name="c_idx" value="${r.c_idx}">
                            <textarea rows="3" name="contents">${r.contents}</textarea>
                            <button>수정 완료</button>
                        </form>
                    </div>

                </div>
            </c:forEach>

        </c:forEach>

        <!-- 새 댓글 입력 -->
        <form class="comment-input-box" method="post" action="/Success/Reply/write.do">
            <input type="hidden" name="idx" value="${dto.idx}">
            <input type="hidden" name="parent_idx" value="0">
            <input type="text" name="n_name" value="${loginUser}" readonly>
            <textarea rows="3" name="contents" placeholder="댓글을 입력하세요"></textarea>
            <button>댓글 등록</button>
        </form>

    </div>

</div>

</body>
</html>
    