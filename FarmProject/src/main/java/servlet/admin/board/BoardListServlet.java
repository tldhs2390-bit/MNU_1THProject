package servlet.admin.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.board.BoardDAO;
import model.board.BoardDTO;
import util.PageIndex;

/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/admin_board_list.do")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
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
		
		//DB연결
		//BoardDAO dao = new BoardDAO();
		BoardDAO dao = BoardDAO.getInstance();
		
	
		String search ="", key="";
		String url="board_list.do";
		int totcount = 0; //총게시글 수
		if(request.getParameter("key") != null){
			//post방식
			key = request.getParameter("key");
			search = request.getParameter("search");
			totcount = dao.boardCount(search,key);//검색 조건에 맞는 총 게시글 수
		}else {
			//get방식
			totcount = dao.boardCount();//총 게시글 수
		}
		
		int nowpage=1;//현재 페이지
		int maxlist = 10; //한 페이지 당 글 수
		int totpage = 1;//총 페이지 수
		
		
		//실제로 사용된 총 페이지 수 계산(totpage)
		if(totcount % maxlist == 0) {
			totpage = totcount / maxlist;  //딱 나눠 떨어지는 경우
		}else {
			totpage = totcount / maxlist + 1; //나머지가 남을 때
		}
		
		if(request.getParameter("page") != null) {
			nowpage = Integer.parseInt(request.getParameter("page"));
		}
/*		oracle
		int pagestart = (nowpage-1) * maxlist;
		int endpage = nowpage*maxlist;
		int listcount = totcount-((nowpage-1)* maxlist);
*/		
		//mysql
		int pagestart = (nowpage-1) * maxlist;
		int listcount = totcount- pagestart; //listcount 는 가상 번호(IDX)
		
		List<BoardDTO> blist = null;
		if(key.equals("")) {
			blist = dao.boardList(pagestart,maxlist);
		}else {
			blist = dao.boardList(pagestart,maxlist,search,key);
		}
		
		//페이지처리 메소드 호출(pageIndex)
		String pageSkip = "";
		if(key.equals("")) {
			pageSkip = PageIndex.pageList(nowpage, totpage, url, maxlist);
		}else {
			pageSkip = PageIndex.pageListHan(nowpage, totpage, url, maxlist, search, key);
		}
		
		
		request.setAttribute("totcount", totcount);// 총게시글 수
		request.setAttribute("totpage", totpage); // 총 페이지
		request.setAttribute("page", nowpage); // 현재 페이지
		request.setAttribute("listcount", listcount); // 번호 출력용
		
		request.setAttribute("blist", blist); 
		request.setAttribute("pageSkip", pageSkip); 
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/Admin_board/admin_board_list.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		doGet(request,response);
		
	}

}
