package SuccessServlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import success.model.SuccessDAO;

@WebServlet("/Success/good.do")
public class SuccessGoodServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idx = Integer.parseInt(request.getParameter("idx"));

        SuccessDAO dao = SuccessDAO.getInstance();
        dao.successLikes(idx);

        response.sendRedirect("/Success/read.do?idx=" + idx);
    }
}
