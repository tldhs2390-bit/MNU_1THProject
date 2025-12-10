package servlet.growth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.growth.GrowthDAO;
import model.growth.GrowthDTO;
import model.user.UserDTO;

@WebServlet("/growth_modify.do")
public class GrowthModifyServlet extends HttpServlet {

    // ============================================================
    // ⭐ 글 수정 화면 열기 (GET 전용)
    // ============================================================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 로그인 체크 (UserDTO 기반)
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");   // ✔ 수정됨

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        int idx = Integer.parseInt(request.getParameter("idx"));

        GrowthDAO dao = new GrowthDAO();
        GrowthDTO dto = dao.getOne(idx);

        request.setAttribute("dto", dto);

        request.getRequestDispatcher("/Growth/growth_modify.jsp").forward(request, response);
    }

    // ============================================================
    // ❌ POST는 사용하면 안 됨 → modify_ok.do 가 따로 있음
    // ============================================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                "POST 지원 안함 → /growth_modify_ok.do 사용하세요.");
    }
}