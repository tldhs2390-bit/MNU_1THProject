package success.reply.servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.success.reply.ReplyDAO;
import model.success.reply.ReplyDTO;

@WebServlet("/Success/replyReplyWrite.do")
public class ReplyReplyWriteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int idx = Integer.parseInt(request.getParameter("idx"));
        int parent = Integer.parseInt(request.getParameter("parent"));
        String nickname = (String) request.getSession().getAttribute("n_name");
        String contents = request.getParameter("contents");

        ReplyDTO dto = new ReplyDTO();
        dto.setIdx(idx);
        dto.setNickname(nickname);
        dto.setContents(contents);
        dto.setParent(parent);

        ReplyDAO dao = new ReplyDAO();
        dao.insert(dto);

        response.sendRedirect("/Success/read.do?idx=" + idx);
    }
}
