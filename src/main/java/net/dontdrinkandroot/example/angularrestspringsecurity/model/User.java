package net.dontdrinkandroot.example.angularrestspringsecurity.model;

import java.util.Date;

import net.dontdrinkandroot.example.angularrestspringsecurity.entity.Entity;

public class User implements Entity{
	private static final long serialVersionUID = 7654622216677068775L;

	private Long id;

    private String name;

    private String password;
    
    private Date createDate;
    
    private Date updateDate;
    
    private Boolean black;
    
    private String sourceIp;
    
    private Date lastLoginDate;
//    private List<UserRole> userRoleList;

    public User(){
    }
    
    public User(String name, String password){
    
    	this.name = name;
    	this.password = password;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Boolean getBlack() {
		return black;
	}

	public void setBlack(Boolean black) {
		this.black = black;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

//	public List<UserRole> getUserRoleList() {
//		return userRoleList;
//	}
//
//	public void setUserRoleList(List<UserRole> userRoleList) {
//		this.userRoleList = userRoleList;
//	}
}