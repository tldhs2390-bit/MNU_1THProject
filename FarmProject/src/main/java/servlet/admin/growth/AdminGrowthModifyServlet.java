package servlet.admin.growth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.admin.growth.AdminGrowthDAO;
import model.growth.GrowthDTO;

@WebServlet("/admin_growth_modify.do")
public class AdminGrowthModifyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1) 글 번호 받기
        int idx = Integer.parseInt(request.getParameter("idx"));

        // 2) DAO 호출하여 기존 글 정보 가져오기
        AdminGrowthDAO dao = new AdminGrowthDAO();
        GrowthDTO dto = dao.getPost(idx);

        // 3) JSP로 전달
        request.setAttribute("dto", dto);

        // 4) 관리자 수정페이지로 이동
        request.getRequestDispatcher("/Admin/admin_growth_modify.jsp")
               .forward(request, response);
    }
}
