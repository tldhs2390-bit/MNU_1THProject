package admin.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.model.UserDAO;

/**
 * Servlet implementation class AdminUserDeleteServlet
 */
@WebServlet("/Admin/user_delete.do")
public class AdminUserDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idx = Integer.parseInt(request.getParameter("idx"));

        UserDAO dao = UserDAO.getInstance();
        int row = dao.userDelete(idx);

        if(row > 0) {
            response.sendRedirect("/Admin/user_list.do");
        } else {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script>alert('삭제 실패'); history.back();</script>");
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/Admin/user_list.do");
	}

}
