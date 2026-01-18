package servlet.admin.reply;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.admin.reply.AdminReplyDAO;

@WebServlet("/admin_reply_status_json.do")
public class AdminReplyStatusJSONServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int r_idx = Integer.parseInt(request.getParameter("r_idx"));
        int status = Integer.parseInt(request.getParameter("status"));

        AdminReplyDAO dao = new AdminReplyDAO();
        int result = dao.updateStatus(r_idx, status);

        response.setContentType("application/json; charset=UTF-8");

        if (result > 0) {
            response.getWriter().write("{\"result\":\"OK\"}");
        } else {
            response.getWriter().write("{\"result\":\"FAIL\"}");
        }
    }
}