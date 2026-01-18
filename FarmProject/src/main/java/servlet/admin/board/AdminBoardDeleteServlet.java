package servlet.admin.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.board.BoardDAO;

/**
 * Servlet implementation class BoardDeleteServlet
 */
@WebServlet("/admin_board_delete.do")
public class AdminBoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminBoardDeleteServlet() {
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
		int idx = Integer.parseInt(request.getParameter("idx"));
		int page = Integer.parseInt(request.getParameter("page"));
		request.setAttribute("idx", idx);
		request.setAttribute("page", page);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Admin_board/admin_board_delete.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int idx = Integer.parseInt(request.getParameter("idx"));
	    int page = Integer.parseInt(request.getParameter("page"));
	    String pass = request.getParameter("pass");

	    BoardDAO dao = BoardDAO.getInstance();
	    int row = dao.boardDelete(idx, pass); // 비밀번호 확인 포함

	    response.setContentType("text/html;charset=UTF-8");
	    java.io.PrintWriter out = response.getWriter();

	    if(row > 0) {
	        // 삭제 성공 → 목록으로 이동
	        response.sendRedirect("admin_board_list.do?page=" + page);
	    } else {
	        // 삭제 실패 → 팝업 후 다시 이전 화면
	        out.println("<script>");
	        out.println("alert('비밀번호가 틀렸습니다.');");
	        out.println("history.back();");
	        out.println("</script>");
	    }
	}
}