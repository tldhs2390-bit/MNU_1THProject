package model.success.user;

import java.sql.*;
import util.DBManager;

public class UserDAO {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    // ⭐ 닉네임으로 회원 정보 가져오기
    public UserDTO getUserByNickname(String n_name) {

        UserDTO dto = null;

        String sql = "SELECT userid, n_name, regdate, email FROM tbl_user WHERE n_name=?";

        try {
            conn = DBManager.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, n_name);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = new UserDTO();
                dto.setUserid(rs.getString("userid"));
                dto.setN_name(rs.getString("n_name"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setEmail(rs.getString("email"));
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBManager.close(conn, pstmt, rs);
        }

        return dto;
    }
}
