package model.success;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class SuccessDAO {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    // ============================================================
    // 1. 전체 목록 + 검색 + 페이징
    // ============================================================
    public List<SuccessDTO> list(int start, int limit, String search, String key) {
        List<SuccessDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM tbl_success ";

        if (search != null && key != null && !search.isEmpty() && !key.isEmpty()) {
            sql += "WHERE " + key + " LIKE ? ";
        }

        sql += " ORDER BY idx DESC LIMIT ?, ?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            int index = 1;

            if (search != null && key != null && !search.isEmpty() && !key.isEmpty()) {
                pstmt.setString(index++, "%" + search + "%");
            }

            pstmt.setInt(index++, start);
            pstmt.setInt(index, limit);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                SuccessDTO dto = new SuccessDTO();
                dto.setIdx(rs.getInt("idx"));
                dto.setSubject(rs.getString("subject"));
                dto.setContents(rs.getString("contents"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setPass(rs.getString("pass"));
                dto.setReadcnt(rs.getInt("readcnt"));
                dto.setLikes(rs.getInt("likes"));
                dto.setN_name(rs.getString("n_name"));
                dto.setHashtag(rs.getString("hashtag"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return list;
    }


    // ============================================================
    // 2. 총 게시글 수
    // ============================================================
    public int totalCount(String search, String key) {

        int cnt = 0;

        String sql = "SELECT COUNT(*) FROM tbl_success";

        if (search != null && key != null && !search.isEmpty() && !key.isEmpty()) {
            sql += " WHERE " + key + " LIKE ?";
        }

        try {

            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            if (search != null && key != null && !search.isEmpty() && !key.isEmpty()) {
                pstmt.setString(1, "%" + search + "%");
            }

            rs = pstmt.executeQuery();
            if (rs.next()) cnt = rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return cnt;
    }


    // ============================================================
    // 3. 글 1개 조회
    // ============================================================
    public SuccessDTO read(int idx) {
        SuccessDTO dto = null;

        String sql = "SELECT * FROM tbl_success WHERE idx=?";

        try {

            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idx);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = new SuccessDTO();
                dto.setIdx(rs.getInt("idx"));
                dto.setSubject(rs.getString("subject"));
                dto.setContents(rs.getString("contents"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setPass(rs.getString("pass"));
                dto.setReadcnt(rs.getInt("readcnt"));
                dto.setLikes(rs.getInt("likes"));
                dto.setN_name(rs.getString("n_name"));
                dto.setHashtag(rs.getString("hashtag"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return dto;
    }


    // ============================================================
    // 4. 조회수 증가
    // ============================================================
    public void updateReadCnt(int idx) {
        String sql = "UPDATE tbl_success SET readcnt = readcnt + 1 WHERE idx=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idx);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, null);
        }
    }


    // ============================================================
    // 5. 글 작성
    // ============================================================
    public void write(SuccessDTO dto) {
        String sql = "INSERT INTO tbl_success(subject, contents, pass, n_name, hashtag) VALUES (?, ?, ?, ?, ?)";

        try {

            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getSubject());
            pstmt.setString(2, dto.getContents());
            pstmt.setString(3, dto.getPass());
            pstmt.setString(4, dto.getN_name());
            pstmt.setString(5, dto.getHashtag());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, null);
        }
    }


    // ============================================================
    // 6. 글 수정
    // ============================================================
    public void modify(SuccessDTO dto) {
        String sql = "UPDATE tbl_success SET subject=?, contents=?, pass=?, hashtag=? WHERE idx=?";

        try {

            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getSubject());
            pstmt.setString(2, dto.getContents());
            pstmt.setString(3, dto.getPass());
            pstmt.setString(4, dto.getHashtag());
            pstmt.setInt(5, dto.getIdx());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, null);
        }
    }


    // ============================================================
    // 7. 글 삭제
    // ============================================================
    public void delete(int idx) {
        String sql = "DELETE FROM tbl_success WHERE idx=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idx);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, null);
        }
    }


    // ============================================================
    // ⭐ 8. 좋아요 기능 — 하루 3번 제한 + 토글 기능
    // ============================================================
    public String toggleLike(int idx, String userid) {

        try {
            conn = DBManager.getConn();

            // 오늘 좋아요 횟수 확인
            String countSql =
                "SELECT COUNT(*) FROM tbl_success_likes " +
                "WHERE userid=? AND DATE(regdate)=CURDATE()";

            pstmt = conn.prepareStatement(countSql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();

            int todayCount = 0;
            if (rs.next()) todayCount = rs.getInt(1);

            if (todayCount >= 3) {
                return "limit"; // 하루 3번 초과
            }

            // 이미 눌렀는지 확인
            String checkSql =
                "SELECT COUNT(*) FROM tbl_success_likes WHERE userid=? AND idx=?";

            pstmt = conn.prepareStatement(checkSql);
            pstmt.setString(1, userid);
            pstmt.setInt(2, idx);
            rs = pstmt.executeQuery();

            boolean exists = false;
            if (rs.next()) exists = rs.getInt(1) > 0;

            if (!exists) {
                // 좋아요 추가
                pstmt = conn.prepareStatement(
                    "INSERT INTO tbl_success_likes(idx, userid, regdate) VALUES (?, ?, NOW())"
                );
                pstmt.setInt(1, idx);
                pstmt.setString(2, userid);
                pstmt.executeUpdate();

                pstmt = conn.prepareStatement(
                    "UPDATE tbl_success SET likes = likes + 1 WHERE idx=?"
                );
                pstmt.setInt(1, idx);
                pstmt.executeUpdate();

                return "liked";

            } else {
                // 좋아요 취소
                pstmt = conn.prepareStatement(
                    "DELETE FROM tbl_success_likes WHERE userid=? AND idx=? LIMIT 1"
                );
                pstmt.setString(1, userid);
                pstmt.setInt(2, idx);
                pstmt.executeUpdate();

                pstmt = conn.prepareStatement(
                    "UPDATE tbl_success SET likes = likes - 1 WHERE idx=?"
                );
                pstmt.setInt(1, idx);
                pstmt.executeUpdate();

                return "unliked";
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return "error";
    }


    // ===============================
    // ✨ 인기글 TOP 3 가져오기
    // ===============================
    public List<SuccessDTO> getTop3() {

        List<SuccessDTO> list = new ArrayList<>();

        String sql = "SELECT idx, subject, likes "
                   + "FROM tbl_success "
                   + "ORDER BY likes DESC, idx DESC "
                   + "LIMIT 3";

        try {

            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                SuccessDTO dto = new SuccessDTO();
                dto.setIdx(rs.getInt("idx"));
                dto.setSubject(rs.getString("subject"));
                dto.setLikes(rs.getInt("likes"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return list;
    }
    
	 // ============================================================
	 // 👍 사용자가 이 글을 좋아요 눌렀는지 체크 (read.jsp 표시용)
	 // ============================================================
	 public boolean checkUserLiked(int idx, String userid) {
	
	     boolean liked = false;
	
	     String sql = "SELECT COUNT(*) FROM tbl_success_likes WHERE idx=? AND userid=?";
	
	     try {
	         conn = DBManager.getConn();
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1, idx);
	         pstmt.setString(2, userid);
	
	         rs = pstmt.executeQuery();
	         if (rs.next()) {
	             liked = rs.getInt(1) > 0;
	         }
	
	     } catch (Exception e) {
	         e.printStackTrace();
	     } finally {
	         DBManager.close(conn, pstmt, rs);
	     }
	
	     return liked;
	 }

}
