<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/Include/topmenu.jsp" %>

<!-- loginUser 불러오기 -->
<c:set var="loginUser" value="${sessionScope.user.n_name}" />

<html>
<head>
<title>🌱 성장 이야기 - 글 보기</title>

<style>
body { background:#f4fbe9; font-family:'Noto Sans KR',sans-serif; margin:0; padding:0; }
@keyframes fadeIn { from {opacity:0; transform:translateY(10px);} to {opacity:1; transform:translateY(0);} }

.read-container { width:80%; max-width:900px; margin:40px auto; background:white; border-radius:25px; padding:45px; border:3px solid #d8eec5; animation:fadeIn .4s; }
.read-title { font-size:30px; font-weight:900; color:#4CAF50; margin-bottom:15px; }
.read-info { color:#666; margin-bottom:25px; font-size:14px; }
.read-img { width:100%; max-height:380px; object-fit:cover; border-radius:18px; border:3px solid #e6f5da; margin-bottom:25px; }
.read-contents { font-size:17px; line-height:1.7; margin-bottom:30px; }

.tag { padding:6px 12px; border-radius:12px; margin-right:8px; color:white; font-size:13px; font-weight:700; }
.tag-vegetable { background:#60b860; }
.tag-fruit { background:#7dc2ff; }
.tag-herb { background:#be9cff; }

.emotion-btn { padding:10px 16px; border-radius:12px; background:#f2f2f2; margin-right:10px; cursor:pointer; font-size:18px; transition:.15s; }
.emotion-btn:hover { background:#e9e9e9; transform:translateY(-2px); }

.btn-area { text-align:center; margin-top:30px; }
.btn { padding:12px 24px; border:none; border-radius:14px; color:white; font-weight:700; cursor:pointer; }
.btn-list { background:#8bd17c; }
.btn-edit { background:#6cb8ff; }
.btn-delete { background:#ff7070; }

.btn-row {
    display:flex;
    justify-content:center;
    gap:20px;
}

.reply-wrap { margin-top:40px; background:white; padding:25px; border-radius:20px; border:3px solid #d8eec5; }
.reply-item { padding:10px 15px; border-bottom:1px solid #eee; margin-bottom:10px; }
.reply-child { margin-left:30px; background:#f8fff1; border-radius:10px; padding:10px; }
.reply-name { font-weight:800; color:#4CAF50; }
.reply-text { margin:6px 0; }
.reply-actions a { font-size:13px; margin-right:8px; cursor:pointer; }
.reply-actions a.modify { color:#6cb8ff; }
.reply-actions a.delete { color:#ff7070; }
.reply-emoji { cursor:pointer; margin-right:8px; font-size:17px; }
.reply-emoji:hover { transform:scale(1.1); }

.popup-bg, .limit-popup-bg, #replyModifyPopup {
    position:fixed; top:0; left:0; width:100%; height:100%;
    background:rgba(0,0,0,.45); display:none; justify-content:center; align-items:center; z-index:5000;
}

.popup-box, .limit-popup-box {
    width:330px; background:white; padding:25px; border-radius:20px;
    text-align:center; animation:pop .25s ease-out;
    border:3px solid #cbe6b0;
}

@keyframes pop { 0% {transform:scale(0.6); opacity:0;} 100% {transform:scale(1); opacity:1;} }

.popup-input { width:90%; padding:10px; border-radius:10px; border:2px solid #cfe8c8; margin-bottom:20px; font-size:15px; }

.popup-btns button { padding:10px 18px; border-radius:10px; border:none; cursor:pointer; font-weight:700; color:white; }
.popup-confirm { background:#ff7070; }
.popup-cancel { background:#6cb8ff; }

.limit-popup-box { border:3px solid #ffd5d5; }
.limit-popup-title { font-size:20px; font-weight:900; color:#ff6b6b; margin-bottom:6px; }
.limit-popup-msg { font-size:15px; color:#555; margin-bottom:14px; }
.limit-popup-btn { padding:8px 18px; background:#ff6b6b; color:white; border-radius:10px; border:none; font-weight:700; cursor:pointer; }
</style>

<script>
function emotion(idx, type){
    fetch("/growth_emotion.do", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "idx=" + idx + "&type=" + type
    })
    .then(res => res.json())
    .then(data => {
        if (data.result === "limit-over") {
            document.getElementById("emotionLimitPopup").style.display="flex";
            return;
        }
        document.getElementById("post-emotion-like").innerHTML = "❤️ " + data.current.like;
        document.getElementById("post-emotion-sym").innerHTML  = "👍 " + data.current.sym;
        document.getElementById("post-emotion-sad").innerHTML  = "😢 " + data.current.sad;
    });
}

function replyEmotion(ridx, type){
    fetch("/reply_emotion.do?r_idx=" + ridx + "&type=" + type)
        .then(r => r.text())
        .then(t => {
            if(t === "limit-over"){
                document.getElementById("emotionLimitPopup").style.display="flex";
                return;
            }
        });
}

function openModifyPopup(r_idx, raw) {
    let decoded = raw.replace(/&lt;/g,"<")
                     .replace(/&gt;/g,">")
                     .replace(/&amp;/g,"&")
                     .replace(/&#39;/g,"'")
                     .replace(/&quot;/g,'"');

    document.getElementById("modify_r_idx").value = r_idx;
    document.getElementById("modify_contents").value = decoded;
    document.getElementById("replyModifyPopup").style.display = "flex";
}
function closeModifyPopup(){ document.getElementById("replyModifyPopup").style.display="none"; }
function submitModify(){ document.getElementById("modifyForm").submit(); }

function openDeletePopup(){ document.getElementById("deletePopup").style.display="flex"; }
function closeDeletePopup(){ document.getElementById("deletePopup").style.display="none"; }
</script>

</head>
<body>

<div class="read-container">

    <div class="read-title">${dto.subject}</div>
    <div class="read-info">
        작성자: ${dto.n_name} | 날짜: ${dto.regdate} | 조회수: ${dto.readcnt}
    </div>

    <c:if test="${not empty dto.img}">
        <img src="${pageContext.request.contextPath}/asset/growth/${dto.img}" width="300">
    </c:if>

    <div class="read-contents">${dto.contents}</div>

    <!-- 태그 출력 -->
    <div>
        <c:forEach items="${fn:split(dto.hashtags, ',')}" var="tag">
            <c:choose>
                <c:when test="${fn:contains(tag,'채소')}"><span class="tag tag-vegetable">${tag}</span></c:when>
                <c:when test="${fn:contains(tag,'과일')}"><span class="tag tag-fruit">${tag}</span></c:when>
                <c:otherwise><span class="tag tag-herb">${tag}</span></c:otherwise>
            </c:choose>
        </c:forEach>
    </div>

    <br>

    <!-- 게시글 감정 -->
    <div>
        <span id="post-emotion-like" class="emotion-btn" onclick="emotion(${dto.idx}, 'like')">❤️ ${dto.like_cnt}</span>
        <span id="post-emotion-sym" class="emotion-btn" onclick="emotion(${dto.idx}, 'sym')">👍 ${dto.sym_cnt}</span>
        <span id="post-emotion-sad" class="emotion-btn" onclick="emotion(${dto.idx}, 'sad')">😢 ${dto.sad_cnt}</span>
    </div>

    <!-- ⭐ 한 줄: 목록 + 수정 + 삭제 -->
    <div class="btn-area btn-row">
        <button class="btn btn-list" onclick="location.href='/growth_list.do?page=${dto.category}'">📚 목록</button>

        <c:if test="${loginUser == dto.n_name}">
            <button class="btn btn-edit" onclick="location.href='/growth_modify.do?idx=${dto.idx}'">✏ 수정</button>
            <button class="btn btn-delete" onclick="openDeletePopup()">🗑 삭제</button>
        </c:if>
    </div>

    <!-- ❌ ★★ 아래 중복된 수정/삭제 버튼 블록은 삭제됨 ★★ -->

    <!-- 댓글 전체 영역 -->
    <div class="reply-wrap">
        <h3 style="font-size:22px; font-weight:900; color:#4CAF50;">💬 댓글</h3>

        <form action="/reply_write.do" method="post">
            <input type="hidden" name="post_idx" value="${dto.idx}">
            <input type="hidden" name="parent" value="0">
            <textarea name="contents" style="width:100%; height:70px; border-radius:10px; border:2px solid #cfe8c8; padding:10px;" placeholder="댓글을 입력하세요"></textarea>
            <button style="margin-top:10px; padding:8px 16px; background:#7ad67a; border:none; border-radius:10px; color:white;">등록</button>
        </form>

        <hr style="margin:25px 0;">

        <c:forEach items="${replyList}" var="r">

            <c:if test="${r.parent == 0}">
                <div class="reply-item">
                    <div class="reply-name">${r.n_name}</div>
                    <div class="reply-text">${r.contents}</div>

                    <div style="margin-top:7px;">
                        <span id="reply-${r.r_idx}-like" class="reply-emoji" onclick="replyEmotion(${r.r_idx}, 'like')">❤️ ${r.like_cnt}</span>
                        <span id="reply-${r.r_idx}-sym" class="reply-emoji" onclick="replyEmotion(${r.r_idx}, 'sym')">👍 ${r.sym_cnt}</span>
                        <span id="reply-${r.r_idx}-sad" class="reply-emoji" onclick="replyEmotion(${r.r_idx}, 'sad')">😢 ${r.sad_cnt}</span>
                    </div>

                    <c:if test="${loginUser == r.n_name}">
                    <div class="reply-actions">
                        <a class="modify" onclick="openModifyPopup(${r.r_idx}, '${fn:escapeXml(r.contents)}')">수정</a>
                        <a class="delete" href="/reply_delete.do?r_idx=${r.r_idx}&post_idx=${dto.idx}">삭제</a>
                    </div>
                    </c:if>

                    <form action="/reply_write.do" method="post" style="margin-top:10px;">
                        <input type="hidden" name="post_idx" value="${dto.idx}">
                        <input type="hidden" name="parent" value="${r.r_idx}">
                        <input type="text" name="contents" placeholder="대댓글 작성…" style="width:70%; padding:7px; border-radius:10px; border:2px solid #cfe8c8;">
                        <button style="padding:8px 16px; background:#7ad67a; border:none; border-radius:10px; color:white;">등록</button>
                    </form>
                </div>
            </c:if>

            <c:if test="${r.parent != 0}">
                <div class="reply-item reply-child">
                    <div class="reply-name">${r.n_name}</div>
                    <div class="reply-text">${r.contents}</div>

                    <div style="margin-top:7px;">
                        <span class="reply-emoji" onclick="replyEmotion(${r.r_idx}, 'like')">❤️ ${r.like_cnt}</span>
                        <span class="reply-emoji" onclick="replyEmotion(${r.r_idx}, 'sym')">👍 ${r.sym_cnt}</span>
                        <span class="reply-emoji" onclick="replyEmotion(${r.r_idx}, 'sad')">😢 ${r.sad_cnt}</span>
                    </div>

                    <c:if test="${loginUser == r.n_name}">
                    <div class="reply-actions">
                        <a class="modify" onclick="openModifyPopup(${r.r_idx}, '${fn:escapeXml(r.contents)}')">수정</a>
                        <a class="delete" href="/reply_delete.do?r_idx=${r.r_idx}&post_idx=${dto.idx}">삭제</a>
                    </div>
                    </c:if>
                </div>
            </c:if>

        </c:forEach>

    </div>
</div>

<!-- 댓글 수정 팝업 -->
<div id="replyModifyPopup" class="popup-bg">
    <div class="popup-box">
        <div class="popup-title">댓글 수정</div>

        <form id="modifyForm" method="post" action="/reply_modify.do">
            <input type="hidden" id="modify_r_idx" name="r_idx">
            <input type="hidden" name="post_idx" value="${dto.idx}">
            <textarea id="modify_contents" name="contents" class="popup-input" style="height:120px;"></textarea>

            <div class="popup-btns">
                <button type="button" class="popup-confirm" onclick="submitModify()">수정 완료</button>
                <button type="button" class="popup-cancel" onclick="closeModifyPopup()">취소</button>
            </div>
        </form>
    </div>
</div>

<!-- 게시글 삭제 팝업 -->
<div id="deletePopup" class="popup-bg">
    <div class="popup-box">
        <div class="popup-title">게시글 삭제</div>
        <form action="/growth_delete.do" method="post">
            <input type="hidden" name="idx" value="${dto.idx}">
            <input type="password" name="pass" class="popup-input" placeholder="비밀번호 입력">

            <div class="popup-btns">
                <button class="popup-confirm">삭제</button>
                <button type="button" class="popup-cancel" onclick="closeDeletePopup()">취소</button>
            </div>
        </form>
    </div>
</div>

<!-- 감정 제한 팝업 -->
<div id="emotionLimitPopup" class="limit-popup-bg">
    <div class="limit-popup-box">
        <div class="limit-popup-title">⏳ 감정 제한</div>
        <div class="limit-popup-msg">6시간 동안 감정은 최대 5번까지 가능합니다!</div>
        <button class="limit-popup-btn" onclick="this.parentElement.parentElement.style.display='none'">확인</button>
    </div>
</div>

</body>
</html>