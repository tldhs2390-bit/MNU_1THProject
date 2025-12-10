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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/User/user_login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String user_id = request.getParameter("user_id");
		String user_pass = request.getParameter("user_pass");
		
		UserDAO dao = UserDAO.getInstance();
		
		int row = dao.userLogin(user_id,user_pass);
		
		if(row==1) {
			//로그인 성공(세션에 저장할 정보 검색)
			UserDTO dto = dao.userSelect(user_id);
			//세션 형성
			HttpSession session = request.getSession();
			session.setAttribute("user", dto);
			session.setMaxInactiveInterval(1800);//세션유지시간30분
		}
		
		request.setAttribute("row", row);

		RequestDispatcher rd = request.getRequestDispatcher("/User/user_login_ok.jsp");
		rd.forward(request, response);
	}

}
