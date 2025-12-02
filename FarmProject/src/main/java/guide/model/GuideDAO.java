package guide.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import util.DBManager;

public class GuideDAO {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		
		//1. 전체 채소, 과일, 허브 조회 메소드
		public List<GuideDTO> GuideList(){
			//반환값 정의
			List<GuideDTO> guideList = new ArrayList();
			//쿼리
			String sql="select * from tbl_guide";
			try {
				conn = DBManager.getConn();
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					GuideDTO dto = new GuideDTO();
					dto.setName(rs.getString("name").trim());
					dto.setCategory(rs.getString("category"));
					dto.setBest_date(rs.getString("best_date"));
					dto.setLevel(rs.getString("Level"));
					dto.setWater(rs.getString("water"));
					dto.setMedicine(rs.getString("medicine"));
					dto.setLast_date(rs.getString("last_date"));
					

					guideList.add(dto);
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				//insert, update, delete
				DBManager.close(conn, pstmt);
			}
			return guideList;
		}
		
}
	
