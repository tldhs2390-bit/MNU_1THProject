package servlet.success;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.success.SuccessDAO;

@WebServlet("/Success/deletePro.do")
public class SuccessDeleteProServlet extends HttpServlet {

    // ⭐ 실제 삭제 처리
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idx = Integer.parseInt(request.getParameter("idx"));

        SuccessDAO dao = new SuccessDAO();
        dao.delete(idx);

        // ⭐ 삭제 후 목록 이동
        response.sendRedirect("/Success/list.do");
    }
}

