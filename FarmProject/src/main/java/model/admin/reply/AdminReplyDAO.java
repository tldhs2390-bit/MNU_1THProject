package model.admin.reply;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.reply.ReplyDTO;
import util.DBManager;

public class AdminReplyDAO {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    // ============================================================
    // 1) 모든 댓글 조회 (관리자용) — 숨김 포함 전체
    // ============================================================
    public List<ReplyDTO> getRepliesByPost_All(int post_idx) {

        List<ReplyDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM tbl_reply WHERE post_idx=? ORDER BY r_idx ASC";

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
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return list;
    }


    // ============================================================
    // 2) 댓글 1개 조회
    // ============================================================
    public ReplyDTO getReply(int r_idx) {

        ReplyDTO dto = null;

        String sql = "SELECT * FROM tbl_reply WHERE r_idx=?";

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
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return dto;
    }


    // ============================================================
    // 3) 댓글 수정
    // ============================================================
    public int updateReply(ReplyDTO dto) {

        int result = 0;

        String sql = "UPDATE tbl_reply SET contents=?, emoji=? WHERE r_idx=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getContents());
            pstmt.setString(2, dto.getEmoji());
            pstmt.setInt(3, dto.getR_idx());

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt);
        }

        return result;
    }


 // ============================================================
 // 4) 댓글 삭제 (대댓글 구조 처리)
 // ============================================================
 public int deleteReply(int r_idx) {

     int result = 0;

     try {
         conn = DBManager.getConn();

         // 1) 먼저 삭제할 댓글의 parent 확인
         String checkSql = "SELECT parent FROM tbl_reply WHERE r_idx=?";
         pstmt = conn.prepareStatement(checkSql);
         pstmt.setInt(1, r_idx);
         rs = pstmt.executeQuery();

         int parentValue = 0;
         if (rs.next()) parentValue = rs.getInt("parent");

         rs.close();
         pstmt.close();

         // ------------------------------------
         // 부모 댓글일 경우 → 대댓글 먼저 삭제
         // ------------------------------------
         if (parentValue == 0) {
             String delChild = "DELETE FROM tbl_reply WHERE parent=?";
             pstmt = conn.prepareStatement(delChild);
             pstmt.setInt(1, r_idx);
             pstmt.executeUpdate();
             pstmt.close();
         }

         // ------------------------------------
         // 본인 댓글 삭제
         // ------------------------------------
         String delSelf = "DELETE FROM tbl_reply WHERE r_idx=?";
         pstmt = conn.prepareStatement(delSelf);
         pstmt.setInt(1, r_idx);

         result = pstmt.executeUpdate();

     } catch (Exception e) {
         e.printStackTrace();
     } finally {
         DBManager.close(conn, pstmt, rs);
     }

     return result;
 }


    // ============================================================
    // 5) 숨김/보임 상태 변경
    // ============================================================
    public int updateStatus(int r_idx, int newStatus) {

        int result = 0;

        String sql = "UPDATE tbl_reply SET status=? WHERE r_idx=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, newStatus);
            pstmt.setInt(2, r_idx);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt);
        }

        return result;
    }
    
	 // ============================================================
	 // ★ 전체 댓글 조회 (관리자용) — post_idx 상관없이 모두 조회
	 // ============================================================
	 public List<ReplyDTO> getAllReplies() {
	
	     List<ReplyDTO> list = new ArrayList<>();
	
	     String sql = "SELECT * FROM tbl_reply ORDER BY r_idx DESC";
	
	     try {
	         conn = DBManager.getConn();
	         pstmt = conn.prepareStatement(sql);
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
	         e.printStackTrace();
	     } finally {
	         DBManager.close(conn, pstmt, rs);
	     }
	
	     return list;
	 }
}