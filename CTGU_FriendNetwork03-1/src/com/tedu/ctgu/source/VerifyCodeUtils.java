package com.tedu.ctgu.source;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * 验证码工具类
 * 
 * @author kamen
 *
 */
public class VerifyCodeUtils {
	// 封装的生成图片和code的方法
	public static Map<String, BufferedImage> getMap() {
		// 定义一个空图片
		BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_3BYTE_BGR);
		// 获得画笔对象
		Graphics g = image.getGraphics();
		g.setColor(Color.white);
		// 填充背景
		g.fillRect(0, 0, 99, 49);
		// 设置画笔对象颜色 ,绘制边框
		g.setColor(Color.black);
		g.drawRect(0, 0, 100, 50);
		// 构建一个StringBuilder保存图片内容数据
		StringBuilder st = new StringBuilder();
		// 随机生成四个颜色的数字,颜色 也随机
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			// 颜色随机 RGB
			Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			// 数字随机
			int code = r.nextInt(10);
			g.setColor(c);
			// 设置 字体大小
			Font f = new Font("隶书", Font.BOLD, 30);
			g.setFont(f);
			// 绘制到图片上面
			g.drawString(code + "", 20 * i, 30);
			// 画一些横线
			g.drawLine(10, 10 * i, 45, 10 * i);
			// 保存每次生成的图片
			st.append(code);
		}
		// 绑定code到session中
		Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
		map.put(st.toString(), image);
		return map;
	}

	// 封装一个迭代出图片的方法
	public static BufferedImage getImage(Map<String, BufferedImage> map) {
		Set<Entry<String, BufferedImage>> set = map.entrySet();
		Iterator<Entry<String, BufferedImage>> it = set.iterator();
		Entry<String, BufferedImage> entry = it.next();
		return entry.getValue();
	}

	// 封装一个迭代出数字的方法
	public static String getCode(Map<String, BufferedImage> map) {
		Set<Entry<String, BufferedImage>> set = map.entrySet();
		Iterator<Entry<String, BufferedImage>> it = set.iterator();
		Entry<String, BufferedImage> entry = it.next();
		return entry.getKey();
	}
}
