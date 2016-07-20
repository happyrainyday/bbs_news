package net.dontdrinkandroot.example.angularrestspringsecurity;

import java.math.BigDecimal;

/**
 * @author hzzjh
 *
 */
public class FormatUtils {

	/**
	 * 验证字符串是否存在
	 * @param str
	 * @return
	 */
	public static boolean checkStringIsNull(String str) {
		if (str != null && !str.trim().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 将字符串转化为Integer类型，参数不正确则返回-1
	 */
	public static Integer checkIsInteger(String number) {
		Integer flag = -1;
		try {
			if (number != null && !number.trim().equals("")) {
				flag = Integer.parseInt(number);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 将字符串转化为BigDecimal 参数不正确则返回BigDecimal型-1
	 * 
	 * @param str
	 * @return
	 */
	public static BigDecimal BigDecimalNvl(String str) {
		try {
			return new BigDecimal(str);
		} catch (Exception e) {
			return new BigDecimal(-1);
		}

	}
}