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

        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        GrowthDAO dao = new GrowthDAO();

        String key = request.getParameter("key");
        String search = request.getParameter("search");
        String category = request.getParameter("category");
        String ctype = request.getParameter("ctype");

        if (key == null) key = "";
        if (search == null) search = "";
        if (category == null) category = "";
        if (ctype != null && !ctype.equals("")) category = ctype;

        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && pageParam.matches("\\d+")) {
            page = Integer.parseInt(pageParam);
        }

        int limit = 10;
        int start = (page - 1) * limit;

        int totalCount = dao.getTotalCount(key, search, category);
        int totalPage = (int) Math.ceil(totalCount / 10.0);

        List<GrowthDTO> list = dao.getListPaging(key, search, category, start, limit);

        // -----------------------------
        // 인기글 (무조건 존재하도록 처리)
        // -----------------------------
        GrowthDTO top = dao.getTopLike();
        if(top == null) top = createEmptyDTO("전체");

        GrowthDTO topVeg = dao.getTopByCategory("vegetable");
        if(topVeg == null) topVeg = createEmptyDTO("vegetable");

        GrowthDTO topFruit = dao.getTopByCategory("fruit");
        if(topFruit == null) topFruit = createEmptyDTO("fruit");

        GrowthDTO topHerb = dao.getTopByCategory("herb");
        if(topHerb == null) topHerb = createEmptyDTO("herb");

        // -----------------------------
        // 페이징 처리
        // -----------------------------
        int block = 10;
        int startPage = ((page - 1) / block) * block + 1;
        int endPage = startPage + block - 1;
        if (endPage > totalPage) endPage = totalPage;

        // -----------------------------
        // JSP 전달값
        // -----------------------------
        request.setAttribute("list", list);
        request.setAttribute("top", top);
        request.setAttribute("topVeg", topVeg);
        request.setAttribute("topFruit", topFruit);
        request.setAttribute("topHerb", topHerb);

        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
        request.setAttribute("keyValue", key);
        request.setAttribute("searchValue", search);
        request.setAttribute("categoryValue", category);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("totalCount", totalCount);

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

        request.getRequestDispatcher("/Growth/growth_list.jsp").forward(request, response);
    }

    private GrowthDTO createEmptyDTO(String category) {
        GrowthDTO dto = new GrowthDTO();
        dto.setIdx(0);
        dto.setSubject("등록된 글이 없습니다.");
        dto.setLike_cnt(0);
        dto.setCategory(category);
        return dto;
    }
}