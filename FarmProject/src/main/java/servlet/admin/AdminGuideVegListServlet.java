package servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import guide.model.GuideDAO;
import guide.model.GuideDTO;

/**
 * Servlet implementation class AdminGuideVegListServlet
 */
@WebServlet("/admin_guide_veg_list.do")
public class AdminGuideVegListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminGuideVegListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//관리자가 로그인 하지 않은 경우 관리자 로그인 페이지로 이동 
		HttpSession session = request.getSession();
		if(session.getAttribute("admin") == null) {
			response.sendRedirect("admin_login.do");
			return;
		}
		
		//DB 연결
		GuideDAO dao = new GuideDAO();
		//메소드 호출
		List<GuideDTO> vegList = dao.vegList();
		//값을 넘기기
		request.setAttribute("vegList", vegList);
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/admin_guide_veg_list.jsp");
		rd.forward(request, response);
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
