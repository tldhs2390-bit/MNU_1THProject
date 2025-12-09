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

@WebServlet("/guide_list.do")
public class GuideListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GuideListServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        GuideDAO dao = new GuideDAO();

        // 검색값 받기
        String search = request.getParameter("search");
        String key = request.getParameter("key");

        // null 방지 처리
        if (search == null || search.trim().equals("")) {
            search = "name"; // 기본 검색 기준
        }
        if (key == null) {
            key = "";
        }

        // 검색 적용
        List<GuideDTO> guideList;

        if (key.trim().equals("")) {
            // 검색어 없을 때 전체 목록
            guideList = dao.GuideList();
        } else {
            // 검색어 있을 때 조건 검색
            guideList = dao.GuideList(search, key);
        }

        // JSP로 값 전달
        request.setAttribute("guideList", guideList);
        request.setAttribute("search", search);
        request.setAttribute("key", key);

        RequestDispatcher rd = request.getRequestDispatcher("/Guide/guide_list.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}