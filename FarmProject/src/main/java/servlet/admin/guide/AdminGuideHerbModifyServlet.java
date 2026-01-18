package servlet.admin.guide;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import guide.model.GuideDAO;
import guide.model.GuideDTO;

@WebServlet("/admin_guide_herb_modify.do")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)   // 10MB 업로드 허용
public class AdminGuideHerbModifyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminGuideHerbModifyServlet() {
        super();
    }

    // 수정 폼 이동
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	//관리자가 로그인 하지 않은 경우 관리자 로그인 페이지로 이동 
		HttpSession session = request.getSession();
		if(session.getAttribute("admin") == null) {
			response.sendRedirect("admin_login.do");
			return;
		}
        String id = request.getParameter("id");
        GuideDAO dao = new GuideDAO();
        GuideDTO dto = dao.guideSelect(Integer.parseInt(id));

        request.setAttribute("dto", dto);

        RequestDispatcher rd = request.getRequestDispatcher("/Admin/admin_guide_herb_modify.jsp");
        rd.forward(request, response);
    }

    // 수정 처리
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ----------- Multipart 방식으로 파라미터 읽기 -----------
        int id = Integer.parseInt(getValue(request.getPart("id")));
        String name = getValue(request.getPart("name"));
        String category = getValue(request.getPart("category"));
        String best_date = getValue(request.getPart("best_date"));
        String level = getValue(request.getPart("level"));
        String water = getValue(request.getPart("water"));
        String medicine = getValue(request.getPart("medicine"));
        String last_date = getValue(request.getPart("last_date"));
        String link = getValue(request.getPart("link"));

        // checkbox는 여러 개이므로 getParameterValues처럼 따로 처리
        String place = "";
        for (Part p : request.getParts()) {
            if ("place".equals(p.getName())) {
                String v = getValue(p);
                if (v != null && !v.equals("")) {
                    if (!place.equals("")) place += ",";
                    place += v;
                }
            }
        }

        // 기존 데이터 조회
        GuideDAO dao = new GuideDAO();
        GuideDTO old = dao.guideSelect(id);

        // -------------- 이미지 업로드 처리 ------------------
        Part imgPart = request.getPart("image_filename");
        String newFile = imgPart.getSubmittedFileName();

        String savePath = request.getServletContext().getRealPath("/img/guide");
        File dir = new File(savePath);
        if (!dir.exists()) dir.mkdirs();

        String image_filename;

        if (newFile != null && !newFile.equals("")) {
            // 새 파일 업로드
            imgPart.write(savePath + File.separator + newFile);

            // 기존 파일 삭제
            File oldFile = new File(savePath + File.separator + old.getImage_filename());
            if (oldFile.exists()) oldFile.delete();

            image_filename = newFile;
        } else {
            // 새 파일 업로드 안했으면 기존 파일 유지
            image_filename = old.getImage_filename();
        }

        // DTO 담기
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

        response.sendRedirect("admin_guide_herb_list.do");
    }

    // ----------- Part → String 변환용 메서드 -------------
    private String getValue(Part part) throws IOException {
        if (part == null) return null;
        return new String(part.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}