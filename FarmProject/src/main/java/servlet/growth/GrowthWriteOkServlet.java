package servlet.growth;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.growth.GrowthDAO;
import model.growth.GrowthDTO;
import model.user.UserDTO;

@WebServlet("/growth_write_ok.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,       // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class GrowthWriteOkServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 🔐 로그인 체크 — UserDTO 기반으로 변경
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        // 로그인한 사용자 닉네임
        String n_name = loginUser.getN_name();

        // ======================================
        // ⭐ 오늘 글쓰기 횟수 체크 (제한: 2회)
        // ======================================
        GrowthDAO dao = new GrowthDAO();
        int todayCount = dao.getTodayWriteCount(n_name);

        if (todayCount >= 2) {
            // 제한 초과 → 팝업 페이지로 이동
            response.sendRedirect("/growth_limit.jsp");
            return;
        }

        GrowthDTO dto = new GrowthDTO();

        // -----------------------------
        // 기본 데이터
        // -----------------------------
        dto.setCategory(request.getParameter("category"));
        dto.setSubject(request.getParameter("subject"));
        dto.setContents(request.getParameter("contents"));
        dto.setHashtags(request.getParameter("hashtags"));
        dto.setPass(request.getParameter("pass"));
        dto.setN_name(n_name);   // 로그인 사용자 닉네임 저장

        // -----------------------------
        // ⭐ 이미지 업로드
        // -----------------------------
        Part filePart = request.getPart("img");
        String fileName = "";

        if (filePart != null && filePart.getSize() > 0) {

            fileName = extractFileName(filePart);

            String savePath = request.getServletContext().getRealPath("/")
                    .replace("\\build\\", "\\")
                    + "asset" + File.separator + "growth";

            File uploadDir = new File(savePath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            filePart.write(savePath + File.separator + fileName);
        }

        dto.setImg(fileName);

        // -----------------------------
        // DB 저장
        // -----------------------------
        dao.insert(dto);

        // ======================================
        // ⭐ 글 작성 성공 → 사용자 포인트 +100
        // ======================================
        dao.updateUserPoint(n_name, 100);

        // ⭐⭐ 세션의 UserDTO 포인트도 함께 +100 증가 (UI에 바로 반영됨)
        loginUser.setPoint(loginUser.getPoint() + 100);
        session.setAttribute("user", loginUser);

        // ======================================
        // ⭐ 포인트 증가 애니메이션 보여주는 페이지로 이동
        // ======================================
        request.setAttribute("pointPlus", 100);
        request.getRequestDispatcher("/growth_write_success.jsp").forward(request, response);
    }

    // -----------------------------
    // 파일명 추출
    // -----------------------------
    private String extractFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf("=") + 2, cd.length() - 1);
            }
        }
        return "";
    }
}