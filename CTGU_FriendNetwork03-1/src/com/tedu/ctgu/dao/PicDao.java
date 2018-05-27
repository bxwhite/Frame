package com.tedu.ctgu.dao;

import java.sql.SQLException;
import java.util.List;

import com.tedu.ctgu.pojo.Pic;

public interface PicDao {
	//保存
	public void save (Pic p);
	//根据用户id查询图片名称
	public List<Pic> findByUserId(Integer userId);	
	public void delete(Integer userId); 
}
