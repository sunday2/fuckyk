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
	//ע����Ҫִ�еĲ���
		public JSONObject regist(HttpServletRequest request, HttpServletResponse response) {
			JSONObject joj=new JSONObject();
			try {
				//Chinese toc = new Chinese();
				String name = request.getParameter("username");
				String psw = request.getParameter("password");
				//String realname = toc.toChinese(request.getParameter("realname"))
				Member m = new Member();
				m.setName(name);
				m.setPassword(psw);
				//�������ݿ�鿴�û��Ƿ����
				List<Member> lm = md.findByMembername(name);
				if (lm.size() == 0) { //���ݿⲻ���ڸ�����
					if (md.save(m)) {
						/*RequestDispatcher rd = request
								.getRequestDispatcher("registSuccess.jsp");
						rd.forward(request, response);*/
						joj.put("ret", 200);
						
					} else {
						/*request.setAttribute("errors", "�û�ע��ʧ�ܣ�");
						RequestDispatcher rd = request
								.getRequestDispatcher("errors.jsp");
						rd.forward(request, response);*/
						joj.put("ret", 200);
						joj.put("msg", "���ݿ�������");
					}
				} else {   //���ݿ��Ѿ����������
					/*request.setAttribute("errors", "�û����Ѿ����ڣ�");
					RequestDispatcher rd = request
							.getRequestDispatcher("errors.jsp");
					rd.forward(request, response);*/
					joj.put("ret", 400);
					joj.put("msg","�������Ѿ�����");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return joj;
			
		}

}
