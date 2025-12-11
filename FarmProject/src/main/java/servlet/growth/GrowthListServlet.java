package servlet.growth;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.growth.GrowthDAO;
import model.growth.GrowthDTO;
import model.user.UserDTO;

@WebServlet("/growth_list.do")
public class GrowthListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 ========================================
        // 🔐 로그인 체크 (UserDTO 기반)
        // 🔐 ========================================
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        GrowthDAO dao = new GrowthDAO();

        // ----------------------------
        // 검색 / 카테고리 / 페이지 타입
        // ----------------------------
        String key = request.getParameter("key");
        String search = request.getParameter("search");

        String category = request.getParameter("category");
        String ctype = request.getParameter("ctype");

        if (key == null) key = "";
        if (search == null) search = "";
        if (category == null) category = "";

        // ctype이 들어오면 category 대신 적용
        if (ctype != null && !ctype.equals("")) {
            category = ctype;
        }

        // ----------------------------
        // 페이징 처리 (⭐ 안전하게 처리)
        // ----------------------------
        int page = 1;
        String pageParam = request.getParameter("page");

        if (pageParam != null && pageParam.matches("\\d+")) {
            page = Integer.parseInt(pageParam);
        }

        int limit = 10;                       // 한 페이지에 출력할 개수
        int start = (page - 1) * limit;       // 시작 번호

        // ----------------------------
        // 총 개수 + 목록 조회
        // ----------------------------
        int totalCount = dao.getTotalCount(key, search, category);
        int totalPage = (int) Math.ceil(totalCount / 10.0);

        List<GrowthDTO> list = dao.getListPaging(key, search, category, start, limit);

        // ⭐ 전체 인기글 1개
        GrowthDTO top = dao.getTopLike();

        // ⭐ 카테고리 인기글 Top 1
        GrowthDTO topVeg = dao.getTopByCategory("vegetable");
        GrowthDTO topFruit = dao.getTopByCategory("fruit");
        GrowthDTO topHerb = dao.getTopByCategory("herb");

        // ----------------------------
        // 페이징 숫자 처리
        // ----------------------------
        int block = 10;
        int startPage = ((page - 1) / block) * block + 1;
        int endPage = startPage + block - 1;

        if (endPage > totalPage) endPage = totalPage;

        // ----------------------------
        // ⭐⭐ JSP 전달값 (여기 중요!!)
        // ----------------------------
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);

        request.setAttribute("list", list);
        request.setAttribute("top", top);
        request.setAttribute("topVeg", topVeg);
        request.setAttribute("topFruit", topFruit);
        request.setAttribute("topHerb", topHerb);

        request.setAttribute("keyValue", key);
        request.setAttribute("searchValue", search);
        request.setAttribute("categoryValue", category);

        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);

        // ⭐⭐⭐ 여기 추가됨 — 번호 정상 표시 위해 반드시 필요!!
        request.setAttribute("totalCount", totalCount);

        // ----------------------------
        // 카테고리 전용 JSP 분기
        // ----------------------------
        if ("vegetable".equals(ctype)) {
            request.getRequestDispatcher("/Growth/vegetable_list.jsp").forward(request, response);
            return;
        }
        if ("fruit".equals(ctype)) {
            request.getRequestDispatcher("/Growth/fruit_list.jsp").forward(request, response);
            return;
        }
        if ("herb".equals(ctype)) {
            request.getRequestDispatcher("/Growth/herb_list.jsp").forward(request, response);
            return;
        }

        // 기본 목록 JSP
        request.getRequestDispatcher("/Growth/growth_list.jsp").forward(request, response);
    }
}