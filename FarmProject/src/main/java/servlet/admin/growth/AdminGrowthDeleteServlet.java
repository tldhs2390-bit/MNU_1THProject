package servlet.admin.growth;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.admin.growth.AdminGrowthDAO;
import model.growth.GrowthDTO;

@WebServlet("/admin_growth_delete.do")
public class AdminGrowthDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int idx = Integer.parseInt(request.getParameter("idx"));

        AdminGrowthDAO dao = new AdminGrowthDAO();

        // ---------------------------
        // 1) 삭제 전 이미지 파일 조회
        // ---------------------------
        GrowthDTO dto = dao.getPost(idx);
        String imgName = (dto != null) ? dto.getImg() : null;

        // ---------------------------
        // 2) DB에서 글 + 댓글 삭제
        // ---------------------------
        int result = dao.deletePost(idx);

        // ---------------------------
        // 3) 이미지 파일 삭제
        // ---------------------------
        if (result > 0 && imgName != null && !imgName.equals("")) {

            String savePath = request.getServletContext().getRealPath("/asset/growth");
            File file = new File(savePath + File.separator + imgName);

            if (file.exists()) {
                file.delete(); // 이미지 파일 삭제
            }
        }

        // ---------------------------
        // 4) 관리자 리스트로 이동
        // ---------------------------
        response.sendRedirect("/admin_growth_list.do");
    }
}