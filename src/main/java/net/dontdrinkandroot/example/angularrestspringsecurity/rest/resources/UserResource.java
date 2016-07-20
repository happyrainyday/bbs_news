package net.dontdrinkandroot.example.angularrestspringsecurity.rest.resources;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.dontdrinkandroot.example.angularrestspringsecurity.BaseResult;
import net.dontdrinkandroot.example.angularrestspringsecurity.Constant;
import net.dontdrinkandroot.example.angularrestspringsecurity.ErrorCodeEnum;
import net.dontdrinkandroot.example.angularrestspringsecurity.FormatUtils;
import net.dontdrinkandroot.example.angularrestspringsecurity.dao.role.UserRoleDao;
import net.dontdrinkandroot.example.angularrestspringsecurity.dao.user.UserDao;
import net.dontdrinkandroot.example.angularrestspringsecurity.entity.UserDetail;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.Roles;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.User;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.UserRole;
import net.dontdrinkandroot.example.angularrestspringsecurity.rest.TokenUtils;
import net.dontdrinkandroot.example.angularrestspringsecurity.transfer.Pagination;
import net.dontdrinkandroot.example.angularrestspringsecurity.transfer.PasswordDto;
import net.dontdrinkandroot.example.angularrestspringsecurity.transfer.RegisterDto;
import net.dontdrinkandroot.example.angularrestspringsecurity.transfer.TokenTransfer;
import net.dontdrinkandroot.example.angularrestspringsecurity.transfer.UserAndRole;
import net.dontdrinkandroot.example.angularrestspringsecurity.transfer.UserInfoDto;
import net.dontdrinkandroot.example.angularrestspringsecurity.transfer.UserTransfer;


@Component
@Path("/user")
public class UserResource
{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDetailsService userService;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;


	/**
	 * Retrieves the currently logged in user.
	 * 
	 * @return A transfer containing the username and the roles.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserTransfer getUser()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
			throw new WebApplicationException(401);
		}
		UserDetails userDetails = (UserDetails) principal;
        
		UserDetail userDetail = (UserDetail)userDetails;
		
		return new UserTransfer(userDetail.getUser().getId(), userDetails.getUsername(), this.createRoleMap(userDetails));
	}

	
	@GET
	@Path("/pager")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> getUserPageList(@QueryParam("pageIndex") String pageIndex, @QueryParam("pageSize") String pageSize)
	{   
		int index = 0;
		int size = 0;
		
		if (-1 == FormatUtils.checkIsInteger(pageIndex)) {
			index = Constant.DEFAULT_PAGE_INDEX;
		} else {
			index = Integer.parseInt(pageIndex);
		}

		if (-1 == FormatUtils.checkIsInteger(pageSize)) {
			size = Constant.DEFAULT_PAGE_SIZE;
		} else {
			size = Integer.parseInt(pageSize);
		}

		Pagination<UserAndRole> pagination = new Pagination<UserAndRole>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageIndex", (index - 1) * size);
		map.put("pageSize", size);
		List<UserAndRole> userPageList = this.userDao.getUserPageList(map);
		
		pagination.setContent(userPageList);
		pagination.setCurrentPage(index);
		pagination.setPageSize(size);
		pagination.setTotalPage(this.userDao.getUserNum());		
		
		return new BaseResult<>(pagination);
	}
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> getUserList()
	{   
		List<User> list = this.userDao.findAll();
		this.logger.info("用户列表："+ list.size());
		
		return new BaseResult<>(list);
	}

	/**
	 * Authenticates a user and creates an authentication token.
	 * 
	 * @param username
	 *            The name of the user.
	 * @param password
	 *            The password of the user.
	 * @return A transfer containing the authentication token.
	 */
	@Path("authenticate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> authenticate(@FormParam("username") String username, @FormParam("password") String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		SecurityContextHolder.getContext().setAuthentication(this.authManager.authenticate(authenticationToken));

		/*
		 * Reload user as password of authentication principal will be null
		 * after authorization and password is needed for token generation
		 */
		UserDetails userDetails = this.userService.loadUserByUsername(username);
		if (((UserDetail)userDetails).getUser().getBlack()){
			return new BaseResult<>("20000", "Account Exception");
		}
		// return new TokenTransfer(TokenUtils.createToken(userDetails));
		return new BaseResult<>(new TokenTransfer(TokenUtils.createToken(userDetails)));
	}

