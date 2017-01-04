package edu.gzhu.yk.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;

import edu.gzhu.yk.dao.PictureDAO;
import edu.gzhu.yk.util.ResponseUtils;

/**
 * Servlet implementation class UploadHandleServlet
 */
@WebServlet("/UploadHandleServlet")
public class UploadHandleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PictureDAO pd;
	private String fileName = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadHandleServlet() {
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
		String savePath = this.getServletContext().getRealPath("/upload");
		JSONObject joj = new JSONObject();
		String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
		File file = new File(tempPath);
		if (!file.exists() && !file.isDirectory()) {
			System.out.println("目录不存在");
			file.mkdir();
		}
		try {
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			diskFileItemFactory.setSizeThreshold(1024 * 100);
			diskFileItemFactory.setRepository(file);
			ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
			fileUpload.setHeaderEncoding("UTF-8");
			fileUpload.setProgressListener(new ProgressListener() {
				public void update(long pBytesRead, long pContentLength, int arg2) {
					System.out.println("锟侥硷拷锟斤拷小为锟斤拷" + pContentLength + ",锟斤拷前锟窖达拷锟斤拷" + pBytesRead);
				}
			});
			if (!fileUpload.isMultipartContent(request)) {
				try {
					joj.put("msg", "不是文件");
					joj.put("ret", 400);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*PrintWriter writer = response.getWriter();
				writer.write(joj.toString());
				writer.flush();*/
				ResponseUtils.renderJson(response, joj.toString());
				return;
			}
			//设置上传文件大小的最大值
			fileUpload.setFileSizeMax(1024 * 1024);
			//设置请求消息实体大小的最大值
			fileUpload.setSizeMax(1024 * 1024 * 10);
			List<FileItem> list = fileUpload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					String value1 = new String(name.getBytes("iso8859-1"), "UTF-8");
					System.out.println(name + "  " + value);
					System.out.println(name + "  " + value1);
				} else {
					fileName = item.getName();
					System.out.println(fileName);
					if (fileName == null || fileName.trim().equals("")) {
						continue;
					}
					fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
					String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
					if ("zip".equals(fileExtName) || "rar".equals(fileExtName) || "tar".equals(fileExtName)
							|| "jar".equals(fileExtName)) {
						try {
							joj.put("ret", 400);
							joj.put("msg", "不是文件,是压缩包");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						/*PrintWriter writer = response.getWriter();
						writer.write(joj.toString());
						writer.flush();*/
						ResponseUtils.renderJson(response, joj.toString());
						return;
					}
					System.out.println("文件扩展名:" + fileExtName);
					InputStream is = item.getInputStream();
					fileName = mkFileName(fileName);
					System.out.println("fi:" + fileName);
					String savePathStr = mkFilePath(savePath, fileName);
					System.out.println("保存路径为:" + savePathStr);
					FileOutputStream fos = new FileOutputStream(savePathStr + File.separator + fileName);
					byte buffer[] = new byte[1024];
					int length = 0;
					while ((length = is.read(buffer)) > 0) {
						fos.write(buffer, 0, length);
					}
					is.close();
					fos.close();
					item.delete();
				}
			}
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			e.printStackTrace();
			try {
				joj.put("ret", "400");
				joj.put("msg", "上传文件太大");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		} catch (FileUploadBase.SizeLimitExceededException e) {
			e.printStackTrace();
			try {
				joj.put("ret", "400");
				joj.put("msg", "请求消息体太大");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				joj.put("ret", "400");
				joj.put("msg", "文件上传异常");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		try {
			System.out.println(fileName);
			if (this.pd.save(fileName)) {
				joj.put("ret", "200");
				JSONObject dataJson = new JSONObject();
				dataJson.put("fileId", 1);
				dataJson.put("fileName", fileName);
				joj.put("data", dataJson);
			} else {
				joj.put("ret", "400");
				joj.put("msg", "插入失败");
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*PrintWriter writer = response.getWriter();
		writer.write(joj.toString());
		writer.flush();*/
		ResponseUtils.renderJson(response, joj.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub k
		doGet(request, response);
	}

	public String mkFileName(String fileName) {
		return UUID.randomUUID().toString() + "_" + fileName;
	}

	public String mkFilePath(String savePath, String fileName) {
		int hashcode = fileName.hashCode();
		int dir1 = hashcode & 0xf;
		int dir2 = (hashcode & 0xf0) >> 4;
		String dir = savePath;
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		return dir;
	}

}
