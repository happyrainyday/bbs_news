package net.dontdrinkandroot.example.angularrestspringsecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import net.dontdrinkandroot.example.angularrestspringsecurity.entity.Role;

public class UserUtils {

	public static boolean isAdmin()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		return userDetails.getAuthorities().contains(Role.ADMIN);
	}
}
