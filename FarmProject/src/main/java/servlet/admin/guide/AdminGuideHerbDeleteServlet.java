package servlet.admin.guide;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import guide.model.GuideDAO;

/**
 * Servlet implementation class AdminGuideDeleteServlet
 */
@WebServlet("/admin_guide_herb_delete.do")
public class AdminGuideHerbDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminGuideHerbDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
        if(idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            GuideDAO dao = new GuideDAO();
            int row = dao.guideDelete(id);

            if(row > 0) {
                // 삭제 성공 시 목록 페이지로 이동
                response.sendRedirect(request.getContextPath() + "admin_guide_herb_list.do");
            } else {
                response.getWriter().println("<script>alert('삭제 실패');history.back();</script>");
            }
        } else {
            response.getWriter().println("<script>alert('잘못된 요청');history.back();</script>");
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
