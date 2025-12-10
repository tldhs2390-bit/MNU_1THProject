package servlet.growth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.user.UserDTO;   // ★ UserDTO import 필요!

@WebServlet("/growth_vegetable.do")
public class VegetableListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 로그인 세션 체크 (UserDTO로 확인)
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");  // ★ 핵심 수정

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        // vegetable 카테고리 목록으로 이동
        response.sendRedirect(request.getContextPath() + "/growth_list.do?ctype=vegetable");
    }
}