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

@WebServlet("/growth_modify_ok.do")

// ⭐ 파일 업로드 가능하도록 설정
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,   // 1MB
        maxFileSize = 1024 * 1024 * 10,        // 10MB
        maxRequestSize = 1024 * 1024 * 50      // 50MB
)
public class GrowthModifyOkServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 🔐 ==========================================
        // 🔐 로그인 체크 (UserDTO 사용)
        // 🔐 ==========================================
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");   // ✔ 수정됨
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user_login.do?msg=login_required");
            return;
        }

        // ==============================
        // 📌 1) DTO 채우기
        // ==============================
        GrowthDTO dto = new GrowthDTO();

        dto.setIdx(Integer.parseInt(request.getParameter("idx")));
        dto.setCategory(request.getParameter("category"));
        dto.setSubject(request.getParameter("subject"));
        dto.setContents(request.getParameter("contents"));
        dto.setHashtags(request.getParameter("hashtags"));

        // ==============================
        // 📌 2) 기존 이미지 파일명 받아오기
        // ==============================
        String oldImg = request.getParameter("oldImg");  // JSP hidden 필드

        // ==============================
        // 📌 3) 새 이미지 업로드 처리
        // ==============================
        Part imgPart = request.getPart("img");
        String fileName = "";

        if (imgPart != null && imgPart.getSize() > 0) {

            // 업로드 경로
            String path = request.getServletContext().getRealPath("/asset/growth/");
            File uploadDir = new File(path);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // 고유 파일명 생성
            fileName = System.currentTimeMillis() + "_" + imgPart.getSubmittedFileName();

            // 실제 저장
            imgPart.write(path + fileName);

            dto.setImg(fileName);   // 새 이미지 적용

        } else {
            // 새 이미지를 선택하지 않으면 기존 이미지 유지
            dto.setImg(oldImg);
        }

        // ==============================
        // 📌 4) DB 업데이트 실행
        // ==============================
        GrowthDAO dao = new GrowthDAO();
        dao.update(dto);

        // ==============================
        // 📌 5) 수정 완료 → 상세보기 이동
        // ==============================
        response.sendRedirect("/growth_read.do?idx=" + dto.getIdx());
    }
}
