package net.dontdrinkandroot.example.angularrestspringsecurity.transfer;

/**
 * @ClassName: PasswordDto
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tjf
 * @date 2016年7月1日下午2:52:20
 * @Version V1.00
 */
public class PasswordDto {

	private String username;
	
	private String password;
	
	private String oldpassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPassword() {
		return oldpassword;
	}


	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	
	
}
