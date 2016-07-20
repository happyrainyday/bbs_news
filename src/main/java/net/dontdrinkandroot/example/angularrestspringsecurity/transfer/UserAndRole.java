package net.dontdrinkandroot.example.angularrestspringsecurity.transfer;

import java.util.List;

import net.dontdrinkandroot.example.angularrestspringsecurity.model.User;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.UserRole;

/**
 * @ClassName: UserAndRole
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tjf
 * @date 2016年7月6日上午9:41:41
 * @Version V1.00
 */
public class UserAndRole extends User {

	private static final long serialVersionUID = 6675351716646884697L;

	private List<UserRole> userRoleList;
	

	public List<UserRole> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<UserRole> userRoleList) {
		this.userRoleList = userRoleList;
	}
	
}
