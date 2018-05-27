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
@RequestMapping("/user") // 给请求增加命名空间
public class UserController {
	// 注入接口对象
	@Resource // 默认使用set的方式注入
	private UserDao userDao;
	@Resource
	private PicDao picDao;
  
	@RequestMapping("/toRegist.do")//进入注册界面
	public String Regist() {
		return "/regist";
	}
	@RequestMapping("/findByName.do")//查询用户是否存在
	@ResponseBody
	public boolean findName(@RequestParam("username") String username, HttpServletResponse response)
			throws IOException {
		User u = userDao.findByName(username);
		if (u == null)
			return true;// 不存在用户
		else
			return false;// 已注册用户
	}
    @RequestMapping("/welcome.do")
    public String welcome(){
    	return "/welcome";
    }
	
	@RequestMapping("/checkCode.do")//判断验证码输入是否正确
	@ResponseBody
	public boolean check(@RequestParam("code") String code1, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 获取服务器绑定生成的code值
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
	//登出
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request){
		System.out.println(request.getSession().getId());
		request.getSession().removeAttribute("loger");
		System.out.println(request.getSession().getId());
		return "/login";
	}
	//检查是否有指定账号密码
	@RequestMapping("/checkpassword.do")
	@ResponseBody
	public boolean loginSubmit(@RequestParam("username")String username,
			@RequestParam("password")String password,HttpServletRequest request,HttpServletResponse response) throws IOException{
		User u=userDao.login(username,password);

		if(u!=null){//@Responsebody boolean
			
			return true;
		}// 正确
		else
			return false;// 不正确	
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
			// 文件上传到本地服务器
			// 实例化一些参数
			DiskFileItemFactory disk = new DiskFileItemFactory();
			// 创建解析对象upload
			ServletFileUpload upload = new ServletFileUpload(disk);
			List<FileItem> list = new ArrayList<FileItem>();
			// 解析请求头对象request
			try {
				list = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			
			}
			// 迭代循环读取文件上传FileItem
			for (int i = 0; i < list.size(); i++) {
				FileItem item = list.get(i);
				// 得到 文件名称
				String name = item.getName();
				String filename = name.substring(name.lastIndexOf("\\")+1);				
				//System.out.println("name="+name);
				System.out.println("filename="+filename);
				// 可以创建一个本地目录
				String path = request.getRealPath("//upload");
				System.out.println("path="+path);
				/*String realPath = request.getSession().getServletContext()
						.getRealPath("/upload");
				System.out.println("realpath="+realPath);*/
				//String path1 = path.substring(path.lastIndexOf("\\") + 1);
				System.out.println("path="+path);
				//System.out.println("path1="+path1);
				// upload\pic_id\传入文件名
				/*String newpath = path + File.separator + "pic_" + id;
				System.out.println("newpath="+newpath);*/
				// 创建目录
				File file = new File(path);
				if(!file.exists()){
					file.mkdirs();
				}
				
				System.out.println(file.getPath());
				// 构建图片类
				Pic p = new Pic();
				String pname = "pic_" + id  + filename;
				
				p.setPicName(pname);
				p.setUserId(id);
				try {
					System.out.println("newpath+pname="+path + File.separator + pname);
					item.write(new File(path + File.separator + pname));
				 
					// 保存图片名到ImgDao...
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
			    
			// 由于更新了图片信息,所以需要重新 查询一遍
			 return new ModelAndView("/userDetail",map);
		
		/*//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = request.getRealPath("/WEB-INF/upload");
   
        System.out.println(savePath);
        File file = new File(savePath);
        //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath+"目录不存在，需要创建");
            //创建目录
            file.mkdir();
        }
        //消息提示
        String message = "";
        try{
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
             //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8"); 
            //3、判断提交上来的数据是否是上传表单的数据
            if(!ServletFileUpload.isMultipartContent(request)){
                //按照传统方式获取数据
                return null;
            }
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list){
                //如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name + "=" + value);
                }else{//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();
                    System.out.println(filename);
                    if(filename==null || filename.trim().equals("")){
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while((len=in.read(buffer))>0){
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                    message = "文件上传成功！";
                    
                }
            }
        }catch (Exception e) {
            message= "文件上传失败！";
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
