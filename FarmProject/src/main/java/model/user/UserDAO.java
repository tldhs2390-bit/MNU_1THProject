package model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;
import util.UserSHA256;

public class UserDAO {
   Connection conn = null;
   PreparedStatement pstmt = null;
   ResultSet rs = null;
   
   // 싱글톤
   private UserDAO() {}
   private static UserDAO instance = new UserDAO();
   public static UserDAO getInstance() {
      return instance;
   }

   // ===========================
   // ID 중복 검사
   // ===========================
   public int userIdCheck(String user_id) {
      int row = 0;
      String sql = "select count(*) from tbl_user where user_id=?";
      try {
         conn = DBManager.getConn();
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, user_id);
         rs = pstmt.executeQuery();
         if (rs.next()) row = rs.getInt(1);
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBManager.close(conn, pstmt, rs);
      }
      return row;
   }

   // ===========================
// 회원가입
   // ===========================
   public int userWrite(UserDTO dto) {
      int row = 0;

      // 신규 회원 가입 시 today_point = 0 자동
      String sql = "insert into tbl_user(user_name,n_name,tel,email,address,user_id,user_pass,point,today_point,last_point_date) "
            + "values(?,?,?,?,?,?,?,?,0,null)";
      try {
         conn = DBManager.getConn();
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, dto.getUser_name());
         pstmt.setString(2, dto.getN_name());
         pstmt.setString(3, dto.getTel());
         pstmt.setString(4, dto.getEmail());
         pstmt.setString(5, dto.getAddress());
         pstmt.setString(6, dto.getUser_id());
         pstmt.setString(7, dto.getUser_pass());

         // 초기 포인트 1,000P 부여
         pstmt.setInt(8, 1000);

         row = pstmt.executeUpdate();

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBManager.close(conn, pstmt);
      }
      return row;
   }

   // ===========================
   // 로그인
   // ===========================
   public int userLogin(String user_id, String user_pass) {
       int row = 0;
       String sql = "select user_pass from tbl_user where user_id=?";

       try {
           conn = DBManager.getConn();
           pstmt = conn.prepareStatement(sql);
           pstmt.setString(1, user_id);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               String dbPass = rs.getString("user_pass");
               String encPass = UserSHA256.getSHA256(user_pass);

               if (dbPass != null && encPass.equals(dbPass.trim())) {
                   row = 1;

                   // 마지막 로그인 시간 업데이트
                   DBManager.close(null, pstmt, rs);

                 
                   pstmt = conn.prepareStatement(sql);
                   pstmt.setString(1, user_id);
                   pstmt.executeUpdate();
               } else {
                   row = 0;
               }
           } else {
               row = -1;
           }
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           DBManager.close(conn, pstmt, rs);
       }
       return row;
   }

   // ===========================
   // 로그인 사용자 정보 가져오기
   // ===========================
   public UserDTO userSelect(String userid) {
      UserDTO dto = new UserDTO();
      String sql = "select * from tbl_user where user_id=?";
      try {
         conn = DBManager.getConn();
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, userid);
         rs = pstmt.executeQuery();

         if (rs.next()) {
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
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBManager.close(conn, pstmt, rs);
      }
      return dto;
   }

   // ===========================
   // 회원정보 수정
   // ===========================
   public int userModify(UserDTO dto) {
      int row = 0;
      String sql = "update tbl_user set user_name=?, n_name=?, user_pass=?, tel=?, email=?, address=? where user_id=?";

      try {
         conn = DBManager.getConn();
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, dto.getUser_name());
         pstmt.setString(2, dto.getN_name());
         pstmt.setString(3, dto.getUser_pass());
         pstmt.setString(4, dto.getTel());
         pstmt.setString(5, dto.getEmail());
         pstmt.setString(6, dto.getAddress());
         pstmt.setString(7, dto.getUser_id());

         row = pstmt.executeUpdate();

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBManager.close(conn, pstmt);
      }
      return row;
   }

   // ===========================
   // 하루 포인트 제한 적용된 addPoint (100P 고정 지급)
   // ===========================
   public boolean addPointLimit(String user_id) {

      try {
         conn = DBManager.getConn();

         // 1) 사용자 정보 조회
         String sql = "SELECT idx, today_point, last_point_date FROM tbl_user WHERE user_id=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, user_id);
         rs = pstmt.executeQuery();

         int idx = 0;
         int todayPoint = 0;
         Date lastDate = null;

         if (rs.next()) {
            idx = rs.getInt("idx");
            todayPoint = rs.getInt("today_point");
            lastDate = rs.getDate("last_point_date");
         }

         LocalDate today = LocalDate.now();

         // 날짜가 바뀌었으면 초기화
         if (lastDate == null || !lastDate.toLocalDate().equals(today)) {

            String resetSql = "UPDATE tbl_user SET today_point = 0, last_point_date=? WHERE idx=?";
            PreparedStatement ps = conn.prepareStatement(resetSql);
            ps.setDate(1, java.sql.Date.valueOf(today));
            ps.setInt(2, idx);
            ps.executeUpdate();
            ps.close();

            todayPoint = 0;
         }

         int reward = 100; // 글쓰기 1회 = 100P
         int limit = 200;  // 하루 최대 200P

         // 이미 오늘 200포인트 이상 받은 경우 → 추가 지급 금지
         if (todayPoint + reward > limit) {
            return false;
         }

         // 포인트 지급
         String updateSql =
            "UPDATE tbl_user SET point = point + ?, today_point = today_point + ?, last_point_date=? WHERE idx=?";
         pstmt = conn.prepareStatement(updateSql);
         pstmt.setInt(1, reward);
         pstmt.setInt(2, reward);
         pstmt.setDate(3, java.sql.Date.valueOf(today));
         pstmt.setInt(4, idx);
         pstmt.executeUpdate();

         return true;

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBManager.close(conn, pstmt, rs);
      }

      return false;
   }

   // ===========================
   // find id
   // ===========================
   public String findUserId(String email) {
      String sql = "select user_id from tbl_user where email=?";
      String userId = null;

      try {
         conn = DBManager.getConn();
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, email);
         rs = pstmt.executeQuery();

         if (rs.next()) {
            userId = rs.getString("user_id");
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBManager.close(conn, pstmt, rs);
      }
      return userId;
   }

 //비밀번호 찾기 시 임시 비밀번호-1
   public int findPassCheck(String userId, String email) {
	    int row = 0;
	    String sql = "SELECT count(*) FROM tbl_user WHERE user_id=? AND email=?";
	    try {
	        conn = DBManager.getConn();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, userId);
	        pstmt.setString(2, email);
	        rs = pstmt.executeQuery();
	        if(rs.next()) row = rs.getInt(1);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(conn, pstmt, rs);
	    }
	    return row;
	}
   //비밀번호 찾기 시 임시 비밀번호-2
   public int updatePass(String userId, String encPw) {
	    int row = 0;
	    String sql = "UPDATE tbl_user SET user_pass=? WHERE user_id=?";
	    try {
	        conn = DBManager.getConn();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, encPw);
	        pstmt.setString(2, userId);
	        row = pstmt.executeUpdate();
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(conn, pstmt);
	    }
	    return row;
	}


   // ===========================
   // 관리자용 유저 리스트
   // ===========================
   public List<UserDTO> userList() {
      List<UserDTO> list = new ArrayList<>();
      String sql = "select * from tbl_user order by point desc";

      try {
         conn = DBManager.getConn();
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         while (rs.next()) {
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

   // ===========================
   // 관리자 — 유저수 카운트
   // ===========================
   public int userCount() {
      int row = 0;
      String sql = "select count(*) from tbl_user";

      try {
         conn = DBManager.getConn();
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         if (rs.next()) row = rs.getInt(1);
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBManager.close(conn, pstmt, rs);
      }
      return row;
   }

   // ===========================
   // 관리자 — 유저 삭제
   // ===========================
   public int userDelete(int idx) {
      int row = 0;
      String sql = "delete from tbl_user where idx=?";
      try {
         conn = DBManager.getConn();
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, idx);
         row = pstmt.executeUpdate();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBManager.close(conn, pstmt);
      }
      return row;
   }

   // ===========================
   // 관리자 — 유저 업데이트
   // ===========================
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
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBManager.close(conn, pstmt);
      }

      return row;
   }

   // ===========================
   // 포인트 상위 5명
   // ===========================
   public List<UserDTO> getPointRankTop5() {
       List<UserDTO> list = new ArrayList<>();

       String sql =
           "SELECT user_id, n_name, point " +
           "FROM tbl_user " +
           "GROUP BY user_id, n_name, point " + 
           "ORDER BY point DESC, user_id ASC " + 
           "LIMIT 5";

       try {
           conn = DBManager.getConn();
           pstmt = conn.prepareStatement(sql);
           rs = pstmt.executeQuery();

           while (rs.next()) {
               UserDTO dto = new UserDTO();
               dto.setUser_id(rs.getString("user_id"));
               dto.setN_name(rs.getString("n_name"));
               dto.setPoint(rs.getInt("point"));

               dto.setBadge(calcBadge(dto.getPoint()));
               list.add(dto);
           }
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           DBManager.close(conn, pstmt, rs);
       }

       return list;
   }

   // ===========================
   // 검색 기능
   // ===========================
   public List<UserDTO> searchByNickname(String keyword) {
      List<UserDTO> list = new ArrayList<>();
      String sql = "select * from tbl_user where n_name like ? order by point desc";

      try {
         conn = DBManager.getConn();
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%" + keyword + "%");
         rs = pstmt.executeQuery();

         while (rs.next()) {
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

   // ===========================
   // 닉네임 중복 검사
   // ===========================
   public int nickCheck(String n_name) {
      int row = 0;
      String sql = "select count(*) from tbl_user where n_name=?";

      try {
         conn = DBManager.getConn();
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, n_name);
         rs = pstmt.executeQuery();
         if (rs.next()) row = rs.getInt(1);

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBManager.close(conn, pstmt, rs);
      }
      return row;
   }

   // ===========================
   // 회원 탈퇴
   // ===========================
   public int userDelete(String user_id) {
      int row = 0;
      String sql = "delete from tbl_user where user_id=?";

      try {
         conn = DBManager.getConn();
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, user_id);
         row = pstmt.executeUpdate();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBManager.close(conn, pstmt);
      }
      return row;
   }

   // ===========================
   // 최신 사용자 정보
   // ===========================
   public UserDTO getUserById(String user_id) {
       UserDTO dto = null;
       String sql = "SELECT * FROM tbl_user WHERE user_id=?";

       try {
           conn = DBManager.getConn();
           pstmt = conn.prepareStatement(sql);
           pstmt.setString(1, user_id);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               dto = new UserDTO();
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

       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           DBManager.close(conn, pstmt, rs);
       }

       return dto;
   }

   // ===========================
   // 배지 계산
   // ===========================
   private String calcBadge(int point) {
       if (point >= 5000) return "🌳";
       if (point >= 4000) return "🐄";
       if (point >= 3000) return "🌿";
       if (point >= 2000) return "🐥";
       if (point >= 1000) return "🌰";
       return "🌱";
   }

}