package servlet.success;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.success.SuccessDAO;
import model.success.SuccessDTO;

@WebServlet("/Success/modifyPro.do")
public class SuccessModifyProServlet extends HttpServlet {

    // ⭐ 수정 처리 후 글보기로 이동
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        SuccessDTO dto = new SuccessDTO();
        dto.setIdx(Integer.parseInt(request.getParameter("idx")));
        dto.setSubject(request.getParameter("subject"));
        dto.setContents(request.getParameter("contents"));
        dto.setPass(request.getParameter("pass"));

        SuccessDAO dao = new SuccessDAO();
        dao.modify(dto);

        // ⭐ 수정 후 해당 글 보기로 이동
        response.sendRedirect("/Success/read.do?idx=" + dto.getIdx());
    }
}