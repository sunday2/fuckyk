package edu.gzhu.yk.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.gzhu.fuckyk.pojo.Member;
import edu.gzhu.yk.dao.MemberDAO;

public class MemberinfoController {

	private static final long serialVersionUID = 1L;
	private MemberDAO md;

	
	//�õ���Ӧ��dao
	public MemberinfoController() {
		md = new MemberDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//����flag��ʶ�ж����ͣ�ע�ᣬ��½���ǳ�������form��Ӧ�ô���flag����
		switch (Integer.parseInt(request.getParameter("flag"))) {
		case 0:
			regist(request, response);
			break;
		case 1:
			login(request, response);
			break;
		case 2:
			logout(request,response);
			break;
		}
	}

	//ע����Ҫִ�еĲ���
	public void regist(HttpServletRequest request, HttpServletResponse response) {
		try {
			//Chinese toc = new Chinese();
			String name = request.getParameter("membername");
			String psw = request.getParameter("password");
			//String realname = toc.toChinese(request.getParameter("realname"))
			Member m = new Member();
			m.setName(name);
			m.setPassword(psw);
			//�������ݿ�鿴�û��Ƿ����
			List<Member> lm = md.findByMembername(name);
			if (lm.size() == 0) {
				if (md.save(m)) {
					RequestDispatcher rd = request
							.getRequestDispatcher("registSuccess.jsp");
					rd.forward(request, response);
				} else {
					request.setAttribute("errors", "�û�ע��ʧ�ܣ�");
					RequestDispatcher rd = request
							.getRequestDispatcher("errors.jsp");
					rd.forward(request, response);
				}
			} else {
				request.setAttribute("errors", "�û����Ѿ����ڣ�");
				RequestDispatcher rd = request
						.getRequestDispatcher("errors.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��½��Ҫִ�еĲ���
	public void login(HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter("username");
		String psw = request.getParameter("password");
		try {

			List<Member> lm = md.findByMembername(name);
			//�ж���֤��
			if (!request.getParameter("safecode").equals(
					request.getSession().getAttribute("rand"))) {
				request.setAttribute("errors", "��֤�����");
				RequestDispatcher rd = request
						.getRequestDispatcher("errors.jsp");
				rd.forward(request, response);
			} else if (lm.size() > 0 && lm.get(0).getPassword().equals(psw)) {
				request.getSession().setAttribute("member", lm.get(0));
				RequestDispatcher rd = request
						.getRequestDispatcher("goods.do?flag=0");//��½�ɹ�������ת��ת���ҳ��
				rd.forward(request, response);
			} else {
				request.setAttribute("errors", "�û������������!"); //��½���ɹ�ת���ҳ��
				RequestDispatcher rd = request
						.getRequestDispatcher("errors.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//�ǳ���Ҫִ�еĲ���
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("member");
		//request.getSession().removeAttribute("Shoppingcart");
		RequestDispatcher rd = request.getRequestDispatcher("logout.jsp");//�ǳ�ת���ҳ��
		rd.forward(request, response);
	}
	
}
