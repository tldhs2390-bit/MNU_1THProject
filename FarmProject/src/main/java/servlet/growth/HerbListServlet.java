package servlet.growth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/growth_herb.do")
public class HerbListServlet extends HttpServlet {

    @Override
   	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 로그인 세션 체크 (UserDTO 기반)
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("user");  // ★ 핵심

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        // herb 카테고리 목록으로 이동 (ctype 사용)
        response.sendRedirect(request.getContextPath() + "/growth_list.do?ctype=herb");
    }
}