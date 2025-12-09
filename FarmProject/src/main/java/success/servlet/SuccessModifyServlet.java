package success.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import success.model.SuccessDAO;
import success.model.SuccessDTO;

@WebServlet("/Success/modify.do")
public class SuccessModifyServlet extends HttpServlet {

    // ⭐ 수정 폼 열기 (기존 데이터 불러오기)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idx = Integer.parseInt(request.getParameter("idx"));

        SuccessDAO dao = new SuccessDAO();
        SuccessDTO dto = dao.read(idx);

        // JSP 전달
        request.setAttribute("dto", dto);

        // ⭐ 실제 JSP 파일로 이동
        RequestDispatcher rd = request.getRequestDispatcher("/Success/success_modify.jsp");
        rd.forward(request, response);
    }
}
