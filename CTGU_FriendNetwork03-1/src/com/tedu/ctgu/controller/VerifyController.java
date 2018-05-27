package com.tedu.ctgu.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.tedu.ctgu.source.VerifyCodeUtils;
    @Controller
public class VerifyController {
	@RequestMapping("/user/verify.do") // 如何通过注解显示验证码
	public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 返回类型设置图片类型
		response.setContentType("image/jpeg");
		// 得到图片和code的map
		Map<String, BufferedImage> map = VerifyCodeUtils.getMap();
		String code = VerifyCodeUtils.getCode(map);
		BufferedImage image = VerifyCodeUtils.getImage(map);
		// 将code绑定到session
		HttpSession session = request.getSession();
		session.setAttribute("code", code);
		// image可以压缩当做一个输出流
		OutputStream os = response.getOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
		encoder.encode(image);
		
	}

}
