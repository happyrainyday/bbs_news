package net.dontdrinkandroot.example.angularrestspringsecurity;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class IpHelper {
	private static Logger logger = Logger.getLogger(IpHelper.class);
	private static List list = new ArrayList();
	private static String ips = "";
	static {
		// 初始化一次
		getAllLocalHostIP();
	}

	// TODO 这里的IP匹配有问题，如果regex里面配置了多个IP，则返回值是错误的
	public static boolean is_ip_match(String regex, String ip) {
		return Pattern.matches(regex, ip);
	}

	public static boolean is_ip_match_local(String ip) {
		List list = getAllLocalHostIP();
		for (int i = 0; i < list.size(); i++) {
			if (is_ip_match(ip, (String) list.get(i))) {
				return true;
			}
		}
		return false;
	}

	public static String getAllLocalHostIPString() {
		// 初始化一次
		getAllLocalHostIP();
		return ips;
	}

	public static List getAllLocalHostIP() {
		if (list.size() > 0) {
			return list;
		}
		Enumeration allNetInterfaces = null;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				String name = netInterface.getName();
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						logger.debug(ip.getHostAddress());
						list.add(ip.getHostAddress());
						if (list.size() > 1) {
							ips = ips + ",";
						}
						ips = ips + ip.getHostAddress();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		if ((ip != null) && (ip.length() >= 15) && (ip.indexOf(",") > 0)) {
			String ipLeval = ip.substring(ip.indexOf(",") + 1, ip.length());
			ip = ip.substring(0, ip.indexOf(","));
			while ("unknown".equalsIgnoreCase(ip)) {
				if (ipLeval.indexOf(",") > 0) {
					ip = ipLeval.substring(0, ipLeval.indexOf(","));
					ipLeval = ipLeval.substring(ipLeval.indexOf(",") + 1, ipLeval.length());
				} else {
					ip = ipLeval;
					break;
				}
			}
		}
		return ip;
	}

	public static String getIpAddr1(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Host");
		}
		return ip;
	}
}
