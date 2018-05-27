package com.tedu.ctgu.dao;

import java.util.List;

import com.tedu.ctgu.pojo.User;
public interface UserDao {
	//��ѯ�û��Ƿ����
	public User findByName(String username);
	//�ύע���û�
	public void registerUser(User user);
	//ͨ��id��ѯ�û�
	public User findById(Integer id);
	//ƥ���û���������
	public User login(String username, String password);
	//��ѯ�����û�
	public List<User> findAll();
	//ɾ���û�
	public void deleteUser(Integer id);
	//�����û�
	public void updateUser(User user);


}

