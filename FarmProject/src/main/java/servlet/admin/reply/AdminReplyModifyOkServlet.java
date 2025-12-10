package servlet.admin.reply;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.admin.reply.AdminReplyDAO;
import model.reply.ReplyDTO;

@WebServlet("/admin_reply_modify_ok.do")
public class AdminReplyModifyOkServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        ReplyDTO dto = new ReplyDTO();

        dto.setR_idx(Integer.parseInt(request.getParameter("r_idx")));
        dto.setContents(request.getParameter("contents"));
        dto.setEmoji(request.getParameter("emoji"));

        AdminReplyDAO dao = new AdminReplyDAO();
        int result = dao.updateReply(dto);

        int post_idx = Integer.parseInt(request.getParameter("post_idx"));

        if (result > 0) {
            response.sendRedirect("/admin_growth_read.do?idx=" + post_idx);
        } else {
            response.sendRedirect("/Admin/error.jsp");
        }
    }
}
