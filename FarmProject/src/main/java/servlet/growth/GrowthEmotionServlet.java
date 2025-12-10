package servlet.growth;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.growth.GrowthDAO;
import model.growth.GrowthDAO.GrowthEmotionSummary;
import model.growth.GrowthDTO;
import model.user.UserDTO;

/**
 * 게시글 감정(좋아요/공감/아쉬워요) 처리 서블릿
 *
 * 기능:
 * - 감정 토글 지원 (누르면 증가, 다시 누르면 취소)
 * - 6시간마다 감정횟수 자동 초기화
 * - 6시간 동안 최대 5회까지만 감정 가능
 * - AJAX 응답(JSON 반환 — success / limit-over)
 * - 실시간 목록/인기글 자동 업데이트 포함
 */
@WebServlet("/growth_emotion.do")
public class GrowthEmotionServlet extends HttpServlet {

    private static final int MAX_EMOTION = 5; // 6시간 최대 5회

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 로그인 세션 체크 — ★ UserDTO 기반으로 수정 ★
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");   // ✔ 수정됨

        if (loginUser == null) {
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"result\":\"login-required\"}");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        int idx = Integer.parseInt(request.getParameter("idx"));
        String type = request.getParameter("type");

        GrowthDAO dao = new GrowthDAO();

        // ------------------------------
        // 1) 감정 토글 체크
        // ------------------------------
        String key = "emotion_post_" + idx + "_" + type;
        Boolean pressed = (Boolean) session.getAttribute(key);

        if (pressed != null && pressed) {
            dao.updateEmotion(idx, type + "_cancel");
            session.setAttribute(key, false);

            GrowthEmotionSummary data = dao.getEmotionSummary(idx);
            String json = buildSuccessJSON(data, "cancel", idx);
            response.getWriter().write(json);
            return;
        }

        // ------------------------------
        // 2) 감정 제한 체크 (6시간 5회)
        // ------------------------------
        Integer count = (Integer) session.getAttribute("growth_emotion_count");
        LocalDateTime lastTime = (LocalDateTime) session.getAttribute("growth_emotion_time");
        if (count == null) count = 0;

        LocalDateTime now = LocalDateTime.now();
        boolean reset = false;

        if (lastTime == null) {
            reset = true;
        } else {
            long hours = Duration.between(lastTime, now).toHours();
            if (hours >= 6) reset = true;
        }

        if (reset) {
            count = 0;
            session.setAttribute("growth_emotion_count", 0);
            session.setAttribute("growth_emotion_time", now);
        }

        if (count >= MAX_EMOTION) {
            response.getWriter().write("{\"result\":\"limit-over\"}");
            return;
        }

        // ------------------------------
        // 3) 감정 증가
        // ------------------------------
        dao.updateEmotion(idx, type);

        session.setAttribute(key, true);
        session.setAttribute("growth_emotion_count", count + 1);
        session.setAttribute("growth_emotion_time", now);

        GrowthEmotionSummary data = dao.getEmotionSummary(idx);
        String json = buildSuccessJSON(data, "success", idx);

        response.getWriter().write(json);
    }

    // ============================================================
    // JSON 생성 — 목록 업데이트 포함
    // ============================================================
    private String buildSuccessJSON(GrowthEmotionSummary d, String state, int idx) {

        GrowthDTO cur = d.current;
        GrowthDTO top = d.top;
        GrowthDTO veg = d.veg;
        GrowthDTO fruit = d.fruit;
        GrowthDTO herb = d.herb;

        return "{"
            + "\"result\":\"" + state + "\","

            + "\"current\":{"
                + "\"idx\":" + cur.getIdx()
                + ",\"like\":" + cur.getLike_cnt()
                + ",\"sym\":" + cur.getSym_cnt()
                + ",\"sad\":" + cur.getSad_cnt()
            + "},"

            + "\"top\":{"
                + "\"idx\":" + top.getIdx()
                + ",\"subject\":\"" + escape(top.getSubject()) + "\""
                + ",\"like\":" + top.getLike_cnt()
                + ",\"category\":\"" + top.getCategory() + "\""
            + "},"

            + "\"veg\":{"
                + "\"idx\":" + veg.getIdx()
                + ",\"subject\":\"" + escape(veg.getSubject()) + "\""
                + ",\"like\":" + veg.getLike_cnt()
            + "},"

            + "\"fruit\":{"
                + "\"idx\":" + fruit.getIdx()
                + ",\"subject\":\"" + escape(fruit.getSubject()) + "\""
                + ",\"like\":" + fruit.getLike_cnt()
            + "},"

            + "\"herb\":{"
                + "\"idx\":" + herb.getIdx()
                + ",\"subject\":\"" + escape(herb.getSubject()) + "\""
                + ",\"like\":" + herb.getLike_cnt()
            + "},"

            + "\"listUpdate\":{"
                + "\"idx\":" + cur.getIdx()
                + ",\"like\":" + cur.getLike_cnt()
                + ",\"sym\":" + cur.getSym_cnt()
                + ",\"sad\":" + cur.getSad_cnt()
            + "}"

        + "}";
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}