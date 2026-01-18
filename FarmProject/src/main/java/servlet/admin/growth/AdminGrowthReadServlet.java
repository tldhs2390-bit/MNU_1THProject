package servlet.admin.growth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.admin.growth.AdminGrowthDAO;
import model.admin.reply.AdminReplyDAO;
import model.growth.GrowthDTO;
import model.reply.ReplyDTO;

import java.util.List;

@WebServlet("/admin_growth_read.do")
public class AdminGrowthReadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ***************************************
        // 🔥 idx null 체크 (가장 중요한 부분)
        // ***************************************
        String idxStr = request.getParameter("idx");

        if (idxStr == null || idxStr.equals("")) {
            // idx가 없으면 리스트로 보냄 (에러 방지)
            response.sendRedirect("admin_growth_list.do");
            return;
        }

        int idx = Integer.parseInt(idxStr);

        // DAO 준비
        AdminGrowthDAO gdao = new AdminGrowthDAO();
        AdminReplyDAO rdao = new AdminReplyDAO();

        // 글 정보 가져오기
        GrowthDTO dto = gdao.getPost(idx);
        request.setAttribute("dto", dto);

        // 댓글 목록 가져오기 (숨김 포함 전체)
        List<ReplyDTO> replyList = rdao.getRepliesByPost_All(idx);
        request.setAttribute("replyList", replyList);

        // JSP 이동
        request.getRequestDispatcher("/Admin/admin_growth_read.jsp")
               .forward(request, response);
    }
}