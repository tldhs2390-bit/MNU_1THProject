package servlet.reply;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.reply.ReplyDAO;
import model.reply.ReplyDTO;
import model.user.UserDTO;

/**
 * ============================================
 * 댓글 / 대댓글 등록 서블릿
 * URL: /reply_write.do
 * --------------------------------------------
 * parent = 0 → 최상위 댓글
 * parent = r_idx → 특정 댓글의 대댓글
 * ============================================
 */
@WebServlet("/reply_write.do")
public class ReplyWriteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        // 🔐 1) 로그인 체크 (UserDTO 기반)
        UserDTO loginUser = (UserDTO) session.getAttribute("user");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        // ⭐ 로그인한 사용자 닉네임
        String n_name = loginUser.getN_name();

        // ★ 게시글 번호(post_idx) + 부모댓글(parent)
        int post_idx = Integer.parseInt(request.getParameter("post_idx"));
        int parent = Integer.parseInt(request.getParameter("parent"));
        String contents = request.getParameter("contents");

        // 🔍 2) 내용이 비었는지 체크
        if (contents == null || contents.trim().equals("")) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(
                "<script>alert('댓글 내용을 입력하세요!'); history.back();</script>"
            );
            return;
        }

        // ★ 이미지 / 이모지는 선택사항
        String img = request.getParameter("img");
        String emoji = request.getParameter("emoji");

        // DTO 구성
        ReplyDTO dto = new ReplyDTO();
        dto.setPost_idx(post_idx);
        dto.setParent(parent);
        dto.setContents(contents);
        dto.setN_name(n_name);      // ← 여기 매우 중요
        dto.setImg(img);
        dto.setEmoji(emoji);

        // DB INSERT
        ReplyDAO dao = new ReplyDAO();
        dao.write(dto);

        // ⭐⭐⭐ 댓글 작성 후 원래 글로 이동
        response.sendRedirect("/growth_read.do?idx=" + post_idx);
    }
}