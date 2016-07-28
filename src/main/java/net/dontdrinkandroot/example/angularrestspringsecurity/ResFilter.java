package net.dontdrinkandroot.example.angularrestspringsecurity;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;

/**
 * Servlet Filter implementation class ResFilter
 */
public class ResFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public ResFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		String username = (String) req.getAttribute(Constant.USER_NAME_PARAM);
		Long userid = (Long) req.getAttribute(Constant.USER_ID_PARAM);
		Boolean isFromMobile = false;
		try {
			// 用来判断是否为移动端访问
			String userAgent = req.getHeader("USER-AGENT").toLowerCase();
			if (null == userAgent) {
				userAgent = "";
			}
			isFromMobile = CheckMobileUtils.check(userAgent);
			// 判断是否为移动端访问
			if (isFromMobile) {
				MDC.put(Constant.USER_OPER_SOURCE, "mobile");
			} else {
				MDC.put(Constant.USER_OPER_SOURCE, "pc");
			}
		} catch (Exception e) {
		}
		
		if (StringUtils.isNoneBlank(username)) {
			MDC.put(Constant.USER_NAME_PARAM, username);
		}

		if (userid != null) {
			MDC.put(Constant.USER_ID_PARAM, userid.toString());
		}
        
		Boolean isAdmin = (Boolean)req.getAttribute(Constant.USER_IS_ADMIN);
		if (isAdmin != null){
			MDC.put(Constant.USER_IS_ADMIN, isAdmin?"admin":"user");

		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub


	}

}
