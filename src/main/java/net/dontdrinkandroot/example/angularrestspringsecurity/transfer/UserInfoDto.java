package net.dontdrinkandroot.example.angularrestspringsecurity.transfer;

/**
 * @ClassName: UserInfoDto
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tjf
 * @date 2016年7月7日下午2:32:20
 * @Version V1.00
 */
public class UserInfoDto extends RegisterDto {

	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


}
