package model.growth;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class GrowthDAO {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    // ============================================================
    // 1) 오늘 작성한 글 수 조회 (하루 2회 제한)
    // ============================================================
    public int getTodayWriteCount(String n_name) {  
        int count = 0;

        String sql =
            "SELECT COUNT(*) FROM tbl_growth " +
            "WHERE n_name = ? AND DATE(regdate) = CURDATE()";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, n_name);

            rs = pstmt.executeQuery();
            if (rs.next()) count = rs.getInt(1);

        } catch (Exception e) {
            System.out.println("getTodayWriteCount() 에러: " + e.getMessage());
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return count;
    }

    // ============================================================
    // 2) 사용자 포인트 증가
    // ============================================================
    public int updateUserPoint(String n_name, int point) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        int result = 0;

        try {
            conn = DBManager.getConn();

            String sql = "UPDATE tbl_user SET point = point + ? WHERE n_name = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, point);
            pstmt.setString(2, n_name);

            result = pstmt.executeUpdate();  

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt);
        }

        return result;
    }

    // ============================================================
    // 공통 SELECT 컬럼 (명시적)
    // ============================================================
    private final String SELECT_COLUMNS =
        "idx, category, subject, contents, img, hashtags, n_name, pass, " +
        "regdate, readcnt, like_cnt, sym_cnt, sad_cnt";

    // ============================================================
    // 3) 검색 + 카테고리 + 전체 목록
    // ============================================================
    public List<GrowthDTO> getList(String key, String search, String category) {

        List<GrowthDTO> list = new ArrayList<>();

        String sql = "SELECT " + SELECT_COLUMNS + " FROM tbl_growth WHERE status = 1 ";

        if (search != null && !search.equals("")) {
            sql += " AND " + key + " LIKE ? ";
        }

        if (category != null && !category.equals("")) {
            sql += " AND category = ? ";
        }

        sql += " ORDER BY idx DESC";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            int idx = 1;

            if (search != null && !search.equals("")) {
                pstmt.setString(idx++, "%" + search + "%");
            }

            if (category != null && !category.equals("")) {
                pstmt.setString(idx++, category);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(toDTO(rs));
            }

        } catch (Exception e) {
            System.out.println("getList() 에러: " + e.getMessage());
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return list;
    }

    // ============================================================
    // 4) 페이징 목록
    // ============================================================
    public List<GrowthDTO> getListPaging(String key, String search, String category, int start, int limit) {

        List<GrowthDTO> list = new ArrayList<>();

        String sql = "SELECT " + SELECT_COLUMNS + " FROM tbl_growth WHERE status = 1 ";

        if (search != null && !search.equals("")) {
            sql += " AND " + key + " LIKE ? ";
        }

        if (category != null && !category.equals("")) {
            sql += " AND category = ? ";
        }

        sql += " ORDER BY idx DESC LIMIT ?, ?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            int idx = 1;

            if (search != null && !search.equals("")) {
                pstmt.setString(idx++, "%" + search + "%");
            }

            if (category != null && !category.equals("")) {
                pstmt.setString(idx++, category);
            }

            pstmt.setInt(idx++, start);
            pstmt.setInt(idx, limit);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(toDTO(rs));
            }

        } catch (Exception e) {
            System.out.println("getListPaging() 에러: " + e.getMessage());
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return list;
    }

    // ============================================================
    // 5) 전체 글 개수
    // ============================================================
    public int getTotalCount(String key, String search, String category) {

        int total = 0;

        String sql = "SELECT COUNT(*) FROM tbl_growth WHERE status = 1 ";

        if (search != null && !search.equals("")) {
            sql += " AND " + key + " LIKE ? ";
        }

        if (category != null && !category.equals("")) {
            sql += " AND category = ? ";
        }

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            int idx = 1;

            if (search != null && !search.equals("")) {
                pstmt.setString(idx++, "%" + search + "%");
            }

            if (category != null && !category.equals("")) {
                pstmt.setString(idx++, category);
            }

            rs = pstmt.executeQuery();
            if (rs.next()) total = rs.getInt(1);

        } catch (Exception e) {
            System.out.println("getTotalCount() 에러: " + e.getMessage());
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return total;
    }

    // ============================================================
    // 6) 글 1개 조회
    // ============================================================
    public GrowthDTO getOne(int idx) {

        GrowthDTO dto = null;

        String sql = "SELECT " + SELECT_COLUMNS + " FROM tbl_growth WHERE idx=? AND status = 1";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idx);

            rs = pstmt.executeQuery();
            if (rs.next()) dto = toDTO(rs);

        } catch (Exception e) {
            System.out.println("getOne() 에러: " + e.getMessage());
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return dto;
    }

    // ============================================================
    // 7) 글 작성
    // ============================================================
    public int insert(GrowthDTO dto) {

        int result = 0;

        String sql = "INSERT INTO tbl_growth "
                + "(category, subject, contents, img, hashtags, n_name, pass) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getCategory());
            pstmt.setString(2, dto.getSubject());
            pstmt.setString(3, dto.getContents());
            pstmt.setString(4, dto.getImg());
            pstmt.setString(5, dto.getHashtags());
            pstmt.setString(6, dto.getN_name());
            pstmt.setString(7, dto.getPass());

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("insert() 에러: " + e.getMessage());
        } finally {
            DBManager.close(conn, pstmt);
        }

        return result;
    }

    // ============================================================
    // 8) 글 수정
    // ============================================================
    public int update(GrowthDTO dto) {

        int result = 0;

        String sql =
            "UPDATE tbl_growth SET category=?, subject=?, contents=?, img=?, hashtags=? WHERE idx=?";

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
            System.out.println("update() 에러: " + e.getMessage());
        } finally {
            DBManager.close(conn, pstmt);
        }

        return result;
    }

    // ============================================================
    // 9) 글 삭제
    // ============================================================
    public int delete(int idx) {

        int result = 0;

        String sql = "DELETE FROM tbl_growth WHERE idx=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idx);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("delete() 에러: " + e.getMessage());
        } finally {
            DBManager.close(conn, pstmt);
        }

        return result;
    }

    // ============================================================
    // 10) 조회수 증가
    // ============================================================
    public void increaseReadcnt(int idx) {
        String sql = "UPDATE tbl_growth SET readcnt = readcnt + 1 WHERE idx=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idx);
            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("increaseReadcnt() 에러: " + e.getMessage());
        } finally {
            DBManager.close(conn, pstmt);
        }
    }

    // ============================================================
    // 11) 감정 업데이트
    // ============================================================
    public void updateEmotion(int idx, String type) {

        String field = "";
        int value = 0;

        switch (type) {
            case "like": field = "like_cnt"; value = 1; break;
            case "sym":  field = "sym_cnt";  value = 1; break;
            case "sad":  field = "sad_cnt";  value = 1; break;

            case "like_cancel": field = "like_cnt"; value = -1; break;
            case "sym_cancel":  field = "sym_cnt";  value = -1; break;
            case "sad_cancel":  field = "sad_cnt";  value = -1; break;

            default: return;
        }

        String sql =
            "UPDATE tbl_growth SET " + field + " = COALESCE(" + field + ",0) + ? WHERE idx=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, value);
            pstmt.setInt(2, idx);

            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("updateEmotion() 에러: " + e.getMessage());
        } finally {
            DBManager.close(conn, pstmt);
        }
    }

    // ============================================================
    // 12) 인기글 Top1
    // ============================================================
    public GrowthDTO getTopLike() {

        GrowthDTO dto = null;

        String sql =
            "SELECT " + SELECT_COLUMNS + 
            " FROM tbl_growth WHERE status = 1 ORDER BY like_cnt DESC LIMIT 1";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            if (rs.next()) dto = toDTO(rs);

        } catch (Exception e) {
            System.out.println("getTopLike() 에러: " + e.getMessage());
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return dto;
    }

    // ============================================================
    // 13) 카테고리 인기글 Top1
    // ============================================================
    public GrowthDTO getTopByCategory(String category) {

        GrowthDTO dto = null;

        String sql =
            "SELECT " + SELECT_COLUMNS +
            " FROM tbl_growth WHERE category = ? AND status = 1 " +
            "ORDER BY like_cnt DESC LIMIT 1";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category);

            rs = pstmt.executeQuery();
            if (rs.next()) dto = toDTO(rs);

        } catch (Exception e) {
            System.out.println("getTopByCategory() 에러: " + e.getMessage());
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return dto;
    }

    // ============================================================
    // 14) 감정 요약 객체
    // ============================================================
    public GrowthEmotionSummary getEmotionSummary(int idx) {

        GrowthEmotionSummary summary = new GrowthEmotionSummary();

        summary.current = getOne(idx);
        summary.top = getTopLike();
        summary.veg = getTopByCategory("vegetable");
        summary.fruit = getTopByCategory("fruit");
        summary.herb = getTopByCategory("herb");

        return summary;
    }

    // ============================================================
    // ResultSet → DTO
    // ============================================================
    private GrowthDTO toDTO(ResultSet rs) throws Exception {

        GrowthDTO dto = new GrowthDTO();

        dto.setIdx(rs.getInt("idx"));
        dto.setCategory(rs.getString("category"));
        dto.setSubject(rs.getString("subject"));
        dto.setContents(rs.getString("contents"));
        dto.setImg(rs.getString("img"));
        dto.setHashtags(rs.getString("hashtags"));
        dto.setN_name(rs.getString("n_name"));
        dto.setPass(rs.getString("pass"));
        dto.setRegdate(rs.getString("regdate"));
        dto.setReadcnt(rs.getInt("readcnt"));

        dto.setLike_cnt(rs.getInt("like_cnt"));
        dto.setSym_cnt(rs.getInt("sym_cnt"));
        dto.setSad_cnt(rs.getInt("sad_cnt"));

        return dto;
    }

    // ============================================================
    // 내부 DTO 묶음
    // ============================================================
    public static class GrowthEmotionSummary {
        public GrowthDTO current;
        public GrowthDTO top;
        public GrowthDTO veg;
        public GrowthDTO fruit;
        public GrowthDTO herb;
    }
}