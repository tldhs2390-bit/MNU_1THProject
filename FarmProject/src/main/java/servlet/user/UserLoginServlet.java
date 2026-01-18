package servlet.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.user.UserDAO;
import model.user.UserDTO;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/user_login.do")
public class UserLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public UserLoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/User/user_login.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        
        String user_id = request.getParameter("user_id");
        String user_pass = request.getParameter("user_pass");
        
        UserDAO dao = UserDAO.getInstance();
        
        int row = dao.userLogin(user_id, user_pass);
        
        if (row == 1) {
            // 로그인 성공 → 최신 사용자 정보로 세션 업데이트
            UserDTO dto = dao.getUserById(user_id);   // ⭐ 핵심 변경 부분!!

            HttpSession session = request.getSession();
            session.setAttribute("user", dto);
            session.setMaxInactiveInterval(1800); // 세션 유지시간 30분
        }
        
        request.setAttribute("row", row);

        RequestDispatcher rd = request.getRequestDispatcher("/User/user_login_ok.jsp");
        rd.forward(request, response);
    }
}