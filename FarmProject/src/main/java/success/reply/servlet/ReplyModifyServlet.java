package success.reply.servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.success.reply.ReplyDAO;
import model.success.reply.ReplyDTO;

@WebServlet("/Success/Reply/modify.do")   // ⭐ read.jsp의 URL과 100% 일치시킴
public class ReplyModifyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int c_idx = Integer.parseInt(request.getParameter("c_idx")); // 수정할 댓글 번호
        int idx = Integer.parseInt(request.getParameter("idx"));     // 원글 번호 (리다이렉트용)
        String contents = request.getParameter("contents");

        ReplyDTO dto = new ReplyDTO();
        dto.setC_idx(c_idx);
        dto.setContents(contents);

        ReplyDAO dao = new ReplyDAO();
        dao.modify(dto);

        // ⭐ 수정 후 다시 글 보기 페이지로 이동
        response.sendRedirect("/Success/read.do?idx=" + idx);
    }
}