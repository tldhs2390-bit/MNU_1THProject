package fail.model;

import java.sql.*;
import java.util.*;
import util.DBManager;

public class FailDAO {

    Connection conn;
    PreparedStatement pstmt;
    ResultSet rs;

    private FailDAO() {}
    private static FailDAO instance = new FailDAO();
    public static FailDAO getInstance() { return instance; }

    // 목록 불러오기
    public List<FailDTO> getList() {
        List<FailDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tbl_fail ORDER BY idx DESC";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FailDTO dto = new FailDTO();
                dto.setIdx(rs.getInt("idx"));
                dto.setSubject(rs.getString("subject"));
                dto.setContents(rs.getString("contents"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setPass(rs.getString("pass"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }
        return list;
    }
    
    // 글 저장
    public int write(FailDTO dto) {
        int row = 0;
        String sql = "INSERT INTO tbl_fail(subject, contents, pass) VALUES(?,?,?)";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getSubject());
            pstmt.setString(2, dto.getContents());
            pstmt.setString(3, dto.getPass());
            row = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt);
        }
        return row;
    }

    // 글 읽기
    public FailDTO read(int idx) {
        FailDTO dto = null;
        String sql = "SELECT * FROM tbl_fail WHERE idx=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idx);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = new FailDTO();
                dto.setIdx(rs.getInt("idx"));
                dto.setSubject(rs.getString("subject"));
                dto.setContents(rs.getString("contents"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setPass(rs.getString("pass"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }
        return dto;
    }
    
    // 글 수정
    public int modify(FailDTO dto) {
        int row = 0;
        String sql = "UPDATE tbl_fail SET subject=?, contents=? WHERE idx=? AND pass=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getSubject());
            pstmt.setString(2, dto.getContents());
            pstmt.setInt(3, dto.getIdx());
            pstmt.setString(4, dto.getPass());
            row = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt);
        }
        return row;
    }
    
    // 삭제
    public int delete(int idx, String pass) {
        int row = 0;
        String sql = "DELETE FROM tbl_fail WHERE idx=? AND pass=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idx);
            pstmt.setString(2, pass);
            row = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt);
        }
        return row;
    }
}
