package net.dontdrinkandroot.example.angularrestspringsecurity.dao.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.dontdrinkandroot.example.angularrestspringsecurity.model.UserRole;

/**
 * @ClassName: JspUserRoleDao
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tjf
 * @date 2016年6月21日下午5:34:21
 * @Version V1.00
 */
public class JpaUserRoleDao implements UserRoleDao {

	@Autowired
	UserRoleMapper userRoleMapper;
	/* 
	 * @Name: findAll
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年6月21日下午5:34:21
	 * @return
	 */
	@Override
	public List<UserRole> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * @Name: find
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年6月21日下午5:34:21
	 * @param id
	 * @return
	 */
	@Override
	public UserRole find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * @Name: save
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年6月21日下午5:34:21
	 * @param object
	 * @return
	 */
	@Override
	public UserRole save(UserRole object) {
		
		userRoleMapper.insert(object);
		return null;
	}

	/* 
	 * @Name: delete
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年6月21日下午5:34:21
	 * @param id
	 */
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

}
