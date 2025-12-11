<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/Admin/Include/admin_topmenu.jsp" %>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 수정</title>

<style>
    body { 
        margin:0; 
        background:#f0f7e8;   
        font-family:"Noto Sans KR"; 
    }

    .page-wrapper {
        display:flex; 
        justify-content:center; 
        padding:40px 20px;
    }

    .content-area {
        width:800px;
        background:#ffffff;
        border:2px solid #d8eec5;
        padding:30px;
        border-radius:12px;
        box-shadow:0 4px 15px rgba(0,0,0,0.08);
    }

    .board-title {
        font-size:28px;
        font-weight:700;
        color:#4CAF50;
        margin-bottom:25px;
        text-align:left;
    }

    .form-table {
        width:100%;
        border-collapse:collapse;
    }

    .form-table td {
        padding:10px 8px;
        font-size:14px;
        vertical-align:middle;
    }

    /* 글쓴이, 비밀번호 입력창 너비 25% */
input[name="name"], input[name="pass"] {
    width:25%;
    padding:10px;
    border:1px solid #cfe8c8;
    border-radius:6px;
    font-size:14px;
    box-sizing:border-box;
}

/* 제목 입력창 너비 50% 유지 */
input[name="subject"] {
    width:50%;
    padding:10px;
    border:1px solid #cfe8c8;
    border-radius:6px;
    font-size:14px;
    box-sizing:border-box;
}

    /* 내용 칸 */
    textarea[name="contents"] {
        width:100%;
        height:450px;
        padding:12px;
        border:1px solid #cfe8c8;
        border-radius:6px;
        font-size:14px;
        box-sizing:border-box;
        resize:vertical;
    }

    .btn-area {
        margin-top:25px;
        text-align:right;
    }

    .btn {
        display:inline-block;
        padding:12px 18px;
        border-radius:8px;
        border:none;
        cursor:pointer;
        font-weight:700;
        font-size:14px;
        margin-left:12px;
        text-decoration:none;
        color:white;
        transition:0.25s;
    }

    .btn-save { background:#4CAF50; }
    .btn-cancel { background:#ef5350; }

</style>

<script>
function board_send(){
    if(board.subject.value==""){
        alert("제목을 입력하세요.");
        board.subject.focus();
        return;
    }
    if(board.contents.value==""){
        alert("내용을 입력하세요.");
        board.contents.focus();
        return;
    }
    if(board.pass.value==""){
        alert("비밀번호를 입력하세요.");
        board.pass.focus();
        return;
    }
    board.submit();
};
</script>

</head>
<body>
<div class="page-wrapper">
    <div class="content-area">
        <div class="board-title">📝 공지사항 수정</div>

        <form name="board" method="post" action="admin_board_modify.do">
        <input type="hidden" name="idx" value="${dto.idx}">
        <input type="hidden" name="page" value="${page}">
            <table class="form-table">
            	<tr>
                    <td style="text-align:left;">제목 *</td>
                    <td><input type="text" name="subject" value="${dto.subject }"></td>
                </tr>
                <tr>
                    <td style="width:120px; text-align:left;">작성자 *</td>
                    <td><input type="text" name="name" value="관리자" readonly></td>
                </tr>
                
                <tr>
                    <td style="text-align:left;">비밀번호 *</td>
                    <td><input type="password" name="pass"></td>
                </tr>
            </table>

            <div style="margin-top:20px;">
                <label for="contents" style="display:block; margin-bottom:5px;">내용 *</label>
                <textarea id="contents" name="contents">${dto.contents }</textarea>
            </div>

            <div class="btn-area">
                <a href="javascript:board_send()" class="btn btn-save">수정</a>
                <a href="javascript:history.back()" class="btn btn-cancel">취소</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>