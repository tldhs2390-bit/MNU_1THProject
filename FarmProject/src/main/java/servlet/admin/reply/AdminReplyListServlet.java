package servlet.admin.reply;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.admin.reply.AdminReplyDAO;
import model.reply.ReplyDTO;

@WebServlet("/admin_reply_list.do")
public class AdminReplyListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // --------------------------------------------
        // 1) DAO 호출 → 모든 댓글(숨김 포함)
        // --------------------------------------------
        AdminReplyDAO dao = new AdminReplyDAO();
        List<ReplyDTO> list = dao.getAllReplies();   // 여기서 status 포함 전체 가져옴

        // --------------------------------------------
        // 2) JSP 전달
        // --------------------------------------------
        request.setAttribute("list", list);

        // --------------------------------------------
        // 3) 관리자 댓글 리스트 페이지 이동
        // --------------------------------------------
        request.getRequestDispatcher("/Admin/admin_reply_list.jsp")
               .forward(request, response);
    }
}
