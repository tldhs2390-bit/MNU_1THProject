package servlet.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.board.BoardDAO;
import model.board.BoardDTO;

/**
 * Servlet implementation class BoardViewServlet
 */
@WebServlet("/board_view.do")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//넘어오는 값을 먼저 받고
		int idx = Integer.parseInt(request.getParameter("idx"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		//DB에 들어가서 idx에 해당하는 글 검색
		BoardDAO dao = BoardDAO.getInstance();
		
		//쿠키 검사
		boolean bool = false;
		Cookie info = null;
		Cookie[] cookies = request.getCookies();
		for(int x = 0; x<cookies.length; x++) {
			info = cookies[x];
			if(info.getName().equals("mnuboard"+idx)){
				bool = true;
				break;
			}
		} 
		// 쿠키에서 사용할 값 설정
		String newValue=""+System.currentTimeMillis();
		if(!bool) {//쿠키가 존재하지 않으면
			dao.boardHits(idx);//조회수 증가 메소드 조회수를 먼저 증가 시켜야함
			info = new Cookie("mnuboard"+idx,newValue);//쿠키 생성
			info.setMaxAge(60*60);//쿠키 유지시간 1시간 (24*60*60)->1일
			response.addCookie(info); // 쿠키 전용
		}
		
		
		
		
		
		BoardDTO dto = dao.boardSelect(idx);
		String contents = dto.getContents();
		if (contents == null) contents = "";
		else contents = contents.replace("\r\n","<br>");
		dto.setContents(contents);
		
		request.setAttribute("dto", dto);
		request.setAttribute("page", page);

		
		RequestDispatcher rd = request.getRequestDispatcher("/Board/board_view.jsp");
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
