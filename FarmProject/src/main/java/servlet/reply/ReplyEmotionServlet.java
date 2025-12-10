package servlet.reply;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.reply.ReplyDAO;
import model.user.UserDTO;

/**
 * ================================================
 *  댓글 감정표현 처리 서블릿
 *  - 하루(6시간 기준) 5회 제한
 *  - AJAX 요청에 대해 "success" 또는 "limit-over" 반환
 * ================================================
 */
@WebServlet("/reply_emotion.do")
public class ReplyEmotionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // 🔐 0) 로그인 체크 — UserDTO 기반
        UserDTO loginUserDTO = (UserDTO) session.getAttribute("user");

        if (loginUserDTO == null) {
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write("login-required");
            return;
        }

        // --------------------------------------------
        // 1) 세션에서 감정 표현 횟수 / 마지막 시간 불러오기
        // --------------------------------------------
        Integer cnt = (Integer) session.getAttribute("reply_emotion_count");
        LocalDateTime lastTime = (LocalDateTime) session.getAttribute("reply_emotion_time");

        if (cnt == null) cnt = 0;

        // 6시간 경과 시 자동 초기화
        if (lastTime == null ||
            Duration.between(lastTime, LocalDateTime.now()).toHours() >= 6) {

            cnt = 0;
            session.setAttribute("reply_emotion_count", 0);
            session.setAttribute("reply_emotion_time", LocalDateTime.now());
        }

        // --------------------------------------------
        // 2) 제한 체크 (6시간 동안 5회)
        // --------------------------------------------
        if (cnt >= 5) {
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write("limit-over");
            return;
        }

        // --------------------------------------------
        // 3) 파라미터 유효성 체크
        // --------------------------------------------
        String ridxStr = request.getParameter("r_idx");
        String type = request.getParameter("type");

        if (ridxStr == null || type == null) {
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write("error");
            return;
        }

        int r_idx = Integer.parseInt(ridxStr);

        // --------------------------------------------
        // 4) DAO 감정 처리
        // --------------------------------------------
        ReplyDAO dao = new ReplyDAO();
        dao.updateEmotion(r_idx, type);

        // 횟수 +1, 시간 갱신
        session.setAttribute("reply_emotion_count", cnt + 1);
        session.setAttribute("reply_emotion_time", LocalDateTime.now());

        // --------------------------------------------
        // 5) 정상 응답
        // --------------------------------------------
        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().write("success");
    }
}