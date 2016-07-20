package net.dontdrinkandroot.example.angularrestspringsecurity.transfer;

/**
 * @ClassName: RegisterDto
 * @Description: 注册的Dto
 * @author tjf
 * @date 2016年7月1日上午11:10:17
 * @Version V1.00
 */
public class RegisterDto {

	private String username;
	
	private String password;
	
	private String confirmpassword;

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

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

}
