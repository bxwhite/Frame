package com.tedu.ctgu.dao;

import java.util.List;

import com.tedu.ctgu.pojo.User;
public interface UserDao {
	//查询用户是否存在
	public User findByName(String username);
	//提交注册用户
	public void registerUser(User user);
	//通过id查询用户
	public User findById(Integer id);
	//匹配用户名和密码
	public User login(String username, String password);
	//查询所有用户
	public List<User> findAll();
	//删除用户
	public void deleteUser(Integer id);
	//更新用户
	public void updateUser(User user);


}

