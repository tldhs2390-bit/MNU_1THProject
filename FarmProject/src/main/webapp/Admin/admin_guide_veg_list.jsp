<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/Admin/Include/admin_topmenu.jsp" %>

<html>
<head>
    <title>채소 가이드 목록(관리자용)</title>
    <link rel="stylesheet" type="text/css" href="/css/main.css">

    <style>
    .page-wrapper {
        display: flex;
        justify-content: center; /* 가로 가운데 정렬 */
        padding: 20px 0; /* 위아래 여백 */
    }

    .content {
        width: 1000px; /* 원하는 최대 너비 */
        padding: 20px;
        box-sizing: border-box;
    }

    table.guide-table { 
        width: 100%; 
        border-collapse: collapse; 
    }

    table.guide-table td { 
        padding: 15px; 
        text-align: center; 
    }

        .guide-card {
            width: 160px;
            background: white;
            border: 1px solid #C8E6C9;
            border-radius: 10px;
            padding: 10px;
            box-shadow: 0 3px 6px rgba(0,0,0,0.05);
            transition: transform 0.2s;
        }
        .guide-card:hover { transform: scale(1.05); }

        .guide-card img {
            width: 100%;
            height: 120px;
            object-fit: cover;
            border-radius: 8px;
            margin-bottom: 8px;
        }
        
        .detail-btn {
		    display: inline-block;
		    margin-top: 10px;
		    padding: 6px 10px;
		    background: #4CAF50;
		    color: white;
		    font-size: 12pt;
		    border-radius: 6px;
		    text-decoration: none;
		    transition: background 0.2s;
		}
		
		.detail-btn:hover {
		    background: #43A047;
		}
    </style>
<script>
	function guideDelete(id){
		if(confirm("정말 삭제하시겠습니까?")){
			location.href="admin_guide_delete.do?id=" + id;
		}
	}

</script>
</head>

<body>
<div class="page-wrapper">

    
    <!-- 오른쪽 -->
    <div class="content">
        <h1 class="main-title">🥬 채소 가이드(관리자용)</h1>
		<p>아파트에서도 쉽게 키우는 12종 채소</p>
		<div style="margin-top: 30px; text-align: left;">
		    <a href="admin_guide_write.do" 
		       style="padding:10px 20px; background:#4CAF50; color:white; border-radius:8px; text-decoration:none; margin-right:10px;">
		        카드 등록
		    </a>
		</div>
		
        	<table class="guide-table">
    			<tr>
        		<c:set var="count" value="0"/>
        		<c:forEach var="veg" items="${vegList}">
           	  	<!-- name이 '채소'인 것만 출력 -->
            	<c:if test="${veg.category eq '채소'}">
				<c:set var="count" value="${count + 1}" />

                	<td>
                   	<a href="${veg.link}" target="_blank">
                    <div class="guide-card">
                        <img src="${pageContext.request.contextPath}/img/guide/${fn:escapeXml(veg.image_filename)}" alt="${veg.name}"></a>
                        <h3>${veg.name}</h3>
                        <p>카테고리: ${veg.category}</p>
                        <p>파종 시기: ${veg.best_date}</p>
                        <p>난이도: ${veg.level}</p>
                        <p>급수: ${veg.water}</p>
                        <p>비료: ${veg.medicine}</p>
                        <p>수확 기간: ${veg.last_date}</p>
                        <p>재배 장소: ${veg.place}</p>
							<!-- 자세히 보기 버튼 -->
                   			<a href="${veg.link}" target="_blank" class="detail-btn">
                        	🔍 자세히 보기
                        	</a>
                       	<!-- 수정 / 삭제 버튼 추가 -->
						<div style="margin-top:10px;">
						    <a href="admin_guide_modify.do?id=${veg.id}"
						       style="padding:6px 10px; background:#FFC107; color:white; border-radius:6px; text-decoration:none; margin-right:5px;">
						        수정
						    </a>
						    <a href="javascript:void(0);" onclick="guideDelete(${veg.id});"
						       style="padding:6px 10px; background:#F44336; color:white; border-radius:6px; text-decoration:none;">
						        삭제
						    </a>
						</div>
                    </div>
                	</td>

                	<c:if test="${count % 5 == 0}">
                    	</tr><tr>
                	</c:if>

            		</c:if>
       				</c:forEach>
    					</tr>
				</table>

    				</div>
					</div>
</body>
</html>