package edu.gzhu.yk.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import edu.gzhu.fuckyk.pojo.Member;
import edu.gzhu.yk.dao.MemberDAO;

/**
 * Servlet implementation class LoginHandleServlet
 */
@WebServlet("/LoginHandleServlet")
public class LoginHandleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberDAO md;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginHandleServlet() {
        super();
        this.md = new MemberDAO();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject joj = new JSONObject();
		String name = request.getParameter("username");
		String psw = request.getParameter("password");
		try {

			List<Member> lm = this.md.findByMembername(name);
		    if (lm.size() > 0 && lm.get(0).getPassword().equals(psw)) {
				joj.put("ret", 200);
				JSONObject datajson=new JSONObject();
				int member_id=lm.get(0).getMember_id();
				datajson.put("user_id",member_id);
				joj.put("data", datajson);
			} else {
				joj.put("ret", 400);
				joj.put("msg","Ãû×Ö»òÃÜÂë´íÎó");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(joj.toString());
		PrintWriter writer = response.getWriter();
	    writer.write(joj.toString());
	    writer.flush();
	}
}
