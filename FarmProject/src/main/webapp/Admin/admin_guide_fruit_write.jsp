<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/Admin/Include/admin_topmenu.jsp" %>

<html>
<head>
    <title>초심자 가이드 등록(관리자)</title>
    <link rel="stylesheet" type="text/css" href="/css/main.css">

    <style>
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
    </style>
<script>
    function write_send(){
    	if(!guide_write.name.value){
    		alert("식물 이름을 입력해주세요.");
    		guide_write.name.focus();
    		return;
    	}
    	if (!guide_write.image_filename.value) {
    	    alert("파일을 선택해주세요.");
    	    guide_write.image_filename.focus();
    	    return;
    	}
    	if(!guide_write.best_date.value){
    		alert("파종 시기를 입력해주세요.");
    		guide_write.best_date.focus();
    		return;
    	}
    	if(guide_write.level.selectedIndex==0){
    		alert("난이도를 선택해주세요.");
    		guide_write.level.focus();
    		return;
    	}
    	if(!guide_write.water.value){
    		alert("급수량을 입력해주세요.");
    		guide_write.water.focus();
    		return;
    	}
    	if(!guide_write.medicine.value){
    		alert("비료 정보를 입력해주세요.");
    		guide_write.medicine.focus();
    		return;
    	}
    	if(!guide_write.last_date.value){
    		alert("수확 기간을 입력해주세요.");
    		guide_write.last_date.focus();
    		return;
    	}
    	if(!guide_write.place[0].checked && !guide_write.place[1].checked){
    		alert("재배 장소를 입력해주세요.");
    		guide_write.place.focus();
    		return;
    	}
    	if(!guide_write.link.value){
    		alert("링크를 첨부해주세요.");
    		guide_write.link.focus();
    		return;
    	}
    	guide_write.submit();
    }
</script>
</head>

<body>
<div class="page-wrapper">

    <!-- 오른쪽 내용 -->
    <div class="content">

        <div class="form-box">
            <h2>🌿 초심자 가이드 등록(관리자용)</h2>
			<span style="color:#D32F2F; font-size:10pt;">*등록 시 전부 기입해주세요.</span>
            <form action="admin_guide_fruit_write.do" method="post" name="guide_write" enctype="multipart/form-data">

                <div class="form-row">
                    <label>식물 이름</label>
                    <input type="text" name="name" required>
                </div>

                <div class="form-row">
                    <label>카테고리</label>
                    	<input type="text" name="category" value="과일" readonly required> 
                </div>

                <div class="form-row">
                    <label>이미지 파일</label>
                    <input type="file" name="image_filename" accept="image/*" required>
                </div>

                <div class="form-row">
                    <label>파종 시기</label>
                    <input type="text" name="best_date" placeholder="예: 3~4월" required>
                </div>

                <div class="form-row">
				    <label>난이도</label>
				    <select name="level" required>
				     	<option>난이도를 선택해주세요</option>
				        <option value="★☆☆☆☆">★☆☆☆☆ (1)</option>
				        <option value="★★☆☆☆">★★☆☆☆ (2)</option>
				        <option value="★★★☆☆">★★★☆☆ (3)</option>
				        <option value="★★★★☆">★★★★☆ (4)</option>
				        <option value="★★★★★">★★★★★ (5)</option>
				    </select>
				</div>

                <div class="form-row">
                    <label>급수량</label>
                    <input type="text" name="water" placeholder="예: 주 700ml" required>
                </div>

                <div class="form-row">
                    <label>비료 정보</label>
                    <input type="text" name="medicine" placeholder="예: NPK균형비료" required>
                </div>

                <div class="form-row">
                    <label>수확 기간</label>
                    <input type="text" name="last_date" placeholder="예: 2~3개월" required>
                </div>

                <div class="form-row">

				    <!-- 1줄: 라벨만 -->
				    <label style="display:block; margin-bottom:8px; white-space:nowrap;">
				        재배 장소 
				    </label>
				
				    <!-- 2줄: 체크박스 + 텍스트 가로 정렬 -->
				    <div style="display:flex; align-items:center; gap:25px;">
				        
				        <label style="display:flex; align-items:center; gap:6px; white-space:nowrap;">
				            <input type="checkbox" name="place" value="실내">
				            실내
				        </label>
				
				        <label style="display:flex; align-items:center; gap:6px; white-space:nowrap;">
				            <input type="checkbox" name="place" value="실외">
				            실외
				        </label>
				
				    </div>
				</div>
				
	                <div class="form-row">
	                    <label>자세히 보기 링크(URL)</label>
	                    <input type="text" name="link" placeholder="예: https://example.com/guide" required>
	                </div>
	
	                <div class="btn-wrap">
	                    <button type="button" class="btn btn-submit" onclick="write_send()">등록</button>
	                    <a href="admin_guide_fruit_list.do" class="btn btn-cancel">취소</a>
	                </div>
	
	            </form>
	        		</div>
				
				    </div>
</div>
</body>
</html>