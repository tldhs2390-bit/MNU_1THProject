package servlet.user;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.user.MailSender;
import model.user.UserDAO;
import util.UserSHA256;

/**
 * Servlet implementation class UserFindPassServlet
 */
@WebServlet("/user_find_pass.do")
public class UserFindPassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserFindPassServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/User/user_find_pass.jsp");
	    rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

	    String userId = request.getParameter("user_id");
	    String email  = request.getParameter("email");

	    UserDAO dao = UserDAO.getInstance();

	    // 1) 회원 존재 여부 확인
	    int result = dao.findPassCheck(userId, email);

	    if(result == 0) {
	        request.setAttribute("msg", "입력하신 정보와 일치하는 계정이 없습니다.");

	    } else {

	        // 2) 임시 비밀번호 생성
	        String tempPw = UUID.randomUUID().toString().substring(0, 8);

	        // 3) 암호화 후 DB 저장
	        String encPw = UserSHA256.getSHA256(tempPw);
	        dao.updatePass(userId, encPw);

	        // 4) 이메일 발송
	        String title = "[FarmProject] 임시 비밀번호 안내";
	        String content =
	                "안녕하세요.\n\n" +
	                "회원님의 임시 비밀번호는 아래와 같습니다.\n\n" +
	                "임시 비밀번호 : " + tempPw + "\n\n" +
	                "로그인 후 반드시 비밀번호를 변경해주세요.\n";

	        boolean mailResult = MailSender.sendMail(email, title, content);

	        if(mailResult) {
	            request.setAttribute("msg", "임시 비밀번호가 이메일로 발송되었습니다.");
	        } else {
	            request.setAttribute("msg", "이메일 전송에 실패했습니다. 다시 시도해주세요.");
	        }
	    }

	    RequestDispatcher rd = request.getRequestDispatcher("/User/user_find_pass_ok.jsp");
	    rd.forward(request, response);
	}
	}
