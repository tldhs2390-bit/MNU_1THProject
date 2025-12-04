package servlet.admin;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import guide.model.GuideDAO;
import guide.model.GuideDTO;

/**
 * Servlet implementation class AdminGuideWriteServlet
 */

@WebServlet("/admin_guide_write.do")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 50,
    maxRequestSize = 1024 * 1024 * 100
)
public class AdminGuideWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminGuideWriteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/Admin/admin_guide_write.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

        // ---------- 1) 일반 데이터 ----------
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

        // ---------- 2) 이미지 파일 ----------
        Part part = request.getPart("image_filename");
        String image_filename = part.getSubmittedFileName();

        // 저장 경로: WebContent/img/guide/
        String savePath = request.getServletContext().getRealPath("/img/guide");

        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        part.write(savePath + File.separator + image_filename);

        // ---------- 3) DTO ----------
        GuideDTO dto = new GuideDTO();
        dto.setName(name);
        dto.setCategory(category);
        dto.setBest_date(best_date);
        dto.setLevel(level);
        dto.setWater(water);
        dto.setMedicine(medicine);
        dto.setLast_date(last_date);
        dto.setPlace(place);
        dto.setLink(link);
        dto.setImage_filename(image_filename); // 파일 이름만 저장

        // ---------- 4) DB 저장 ----------
        GuideDAO dao = new GuideDAO();
        dao.guideWrite(dto);

        // ---------- 5) 등록 완료 후 이동 ----------
        response.sendRedirect("admin_guide_list.do");
    }

	}


