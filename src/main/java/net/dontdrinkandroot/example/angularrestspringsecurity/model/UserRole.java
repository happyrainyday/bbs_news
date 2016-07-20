package net.dontdrinkandroot.example.angularrestspringsecurity.model;

import net.dontdrinkandroot.example.angularrestspringsecurity.entity.Entity;

public class UserRole implements Entity{
	private static final long serialVersionUID = 6153794263572123243L;

	private Long userId;

    private Integer roles;

    public UserRole(){
    	
    }
    public UserRole(Long userId, Integer roles){
    	this.userId = userId;
    	this.roles = roles;
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRoles() {
        return roles;
    }

    public void setRoles(Integer roles) {
        this.roles = roles;
    }
}