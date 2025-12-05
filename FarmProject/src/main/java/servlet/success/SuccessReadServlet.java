package servlet.success;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.success.SuccessDAO;
import model.success.SuccessDTO;

@WebServlet("/Success/read.do")
public class SuccessReadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // -------------------------
        // 1) 글 번호 받기
        // -------------------------
        int idx = Integer.parseInt(request.getParameter("idx"));

        // -------------------------
        // 2) DAO 호출
        // -------------------------
        SuccessDAO dao = new SuccessDAO();

        // 조회수 증가
        dao.updateReadCnt(idx);

        // 글 정보 불러오기
        SuccessDTO dto = dao.read(idx);

        // -------------------------
        // ⭐ 3) 좋아요 여부 체크 (로그인 닉네임 기반)
        // -------------------------
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("n_name");   // ← 닉네임 기반 로그인

        boolean userLiked = false;

        if (userid != null) {
            // tbl_success_likes 테이블을 검색하여 좋아요 여부 확인
            userLiked = dao.checkUserLiked(dto.getIdx(), userid);
        }

        dto.setUserLiked(userLiked);

        // -------------------------
        // 4) JSP로 전달
        // -------------------------
        request.setAttribute("dto", dto);

        // ⭐⭐ 가장 중요! JSP 이름 맞춤
        request.getRequestDispatcher("/Success/success_read.jsp")
                .forward(request, response);
    }
}