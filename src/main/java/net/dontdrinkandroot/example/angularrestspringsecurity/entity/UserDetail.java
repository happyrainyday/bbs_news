package net.dontdrinkandroot.example.angularrestspringsecurity.entity;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import net.dontdrinkandroot.example.angularrestspringsecurity.model.User;


public class UserDetail implements Entity, UserDetails
{

	private static final long serialVersionUID = 5537601662796229123L;

	private User user;
	
	private Set<Role> roles;

	protected UserDetail()
	{
		/* Reflection instantiation */
	}

	
    // 构造函数填充所需要的东西
	public UserDetail(User user, Set<Role> roles){
		this.user = user;
		this.roles = roles;
	}
	

	public Set<Role> getRoles()
	{
		return this.roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	@Override
	public String getPassword()
	{
		return this.user.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return this.getRoles();
	}

	@Override
	public String getUsername()
	{
		return this.user.getName();
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
}
