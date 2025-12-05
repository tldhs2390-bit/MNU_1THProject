package servlet.success.user;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.success.user.UserDAO;   // ← 여기
import model.success.user.UserDTO;   // ← 여기

@WebServlet("/User/profile.do")
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String n_name = request.getParameter("name");

        UserDAO dao = new UserDAO();
        UserDTO dto = dao.getUserByNickname(n_name);

        request.setAttribute("dto", dto);

        RequestDispatcher rd = request.getRequestDispatcher("/User/user_profile.jsp");
        rd.forward(request, response);
    }
}
