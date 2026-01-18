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
    // 1) 댓글 + 대댓글 전체 목록
    // ============================================================
    public List<ReplyDTO> list(int post_idx) {
        List<ReplyDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tbl_reply WHERE post_idx=? AND status=1 ORDER BY parent ASC, r_idx ASC";

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
    // 2) 댓글 등록
    // ============================================================
    public void write(ReplyDTO dto) {
        String sql = "INSERT INTO tbl_reply "
                   + "(post_idx, parent, n_name, contents, img, emoji, regdate, like_cnt, sym_cnt, sad_cnt, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, NOW(), 0, 0, 0, 1)";

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

            pstmt = conn.prepareStatement(sql1);
            pstmt.setInt(1, r_idx);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, r_idx);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, null);
        }
    }

    // ============================================================
    // 5) 댓글 감정 처리 (한 번만 가능)
    // ============================================================
    public boolean hasUserPressed(int userId, int r_idx, String type) {
        String sql = "SELECT 1 FROM tbl_reply_emotion WHERE r_idx=? AND user_idx=? AND type=?";
        boolean pressed = false;
        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, r_idx);
            pstmt.setInt(2, userId);
            pstmt.setString(3, type);
            rs = pstmt.executeQuery();
            pressed = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }
        return pressed;
    }

    public boolean updateEmotionOnce(int userId, int r_idx, String type) {
        String field = switch(type) {
            case "like" -> "like_cnt";
            case "sym" -> "sym_cnt";
            case "sad" -> "sad_cnt";
            default -> null;
        };
        if (field == null) return false;

        String sql1 = "UPDATE tbl_reply SET " + field + " = COALESCE(" + field + ",0)+1 WHERE r_idx=?";
        String sql2 = "INSERT INTO tbl_reply_emotion(r_idx, user_idx, type) VALUES(?,?,?)";

        try {
            conn = DBManager.getConn();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql1);
            pstmt.setInt(1, r_idx);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, r_idx);
            pstmt.setInt(2, userId);
            pstmt.setString(3, type);
            pstmt.executeUpdate();

            conn.commit();
            return true;
        } catch(Exception e) {
            try { conn.rollback(); } catch(Exception ex) {}
            e.printStackTrace();
            return false;
        } finally {
            DBManager.close(conn, pstmt, null);
        }
    }

    public int getEmotionCount(int r_idx, String type) {
        String field = switch(type) {
            case "like" -> "like_cnt";
            case "sym" -> "sym_cnt";
            case "sad" -> "sad_cnt";
            default -> null;
        };
        if (field == null) return 0;

        String sql = "SELECT COALESCE(" + field + ",0) FROM tbl_reply WHERE r_idx=?";
        int count = 0;
        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, r_idx);
            rs = pstmt.executeQuery();
            if(rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return count;
    }

    // ============================================================
    // 6) 댓글 1개 조회
    // ============================================================
    public ReplyDTO get(int r_idx) {
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
}