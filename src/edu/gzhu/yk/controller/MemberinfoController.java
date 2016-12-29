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

	
	//得到相应的dao
	public MemberinfoController() {
		md = new MemberDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//根据flag标识判断类型，注册，登陆，登出，所以form表单应该传递flag参数
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

	//注册需要执行的操作
	public void regist(HttpServletRequest request, HttpServletResponse response) {
		try {
			//Chinese toc = new Chinese();
			String name = request.getParameter("membername");
			String psw = request.getParameter("password");
			//String realname = toc.toChinese(request.getParameter("realname"))
			Member m = new Member();
			m.setName(name);
			m.setPassword(psw);
			//遍历数据库查看用户是否存在
			List<Member> lm = md.findByMembername(name);
			if (lm.size() == 0) {
				if (md.save(m)) {
					RequestDispatcher rd = request
							.getRequestDispatcher("registSuccess.jsp");
					rd.forward(request, response);
				} else {
					request.setAttribute("errors", "用户注册失败！");
					RequestDispatcher rd = request
							.getRequestDispatcher("errors.jsp");
					rd.forward(request, response);
				}
			} else {
				request.setAttribute("errors", "用户名已经存在！");
				RequestDispatcher rd = request
						.getRequestDispatcher("errors.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//登陆需要执行的操作
	public void login(HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter("username");
		String psw = request.getParameter("password");
		try {

			List<Member> lm = md.findByMembername(name);
			//判断验证码
			if (!request.getParameter("safecode").equals(
					request.getSession().getAttribute("rand"))) {
				request.setAttribute("errors", "验证码错误！");
				RequestDispatcher rd = request
						.getRequestDispatcher("errors.jsp");
				rd.forward(request, response);
			} else if (lm.size() > 0 && lm.get(0).getPassword().equals(psw)) {
				request.getSession().setAttribute("member", lm.get(0));
				RequestDispatcher rd = request
						.getRequestDispatcher("goods.do?flag=0");//登陆成功，请求转发转向的页面
				rd.forward(request, response);
			} else {
				request.setAttribute("errors", "用户名或密码错误!"); //登陆不成功转向的页面
				RequestDispatcher rd = request
						.getRequestDispatcher("errors.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//登出需要执行的操作
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("member");
		//request.getSession().removeAttribute("Shoppingcart");
		RequestDispatcher rd = request.getRequestDispatcher("logout.jsp");//登出转向的页面
		rd.forward(request, response);
	}
	
}
