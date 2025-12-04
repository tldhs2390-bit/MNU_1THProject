package SuccessServlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import success.model.SuccessDAO;

@WebServlet("/Success/list.do")
public class SuccessListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String search = request.getParameter("search");
        String key = request.getParameter("key");

        if (search == null || search.trim().equals("")) search = "subject";
        if (key == null) key = "";

        int page = 1;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        int perpage = 10;
        int start = (page - 1) * perpage;

        SuccessDAO dao = SuccessDAO.getInstance();

        int totalCount = dao.successCount(search, key);
        int totalPage = (int)Math.ceil(totalCount / 10.0);

        request.setAttribute("search", search);
        request.setAttribute("key", key);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);

        request.setAttribute("list", dao.successList(search, key, start, perpage));

        RequestDispatcher rd = request.getRequestDispatcher("/Success/success_list.jsp");
        rd.forward(request, response);
    }
}