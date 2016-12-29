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



/**
 * Servlet implementation class UploadHandleServlet
 */
@WebServlet("/UploadHandleServlet")
public class UploadHandleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadHandleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		 //锟矫碉拷锟较达拷锟侥硷拷锟侥憋拷锟斤拷目录锟斤拷锟斤拷锟较达拷锟斤拷锟侥硷拷锟斤拷锟斤拷锟絎EB-INF目录锟铰ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷锟街憋拷臃锟斤拷剩锟斤拷锟街わ拷洗锟斤拷募锟斤拷陌锟饺�
        String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
        JSONObject joj = new JSONObject();
        //锟较达拷时锟斤拷锟缴碉拷锟斤拷时锟侥硷拷锟斤拷锟斤拷目录
        String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
        File file = new File(tempPath);
        if(!file.exists()&&!file.isDirectory()){
            System.out.println("目录锟斤拷锟侥硷拷锟斤拷锟斤拷锟节ｏ拷");
            file.mkdir();
        }
        //锟斤拷息锟斤拷示
        String message = "";
        try {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            diskFileItemFactory.setSizeThreshold(1024*100);
            diskFileItemFactory.setRepository(file);
            ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
            fileUpload.setHeaderEncoding("UTF-8");
            fileUpload.setProgressListener(new ProgressListener(){
                public void update(long pBytesRead, long pContentLength, int arg2) {
                    System.out.println("锟侥硷拷锟斤拷小为锟斤拷" + pContentLength + ",锟斤拷前锟窖达拷锟斤拷" + pBytesRead);
                }
            });
           if(!fileUpload.isMultipartContent(request)){
                //锟斤拷锟秸达拷统锟斤拷式锟斤拷取锟斤拷锟斤拷
                return;
            }
            //锟斤拷锟斤拷锟较达拷锟斤拷锟斤拷锟侥硷拷锟侥达拷小锟斤拷锟斤拷锟街碉拷锟侥壳帮拷锟斤拷锟斤拷锟轿�1024*1024锟街节ｏ拷也锟斤拷锟斤拷1MB
            fileUpload.setFileSizeMax(1024*1024);
            //锟斤拷锟斤拷锟较达拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟斤拷锟街碉拷锟斤拷锟斤拷值=同时锟较达拷锟侥讹拷锟斤拷募锟斤拷拇锟叫★拷锟斤拷锟斤拷值锟侥和ｏ拷目前锟斤拷锟斤拷为10MB
            fileUpload.setSizeMax(1024*1024*10);
            //4锟斤拷使锟斤拷ServletFileUpload锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟较达拷锟斤拷锟捷ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷氐锟斤拷锟揭伙拷锟絃ist<FileItem>锟斤拷锟较ｏ拷每一锟斤拷FileItem锟斤拷应一锟斤拷Form锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
            List<FileItem> list = fileUpload.parseRequest(request);
            for (FileItem item : list) {
                //锟斤拷锟絝ileitem锟叫凤拷装锟斤拷锟斤拷锟斤拷通锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //锟斤拷锟斤拷锟酵拷锟斤拷锟斤拷锟斤拷锟斤拷锟捷碉拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
                    String value = item.getString("UTF-8");
                    String value1 = new String(name.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name+"  "+value);
                    System.out.println(name+"  "+value1);
                }else{
                    //锟斤拷锟絝ileitem锟叫凤拷装锟斤拷锟斤拷锟较达拷锟侥硷拷锟斤拷锟矫碉拷锟较达拷锟斤拷锟侥硷拷锟斤拷锟狡ｏ拷
                    String fileName = item.getName();
                    System.out.println(fileName);
                    if(fileName==null||fileName.trim().equals("")){
                        continue;
                    }
                    //注锟解：锟斤拷同锟斤拷锟斤拷锟斤拷锟斤拷峤伙拷锟斤拷募锟斤拷锟斤拷遣锟揭伙拷锟斤拷模锟斤拷锟叫╋拷锟斤拷锟斤拷锟结交锟斤拷锟斤拷锟斤拷锟侥硷拷锟斤拷锟角达拷锟斤拷路锟斤拷锟侥ｏ拷锟界：  c:\a\b\1.txt锟斤拷锟斤拷锟斤拷些只锟角碉拷锟斤拷锟斤拷锟侥硷拷锟斤拷锟斤拷锟界：1.txt
                    //锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷洗锟斤拷募锟斤拷锟斤拷募锟斤拷锟斤拷锟铰凤拷锟斤拷锟斤拷郑锟街伙拷锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷锟�
                    fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
                    //锟矫碉拷锟较达拷锟侥硷拷锟斤拷锟斤拷展锟斤拷
                    String fileExtName = fileName.substring(fileName.lastIndexOf(".")+1);
                    if("zip".equals(fileExtName)||"rar".equals(fileExtName)||"tar".equals(fileExtName)||"jar".equals(fileExtName)){
                        request.setAttribute("message", "锟较达拷锟侥硷拷锟斤拷锟斤拷锟酵诧拷锟斤拷锟较ｏ拷锟斤拷锟斤拷");
                        request.getRequestDispatcher("/message.jsp").forward(request, response);
                        return;
                    }
                    //锟斤拷锟斤拷锟揭拷锟斤拷锟斤拷洗锟斤拷锟斤拷募锟斤拷锟斤拷停锟斤拷锟矫达拷锟斤拷锟酵拷锟斤拷募锟斤拷锟斤拷锟秸癸拷锟斤拷锟斤拷卸锟斤拷洗锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷欠锟较凤拷
                    System.out.println("锟较达拷锟侥硷拷锟斤拷锟斤拷展锟斤拷为:"+fileExtName);
                    //锟斤拷取item锟叫碉拷锟较达拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟斤拷
                    InputStream is = item.getInputStream();
                    //锟矫碉拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
                    fileName = mkFileName(fileName);
                    String savePathStr = mkFilePath(savePath, fileName);
                    System.out.println("锟斤拷锟斤拷路锟斤拷为:"+savePathStr);
                    FileOutputStream fos = new FileOutputStream(savePathStr+File.separator+fileName);
                    byte buffer[] = new byte[1024];
                    int length = 0;
                    while((length = is.read(buffer))>0){
                        fos.write(buffer, 0, length);
                    }
                    is.close();
                    fos.close();
                    item.delete();
                    message = "锟侥硷拷锟较达拷锟缴癸拷";
                }
            }
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            e.printStackTrace();
            try {
				joj.put("ret", "400");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            return ;
        }catch (FileUploadBase.SizeLimitExceededException e) {
            e.printStackTrace();
            try {
				joj.put("ret", "400");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            return ;
        }catch (FileUploadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            message = "锟侥硷拷锟较达拷失锟斤拷";
            try {
				joj.put("ret", "400");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        
        
        try {
			joj.put("ret", "200");
			JSONObject dataJson = new JSONObject();
			dataJson.put("fileId", 1);
			dataJson.put("fileName", "avatar");
			joj.put("data", dataJson);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    public String mkFileName(String fileName){
        return UUID.randomUUID().toString()+"_"+fileName;
    }
    public String mkFilePath(String savePath,String fileName){
        int hashcode = fileName.hashCode();
        int dir1 = hashcode&0xf;
        int dir2 = (hashcode&0xf0)>>4;
        String dir = savePath + "\\" + dir1 + "\\" + dir2;
        File file = new File(dir);
        if(!file.exists()){
            file.mkdirs();
        }
        return dir;
    }
	

}
