package success.servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Success/delete.do")
public class SuccessDeleteServlet extends HttpServlet {

    // ⭐ 삭제 비밀번호 입력 페이지
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idx = Integer.parseInt(request.getParameter("idx"));
        request.setAttribute("idx", idx);

        // ⭐ JSP 이동 (파일명 그대로)
        RequestDispatcher rd = request.getRequestDispatcher("/Success/success_delete.jsp");
        rd.forward(request, response);
    }
}