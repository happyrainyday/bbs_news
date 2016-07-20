package net.dontdrinkandroot.example.angularrestspringsecurity;

import java.text.MessageFormat;

/**
 *
 * @author agle
 * @version v 0.1 2016/06/28
 */
public enum ErrorCodeEnum {
    /**
     * 参数类异常
     */
    PARAMETERS_IS_NULL_1("10000", "输入参数不能为空", "输入参数{0}不能为空"),
    PARAMETERS_IS_NULL_2("10001","输入参数不能为空", "输入参数{0},{1}不能为空"),
   
    PARAMETERS_IS_NOT_CORRECT("20000", "参数不正确", "输入参数{0}不正确"),
    
    /**
     * 没有错误
     */
    NO_ERROR("200", "OK", "OK");

    private String code;
    private String defaultMessage;
    private String message;

    /**
     * 自定义错误信息
     * <br/>当未支持定义错误信息时，返回默认的message信息
     * <b>请尽量使用该方法来自定义的明确的错误提示信息</b>
     * <p/>
     * <p>
     * 使用方法：
     * ErrorCodeEnum.VALUES_OVER_MAX_LIMITED.getMessage("mobile_no","50");
     * >> mobile_no参数值最多只支持50个
     * </p>
     *
     * @param input 自定义描述
     * @return
     */
    public String getMessage(String... input) {
        Object[] obj = input;
        return getMessage(obj);
    }

    /**
     * @param input
     * @return
     */
    private String getMessage(Object[] input) {
        if (input == null || input.length == 0 || getMessage() == null) {
            return getDefaultMessage();
        } else {
            return MessageFormat.format(getMessage(), input);
        }
    }

    /**
     * @param code
     * @param defaultMessage
     */
    private ErrorCodeEnum(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    /**
     * @param code
     * @param defaultMessage
     * @param message
     */
    private ErrorCodeEnum(String code, String defaultMessage, String message) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.message = message;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    private String getMessage() {
        return message;
    }

    /**
     * Getter method for property <tt>formatMessage</tt>.
     *
     * @return property value of formatMessage
     */
    public String getDefaultMessage() {
        return defaultMessage;
    }

}