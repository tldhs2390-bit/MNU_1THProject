package servlet.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.model.UserDAO;
import user.model.UserDTO;

/**
 * Servlet implementation class UserJoinServlet
 */
@WebServlet("/User/user_join.do")
public class UserJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public UserJoinServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("user_join.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
request.setCharacterEncoding("utf-8");
		
		UserDTO dto = new UserDTO();
		
		dto.setUser_name(request.getParameter("user_name"));
		dto.setN_name(request.getParameter("n_name"));
		dto.setTel(request.getParameter("tel"));
		dto.setEmail(request.getParameter("email"));
		dto.setAddress(request.getParameter("address"));
		dto.setUser_rank(request.getParameter("user_rank"));
		dto.setUser_id(request.getParameter("user_id"));
		dto.setUser_pass(request.getParameter("user_pass"));		
		
		UserDAO dao = UserDAO.getInstance();
		
		int row = dao.userWrite(dto);
		
		if(row==1) {
			//가입성공시
			response.sendRedirect("user_login.do");//로그인 폼
		}else {
			//가입실패시
			response.sendRedirect("user_join.do");//가입폼
		}
	}

}
