package success.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


