package servlet.admin.user;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class AdminUserListServlet
 */
@WebServlet("/admin_user_list.do")
public class AdminUserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    if(session.getAttribute("admin") == null) {
	        response.sendRedirect("admin_login.do");
	        return;
	    }

	    UserDAO dao = UserDAO.getInstance();

	    // 검색 파라미터
	    String keyword = request.getParameter("keyword");

	    List<UserDTO> uList;

	    if(keyword != null && !keyword.trim().equals("")) {
	        // 검색 모드
	        uList = dao.searchByNickname(keyword);
	    } else {
	        // 전체 회원 리스트
	        uList = dao.userList();
	    }

	    int totcount = uList.size(); // 검색결과일 때도 카운트 적용

	    request.setAttribute("totcount", totcount);
	    request.setAttribute("uList", uList);
	    request.setAttribute("keyword", keyword);

	    RequestDispatcher rd = request.getRequestDispatcher("/Admin/user_list.jsp");
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