package model.admin.growth;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;
import model.growth.GrowthDTO;

public class AdminGrowthDAO {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    // ============================================================
    // 1) 전체 글 조회 + 검색 + 정렬 + 숨김필터 + 페이징 (올인원)
    // ============================================================
    public List<GrowthDTO> getList(String key, String word, String sort, String statusFilter,
                                   int start, int pageSize) {

        List<GrowthDTO> list = new ArrayList<>();

        // ----------------------------------
        // WHERE 조건 만들기
        // ----------------------------------
        String where = " WHERE 1=1 ";

        if (word != null && !word.equals("")) {
            if (key.equals("subject")) {
                where += " AND subject LIKE ? ";
            } else if (key.equals("n_name")) {
                where += " AND n_name LIKE ? ";
            } else if (key.equals("category")) {
                where += " AND category LIKE ? ";
            }
        }

        // statusFilter : all / show / hide
        if (statusFilter != null) {
            if (statusFilter.equals("show")) {
                where += " AND status = 1 ";
            } else if (statusFilter.equals("hide")) {
                where += " AND status = 0 ";
            }
        }

        // ----------------------------------
        // 정렬 조건 만들기
        // ----------------------------------
        String order = " ORDER BY idx DESC ";

        if (sort != null) {
            switch (sort) {
                case "old":
                    order = " ORDER BY idx ASC ";
                    break;
                case "read":
                    order = " ORDER BY readcnt DESC ";
                    break;
                case "like":
                    order = " ORDER BY like_cnt DESC ";
                    break;
                case "sym":
                    order = " ORDER BY sym_cnt DESC ";
                    break;
                case "sad":
                    order = " ORDER BY sad_cnt DESC ";
                    break;
                default:
                    order = " ORDER BY idx DESC ";
            }
        }

        // ----------------------------------
        // 최종 SQL
        // ----------------------------------
        String sql = "SELECT * FROM tbl_growth "
                   + where
                   + order
                   + " LIMIT ?, ? ";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            int idxParam = 1;

            // 검색어 있으면 바인딩
            if (word != null && !word.equals("")) {
                pstmt.setString(idxParam++, "%" + word + "%");
            }

            pstmt.setInt(idxParam++, start);
            pstmt.setInt(idxParam, pageSize);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                GrowthDTO dto = new GrowthDTO();

                dto.setIdx(rs.getInt("idx"));
                dto.setCategory(rs.getString("category"));
                dto.setSubject(rs.getString("subject"));
                dto.setContents(rs.getString("contents"));
                dto.setImg(rs.getString("img"));
                dto.setHashtags(rs.getString("hashtags"));
                dto.setN_name(rs.getString("n_name"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setReadcnt(rs.getInt("readcnt"));
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
    // 2) 페이징을 위한 전체 글 개수 (검색 + 필터 적용)
    // ============================================================
    public int getTotalCount(String key, String word, String statusFilter) {

        int total = 0;

        String where = " WHERE 1=1 ";

        if (word != null && !word.equals("")) {
            if (key.equals("subject")) {
                where += " AND subject LIKE ? ";
            } else if (key.equals("n_name")) {
                where += " AND n_name LIKE ? ";
            } else if (key.equals("category")) {
                where += " AND category LIKE ? ";
            }
        }

        if (statusFilter != null) {
            if (statusFilter.equals("show")) {
                where += " AND status = 1 ";
            } else if (statusFilter.equals("hide")) {
                where += " AND status = 0 ";
            }
        }

        String sql = "SELECT COUNT(*) FROM tbl_growth " + where;

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            if (word != null && !word.equals("")) {
                pstmt.setString(1, "%" + word + "%");
            }

            rs = pstmt.executeQuery();

            if (rs.next()) total = rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return total;
    }

    // ============================================================
    // 3) 보임/숨김 상태 변경
    // ============================================================
    public int updateStatus(int idx, int status) {

        int result = 0;
        String sql = "UPDATE tbl_growth SET status = ? WHERE idx = ?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, status);
            pstmt.setInt(2, idx);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt);
        }

        return result;
    }

    // ============================================================
    // 4) 게시글 삭제 (댓글 + 본문)
    // ============================================================
    public int deletePost(int idx) {

        int result = 0;

        try {
            conn = DBManager.getConn();

            // 트랜잭션 시작
            conn.setAutoCommit(false);

            // 댓글 삭제
            String sqlReply = "DELETE FROM tbl_reply WHERE post_idx = ?";
            pstmt = conn.prepareStatement(sqlReply);
            pstmt.setInt(1, idx);
            pstmt.executeUpdate();
            pstmt.close();

            // 게시글 삭제
            String sqlPost = "DELETE FROM tbl_growth WHERE idx = ?";
            pstmt = conn.prepareStatement(sqlPost);
            pstmt.setInt(1, idx);
            result = pstmt.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try { conn.rollback(); } catch (Exception ignore) {}
        } finally {
            DBManager.close(conn, pstmt);
        }

        return result;
    }

    // ============================================================
    // 5) 게시글 조회 (수정 전)
    // ============================================================
    public GrowthDTO getPost(int idx) {

        GrowthDTO dto = null;

        String sql = "SELECT * FROM tbl_growth WHERE idx = ?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idx);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = new GrowthDTO();

                dto.setIdx(rs.getInt("idx"));
                dto.setCategory(rs.getString("category"));
                dto.setSubject(rs.getString("subject"));
                dto.setContents(rs.getString("contents"));
                dto.setImg(rs.getString("img"));
                dto.setHashtags(rs.getString("hashtags"));
                dto.setN_name(rs.getString("n_name"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setReadcnt(rs.getInt("readcnt"));
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
    // 6) 게시글 수정
    // ============================================================
    public int updatePost(GrowthDTO dto) {

        int result = 0;

        String sql = "UPDATE tbl_growth SET category=?, subject=?, contents=?, img=?, hashtags=? WHERE idx=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getCategory());
            pstmt.setString(2, dto.getSubject());
            pstmt.setString(3, dto.getContents());
            pstmt.setString(4, dto.getImg());
            pstmt.setString(5, dto.getHashtags());
            pstmt.setInt(6, dto.getIdx());

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt);
        }

        return result;
    }

}