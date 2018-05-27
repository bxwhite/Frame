package com.tedu.ctgu.dao;

import java.sql.SQLException;
import java.util.List;

import com.tedu.ctgu.pojo.Pic;

public interface PicDao {
	//����
	public void save (Pic p);
	//�����û�id��ѯͼƬ����
	public List<Pic> findByUserId(Integer userId);	
	public void delete(Integer userId); 
}
