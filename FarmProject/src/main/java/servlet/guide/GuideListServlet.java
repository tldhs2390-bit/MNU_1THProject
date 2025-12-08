package servlet.guide;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import guide.model.GuideDAO;
import guide.model.GuideDTO;

/**
 * Servlet implementation class GuideListServlet
 */
@WebServlet("/guide_list.do")
public class GuideListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuideListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//DB 연결
		GuideDAO dao = new GuideDAO();
		//메소드 호출
		List<GuideDTO> guideList = dao.GuideList();
		//값을 넘기기
		request.setAttribute("guideList", guideList);
<<<<<<< Upstream, based on branch 'kso' of https://github.com/tldhs2390-bit/MNU_1THProject.git
		RequestDispatcher rd = request.getRequestDispatcher("/Guide/guide_list.jsp");
		rd.forward(request, response);
		
=======
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/Guide/guide_list.jsp");
		rd.forward(request, response);
>>>>>>> 51dc2d0 12월 2일
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
