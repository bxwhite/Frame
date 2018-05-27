package com.tedu.ctgu.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.tedu.ctgu.dao.PicDao;
import com.tedu.ctgu.dao.UserDao;
import com.tedu.ctgu.pojo.Pic;
import com.tedu.ctgu.pojo.User;
import com.tedu.ctgu.source.VerifyCodeUtils;

@Controller
@RequestMapping("/user") // ���������������ռ�
public class UserController {
	// ע��ӿڶ���
	@Resource // Ĭ��ʹ��set�ķ�ʽע��
	private UserDao userDao;
	@Resource
	private PicDao picDao;
  
	@RequestMapping("/toRegist.do")//����ע�����
	public String Regist() {
		return "/regist";
	}
	@RequestMapping("/findByName.do")//��ѯ�û��Ƿ����
	@ResponseBody
	public boolean findName(@RequestParam("username") String username, HttpServletResponse response)
			throws IOException {
		User u = userDao.findByName(username);
		if (u == null)
			return true;// �������û�
		else
			return false;// ��ע���û�
	}
    @RequestMapping("/welcome.do")
    public String welcome(){
    	return "/welcome";
    }
	
	@RequestMapping("/checkCode.do")//�ж���֤�������Ƿ���ȷ
	@ResponseBody
	public boolean check(@RequestParam("code") String code1, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// ��ȡ�����������ɵ�codeֵ
		String code2 = (String) request.getSession().getAttribute("code");
		// PrintWriter pw = response.getWriter();
		if (code1.equals(code2)) {
			return true;
		} else {
			return false;
		}
	}
	@RequestMapping("/regist.do")
	public ModelAndView regist(User u){
		userDao.registerUser(u);
		Map<String, String> map=new HashMap<String,String>();
		map.put("username", u.getUsername());
		return new ModelAndView("/regist_success", map);
		
	}
	
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request){
		//System.out.println(request.getRealPath("//WEB-INF//friend_demo//upload"));
		return "/login";
	}
	//�ǳ�
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request){
		System.out.println(request.getSession().getId());
		request.getSession().removeAttribute("loger");
		System.out.println(request.getSession().getId());
		return "/login";
	}
	//����Ƿ���ָ���˺�����
	@RequestMapping("/checkpassword.do")
	@ResponseBody
	public boolean loginSubmit(@RequestParam("username")String username,
			@RequestParam("password")String password,HttpServletRequest request,HttpServletResponse response) throws IOException{
		User u=userDao.login(username,password);

		if(u!=null){//@Responsebody boolean
			
			return true;
		}// ��ȷ
		else
			return false;// ����ȷ	
	}
	
	@RequestMapping("/toUserList.do")
	public ModelAndView loginEx(@RequestParam("username")String username,
			@RequestParam("password")String password,HttpServletRequest request,HttpServletResponse response) throws IOException{

		Map<String, Object> map=new HashMap<String,Object>();
		map.put("list", userDao.findAll());
		map.put("u", userDao.login(username, password));
		request.getSession().setAttribute("loger", userDao.login(username, password));
		
		return new ModelAndView("/userList",map);		
	}
	
	@RequestMapping("/delete.do")
	public String delete(@RequestParam("id")Integer id,HttpServletRequest request){
		userDao.deleteUser(id);
		return "/delete_success";
	}
	
	@RequestMapping("/load.do")
	public ModelAndView update(@RequestParam("id")Integer id,HttpServletRequest request){
		Map<String,Object > map = new HashMap<>();
		map.put("u", userDao.findById(id));
		return new ModelAndView("/upload",map);
	}
	
	@RequestMapping("/update.do")
	public ModelAndView update(User user,@RequestParam("username")String username,HttpServletRequest request){
		userDao.updateUser(user);
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("u", userDao.findByName(username));
		map.put("list", userDao.findAll());
		return new ModelAndView("/userList",map);
	}
	
	@RequestMapping("/userDetail.do")
	public ModelAndView detail(@RequestParam("id")Integer id,HttpServletRequest request) throws SQLException{
		Map<String, Object> map=new HashMap<String,Object>();
		//System.out.println(id)
		map.put("pList", picDao.findByUserId(id));
		map.put("myUser", userDao.findById(id));
		return new ModelAndView("/userDetail",map);
	}
	
	@RequestMapping("/msgUpload.do")
	public ModelAndView msg(@RequestParam("id")Integer id, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, SQLException{
			// �ļ��ϴ������ط�����
			// ʵ����һЩ����
			DiskFileItemFactory disk = new DiskFileItemFactory();
			// ������������upload
			ServletFileUpload upload = new ServletFileUpload(disk);
			List<FileItem> list = new ArrayList<FileItem>();
			// ��������ͷ����request
			try {
				list = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			
			}
			// ����ѭ����ȡ�ļ��ϴ�FileItem
			for (int i = 0; i < list.size(); i++) {
				FileItem item = list.get(i);
				// �õ� �ļ�����
				String name = item.getName();
				String filename = name.substring(name.lastIndexOf("\\")+1);				
				//System.out.println("name="+name);
				System.out.println("filename="+filename);
				// ���Դ���һ������Ŀ¼
				String path = request.getRealPath("//upload");
				System.out.println("path="+path);
				/*String realPath = request.getSession().getServletContext()
						.getRealPath("/upload");
				System.out.println("realpath="+realPath);*/
				//String path1 = path.substring(path.lastIndexOf("\\") + 1);
				System.out.println("path="+path);
				//System.out.println("path1="+path1);
				// upload\pic_id\�����ļ���
				/*String newpath = path + File.separator + "pic_" + id;
				System.out.println("newpath="+newpath);*/
				// ����Ŀ¼
				File file = new File(path);
				if(!file.exists()){
					file.mkdirs();
				}
				
				System.out.println(file.getPath());
				// ����ͼƬ��
				Pic p = new Pic();
				String pname = "pic_" + id  + filename;
				
				p.setPicName(pname);
				p.setUserId(id);
				try {
					System.out.println("newpath+pname="+path + File.separator + pname);
					item.write(new File(path + File.separator + pname));
				 
					// ����ͼƬ����ImgDao...
					item.delete();
					//picDao.delete(id);
					picDao.save(p);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			  Map<String,Object> map=new HashMap<>();
				map.put("myUser", userDao.findById(id));

			    map.put("pList",picDao.findByUserId(id));
			    
			// ���ڸ�����ͼƬ��Ϣ,������Ҫ���� ��ѯһ��
			 return new ModelAndView("/userDetail",map);
		
		/*//�õ��ϴ��ļ��ı���Ŀ¼�����ϴ����ļ������WEB-INFĿ¼�£����������ֱ�ӷ��ʣ���֤�ϴ��ļ��İ�ȫ
        String savePath = request.getRealPath("/WEB-INF/upload");
   
        System.out.println(savePath);
        File file = new File(savePath);
        //�ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath+"Ŀ¼�����ڣ���Ҫ����");
            //����Ŀ¼
            file.mkdir();
        }
        //��Ϣ��ʾ
        String message = "";
        try{
            //ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
            //1������һ��DiskFileItemFactory����
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2������һ���ļ��ϴ�������
            ServletFileUpload upload = new ServletFileUpload(factory);
             //����ϴ��ļ�������������
            upload.setHeaderEncoding("UTF-8"); 
            //3���ж��ύ�����������Ƿ����ϴ���������
            if(!ServletFileUpload.isMultipartContent(request)){
                //���մ�ͳ��ʽ��ȡ����
                return null;
            }
            //4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list){
                //���fileitem�з�װ������ͨ�����������
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //�����ͨ����������ݵ�������������
                    String value = item.getString("UTF-8");
                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name + "=" + value);
                }else{//���fileitem�з�װ�����ϴ��ļ�
                    //�õ��ϴ����ļ����ƣ�
                    String filename = item.getName();
                    System.out.println(filename);
                    if(filename==null || filename.trim().equals("")){
                        continue;
                    }
                    //ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺  c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
                    //�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    //��ȡitem�е��ϴ��ļ���������
                    InputStream in = item.getInputStream();
                    //����һ���ļ������
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
                    //����һ��������
                    byte buffer[] = new byte[1024];
                    //�ж��������е������Ƿ��Ѿ�����ı�ʶ
                    int len = 0;
                    //ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
                    while((len=in.read(buffer))>0){
                        //ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
                        out.write(buffer, 0, len);
                    }
                    //�ر�������
                    in.close();
                    //�ر������
                    out.close();
                    //ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
                    item.delete();
                    message = "�ļ��ϴ��ɹ���";
                    
                }
            }
        }catch (Exception e) {
            message= "�ļ��ϴ�ʧ�ܣ�";
            e.printStackTrace();
            
        }
		*/
		
			 }
	@RequestMapping("/userList.do")
	public ModelAndView toUserList(){
	    Map<String,Object> map=new HashMap<>();
		map.put("list",userDao.findAll());
		return new ModelAndView("/userList",map);
	}
}
