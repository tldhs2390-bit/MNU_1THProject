package servlet.growth;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.growth.GrowthDAO;
import model.growth.GrowthDTO;
import model.user.UserDAO;
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

        // 로그인 체크
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        String n_name = loginUser.getN_name();

        // 오늘 글쓰기 2회 제한
        GrowthDAO dao = new GrowthDAO();
        int todayCount = dao.getTodayWriteCount(n_name);

        if (todayCount >= 2) {
            response.sendRedirect("/Growth/growth_limit.jsp");
            return;
        }

        // 게시글 정보 저장
        GrowthDTO dto = new GrowthDTO();
        dto.setCategory(request.getParameter("category"));
        dto.setSubject(request.getParameter("subject"));
        dto.setContents(request.getParameter("contents"));
        dto.setHashtags(request.getParameter("hashtags"));
        dto.setPass(request.getParameter("pass"));
        dto.setN_name(n_name);

        // -------------------------------
        // ⭐ 파일 업로드 — 예전 방식으로 수정
        // -------------------------------
        Part filePart = request.getPart("img");
        String fileName = "";

        if (filePart != null && filePart.getSize() > 0) {

            // 1) 원본 파일명
            String originalFileName = extractFileName(filePart);

            // 2) UUID를 이용해 중복 방지
            String uuid = UUID.randomUUID().toString();
            fileName = uuid + "_" + originalFileName;

            // 3) 저장 경로 설정
            String savePath = request.getServletContext().getRealPath("/asset/growth");

            // 일부 서버에서 build 경로 문제 해결
            savePath = savePath.replaceAll("\\\\build\\\\", "\\\\")
                               .replaceAll("/build/", "/");

            File uploadDir = new File(savePath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // 4) 실제 저장
            filePart.write(savePath + File.separator + fileName);
        }

        dto.setImg(fileName);

        // DB INSERT
        dao.insert(dto);

        // -------------------------------
        // ⭐ 포인트 지급
        // -------------------------------
        UserDAO udao = UserDAO.getInstance();
        boolean pointAdded = udao.addPointLimit(loginUser.getUser_id());
        int addedPoint = pointAdded ? 100 : 0;

        if (addedPoint > 0) {
            loginUser.setPoint(loginUser.getPoint() + addedPoint);
            session.setAttribute("user", loginUser);
        }

        request.setAttribute("pointPlus", addedPoint);

        request.getRequestDispatcher("/Growth/growth_write_success.jsp").forward(request, response);
    }

    // 파일명 추출 (기존 방식 유지)
    private String extractFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf("=") + 2, cd.length() - 1);
            }
        }
        return "";
    }
}