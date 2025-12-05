package servlet.success;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.success.SuccessDAO;

@WebServlet("/Success/list.do")
public class SuccessListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SuccessDAO dao = new SuccessDAO();

        // ============================================
        // ⭐ 1) 페이지 번호 계산
        // ============================================
        int page = 1;
        int limit = 10;

        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (Exception e) {
                page = 1; // 잘못된 page 파라미터가 들어오면 기본값 사용
            }
        }

        int start = (page - 1) * limit;

        // ============================================
        // ⭐ 2) 검색 처리 (null 방지)
        // ============================================
        String search = request.getParameter("search");
        String key = request.getParameter("key");

        if (search == null) search = "";
        if (key == null) key = "";

        // ============================================
        // ⭐ 3) 목록 데이터 전달
        // ============================================
        request.setAttribute("list", dao.list(start, limit, search, key));

        // ============================================
        // ⭐ 4) Top3 데이터 전달
        // ============================================
        request.setAttribute("top3", dao.getTop3());

        // ============================================
        // ⭐ 5) 전체 글 수 전달 (페이징 처리 핵심)
        // ============================================
        int totalCount = dao.totalCount(search, key);
        request.setAttribute("totalCount", totalCount);

        // ============================================
        // ⭐ 6) 현재 페이지 전달
        // ============================================
        request.setAttribute("nowPage", page);

        // ============================================
        // ⭐ 7) 검색값 유지
        // ============================================
        request.setAttribute("search", search);
        request.setAttribute("key", key);

        // ============================================
        // ⭐ 8) JSP 이동
        // ============================================
        request.getRequestDispatcher("/Success/success_list.jsp").forward(request, response);
    }
}