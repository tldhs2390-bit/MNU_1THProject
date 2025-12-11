package servlet.growth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.growth.GrowthDAO;
import model.growth.GrowthDAO.GrowthEmotionSummary;
import model.growth.GrowthDTO;
import model.user.UserDTO;

/**
 * 게시글 감정 처리 서블릿
 * - 게시글 단위로 한 번만 감정 가능
 * - AJAX 응답(JSON 반환 — success / already-pressed)
 */
@WebServlet("/growth_emotion.do")
public class GrowthEmotionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");

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
        // 1) 게시글 단위로 이미 눌렀는지 체크
        // ------------------------------
        String postKey = "emotion_post_" + idx;  // 게시글 단위
        Boolean pressed = (Boolean) session.getAttribute(postKey);

        if (pressed != null && pressed) {
            response.getWriter().write("{\"result\":\"already-pressed\"}");
            return;
        }

        // ------------------------------
        // 2) 감정 증가
        // ------------------------------
        dao.updateEmotion(idx, type);

        // ------------------------------
        // 3) 게시글 단위로 눌렀음을 기록
        // ------------------------------
        session.setAttribute(postKey, true);

        // ------------------------------
        // 4) AJAX JSON 반환
        // ------------------------------
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