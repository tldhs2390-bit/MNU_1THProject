package model.reply;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class ReplyDAO {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    // ============================================================
    // 1) 댓글 + 대댓글 전체 목록 (post_idx 기준)
    //    → 사용자 화면에서는 숨김(status=0) 제외!
    // ============================================================
    public List<ReplyDTO> list(int post_idx) {

        List<ReplyDTO> list = new ArrayList<>();

        String sql =
            "SELECT * FROM tbl_reply " +
            "WHERE post_idx=? AND status = 1 " +     // ⭐⭐ 핵심: 숨김 댓글 제외
            "ORDER BY parent ASC, r_idx ASC";         // (정렬도 안정적으로 정리)

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, post_idx);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ReplyDTO dto = new ReplyDTO();
                dto.setR_idx(rs.getInt("r_idx"));
                dto.setPost_idx(rs.getInt("post_idx"));
                dto.setParent(rs.getInt("parent"));
                dto.setN_name(rs.getString("n_name"));
                dto.setContents(rs.getString("contents"));
                dto.setImg(rs.getString("img"));
                dto.setEmoji(rs.getString("emoji"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setLike_cnt(rs.getInt("like_cnt"));
                dto.setSym_cnt(rs.getInt("sym_cnt"));
                dto.setSad_cnt(rs.getInt("sad_cnt"));
                dto.setStatus(rs.getInt("status"));

                list.add(dto);
            }

        } catch (Exception e) {
            System.out.println("ReplyDAO.list() 에러: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return list;
    }


    // ============================================================
    // 2) 댓글 등록
    // ============================================================
    public void write(ReplyDTO dto) {

        String sql =
            "INSERT INTO tbl_reply " +
            "(post_idx, parent, n_name, contents, img, emoji, regdate, like_cnt, sym_cnt, sad_cnt, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, NOW(), 0, 0, 0, 1)";   // ⭐ 등록 시 기본값 = 보임(1)

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, dto.getPost_idx());
            pstmt.setInt(2, dto.getParent());
            pstmt.setString(3, dto.getN_name());
            pstmt.setString(4, dto.getContents());
            pstmt.setString(5, dto.getImg());
            pstmt.setString(6, dto.getEmoji());

            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("ReplyDAO.write() 에러: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, null);
        }
    }


    // ============================================================
    // 3) 댓글 수정
    // ============================================================
    public void modify(int r_idx, String contents) {

        String sql = "UPDATE tbl_reply SET contents=? WHERE r_idx=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, contents);
            pstmt.setInt(2, r_idx);

            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("ReplyDAO.modify() 에러: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, null);
        }
    }


    // ============================================================
    // 4) 댓글 삭제 (부모 댓글 삭제 시 대댓글도 삭제)
    // ============================================================
    public void delete(int r_idx) {

        String sql1 = "DELETE FROM tbl_reply WHERE parent=?";
        String sql2 = "DELETE FROM tbl_reply WHERE r_idx=?";

        try {
            conn = DBManager.getConn();

            // 대댓글 먼저 삭제
            pstmt = conn.prepareStatement(sql1);
            pstmt.setInt(1, r_idx);
            pstmt.executeUpdate();

            // 본댓글 삭제
            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, r_idx);
            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("ReplyDAO.delete() 에러: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, null);
        }
    }


    // ============================================================
    // 5) 댓글 감정 증가
    // ============================================================
    public void updateEmotion(int r_idx, String type) {

        String field = "";

        switch (type) {
            case "like": field = "like_cnt"; break;
            case "sym":  field = "sym_cnt";  break;
            case "sad":  field = "sad_cnt";  break;
            default: return;
        }

        String sql =
            "UPDATE tbl_reply SET " + field + " = COALESCE(" + field + ",0) + 1 " +
            "WHERE r_idx=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, r_idx);
            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("ReplyDAO.updateEmotion() 에러: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, null);
        }
    }
    
	 // ============================================================
	 // 6) r_idx 로 댓글 1개 조회 (삭제/수정 권한 확인용)
	 // ============================================================
	 public ReplyDTO get(int r_idx) {
	
	     ReplyDTO dto = null;
	
	     String sql = "SELECT * FROM tbl_reply WHERE r_idx = ?";
	
	     try {
	         conn = DBManager.getConn();
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1, r_idx);
	         rs = pstmt.executeQuery();
	
	         if (rs.next()) {
	             dto = new ReplyDTO();
	             dto.setR_idx(rs.getInt("r_idx"));
	             dto.setPost_idx(rs.getInt("post_idx"));
	             dto.setParent(rs.getInt("parent"));
	             dto.setN_name(rs.getString("n_name"));
	             dto.setContents(rs.getString("contents"));
	             dto.setImg(rs.getString("img"));
	             dto.setEmoji(rs.getString("emoji"));
	             dto.setRegdate(rs.getString("regdate"));
	             dto.setLike_cnt(rs.getInt("like_cnt"));
	             dto.setSym_cnt(rs.getInt("sym_cnt"));
	             dto.setSad_cnt(rs.getInt("sad_cnt"));
	             dto.setStatus(rs.getInt("status"));
	         }
	
	     } catch (Exception e) {
	         System.out.println("ReplyDAO.get() 에러: " + e.getMessage());
	         e.printStackTrace();
	     } finally {
	         DBManager.close(conn, pstmt, rs);
	     }
	
	     return dto;
	 }

}