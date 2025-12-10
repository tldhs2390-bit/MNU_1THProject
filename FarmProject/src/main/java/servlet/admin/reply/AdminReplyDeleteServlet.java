package servlet.admin.reply;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.admin.reply.AdminReplyDAO;

@WebServlet("/admin_reply_delete.do")
public class AdminReplyDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int r_idx = Integer.parseInt(request.getParameter("r_idx"));

        AdminReplyDAO dao = new AdminReplyDAO();
        int result = dao.deleteReply(r_idx);

        if (result > 0) {
            response.sendRedirect("/admin_reply_list.do");
        } else {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script>alert('삭제 실패'); history.back();</script>");
        }
    }
}