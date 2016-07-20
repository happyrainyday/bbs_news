package net.dontdrinkandroot.example.angularrestspringsecurity.dao.user;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;

import net.dontdrinkandroot.example.angularrestspringsecurity.dao.Dao;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.Roles;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.User;
import net.dontdrinkandroot.example.angularrestspringsecurity.transfer.UserAndRole;


public interface UserDao extends Dao<User, Long>, UserDetailsService
{

	User findByName(String name);
    
	public int updateByUserName(User user);
	
	public int updateByUid(User user);
	
	public List<UserAndRole> getUserPageList(Map<String, Object> map);
	
	public int getUserNum();
	
	public List<Roles> getRolesList();
}