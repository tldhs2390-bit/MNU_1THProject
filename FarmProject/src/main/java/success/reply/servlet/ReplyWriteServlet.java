package success.reply.servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.success.reply.ReplyDAO;
import model.success.reply.ReplyDTO;

@WebServlet("/Success/Reply/write.do")
public class ReplyWriteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int idx = Integer.parseInt(request.getParameter("idx"));

        // ⭐ 세션이 아니라 form에서 넘어온 n_name 받기
        String nickname = request.getParameter("n_name");

        int parent = Integer.parseInt(request.getParameter("parent_idx"));
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