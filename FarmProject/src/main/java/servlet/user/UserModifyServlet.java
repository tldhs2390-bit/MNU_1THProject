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
import util.UserSHA256;

/**
 * Servlet implementation class UserModifyServlet
 */
@WebServlet("/user_modify.do")
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
		
		RequestDispatcher rd = request.getRequestDispatcher("/User/user_modify.jsp");
		rd.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

	    HttpSession session = request.getSession();
	    UserDTO old = (UserDTO) session.getAttribute("user");

	    UserDTO dto = new UserDTO();

	    dto.setUser_name(request.getParameter("user_name"));
	    dto.setN_name(request.getParameter("n_name"));
	    dto.setTel(request.getParameter("tel"));
	    dto.setEmail(request.getParameter("email"));
	    dto.setAddress(request.getParameter("address"));
	    dto.setUser_id(request.getParameter("user_id"));

	    String pass = request.getParameter("user_pass");

	    if(pass == null || pass.equals("")) {
	        dto.setUser_pass(old.getUser_pass());
	    } else {
	        dto.setUser_pass(UserSHA256.getSHA256(pass));
	    }

	    UserDAO dao = UserDAO.getInstance();
	    int row = dao.userModify(dto);

	    // 세션 업데이트
	    session.setAttribute("user", dto);
	    session.setMaxInactiveInterval(1800);

	    response.sendRedirect("/admin_index.do");
		}
	}
	