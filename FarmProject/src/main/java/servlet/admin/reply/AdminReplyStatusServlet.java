package servlet.admin.reply;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.admin.reply.AdminReplyDAO;

@WebServlet("/admin_reply_status.do")
public class AdminReplyStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int r_idx = Integer.parseInt(request.getParameter("r_idx"));
        int status = Integer.parseInt(request.getParameter("status"));

        int newStatus = (status == 1 ? 0 : 1);

        AdminReplyDAO dao = new AdminReplyDAO();
        int result = dao.updateStatus(r_idx, newStatus);

        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().write(result > 0 ? String.valueOf(newStatus) : "FAIL");
    }
}