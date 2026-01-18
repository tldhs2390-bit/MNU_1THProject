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
 * 댓글 삭제 서블릿
 * URL: /reply_delete.do?r_idx=번호&post_idx=번호
 * --------------------------------------------
 * r_idx 댓글 삭제 시
 *   → 해당 r_idx가 부모인 대댓글도 함께 삭제됨
 * ============================================
 */
@WebServlet("/reply_delete.do")
public class ReplyDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // 🔐 로그인 체크(UserDTO 기반)
        UserDTO loginUserDTO = (UserDTO) session.getAttribute("user");
        if (loginUserDTO == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        // 사용자 닉네임
        String loginUser = loginUserDTO.getN_name();

        int r_idx = Integer.parseInt(request.getParameter("r_idx"));
        int post_idx = Integer.parseInt(request.getParameter("post_idx"));

        ReplyDAO dao = new ReplyDAO();

        // 🔍 삭제 권한 확인을 위해 댓글 정보 조회
        ReplyDTO reply = dao.get(r_idx);
        if (reply == null) {
            response.sendRedirect(request.getContextPath() + "/growth_read.do?idx=" + post_idx);
            return;
        }

        // 🔐 댓글 작성자만 삭제 가능
        if (!loginUser.equals(reply.getN_name())) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(
                "<script>alert('본인만 댓글을 삭제할 수 있습니다.'); history.back();</script>"
            );
            return;
        }

        // 🗑 댓글 삭제 수행
        dao.delete(r_idx);

        // 삭제 후 원래 글 읽기 페이지로 이동
        response.sendRedirect(request.getContextPath() + "/growth_read.do?idx=" + post_idx);
    }
}