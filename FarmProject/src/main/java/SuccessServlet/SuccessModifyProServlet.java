package SuccessServlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import success.model.SuccessDAO;
import success.model.SuccessDTO;

@WebServlet("/Success/modify_pro.do")
public class SuccessModifyProServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        SuccessDTO dto = new SuccessDTO();

        dto.setIdx(Integer.parseInt(request.getParameter("idx")));
        dto.setSubject(request.getParameter("subject"));
        dto.setContents(request.getParameter("contents"));

        SuccessDAO dao = SuccessDAO.getInstance();
        dao.successModify(dto);

        response.sendRedirect("/Success/read.do?idx=" + dto.getIdx());
    }
}
