package servlet.growth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.growth.GrowthDAO;
import model.growth.GrowthDTO;
import model.user.UserDTO;

@WebServlet("/growth_delete.do")
public class GrowthDeleteServlet extends HttpServlet {

    // ============================================================
    // 1) GET 방식 삭제 (삭제 버튼 클릭 시)
    // ============================================================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 로그인 세션 체크 (UserDTO 기반)
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");   // ★ 핵심 변경

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        int idx = Integer.parseInt(request.getParameter("idx"));

        GrowthDAO dao = new GrowthDAO();
        GrowthDTO dto = dao.getOne(idx);

        if (dto == null) {
            response.getWriter().write("<script>alert('존재하지 않는 글입니다.'); history.back();</script>");
            return;
        }

        // 비밀번호 없이 바로 삭제
        dao.delete(idx);

        response.getWriter().write(
            "<html><head><meta charset='UTF-8'>"
            + "<title>삭제 완료</title>"
            + "<style>"
            + "body { background:#f4fbe9; font-family:'Noto Sans KR'; margin:0; padding:0; text-align:center; }"
            + ".box { margin-top:140px; font-size:32px; font-weight:900; color:#4CAF50;"
            + " animation: pop 1.1s ease-out forwards; }"
            + "@keyframes pop {"
            + " 0% { transform:scale(0.6); opacity:0; }"
            + " 60% { transform:scale(1.15); opacity:1; }"
            + " 100% { transform:scale(1); }"
            + "}"
            + ".fade-msg { margin-top:18px; font-size:18px; color:#666;"
            + " animation: fadein 2s ease-in-out forwards; }"
            + "@keyframes fadein {"
            + " 0% { opacity:0; }"
            + " 100% { opacity:1; }"
            + "}"
            + "</style>"
            + "</head><body>"

            + "<div class='box'>🍂 게시글이 삭제되었습니다 🍂</div>"
            + "<div class='fade-msg'>목록으로 이동 중입니다...</div>"

            + "<script>setTimeout(()=>{ location.href='/growth_list.do'; }, 1500);</script>"
            + "</body></html>"
        );
    }

    // ============================================================
    // 2) POST 방식 삭제 (비밀번호 삭제)
    // ============================================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 로그인 세션 체크 (UserDTO 기반)
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");   // ★ 핵심 변경

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        int idx = Integer.parseInt(request.getParameter("idx"));
        String pass = request.getParameter("pass");

        GrowthDAO dao = new GrowthDAO();
        GrowthDTO dto = dao.getOne(idx);

        if (dto == null) {
            response.getWriter().write("<script>alert('존재하지 않는 글입니다.'); history.back();</script>");
            return;
        }

        if (!dto.getPass().equals(pass)) {
            response.getWriter().write("<script>alert('비밀번호가 틀렸습니다!'); history.back();</script>");
            return;
        }

        dao.delete(idx);

        response.getWriter().write(
            "<html><head><meta charset='UTF-8'>"
            + "<title>삭제 완료</title>"
            + "<style>"
            + "body { background:#f4fbe9; font-family:'Noto Sans KR'; margin:0; padding:0; text-align:center; }"
            + ".box { margin-top:140px; font-size:32px; font-weight:900; color:#4CAF50;"
            + " animation: pop 1.1s ease-out forwards; }"
            + "@keyframes pop {"
            + " 0% { transform:scale(0.6); opacity:0; }"
            + " 60% { transform:scale(1.15); opacity:1; }"
            + " 100% { transform:scale(1); }"
            + "}"
            + ".fade-msg { margin-top:18px; font-size:18px; color:#666;"
            + " animation: fadein 2s ease-in-out forwards; }"
            + "@keyframes fadein {"
            + " 0% { opacity:0; }"
            + " 100% { opacity:1; }"
            + "}"
            + "</style>"
            + "</head><body>"

            + "<div class='box'>🍂 게시글이 삭제되었습니다 🍂</div>"
            + "<div class='fade-msg'>목록으로 이동 중입니다...</div>"

            + "<script>setTimeout(()=>{ location.href='/growth_list.do'; }, 1500);</script>"
            + "</body></html>"
        );
    }
}