package servlet.admin.growth;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.admin.growth.AdminGrowthDAO;
import model.growth.GrowthDTO;

@WebServlet("/admin_growth_modify_ok.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,     // 10MB
        maxRequestSize = 1024 * 1024 * 50   // 50MB
)
public class AdminGrowthModifyOkServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // -------------------------
        // 기본 정보 수집
        // -------------------------
        int idx = Integer.parseInt(request.getParameter("idx"));
        String category = request.getParameter("category");
        String subject = request.getParameter("subject");
        String contents = request.getParameter("contents");
        String hashtags = request.getParameter("hashtags");
        String oldImg = request.getParameter("oldImg"); // 기존 이미지명

        // -------------------------
        // 업로드 폴더 위치
        // -------------------------
        String uploadPath = request.getServletContext().getRealPath("/asset/growth/");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // -------------------------
        // 새 파일 업로드 확인
        // -------------------------
        Part imgPart = request.getPart("imgFile");
        String fileName = null;

        if (imgPart != null && imgPart.getSize() > 0) {

            // 실제 파일명 얻기
            String header = imgPart.getHeader("Content-Disposition");
            String originalName = header.substring(header.lastIndexOf("=") + 2, header.length() - 1);

            // 저장될 파일명
            fileName = System.currentTimeMillis() + "_" + originalName;

            // 서버에 저장
            imgPart.write(uploadPath + File.separator + fileName);

            // 기존 이미지 삭제
            if (oldImg != null && !oldImg.equals("")) {
                File old = new File(uploadPath + File.separator + oldImg);
                if (old.exists()) old.delete();
            }

        } else {
            // 새 이미지 업로드 안함 → 기존 이미지 유지
            fileName = oldImg;
        }

        // -------------------------
        // DTO에 데이터 담기
        // -------------------------
        GrowthDTO dto = new GrowthDTO();
        dto.setIdx(idx);
        dto.setCategory(category);
        dto.setSubject(subject);
        dto.setContents(contents);
        dto.setHashtags(hashtags);
        dto.setImg(fileName);

        // -------------------------
        // DB 업데이트
        // -------------------------
        AdminGrowthDAO dao = new AdminGrowthDAO();
        int result = dao.updatePost(dto);

        // -------------------------
        // 결과 이동
        // -------------------------
        if (result > 0) {
            response.sendRedirect("/admin_growth_list.do?msg=success");
        } else {
            response.sendRedirect("/admin_growth_list.do?msg=fail");
        }
    }
}