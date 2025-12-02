package user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DBManager;

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
		String sql="insert into tbl_user(user_name,n_name,tel,email,address,user_rank,user_id,user_pass) "
				+ " values(?,?,?,?,?,?,?,?)";
		try {
			conn = DBManager.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUser_name());
			pstmt.setString(2, dto.getN_name());
			pstmt.setString(3, dto.getTel());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getAddress());
			pstmt.setString(6, dto.getUser_rank());
			pstmt.setString(7,dto.getUser_id());
			pstmt.setString(8, dto.getUser_pass());
			
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

					System.out.println("== 로그인 디버깅 ==");
					System.out.println("입력한 아이디: " + user_id);
					System.out.println("입력한  비번: [" + user_pass + "]");
					System.out.println("DB 저장 비번: [" + dbPass + "]");

					if(dbPass != null && user_pass.equals(dbPass.trim())) {
						
						row = 1;

						try {

							if(rs != null) rs.close();
							if(pstmt != null) pstmt.close();
							
							sql = "update tbl_user set last_time = now() where user_id=?";
							pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, user_id);
							pstmt.executeUpdate();
						} catch(Exception e) {
							
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
				dto.setTel(rs.getString("tel"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return dto;
		
	}
}
