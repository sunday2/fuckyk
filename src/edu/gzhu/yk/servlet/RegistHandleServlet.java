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
 * Servlet implementation class RegistHandleServlet
 */
@WebServlet("/RegistHandleServlet")
public class RegistHandleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberDAO md;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistHandleServlet() {
        super();
        md=new MemberDAO();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		JSONObject joj=regist(request, response);
		System.out.println(joj.toString());
		 PrintWriter writer=response.getWriter();
	        writer.write(joj.toString());
	        writer.flush();
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("dopost");
		doGet(request, response);
	}
	//注册需要执行的操作
		public JSONObject regist(HttpServletRequest request, HttpServletResponse response) {
			JSONObject joj=new JSONObject();
			try {
				String name = request.getParameter("username");
				String psw = request.getParameter("password");
				Member m = new Member();
				m.setName(name);
				m.setPassword(psw);
				List<Member> lm = md.findByMembername(name);
				if (lm.size() == 0) {
					if (md.save(m)) {
						joj.put("ret", 200);
						
					} else {
						joj.put("ret", 200);
						joj.put("msg", "数据库插入错误");
					}
				} else {  
					joj.put("ret", 400);
					joj.put("msg","该名字已经存在");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return joj;
			
		}

}
