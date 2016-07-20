package net.dontdrinkandroot.example.angularrestspringsecurity.model;

/**
 * @ClassName: NewsEntryUser
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tjf
 * @date 2016年7月2日上午11:30:03
 * @Version V1.00
 */
public class NewsEntryUser extends NewsEntry {

	private static final long serialVersionUID = 7143371570408943979L;

//	private String name; //用户名
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
	
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
