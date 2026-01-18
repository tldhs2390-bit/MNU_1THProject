package servlet.growth;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.growth.GrowthDAO;
import model.growth.GrowthDTO;
import model.reply.ReplyDAO;
import model.user.UserDTO;

@WebServlet("/growth_read.do")
public class GrowthReadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // 🔐 =============================
        // 🔐 로그인 체크 (UserDTO 기반)
        // 🔐 =============================
        UserDTO loginUserDTO = (UserDTO) session.getAttribute("user");
        if (loginUserDTO == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        // ⭐ 로그인 사용자 닉네임 JSP 전달
        String n_name = loginUserDTO.getN_name();
        request.setAttribute("loginUser", n_name);

        // =============================
        // 1) 글 번호 파라미터 확인
        // =============================
        int idx = Integer.parseInt(request.getParameter("idx"));
        System.out.println("🔥 [READ] idx 파라미터 = " + idx);

        if ("over".equals(request.getParameter("limit"))) {
            request.setAttribute("emotionLimit", true);
        }

        GrowthDAO dao = new GrowthDAO();

        // ============================================================
        // ⭐⭐ 2) 조회수 증가 — 6시간 동안 1번만 증가 ⭐⭐
        // ============================================================
        String readKey = "read_time_" + idx;
        LocalDateTime lastReadTime = (LocalDateTime) session.getAttribute(readKey);
        LocalDateTime now = LocalDateTime.now();

        boolean increaseRead = false;

        if (lastReadTime == null) {
            increaseRead = true;
        } else {
            long hours = Duration.between(lastReadTime, now).toHours();
            if (hours >= 6) increaseRead = true;
        }

        if (increaseRead) {
            dao.increaseReadcnt(idx);
            session.setAttribute(readKey, now);
            System.out.println("📈 조회수 증가됨!");
        } else {
            System.out.println("⏱ 조회수 증가 제한 (6시간 미경과)");
        }

        // =============================
        // 3) 게시글 정보 조회
        // =============================
        GrowthDTO dto = dao.getOne(idx);

        if (dto == null) {
            response.sendRedirect("/growth_list.do");
            return;
        }

        // =============================
        // 4) 댓글 목록 조회
        // =============================
        ReplyDAO rdao = new ReplyDAO();
        var replyList = rdao.list(idx);
        request.setAttribute("replyList", replyList);

        // =============================
        // 5) DTO 전달
        // =============================
        request.setAttribute("dto", dto);

        // =============================
        // 6) Forward 실행
        // =============================
        request.getRequestDispatcher("/Growth/growth_read.jsp").forward(request, response);
    }
}