package net.dontdrinkandroot.example.angularrestspringsecurity.dao.user;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import net.dontdrinkandroot.example.angularrestspringsecurity.dao.role.RolesMapper;
import net.dontdrinkandroot.example.angularrestspringsecurity.dao.role.UserRoleMapper;
import net.dontdrinkandroot.example.angularrestspringsecurity.entity.Role;
import net.dontdrinkandroot.example.angularrestspringsecurity.entity.UserDetail;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.Roles;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.User;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.UserRole;
import net.dontdrinkandroot.example.angularrestspringsecurity.transfer.UserAndRole;


public class JpaUserDao implements UserDao
{

	@Autowired
	UserMapper userMapper;
	@Autowired
	UserRoleMapper userRoleMapper;
	@Autowired
	RolesMapper  roleMapper;
	
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = findByName(username);
		if (null == user) {
			// 此异常默认被隐藏，反而报出BadCredentialsException此异常
			throw new UsernameNotFoundException("The user with name " + username + " was not found");
		}

//		if (true == user.getBlack()){
//			
//			throw new InternalAuthenticationServiceException("Account Exception");
//		}
		
		Set<Role> set = new HashSet<Role>();
		
		List<UserRole> list = userRoleMapper.selectRoleByUid(user.getId());
		for (UserRole userRole : list) {
			set.add(Role.genRoleEnum(userRole.getRoles()));
		}
		
		return new UserDetail(user, set);
	}


	@Override
	@Transactional(readOnly = true)
	public User findByName(String name)
	{
		return this.userMapper.selectByUsername(name);
	}

	/**
	 * @Name: update
	 * @Description: 更新
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月1日下午3:34:00
	 * @param user
	 * @Return: int 返回类型
	 */
	public int updateByUserName(User user){
		return this.userMapper.updateByUsernameSelective(user);
	}
	
	/* 
	 * @Name: findAll
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年6月15日下午5:14:56
	 * @return
	 */
	@Override
	public List<User> findAll() {
		return this.userMapper.getUserList();
	}


	/* 
	 * @Name: find
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年6月15日下午5:14:56
	 * @param id
	 * @return
	 */
	@Override
	public User find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	/* 
	 * @Name: save
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年6月15日下午5:14:56
	 * @param newsEntry
	 * @return
	 */
	@Override
	@Transactional
	public User save(User user) {
		User newUser = new User();
		int status = userMapper.insert(user);
		UserRole userRole = new UserRole(user.getId(), Role.USER.ordinal());
        this.userRoleMapper.insert(userRole);
		newUser.setId(Long.valueOf(status));;
		return newUser;
	}


	/* 
	 * @Name: delete
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年6月15日下午5:14:56
	 * @param id
	 */
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}


	/* 
	 * @Name: getUserPageList
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月6日上午11:40:26
	 * @param map
	 * @return
	 */
	@Override
	public List<UserAndRole> getUserPageList(Map<String, Object> map) {
		return this.userMapper.getUserPageList(map);
	}


	/* 
	 * @Name: getUserNum
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月6日下午2:44:06
	 * @return
	 */
	@Override
	public int getUserNum(Map<String, Object> map) {
		return this.userMapper.getUserNum(map);
	}


	/* 
	 * @Name: getRolesList
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月6日下午6:34:11
	 * @return
	 */
	@Override
	public List<Roles> getRolesList() {
		
		return this.roleMapper.getRolesList();
	}


	/* 
	 * @Name: updateByUid
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月7日下午3:01:33
	 * @param user
	 * @return
	 */
	@Override
	public int updateByUid(User user) {
		return this.userMapper.updateByPrimaryKeySelective(user);
	}

}
