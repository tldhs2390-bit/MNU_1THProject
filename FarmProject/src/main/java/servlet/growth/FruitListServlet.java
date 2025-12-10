package servlet.growth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.user.UserDTO;   // ★ UserDTO import 필요!

@WebServlet("/fruit.do")
public class FruitListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 로그인 체크 (UserDTO 기반)
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");   // ★ 핵심 수정

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        // 🔽 로그인 되어 있으면 기존 기능 유지
        response.sendRedirect(request.getContextPath() + "/growth_list.do?ctype=fruit");
    }
}