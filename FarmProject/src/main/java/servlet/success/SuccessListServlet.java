package servlet.success;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.success.SuccessDAO;

@WebServlet("/Success/list.do")
public class SuccessListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SuccessDAO dao = new SuccessDAO();

        int page = 1;
        int limit = 10;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int start = (page - 1) * limit;

        request.setAttribute("list", dao.list(start, limit, null, null));

        // ★ 경로 100% 이거 맞음
        request.getRequestDispatcher("/Success/success_list.jsp").forward(request, response);
    }
}
