package servlet.admin.growth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.admin.growth.AdminGrowthDAO;

@WebServlet("/admin_growth_hide.do")
public class AdminGrowthHideServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 파라미터 받기
        int idx = Integer.parseInt(request.getParameter("idx"));
        int status = Integer.parseInt(request.getParameter("status"));

        AdminGrowthDAO dao = new AdminGrowthDAO();

        // 상태 변경
        int result = dao.updateStatus(idx, status);

        // 결과 반환
        response.setContentType("text/plain; charset=UTF-8");
        if (result > 0) {
            response.getWriter().write("OK");
        } else {
            response.getWriter().write("FAIL");
        }
    }
}