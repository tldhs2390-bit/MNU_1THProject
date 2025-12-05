package servlet.success;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.success.SuccessDAO;

@WebServlet("/Success/like.do")
public class SuccessLikeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");

        HttpSession session = request.getSession();

        // ⭐ 로그인 기능이 없으므로 n_name을 userid로 사용
        String userid = (String) session.getAttribute("n_name");

        if (userid == null || userid.equals("")) {
            response.getWriter().print("not-login");
            return;
        }

        int idx = Integer.parseInt(request.getParameter("idx"));

        SuccessDAO dao = new SuccessDAO();

        // ⭐ 좋아요 토글 실행 (하루 3번 제한 포함)
        String result = dao.toggleLike(idx, userid);

        // ⭐ JS로 결과 전송 → liked / unliked / limit
        response.getWriter().print(result);
    }
}