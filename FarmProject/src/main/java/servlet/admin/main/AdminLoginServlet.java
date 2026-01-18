package servlet.admin.main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminLoginServlet
 */
@WebServlet("/admin_login.do")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/admin_login.jsp");
		rd.forward(request, response);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String adminid = request.getParameter("admin_id");
		String adminpass = request.getParameter("admin_pw");
		
		//하드코딩 방식
		if(adminid.equals("admin") && adminpass.equals("1234")) {
			HttpSession session = request.getSession();
			session.setAttribute("admin","김시온");
			session.setMaxInactiveInterval(1800);//세션유지시간 30분
			
			//RequestDispatcher rd = request.getRequestDispatcher("/Admin/notice_list.jsp");
			//rd.forward(request, response);
			response.sendRedirect("/admin_index.do");
		}else {
			response.sendRedirect("/admin_login.do");
		}
	}

}
