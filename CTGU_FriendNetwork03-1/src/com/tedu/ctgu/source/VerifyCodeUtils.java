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
 * ��֤�빤����
 * 
 * @author kamen
 *
 */
public class VerifyCodeUtils {
	// ��װ������ͼƬ��code�ķ���
	public static Map<String, BufferedImage> getMap() {
		// ����һ����ͼƬ
		BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_3BYTE_BGR);
		// ��û��ʶ���
		Graphics g = image.getGraphics();
		g.setColor(Color.white);
		// ��䱳��
		g.fillRect(0, 0, 99, 49);
		// ���û��ʶ�����ɫ ,���Ʊ߿�
		g.setColor(Color.black);
		g.drawRect(0, 0, 100, 50);
		// ����һ��StringBuilder����ͼƬ��������
		StringBuilder st = new StringBuilder();
		// ��������ĸ���ɫ������,��ɫ Ҳ���
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			// ��ɫ��� RGB
			Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			// �������
			int code = r.nextInt(10);
			g.setColor(c);
			// ���� �����С
			Font f = new Font("����", Font.BOLD, 30);
			g.setFont(f);
			// ���Ƶ�ͼƬ����
			g.drawString(code + "", 20 * i, 30);
			// ��һЩ����
			g.drawLine(10, 10 * i, 45, 10 * i);
			// ����ÿ�����ɵ�ͼƬ
			st.append(code);
		}
		// ��code��session��
		Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
		map.put(st.toString(), image);
		return map;
	}

	// ��װһ��������ͼƬ�ķ���
	public static BufferedImage getImage(Map<String, BufferedImage> map) {
		Set<Entry<String, BufferedImage>> set = map.entrySet();
		Iterator<Entry<String, BufferedImage>> it = set.iterator();
		Entry<String, BufferedImage> entry = it.next();
		return entry.getValue();
	}

	// ��װһ�����������ֵķ���
	public static String getCode(Map<String, BufferedImage> map) {
		Set<Entry<String, BufferedImage>> set = map.entrySet();
		Iterator<Entry<String, BufferedImage>> it = set.iterator();
		Entry<String, BufferedImage> entry = it.next();
		return entry.getKey();
	}
}
