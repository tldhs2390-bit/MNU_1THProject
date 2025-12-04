package SuccessServlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import success.model.SuccessDAO;

@WebServlet("/Success/delete.do")
public class SuccessDeleteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int idx = Integer.parseInt(request.getParameter("idx"));
        String pass = request.getParameter("pass");

        SuccessDAO dao = SuccessDAO.getInstance();
        int row = dao.successDelete(idx, pass);

        if (row == 1) {
            // 삭제 성공
            response.sendRedirect("/Success/list.do");
        } else {
            // 삭제 실패 (비밀번호 틀림)
            response.sendRedirect("/Success/success_delete.jsp?idx=" + idx + "&err=1");
        }
    }
}