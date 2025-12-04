package success.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class SuccessDAO {

    Connection conn;
    PreparedStatement pstmt;
    ResultSet rs;

    private SuccessDAO() {}
    private static SuccessDAO instance = new SuccessDAO();
    public static SuccessDAO getInstance() { return instance; }


    // 전체 글 개수 (검색 포함)
    public int successCount(String search, String key) {

        int count = 0;
        String sql = "SELECT COUNT(*) AS cnt FROM tbl_success "
                   + "WHERE " + search + " LIKE ?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + key + "%");

            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("cnt");
            }

        } catch (Exception e) { e.printStackTrace(); }
        finally { DBManager.close(conn, pstmt, rs); }

        return count;
    }


    // 목록 + 검색 + 페이징
    public List<SuccessDTO> successList(String search, String key, int start, int perpage) {

        List<SuccessDTO> list = new ArrayList<>();

        String sql = "SELECT idx, subject, n_name, readcnt, likes, regdate "
                   + "FROM tbl_success "
                   + "WHERE " + search + " LIKE ? "
                   + "ORDER BY idx DESC LIMIT ?,?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, "%" + key + "%");
            pstmt.setInt(2, start);
            pstmt.setInt(3, perpage);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                SuccessDTO dto = new SuccessDTO();

                dto.setIdx(rs.getInt("idx"));
                dto.setSubject(rs.getString("subject"));
                dto.setN_name(rs.getString("n_name"));
                dto.setReadcnt(rs.getInt("readcnt"));
                dto.setLikes(rs.getInt("likes"));
                dto.setRegdate(rs.getString("regdate"));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return list;
    }


    // 읽기 + 조회수 증가
    public SuccessDTO successRead(int idx) {

        // 조회수 증가
        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(
                    "UPDATE tbl_success SET readcnt = readcnt + 1 WHERE idx=?");
            pstmt.setInt(1, idx);
            pstmt.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
        finally { DBManager.close(null, pstmt); }


        // 내용 가져오기
        SuccessDTO dto = new SuccessDTO();

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement("SELECT * FROM tbl_success WHERE idx=?");
            pstmt.setInt(1, idx);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto.setIdx(rs.getInt("idx"));
                dto.setSubject(rs.getString("subject"));
                dto.setContents(rs.getString("contents"));
                dto.setN_name(rs.getString("n_name"));
                dto.setReadcnt(rs.getInt("readcnt"));
                dto.setLikes(rs.getInt("likes"));
                dto.setRegdate(rs.getString("regdate"));
            }

        } catch (Exception e) { e.printStackTrace(); }
        finally { DBManager.close(conn, pstmt, rs); }

        return dto;
    }


    // 글쓰기
    public int successWrite(SuccessDTO dto) {

        int row = 0;

        String sql = "INSERT INTO tbl_success(subject, contents, pass, n_name) "
                   + "VALUES (?,?,?,?)";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getSubject());
            pstmt.setString(2, dto.getContents());
            pstmt.setString(3, dto.getPass());
            pstmt.setString(4, dto.getN_name());

            row = pstmt.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
        finally { DBManager.close(conn, pstmt); }

        return row;
    }


    // 수정
    public int successModify(SuccessDTO dto) {

        int row = 0;
        String sql = "UPDATE tbl_success SET subject=?, contents=? WHERE idx=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getSubject());
            pstmt.setString(2, dto.getContents());
            pstmt.setInt(3, dto.getIdx());

            row = pstmt.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
        finally { DBManager.close(conn, pstmt); }

        return row;
    }


    // 삭제(비밀번호 검증)
    public int successDelete(int idx, String pass) {

        int row = 0;
        String sql = "DELETE FROM tbl_success WHERE idx=? AND pass=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idx);
            pstmt.setString(2, pass);

            row = pstmt.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
        finally { DBManager.close(conn, pstmt); }

        return row;
    }


    // 좋아요 증가
    public int successLikes(int idx) {

        int row = 0;
        String sql ="UPDATE tbl_success SET likes = likes + 1 WHERE idx=?";
        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(
                    "UPDATE tbl_success SET likes = likes + 1 WHERE idx=?");

            pstmt.setInt(1, idx);
            row = pstmt.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
        finally { DBManager.close(conn, pstmt); }

        return row;
    }
}