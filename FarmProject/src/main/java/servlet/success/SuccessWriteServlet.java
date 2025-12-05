package servlet.success;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Success/write.do")
public class SuccessWriteServlet extends HttpServlet {

    // ⭐ 글쓰기 폼 열기 (단순 페이지 이동)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher("/Success/success_write.jsp");
        rd.forward(request, response);
    }
}


