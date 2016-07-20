package net.dontdrinkandroot.example.angularrestspringsecurity.transfer;

import java.util.Map;


public class UserTransfer
{

	private final Long id;
	
	private final String name;

	private final Map<String, Boolean> roles;


	public UserTransfer(Long id, String userName, Map<String, Boolean> roles)
	{
		this.name = userName;
		this.roles = roles;
		this.id = id;
	}


	public String getName()
	{
		return this.name;
	}


	public Map<String, Boolean> getRoles()
	{
		return this.roles;
	}


	public Long getId() {
		return id;
	}

}