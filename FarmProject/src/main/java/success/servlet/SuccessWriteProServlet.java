package success.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import success.model.SuccessDAO;
import success.model.SuccessDTO;

@WebServlet("/Success/writePro.do")
public class SuccessWriteProServlet extends HttpServlet {

    // ⭐ 글쓰기 처리 (DB 저장 후 목록 이동)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ⭐ 폼 데이터 받기
        SuccessDTO dto = new SuccessDTO();
        dto.setSubject(request.getParameter("subject"));
        dto.setContents(request.getParameter("contents"));
        dto.setPass(request.getParameter("pass"));
        dto.setN_name(request.getParameter("n_name"));

        // ⭐ DB 저장
        SuccessDAO dao = new SuccessDAO();
        dao.write(dto);

        // ⭐ 저장 후 목록으로 이동
        response.sendRedirect("/Success/list.do");
    }
}