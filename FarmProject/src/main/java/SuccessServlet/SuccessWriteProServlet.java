package SuccessServlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import success.model.SuccessDAO;
import success.model.SuccessDTO;

@WebServlet("/Success/write_pro.do")
public class SuccessWriteProServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        SuccessDTO dto = new SuccessDTO();

        dto.setSubject(request.getParameter("subject"));
        dto.setContents(request.getParameter("contents"));
        dto.setN_name(request.getParameter("n_name"));
        dto.setPass(request.getParameter("pass"));

        SuccessDAO dao = SuccessDAO.getInstance();
        dao.successWrite(dto);

        response.sendRedirect("/Success/list.do");
    }
}