package servlet.admin.reply;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.admin.reply.AdminReplyDAO;

@WebServlet("/admin_reply_delete_json.do")
public class AdminReplyDeleteJSONServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int r_idx = Integer.parseInt(request.getParameter("r_idx"));

        AdminReplyDAO dao = new AdminReplyDAO();
        int result = dao.deleteReply(r_idx);

        response.setContentType("application/json; charset=UTF-8");

        if (result > 0) {
            response.getWriter().write("{\"result\":\"OK\"}");
        } else {
            response.getWriter().write("{\"result\":\"FAIL\"}");
        }
    }
}
