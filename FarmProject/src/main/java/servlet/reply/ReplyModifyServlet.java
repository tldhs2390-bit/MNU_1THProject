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
 * 댓글 수정 서블릿
 * URL: /reply_modify.do
 * ============================================
 */
@WebServlet("/reply_modify.do")
public class ReplyModifyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        // 🔐 1) 로그인 체크 — UserDTO 기반
        UserDTO loginUserDTO = (UserDTO) session.getAttribute("user");
        if (loginUserDTO == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        String loginUser = loginUserDTO.getN_name();  // ⭐ 로그인 사용자 닉네임

        // ★ 수정할 댓글(r_idx) + 원본 글(post_idx)
        int r_idx = Integer.parseInt(request.getParameter("r_idx"));
        int post_idx = Integer.parseInt(request.getParameter("post_idx"));
        String contents = request.getParameter("contents");

        ReplyDAO dao = new ReplyDAO();

        // 🔍 2) 댓글 존재 여부 체크
        ReplyDTO reply = dao.get(r_idx);

        if (reply == null) {
            // 댓글이 존재하지 않을 때
            response.sendRedirect(request.getContextPath() + "/growth_read.do?idx=" + post_idx);
            return;
        }

        // 🔐 3) 작성자 본인 여부 확인
        if (!loginUser.equals(reply.getN_name())) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(
                "<script>alert('본인만 댓글을 수정할 수 있습니다.'); history.back();</script>"
            );
            return;
        }

        // ✏ 4) 댓글 내용 업데이트
        dao.modify(r_idx, contents);

        // ⭐ 최종 이동 (contextPath 적용)
        response.sendRedirect(request.getContextPath() + "/growth_read.do?idx=" + post_idx);
    }
}