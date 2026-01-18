package servlet.reply;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.reply.ReplyDAO;
import model.user.UserDTO;

@WebServlet("/reply_emotion.do")
public class ReplyEmotionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        HttpSession session = request.getSession();

        UserDTO loginUser = (UserDTO) session.getAttribute("user");
        if (loginUser == null) {
            response.getWriter().write("{\"status\":\"login-required\"}");
            return;
        }

        String ridxStr = request.getParameter("r_idx");
        String type = request.getParameter("type");
        if (ridxStr == null || type == null) {
            response.getWriter().write("{\"status\":\"error\"}");
            return;
        }

        int r_idx = Integer.parseInt(ridxStr);
        int userId = loginUser.getIdx();

        ReplyDAO dao = new ReplyDAO();

        if (dao.hasUserPressed(userId, r_idx, type)) {
            response.getWriter().write("{\"status\":\"already-pressed\"}");
            return;
        }

        boolean success = dao.updateEmotionOnce(userId, r_idx, type);
        if (!success) {
            response.getWriter().write("{\"status\":\"error\"}");
            return;
        }

        int newCnt = dao.getEmotionCount(r_idx, type);
        response.getWriter().write("{\"status\":\"success\", \"count\":" + newCnt + "}");
    }
}