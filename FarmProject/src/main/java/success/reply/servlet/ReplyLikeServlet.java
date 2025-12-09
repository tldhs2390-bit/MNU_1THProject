package success.reply.servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.success.reply.ReplyDAO;

@WebServlet("/Success/Reply/like.do")   // ⭐ JSP에서 요청하는 URL과 일치시키기
public class ReplyLikeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int c_idx = Integer.parseInt(request.getParameter("c_idx"));

        ReplyDAO dao = new ReplyDAO();
        int newLikeCount = dao.like(c_idx);   // ❤️ 좋아요 증가

        // ⭐ AJAX에 증가된 좋아요 수만 반환
        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().print(newLikeCount);
    }
}
