package servlet.growth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.user.UserDTO;

@WebServlet("/growth_write.do")
public class GrowthWriteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 로그인 세션 체크 (UserDTO 기반)
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        // ⭐ 로그인한 사용자의 닉네임을 JSP에서 쓰고 싶을 때
        request.setAttribute("loginUser", loginUser.getN_name());

        // 글쓰기 페이지 이동
        request.getRequestDispatcher("/Growth/growth_write.jsp").forward(request, response);
    }
}