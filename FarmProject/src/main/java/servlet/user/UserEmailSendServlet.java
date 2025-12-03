package servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.model.MailSender;

/**
 * Servlet implementation class UserEmailServlet
 */
@WebServlet("/User/emailSend.do")
public class UserEmailSendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserEmailSendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        String email = request.getParameter("email");

        // 랜덤 인증코드 생성 (6자리)
        int code = (int)(Math.random() * 900000) + 100000;

        // 세션에 저장
        request.getSession().setAttribute("emailCode", code);

        boolean result = MailSender.sendMail(email, "회원가입 인증코드","인증코드 : " + code);

        if(result){
            response.getWriter().print("OK");
        } else {
            response.getWriter().print("FAIL");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
