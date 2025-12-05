package servlet.success.reply;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.success.reply.ReplyDAO;

@WebServlet("/Success/Reply/delete.do")   // ⭐ JSP에서 요청하는 URL과 일치
public class ReplyDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int c_idx = Integer.parseInt(request.getParameter("c_idx")); // 삭제할 댓글 번호
        int idx = Integer.parseInt(request.getParameter("idx"));     // 원글 번호

        ReplyDAO dao = new ReplyDAO();
        dao.delete(c_idx);

        // 삭제 후 다시 게시글 보기로 이동
        response.sendRedirect("/Success/read.do?idx=" + idx);
    }
}