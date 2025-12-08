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
		
		//1. 채소, 과일, 허브  조회 메소드
		public List<GuideDTO> GuideList(){
			//반환값 정의
			List<GuideDTO> guideList = new ArrayList<GuideDTO>();
			//쿼리
			String sql="select * from tbl_guide ORDER BY name ASC";
			try {
				conn = DBManager.getConn();
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					GuideDTO dto = new GuideDTO();
					dto.setId(rs.getInt("id"));
					dto.setName(rs.getString("name").trim());
					dto.setCategory(rs.getString("category"));
					dto.setBest_date(rs.getString("best_date"));
					dto.setLevel(rs.getString("level"));
					dto.setWater(rs.getString("water"));
					dto.setMedicine(rs.getString("medicine"));
					dto.setLast_date(rs.getString("last_date"));
					dto.setLink(rs.getString("link"));
					dto.setImage_filename(rs.getString("image_filename").trim());
					dto.setPlace(rs.getString("place"));

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
		//2. 채소,조회 메소드
				public List<GuideDTO> vegList(){
					//반환값 정의
					List<GuideDTO> vegList = new ArrayList<GuideDTO>();
					//쿼리
					String sql = "SELECT * FROM tbl_guide WHERE category = '채소' ORDER BY name ASC";
					try {
						conn = DBManager.getConn();
						pstmt = conn.prepareStatement(sql);
						
						rs = pstmt.executeQuery();
						while(rs.next()) {
							GuideDTO dto = new GuideDTO();
							dto.setName(rs.getString("name").trim());
							dto.setCategory(rs.getString("category"));
							dto.setBest_date(rs.getString("best_date"));
							dto.setLevel(rs.getString("level"));
							dto.setWater(rs.getString("water"));
							dto.setMedicine(rs.getString("medicine"));
							dto.setLast_date(rs.getString("last_date"));
							dto.setLink(rs.getString("link"));
							dto.setImage_filename(rs.getString("image_filename").trim());
							dto.setPlace(rs.getString("place"));
							

							vegList.add(dto);
						}
						
					}catch(Exception e) {
						e.printStackTrace();
					}finally {
						//insert, update, delete
						DBManager.close(conn, pstmt);
					}
					return vegList;
				}
				
		//3. 허브,조회 메소드
		public List<GuideDTO> herbList(){
			//반환값 정의
			List<GuideDTO> herbList = new ArrayList<GuideDTO>();
			//쿼리
			String sql = "SELECT * FROM tbl_guide WHERE category = '허브' ORDER BY name ASC";
			try {
				conn = DBManager.getConn();
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					GuideDTO dto = new GuideDTO();
					dto.setName(rs.getString("name").trim());
					dto.setCategory(rs.getString("category"));
					dto.setBest_date(rs.getString("best_date"));
					dto.setLevel(rs.getString("level"));
					dto.setWater(rs.getString("water"));
					dto.setMedicine(rs.getString("medicine"));
					dto.setLast_date(rs.getString("last_date"));
					dto.setLink(rs.getString("link"));
					dto.setImage_filename(rs.getString("image_filename").trim());
					dto.setPlace(rs.getString("place"));

					herbList.add(dto);
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				//insert, update, delete
				DBManager.close(conn, pstmt);
			}
			return herbList;
		}
		//3. 과일,조회 메소드
		public List<GuideDTO> fruitList(){
			//반환값 정의
			List<GuideDTO> fruitList = new ArrayList<GuideDTO>();
			//쿼리
			String sql = "SELECT * FROM tbl_guide WHERE category = '과일' ORDER BY name ASC";
			try {
				conn = DBManager.getConn();
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					GuideDTO dto = new GuideDTO();
					dto.setName(rs.getString("name").trim());
					dto.setCategory(rs.getString("category"));
					dto.setBest_date(rs.getString("best_date"));
					dto.setLevel(rs.getString("level"));
					dto.setWater(rs.getString("water"));
					dto.setMedicine(rs.getString("medicine"));
					dto.setLast_date(rs.getString("last_date"));
					dto.setLink(rs.getString("link"));
					dto.setImage_filename(rs.getString("image_filename").trim());
					dto.setPlace(rs.getString("place"));

					fruitList.add(dto);
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				//insert, update, delete
				DBManager.close(conn, pstmt);
			}
			return fruitList;
		}
		//4.초심자 가이드 등록
		public int guideWrite(GuideDTO dto) {
	        int row = 0;

	        String sql = "INSERT INTO tbl_guide "
	                + "(name, category, best_date, level, water, medicine, last_date, link, image_filename,  place) "
	                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	        try {
	            Connection conn = DBManager.getConn();
	            PreparedStatement pstmt = conn.prepareStatement(sql);

	            pstmt.setString(1, dto.getName());
	            pstmt.setString(2, dto.getCategory());
	            pstmt.setString(3, dto.getBest_date());
	            pstmt.setString(4, dto.getLevel());
	            pstmt.setString(5, dto.getWater());
	            pstmt.setString(6, dto.getMedicine());
	            pstmt.setString(7, dto.getLast_date());
	            pstmt.setString(8, dto.getLink());
	            pstmt.setString(9, dto.getImage_filename());
	            pstmt.setString(10, dto.getPlace());

	            row = pstmt.executeUpdate();

	            pstmt.close();
	            conn.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return row;
	    }
		//5.id에 대한 목록 출력
		public GuideDTO guideSelect(int id) {
			GuideDTO dto = new GuideDTO();
			String sql="select * from tbl_guide where id=?";
			try {
				conn = DBManager.getConn();
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					dto.setId(rs.getInt("id"));
					dto.setName(rs.getString("name"));
					dto.setCategory(rs.getString("category"));
					dto.setBest_date(rs.getString("best_date"));
					dto.setLevel(rs.getString("level"));
					dto.setWater(rs.getString("water"));
					dto.setMedicine(rs.getString("medicine"));
					dto.setLast_date(rs.getString("last_date"));
					dto.setLink(rs.getString("link"));
					dto.setImage_filename(rs.getString("image_filename"));
					dto.setPlace(rs.getString("place"));
				}
			}catch(Exception e) {
				e.printStackTrace();
			
			}finally {
				DBManager.close(conn, pstmt, rs);
			}
			return dto;
		}
		//6.초심자 가이드 수정
		public int guideUpdate(GuideDTO dto) {
		    int row = 0;
		    String sql = "UPDATE tbl_guide SET "
		            + "name=?, category=?,  best_date=?, level=?, water=?, "
		            + "medicine=?, last_date=?, link=?, image_filename=?, place=?"
		            + "WHERE id=?";

		    try {
		        conn = DBManager.getConn();
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, dto.getName());
		        pstmt.setString(2, dto.getCategory()); 
		        pstmt.setString(3, dto.getBest_date());
		        pstmt.setString(4, dto.getLevel());
		        pstmt.setString(5, dto.getWater());
		        pstmt.setString(6, dto.getMedicine());
		        pstmt.setString(7, dto.getLast_date());
		        pstmt.setString(8, dto.getLink());
		        pstmt.setString(9, dto.getImage_filename());
		        pstmt.setString(10, dto.getPlace());
		        pstmt.setInt(11, dto.getId());
		        row = pstmt.executeUpdate();

		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        DBManager.close(conn, pstmt);
		    }
		    return row;
		}
		//7. 초심자 가이드 삭제
		public int guideDelete(int id) {
		    int row = 0;
		    String sql = "DELETE FROM tbl_guide WHERE id=?";

		    try {
		        conn = DBManager.getConn();
		        pstmt = conn.prepareStatement(sql);

		        pstmt.setInt(1, id);
		        row = pstmt.executeUpdate();

		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        DBManager.close(conn, pstmt);
		    }
		    return row;
		}
		
}
