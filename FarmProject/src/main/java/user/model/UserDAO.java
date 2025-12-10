package user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;
import util.UserSHA256;

public class UserDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//싱글톤
	private UserDAO() {}
	private static UserDAO instance = new UserDAO();
	public static UserDAO getInstance() {
		return instance;
	}
	//ID중복검사
	public int userIdCheck(String user_id) {
		int row=0;
		String sql="select count(*) from tbl_user where user_id=?";
		try{
			conn = DBManager.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				row = rs.getInt(1);
				//row = rs.getInt("counter");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	//회원가입
	public int userWrite(UserDTO dto) {
		int row=0;
		String sql="insert into tbl_user(user_name,n_name,tel,email,address,user_id,user_pass,point) "
				+ " values(?,?,?,?,?,?,?,?)";
		try {
			conn = DBManager.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUser_name());
			pstmt.setString(2, dto.getN_name());
			pstmt.setString(3, dto.getTel());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getAddress());
			pstmt.setString(6,dto.getUser_id());
			pstmt.setString(7, dto.getUser_pass());
			pstmt.setInt(8, 1000); //회원가입 포인트
			
			row = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}
	//로그인
	public int userLogin(String user_id, String user_pass) {
	    int row = 0;
	    String sql = "select user_pass from tbl_user where user_id=?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DBManager.getConn();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, user_id);
	        rs = pstmt.executeQuery();

	        if(rs.next()) {
	            String dbPass = rs.getString("user_pass");

	            // 입력 비밀번호 암호화
	            String encPass = UserSHA256.getSHA256(user_pass);

	            if(dbPass != null && encPass.equals(dbPass.trim())) {			
	                row = 1;
	                try {
	                    if(rs != null) rs.close();
	                    if(pstmt != null) pstmt.close();
	                    sql = "update tbl_user set last_time = now() where user_id=?";
	                    pstmt = conn.prepareStatement(sql);
	                    pstmt.setString(1, user_id);
	                    pstmt.executeUpdate();
	                } catch(Exception e) {
	                    e.printStackTrace();
	                }
	            } else {
	                row = 0; // 비밀번호 불일치
	            }
	        } else {
	            row = -1; // 아이디 없음
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(conn, pstmt, rs);
	    }
	    return row;
	}
	//로그인 성공시 세션 -- 수정시 사용
	public UserDTO userSelect(String userid) {
		UserDTO dto = new UserDTO();
		String sql="select * from tbl_user where user_id=?";
		try{
			conn = DBManager.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setUser_name(rs.getString("user_name"));
	            dto.setUser_id(rs.getString("user_id"));
	            dto.setN_name(rs.getString("n_name"));
	            dto.setTel(rs.getString("tel"));
	            dto.setEmail(rs.getString("email"));
	            dto.setAddress(rs.getString("address"));
	            dto.setUser_pass(rs.getString("user_pass"));
	            dto.setPoint(rs.getInt("point"));
	            dto.setIdx(rs.getInt("idx"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return dto;
		
	}
	//modify
		public int userModify(UserDTO dto) {
			int row = 0;
			String sql="update tbl_user set user_name=?,n_name=?,user_pass=?,tel=?,email=?,address=? where user_id=?";
			
			try {
				conn=DBManager.getConn();
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, dto.getUser_name());
				pstmt.setString(2, dto.getN_name());
				pstmt.setString(3, dto.getUser_pass());
				pstmt.setString(4, dto.getTel());
				pstmt.setString(5, dto.getEmail());
				pstmt.setString(6, dto.getAddress());
				pstmt.setString(8, dto.getUser_id());

				row = pstmt.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				DBManager.close(conn, pstmt);
			}
			return row;
		}
		
		//Point Add			****boardDAO에 addPoint 호출하기
		public void addPoint(String user_id,int point) {
			String sql="update tbl_user set point = point + ? where user_id=?";
			try {
				conn=DBManager.getConn();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, point);
				pstmt.setString(2, user_id);
				pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				DBManager.close(conn, pstmt);
			}
		}
		
		//User Find id
		public String findUserId(String email) {
			String sql="select user_id from tbl_user where email=?";
			String userId = null;
			
			try {
				conn = DBManager.getConn();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, email);
				rs=pstmt.executeQuery();
				
				if(rs.next()) {
					userId=rs.getString("user_id");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				DBManager.close(conn, pstmt, rs);
			}
			return userId;
		}
		
		//User Find Pass
		public String findUserPass(String user_id,String email) {
			String sql="select user_pass from tbl_user where user_id=? and email=?";
			String pass=null;
			try {
				conn = DBManager.getConn();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user_id);
				pstmt.setString(2, email);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					pass=rs.getString("user_pass");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				DBManager.close(conn, pstmt, rs);
			}
			return pass;
		}
		
		//User List (admin)
		public List<UserDTO> userList(){
			List<UserDTO> list = new ArrayList();
			String sql = "select * from tbl_user order by point desc";
			
			try {
				conn = DBManager.getConn();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					UserDTO dto = new UserDTO();
					dto.setUser_name(rs.getString("user_name"));
		            dto.setUser_id(rs.getString("user_id"));
		            dto.setN_name(rs.getString("n_name"));
		            dto.setTel(rs.getString("tel"));
		            dto.setEmail(rs.getString("email"));
		            dto.setAddress(rs.getString("address"));
		            dto.setUser_pass(rs.getString("user_pass"));
		            dto.setPoint(rs.getInt("point"));
		            dto.setIdx(rs.getInt("idx"));
		            list.add(dto);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				DBManager.close(conn, pstmt, rs);
			}
			return list;
		}
		
		//User Count (admin)
		public int userCount() {
			int row=0;
			String sql = "select count(*) from tbl_user";
			
			try {
				conn = DBManager.getConn();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					row=rs.getInt(1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				DBManager.close(conn, pstmt, rs);
			}
			return row;
		}
		
		//User Delete (admin)
		public int userDelete(int idx) {
		    int row = 0;
		    String sql = "delete from tbl_user where idx=?";
		    try {
		        conn = DBManager.getConn();
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setInt(1, idx);
		        row = pstmt.executeUpdate();
		    } catch(Exception e) {
		        e.printStackTrace();
		    } finally {
		        DBManager.close(conn, pstmt);
		    }
		    return row;
		}
		
		//User Modify (admin)
		public int adminUserUpdate(UserDTO dto) {
		    int row = 0;
		    String sql = "update tbl_user set n_name=?, point=? where user_id=?";

		    try {
		        conn = DBManager.getConn();
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, dto.getN_name());
		        pstmt.setInt(2, dto.getPoint());
		        pstmt.setString(3, dto.getUser_id());

		        row = pstmt.executeUpdate();
		    } catch(Exception e) {
		        e.printStackTrace();
		    } finally {
		        DBManager.close(conn, pstmt);
		    }

		    return row;
		}
		
		// 포인트 상위 5명 랭킹
		public List<UserDTO> getPointRankTop5() {
		    List<UserDTO> list = new ArrayList<>();

		    String sql = "select user_id, n_name, point from tbl_user order by point desc limit 5";

		    try {
		        conn = DBManager.getConn();
		        pstmt = conn.prepareStatement(sql);
		        rs = pstmt.executeQuery();

		        while(rs.next()) {
		            UserDTO dto = new UserDTO();
		            dto.setUser_id(rs.getString("user_id"));
		            dto.setN_name(rs.getString("n_name"));
		            dto.setPoint(rs.getInt("point"));
		            list.add(dto);
		        }
		    } catch(Exception e) {
		        e.printStackTrace();
		    } finally {
		        DBManager.close(conn, pstmt, rs);
		    }

		    return list;
		}
		
		//회원정보 닉네임 검색 (admin)
		public List<UserDTO> searchByNickname(String keyword) {
		    List<UserDTO> list = new ArrayList<>();
		    String sql = "select * from tbl_user where n_name like ? order by point desc";

		    try {
		        conn = DBManager.getConn();
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, "%" + keyword + "%");
		        rs = pstmt.executeQuery();

		        while(rs.next()) {
		            UserDTO dto = new UserDTO();
		            dto.setUser_name(rs.getString("user_name"));
		            dto.setUser_id(rs.getString("user_id"));
		            dto.setN_name(rs.getString("n_name"));
		            dto.setTel(rs.getString("tel"));
		            dto.setEmail(rs.getString("email"));
		            dto.setAddress(rs.getString("address"));
		            dto.setUser_pass(rs.getString("user_pass"));
		            dto.setPoint(rs.getInt("point"));
		            dto.setIdx(rs.getInt("idx"));
		            list.add(dto);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        DBManager.close(conn, pstmt, rs);
		    }

		    return list;
		}
		
		//닉네임 중복 검사
		public int nickCheck(String n_name) {
			int row =0;
			String sql="select count(*) from tbl_user where n_name=?";
			
			try {
				conn=DBManager.getConn();
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, n_name);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					row=rs.getInt(1); //1은 중복
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				DBManager.close(conn, pstmt, rs);
			}
			return row;
		}
		
		//회원 탈퇴
		public int userDelete(String user_id) {
			int row = 0;
			String sql = "delete from tbl_user where user_id=?";
			
			try {
				conn=DBManager.getConn();
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, user_id);
				row=pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally{
				DBManager.close(conn, pstmt);
			}
			return row;
		}
}
