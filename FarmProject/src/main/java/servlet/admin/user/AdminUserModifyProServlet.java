package servlet.admin.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.user.UserDAO;
import model.user.UserDTO;

/**
 * Servlet implementation class AdminUserModifyProServlet
 */
@WebServlet("/admin_user_modify_pro.do")
public class AdminUserModifyProServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserModifyProServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

        UserDTO dto = new UserDTO();
        dto.setUser_id(request.getParameter("user_id"));
        dto.setN_name(request.getParameter("n_name"));

        dto.setPoint(Integer.parseInt(request.getParameter("point")));

        UserDAO dao = UserDAO.getInstance();
        int row = dao.adminUserUpdate(dto);

        if(row > 0){
            response.sendRedirect("/admin_user_list.do");
        } else {
            response.getWriter().println("<script>alert('수정 실패'); history.back();</script>");
        }
	}

}
