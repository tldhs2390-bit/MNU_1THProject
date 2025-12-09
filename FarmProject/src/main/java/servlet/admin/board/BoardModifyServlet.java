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
import model.board.BoardDTO;

/**
 * Servlet implementation class BoardModifyServlet
 */
@WebServlet("/admin_board_modify.do")
public class BoardModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardModifyServlet() {
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
		
		//넘어오는 값을 먼저 받고
		int idx = Integer.parseInt(request.getParameter("idx"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		//DB에 들어가서 idx에 해당하는 글 검색
		BoardDAO dao = BoardDAO.getInstance();
		
		BoardDTO dto = dao.boardSelect(idx);
		request.setAttribute("dto", dto);// 값 넘겨주기
		request.setAttribute("page", page);// 값 넘겨주기
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/Admin_board/admin_board_modify.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardDTO dto = new BoardDTO();
		
		int page = Integer.parseInt(request.getParameter("page"));
		
		dto.setIdx(Integer.parseInt(request.getParameter("idx")));
		dto.setName(request.getParameter("name"));
		dto.setSubject(request.getParameter("subject"));
		dto.setContents(request.getParameter("contents"));
		dto.setPass(request.getParameter("pass"));
		
		int row = dao.boardModify(dto);
		if(row==1) {
			response.sendRedirect("admin_board_list.do?page="+page);
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("/Admin_board/admin_board_modify_error.jsp");
			rd.forward(request, response);
		}
		
	}

}