	/**
	 * @Name: register
	 * @Description: 注册用户
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月1日下午2:21:38
	 * @param registerDto
	 * @Return: BaseResult<?> 返回类型
	 */
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public BaseResult<?> register(RegisterDto registerDto){
    	
		if (StringUtils.isBlank(registerDto.getUsername())
				|| StringUtils.isBlank(registerDto.getPassword())
				|| StringUtils.isBlank(registerDto.getConfirmpassword())){
			return new BaseResult<>(ErrorCodeEnum.PARAMETERS_IS_NULL_1, "USERNAME");
		}
		//密码不一致
		if (!registerDto.getPassword().equals(registerDto.getConfirmpassword())){
			return new BaseResult<>(ErrorCodeEnum.PARAMETERS_IS_NOT_CORRECT);
		}
		
		User user = new User();
		user.setCreateDate(new Date());
		user.setName(registerDto.getUsername());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setBlack(false);
		
		this.userDao.save(user);
        
		return new BaseResult<>();
    }
    
	/**
	 * @Name: modifyPassword
	 * @Description: 修改密码
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月1日下午3:45:47
	 * @param passwordDto
	 * @Return: BaseResult<?> 返回类型
	 */
	@PUT
	@Path("/password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> modifyPassword(PasswordDto passwordDto){
		
		if (StringUtils.isBlank(passwordDto.getUsername()) ||
				StringUtils.isBlank(passwordDto.getPassword())
				|| StringUtils.isBlank(passwordDto.getOldPassword())){
			return new BaseResult<>(ErrorCodeEnum.PARAMETERS_IS_NULL_1);
		}
		
		User user = userDao.findByName(passwordDto.getUsername());
		if (null == user){ 
			return new BaseResult<>(ErrorCodeEnum.PARAMETERS_IS_NOT_CORRECT);
		}
		
		String dbPass = user.getPassword();
		
        if (!passwordEncoder.matches(passwordDto.getOldPassword(), dbPass)){
        	return new BaseResult<>("20000","旧密码不正确");
        }
		
		User user1 = new User();
		user1.setName(passwordDto.getUsername());
		
		user1.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
		
		int status = userDao.updateByUserName(user1);
		if (status < 1){
			return new BaseResult<>("50000", "更新失败");
		}
		
		return new BaseResult<>();
		
	}
	
	/**
	 * @Name: modifyUserInfo
	 * @Description: 修改用户信息 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月7日下午2:14:45
	 * @param passwordDto
	 * @return
	 * @Return: BaseResult<?> 返回类型
	 */
	@PUT
	@Path("/password/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> modifyUserInfo(@PathParam("id")String id, UserInfoDto userInfoDto){
		
		if (StringUtils.isBlank(userInfoDto.getUsername()) || (-1 == FormatUtils.checkIsInteger(id))
				|| (StringUtils.isBlank(userInfoDto.getPassword())
						&& StringUtils.isBlank(userInfoDto.getConfirmpassword())
						&& (StringUtils.isNotBlank(userInfoDto.getRole())))) {
			return new BaseResult<>(ErrorCodeEnum.PARAMETERS_IS_NULL_1);
		}
		
		if (!StringUtils.isBlank(userInfoDto.getPassword()) && !StringUtils.isBlank(userInfoDto.getConfirmpassword())
				&& userInfoDto.getPassword().equals(userInfoDto.getConfirmpassword())) {
			
			User user = new User();
			user.setId(Long.parseLong(id));
            user.setUpdateDate(new Date());			
			user.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
			
			int status = this.userDao.updateByUid(user);
			if (status < 1){
				return new BaseResult<>("50000", "更新失败");
			}
			
		}
		
        // 说明赋值
		if (-1 != FormatUtils.checkIsInteger(userInfoDto.getRole())){
			
			UserRole userRole = new UserRole();
			userRole.setUserId(Long.parseLong(id));
			userRole.setRoles(Integer.parseInt(userInfoDto.getRole()));
			this.userRoleDao.save(userRole);
		}
		
		return new BaseResult<>();
		
	}
	
	@GET
	@Path("/roles")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?>getRoleList(){
		
		List<Roles> rolesList = this.userDao.getRolesList();
		return new BaseResult<>(rolesList);
	}
	
	@PUT
	@Path("/role/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> blackOrWhiteList(@PathParam("id") String id, @QueryParam("blackOrWhite")String blackOrWhite){
		
		if (StringUtils.isBlank(blackOrWhite) || (-1 == FormatUtils.checkIsInteger(id))){
			return new BaseResult<>(ErrorCodeEnum.PARAMETERS_IS_NOT_CORRECT);
		}
		
//		Boolean.parseBoolean(blackOrWhite);
		
		User user = new User();
		user.setUpdateDate(new Date());
		user.setId(Long.parseLong(id));
		user.setBlack(Boolean.valueOf(blackOrWhite));
		int status = this.userDao.updateByUid(user);
		if (status < 1){
			return new BaseResult<>("50000", "更新失败");
		}
		
		return new BaseResult<>();
	}
	
	private Map<String, Boolean> createRoleMap(UserDetails userDetails)
	{
		Map<String, Boolean> roles = new HashMap<String, Boolean>(); 
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			roles.put(authority.getAuthority(), Boolean.TRUE);
		}

		return roles;
	}

}