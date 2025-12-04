package SuccessServlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import success.model.SuccessDAO;

@WebServlet("/Success/modify.do")
public class SuccessModifyServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idx = Integer.parseInt(request.getParameter("idx"));

        SuccessDAO dao = SuccessDAO.getInstance();

        request.setAttribute("dto", dao.successRead(idx));

        RequestDispatcher rd =
            request.getRequestDispatcher("/Success/success_modify.jsp");
        rd.forward(request, response);
    }
}