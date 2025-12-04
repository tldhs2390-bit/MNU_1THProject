package SuccessServlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Success/search.do")
public class SuccessSearchServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String search = request.getParameter("search");
        String key = request.getParameter("key");

        response.sendRedirect("/Success/list.do?search=" + search + "&key=" + key);
    }
}
