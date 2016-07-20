package net.dontdrinkandroot.example.angularrestspringsecurity.dao;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;

import net.dontdrinkandroot.example.angularrestspringsecurity.dao.newsentry.NewsEntryDao;
import net.dontdrinkandroot.example.angularrestspringsecurity.dao.role.UserRoleDao;
import net.dontdrinkandroot.example.angularrestspringsecurity.dao.user.UserDao;
import net.dontdrinkandroot.example.angularrestspringsecurity.entity.Role;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.NewsEntry;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.User;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.UserRole;


/**
 * Initialize the database with some test entries.
 *
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class DataBaseInitializer
{

	private NewsEntryDao newsEntryDao;

	private UserDao userDao;

	private PasswordEncoder passwordEncoder;

    private UserRoleDao userRoleDao;
    
	protected DataBaseInitializer()
	{
		/* Default constructor for reflection instantiation */
	}

	public DataBaseInitializer(UserDao userDao, NewsEntryDao newsEntryDao, PasswordEncoder passwordEncoder, UserRoleDao userRoleDao)
	{
		this.userDao = userDao;
		this.newsEntryDao = newsEntryDao;
		this.passwordEncoder = passwordEncoder;
		this.userRoleDao = userRoleDao;
	}

	public void initDataBase()
	{
//		User superUser = new User("superuser1", this.passwordEncoder.encode("superuser1"));
//		User user = this.userDao.save(superUser);
//		System.out.println(superUser.getId());
//		User userUser = new User("user", this.passwordEncoder.encode("user"));
//		UserRole userRole = new UserRole(superUser.getId(), Role.ADMIN.ordinal());
//		this.userRoleDao.save(userRole);
//        
//		User adminUser = new User("admin", this.passwordEncoder.encode("admin"));
//		adminUser.addRole(Role.USER);
//		adminUser.addRole(Role.ADMIN);
//		this.userDao.save(adminUser);

//		long timestamp = System.currentTimeMillis() - (1000 * 60 * 60 * 24);
//		for (int i = 0; i < 10; i++) {
//			NewsEntry newsEntry = new NewsEntry();
//			newsEntry.setContent("This is example content " + i);
//			newsEntry.setDate(new Date(timestamp));
//			this.newsEntryDao.save(newsEntry);
//			timestamp += 1000 * 60 * 60;
//		}
	}

}