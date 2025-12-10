package servlet.admin.reply;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.admin.reply.AdminReplyDAO;
import model.reply.ReplyDTO;

@WebServlet("/admin_reply_modify.do")
public class AdminReplyModifyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int r_idx = Integer.parseInt(request.getParameter("r_idx"));

        AdminReplyDAO dao = new AdminReplyDAO();
        ReplyDTO dto = dao.getReply(r_idx);

        request.setAttribute("dto", dto);

        request.getRequestDispatcher("/Admin/admin_reply_modify.jsp")
               .forward(request, response);
    }
}