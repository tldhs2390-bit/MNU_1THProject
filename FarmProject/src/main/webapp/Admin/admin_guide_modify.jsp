<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/Admin/Include/admin_topmenu.jsp" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<html>
<head>
    <title>초심자 가이드 수정(관리자)</title>
    <link rel="stylesheet" type="text/css" href="/css/main.css">

    <style>
    .form-row input[type="checkbox"] {
    width: auto !important;
    padding: 0 !important;
    margin: 0 4px 0 0;
}
        .page-wrapper { display: flex; }
        .left-menu { width: 200px; }
        .content { flex: 1; padding: 30px; }

        .form-box {
            background: white;
            padding: 25px;
            border-radius: 10px;
            border: 1px solid #C8E6C9;
            max-width: 600px;
            margin: auto;
        }

        .form-box h2 {
            color: #388E3C;
            text-align: center;
            margin-bottom: 25px;
        }

        .form-row {
            margin-bottom: 15px;
        }

        .form-row label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
            color: #2E7D32;
        }

        .form-row input,
        .form-row select,
        .form-row textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #C8E6C9;
            border-radius: 6px;
            font-size: 11pt;
        }

        .btn-wrap {
            text-align: center;
            margin-top: 25px;
        }

        .btn {
            padding: 10px 20px;
            border-radius: 6px;
            color: white;
            text-decoration: none;
            border: none;
            cursor: pointer;
            font-size: 11pt;
        }

        .btn-submit { background: #4CAF50; }
        .btn-submit:hover { background: #43A047; }

        .btn-cancel { background: #9E9E9E; }
        .btn-cancel:hover { background: #757575; }
        .chk-label {
	    display: inline-flex !important;
	    align-items: center;
	    gap: 6px;
	    margin-right: 12px;
	    font-weight: normal;
		}
    </style>
    
<script>
    function modify_send(){
    	if(!guide_modify.name.value){
    		alert("식물 이름을 입력해주세요.");
    		guide_modify.name.focus();
    		return;
    	}
    	if(guide_modify.category.selectedIndex==0){
    		alert("카데고리를 선택해주세요.");
    		guide_modify.category.focus();
    		return;
    	}
    	if(!guide_modify.best_date.value){
    		alert("파종 시기를 입력해주세요.");
    		guide_modify.best_date.focus();
    		return;
    	}
    	if(guide_modify.level.selectedIndex==0){
    		alert("난이도를 선택해주세요.");
    		guide_modify.level.focus();
    		return;
    	}
    	if(!guide_modify.water.value){
    		alert("금수량을 입력해주세요.");
    		guide_modify.water.focus();
    		return;
    	}
    	if(!guide_modify.medicine.value){
    		alert("비료 정보를 입력해주세요.");
    		guide_modify.medicine.focus();
    		return;
    	}
    	if(!guide_modify.last_date.value){
    		alert("수확 기간을 입력해주세요.");
    		guide_modify.last_date.focus();
    		return;
    	}
    	if(!guide_modify.place[0].checked && !guide_modify.place[1].checked){
    		alert("재배 장소를 입력해주세요.");
    		guide_modify.place.focus();
    		return;
    	}
    	if(!guide_modify.link.value){
    		alert("링크를 첨부해주세요.");
    		guide_modify.link.focus();
    		return;
    	}
    	guide_modify.submit();
    }
</script>
</head>

<body>
<div class="page-wrapper">

    <!-- 왼쪽 로그인 -->
    <div class="left-menu">
        <%@ include file="/Include/login_form.jsp" %>
    </div>

    <!-- 오른쪽 내용 -->
    <div class="content">

        <div class="form-box">
            <h2>🌿 초심자 가이드 등록(관리자용)</h2>
			<span style="color:#D32F2F; font-size:10pt;">*등록 시 전부 기입해주세요.</span>
            <form action="admin_guide_modify.do" method="post" name="guide_modify" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${dto.id}">
			
                <div class="form-row">
			        <label>식물 이름</label>
			        <input type="text" name="name" value="${dto.name}" required>
			    </div>
			
			    <div class="form-row">
			        <label>카테고리</label>
			        <select name="category">
			            <option value="채소" ${dto.category=="채소"?"selected":""}>채소</option>
			            <option value="과일" ${dto.category=="과일"?"selected":""}>과일</option>
			            <option value="허브" ${dto.category=="허브"?"selected":""}>허브</option>
			        </select>
			    </div>
			
			    <div class="form-row">
			        <label>이미지 파일</label>
			        <input type="file" name="image_filename" accept="image/*">
			        <div>현재 파일: ${dto.image_filename}</div>
			    </div>
			
			    <div class="form-row">
			        <label>파종 시기</label>
			        <input type="text" name="best_date" value="${dto.best_date}">
			    </div>
			
			    <div class="form-row">
			        <label>난이도</label>
			        <select name="level">
			            <option value="★☆☆☆☆" ${dto.level=="★☆☆☆☆"?"selected":""}>★☆☆☆☆</option>
			            <option value="★★☆☆☆" ${dto.level=="★★☆☆☆"?"selected":""}>★★☆☆☆</option>
			            <option value="★★★☆☆" ${dto.level=="★★★☆☆"?"selected":""}>★★★☆☆</option>
			            <option value="★★★★☆" ${dto.level=="★★★★☆"?"selected":""}>★★★★☆</option>
			            <option value="★★★★★" ${dto.level=="★★★★★"?"selected":""}>★★★★★</option>
			        </select>
			    </div>
			
			    <div class="form-row">
			        <label>급수량</label>
			        <input type="text" name="water" value="${dto.water}">
			    </div>
			
			    <div class="form-row">
			        <label>비료 정보</label>
			        <input type="text" name="medicine" value="${dto.medicine}">
			    </div>
			
			    <div class="form-row">
			        <label>수확 기간</label>
			        <input type="text" name="last_date" value="${dto.last_date}">
			    </div>
			
			    <div class="form-row">
				    <label style="margin-bottom:8px;">재배 장소</label>
				
				    <label class="chk-label">
				        <input type="checkbox" name="place" value="실내"
				            ${fn:contains(dto.place, '실내') ? 'checked' : ''}> 실내
				    </label>
				
				    <label class="chk-label">
				        <input type="checkbox" name="place" value="실외"
				            ${fn:contains(dto.place, '실외') ? 'checked' : ''}> 실외
				    </label>
				</div>
			
			    <div class="form-row">
			        <label>자세히 보기 링크</label>
			        <input type="text" name="link" value="${dto.link}">
			    </div>
			
			   <div class="btn-wrap">
                   <button type="button" class="btn btn-submit" onclick="modify_send()">수정</button>
                   <a href="admin_guide_list.do" class="btn btn-cancel">취소</a>
               </div>
			</form>
			</body>
			</html>