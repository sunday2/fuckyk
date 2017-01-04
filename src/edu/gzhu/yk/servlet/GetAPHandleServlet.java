package edu.gzhu.yk.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.gzhu.fuckyk.pojo.Picture;
import edu.gzhu.yk.dao.MemberDAO;
import edu.gzhu.yk.dao.PictureDAO;
import edu.gzhu.yk.util.ResponseUtils;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class GetAPHandleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PictureDAO pd;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAPHandleServlet() {
		super();
		this.pd = new PictureDAO();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		List<Picture> list = new ArrayList<Picture>();
		list = this.pd.getAll();
		JSONArray jsonarray = new JSONArray();
		for (int i = 0; i < list.size(); ++i) {
			JSONObject joj = new JSONObject();
			try {
				joj.put("picture_id", list.get(i).getPicture_id());
				joj.put("picture_url", list.get(i).getPicture_url());
				jsonarray.put(i, joj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		JSONObject joj1 = new JSONObject();
		try {
			joj1.put("ret", 200);
			joj1.put("data", jsonarray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*PrintWriter writer = response.getWriter();
		writer.write(joj1.toString());
		writer.flush();*/
		ResponseUtils.renderJson(response, joj1.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
