package success.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import success.model.SuccessDAO;
import success.model.SuccessDTO;

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