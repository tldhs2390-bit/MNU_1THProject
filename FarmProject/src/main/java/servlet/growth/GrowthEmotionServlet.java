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
 * - 게시글 단위로 각 버튼별 한 번씩 감정 가능
 * - AJAX 응답(JSON 반환 — success / already-pressed / login-required)
 */
@WebServlet("/growth_emotion.do")
public class GrowthEmotionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");

        response.setContentType("application/json; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        if (loginUser == null) {
            response.getWriter().write("{\"result\":\"login-required\"}");
            return;
        }

        int idx = Integer.parseInt(request.getParameter("idx"));
        String type = request.getParameter("type");

        GrowthDAO dao = new GrowthDAO();

        // ▶ 버튼별 세션 체크 (하트/좋아요/슬픈 독립)
        String postKey = "emotion_post_" + idx + "_" + type;
        Boolean pressed = (Boolean) session.getAttribute(postKey);

        if (pressed != null && pressed) {
            response.getWriter().write("{\"result\":\"already-pressed\"}");
            return;
        }

        // ▶ 감정 증가
        dao.updateEmotion(idx, type);

        // ▶ 버튼별 세션 기록
        session.setAttribute(postKey, true);

        // ▶ 최신 감정 수 반환
        GrowthEmotionSummary data = dao.getEmotionSummary(idx);
        String json = "{"
                + "\"result\":\"success\","
                + "\"current\":{"
                + "\"like_cnt\":" + data.current.getLike_cnt() + ","
                + "\"sym_cnt\":" + data.current.getSym_cnt() + ","
                + "\"sad_cnt\":" + data.current.getSad_cnt()
                + "}"
                + "}";
        response.getWriter().write(json);
    }
}