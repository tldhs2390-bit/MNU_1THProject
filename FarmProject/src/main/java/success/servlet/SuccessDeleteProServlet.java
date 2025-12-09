package success.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import success.model.SuccessDAO;

@WebServlet("/Success/deletePro.do")
public class SuccessDeleteProServlet extends HttpServlet {

    // ⭐ 실제 삭제 처리
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idx = Integer.parseInt(request.getParameter("idx"));

        SuccessDAO dao = new SuccessDAO();
        dao.delete(idx);

        // ⭐ 삭제 후 목록 이동
        response.sendRedirect("/Success/list.do");
    }
}

