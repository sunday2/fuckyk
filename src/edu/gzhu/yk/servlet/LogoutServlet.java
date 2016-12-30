package edu.gzhu.yk.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import edu.gzhu.yk.dao.MemberDAO;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberDAO md;    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        md=new MemberDAO();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		JSONObject joj=logout(request, response);
		 PrintWriter writer=response.getWriter();
	        writer.write(joj.toString());
	        writer.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	    //登出需要执行的操作
		public JSONObject logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//request.getSession().removeAttribute("member");
			//request.getSession().removeAttribute("Shoppingcart");
			//RequestDispatcher rd = request.getRequestDispatcher("logout.jsp");//登出转向的页面
			//rd.forward(request, response);
			JSONObject joj=new JSONObject();
			try {
				joj.put("ret",200);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return joj;
		}

}
