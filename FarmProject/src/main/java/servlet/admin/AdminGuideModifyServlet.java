package servlet.admin;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import guide.model.GuideDAO;
import guide.model.GuideDTO;

/**
 * Servlet implementation class AdminGuideModifyServlet
 */
@WebServlet("/admin_guide_modify.do")
public class AdminGuideModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminGuideModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
        GuideDAO dao = new GuideDAO();
        GuideDTO dto = dao.guideSelect(Integer.parseInt(id));  // 단건 조회

        request.setAttribute("dto", dto);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/admin_guide_modify.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));

        // ---- 일반 데이터 ----
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String best_date = request.getParameter("best_date");
        String level = request.getParameter("level");
        String water = request.getParameter("water");
        String medicine = request.getParameter("medicine");
        String last_date = request.getParameter("last_date");
        String[] placeArr = request.getParameterValues("place");
        String place = String.join(",", placeArr);
        String link = request.getParameter("link");

        // ---- 이미지 파일 ----
        Part part = request.getPart("image_filename");
        String newFile = part.getSubmittedFileName();

        GuideDAO dao = new GuideDAO();
        GuideDTO old = dao.guideSelect(id);   // 기존 데이터

        String savePath = request.getServletContext().getRealPath("/img/guide");
        File dir = new File(savePath);
        if (!dir.exists()) dir.mkdirs();

        String image_filename;

        if (newFile != null && !newFile.equals("")) {  
            // 새로운 파일 업로드
            part.write(savePath + File.separator + newFile);

            // 기존 파일 삭제
            File oldFile = new File(savePath + File.separator + old.getImage_filename());
            if (oldFile.exists()) oldFile.delete();

            image_filename = newFile;
        } else {
            // 기존 파일 유지
            image_filename = old.getImage_filename();
        }

        // ---- DTO 담기 ----
        GuideDTO dto = new GuideDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setCategory(category);
        dto.setImage_filename(image_filename);
        dto.setBest_date(best_date);
        dto.setLevel(level);
        dto.setWater(water);
        dto.setMedicine(medicine);
        dto.setLast_date(last_date);
        dto.setPlace(place);
        dto.setLink(link);

        dao.guideUpdate(dto);

        response.sendRedirect("admin_guide_list.do");
    }
}
