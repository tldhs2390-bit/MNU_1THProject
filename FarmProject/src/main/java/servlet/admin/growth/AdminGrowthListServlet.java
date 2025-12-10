package servlet.admin.growth;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.admin.growth.AdminGrowthDAO;
import model.growth.GrowthDTO;

@WebServlet("/admin_growth_list.do")
public class AdminGrowthListServlet extends HttpServlet {

    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        AdminGrowthDAO dao = new AdminGrowthDAO();

        // 검색
        String key = request.getParameter("key");
        String word = request.getParameter("word");

        if (key == null) key = "";
        if (word == null) word = "";

        // 정렬
        String sort = request.getParameter("sort");
        if (sort == null) sort = "recent";

        // 상태 필터 (기본: 보임글(show))
        String statusFilter = request.getParameter("statusFilter");
        if (statusFilter == null || statusFilter.equals("")) {
            statusFilter = "show";
        }

        // 페이징
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int start = (page - 1) * PAGE_SIZE;

        // 전체 게시글 수
        int totalCount = dao.getTotalCount(key, word, statusFilter);
        int totalPage = (int)Math.ceil(totalCount / (double)PAGE_SIZE);

        // 목록 가져오기
        List<GrowthDTO> list = dao.getList(
            key, word, sort, statusFilter, start, PAGE_SIZE
        );

        // JSP로 전달
        request.setAttribute("list", list);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("key", key);
        request.setAttribute("word", word);
        request.setAttribute("sort", sort);
        request.setAttribute("statusFilter", statusFilter);

        request.getRequestDispatcher("/Admin/admin_growth_list.jsp")
               .forward(request, response);
    }
}
