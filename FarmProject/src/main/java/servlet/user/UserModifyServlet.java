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
 * Servlet implementation class UserModifyServlet
 */
@WebServlet("/User/user_modify.do")
public class UserModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    UserDTO dto = (UserDTO)session.getAttribute("user");
		
		request.setAttribute("dto", dto);
		
		RequestDispatcher rd = request.getRequestDispatcher("user_modify.jsp");
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
		
		int row = dao.userModify(dto);
		
		HttpSession session = request.getSession();
		session.setAttribute("user", dto);
		session.setMaxInactiveInterval(1800);
		
		response.sendRedirect("/admin_index.do");
	}

}
