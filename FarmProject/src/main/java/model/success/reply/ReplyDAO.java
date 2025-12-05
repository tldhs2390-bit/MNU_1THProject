package model.success.reply;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class ReplyDAO {

    // ============================================================
    // 1) 부모 댓글 + 대댓글 구성
    // ============================================================
    public List<ReplyDTO> list(int idx) {

        List<ReplyDTO> parentList = new ArrayList<>();

        String sql = "SELECT * FROM tbl_success_comment "
                   + "WHERE idx=? AND parent=0 ORDER BY c_idx ASC";

        try (Connection conn = DBManager.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idx);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ReplyDTO dto = new ReplyDTO();
                dto.setC_idx(rs.getInt("c_idx"));
                dto.setIdx(rs.getInt("idx"));
                dto.setNickname(rs.getString("n_name"));  // ⭐ 수정
                dto.setContents(rs.getString("contents"));
                dto.setParent(rs.getInt("parent"));
                dto.setLikes(rs.getInt("likes"));
                dto.setRegdate(rs.getString("regdate"));

                // ⭐ 대댓글 채우기
                dto.setReplyList(getReplyList(dto.getC_idx()));

                parentList.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parentList;
    }

    // ============================================================
    // 2) 대댓글 목록
    // ============================================================
    private List<ReplyDTO> getReplyList(int parentCidx) {

        List<ReplyDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM tbl_success_comment "
                   + "WHERE parent=? ORDER BY c_idx ASC";

        try (Connection conn = DBManager.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, parentCidx);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ReplyDTO dto = new ReplyDTO();
                dto.setC_idx(rs.getInt("c_idx"));
                dto.setIdx(rs.getInt("idx"));
                dto.setNickname(rs.getString("n_name")); // ⭐ 수정
                dto.setContents(rs.getString("contents"));
                dto.setParent(rs.getInt("parent"));
                dto.setLikes(rs.getInt("likes"));
                dto.setRegdate(rs.getString("regdate"));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ============================================================
    // 3) 댓글 등록
    // ============================================================
    public int insert(ReplyDTO dto) {

        String sql = "INSERT INTO tbl_success_comment "
                   + "(idx, n_name, contents, parent) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBManager.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dto.getIdx());
            pstmt.setString(2, dto.getNickname()); // nickname → n_name 저장
            pstmt.setString(3, dto.getContents());
            pstmt.setInt(4, dto.getParent());

            return pstmt.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }

        return 0;
    }

    // ============================================================
    // 4) 댓글 수정
    // ============================================================
    public int modify(ReplyDTO dto) {

        String sql = "UPDATE tbl_success_comment SET contents=? WHERE c_idx=?";

        try (Connection conn = DBManager.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dto.getContents());
            pstmt.setInt(2, dto.getC_idx());

            return pstmt.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }

        return 0;
    }

    // ============================================================
    // 5) 댓글 삭제
    // ============================================================
    public int delete(int c_idx) {

        String sql = "DELETE FROM tbl_success_comment WHERE c_idx=?";

        try (Connection conn = DBManager.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, c_idx);
            return pstmt.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }

        return 0;
    }

    // ============================================================
    // 6) 댓글 좋아요 (최신 likes 반환)
    // ============================================================
    public int like(int c_idx) {

        String update = "UPDATE tbl_success_comment SET likes = likes + 1 WHERE c_idx=?";
        String select = "SELECT likes FROM tbl_success_comment WHERE c_idx=?";

        try (Connection conn = DBManager.getConn()) {

            try (PreparedStatement pstmt = conn.prepareStatement(update)) {
                pstmt.setInt(1, c_idx);
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(select)) {
                pstmt.setInt(1, c_idx);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) return rs.getInt("likes");
            }

        } catch (Exception e) { e.printStackTrace(); }

        return 0;
    }
}