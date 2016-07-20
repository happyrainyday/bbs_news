package net.dontdrinkandroot.example.angularrestspringsecurity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 基础数据返回对象
 *
 * @author agle
 * @version v 0.1 2016/06/28
 */
public class BaseResult<T> implements Serializable {

	/**  */
	private static final long serialVersionUID = -814929216218701299L;

	/** 服务返回码 */
	private String code;

	/** 服务消息 */
	private String message;

	/** 服务具体数据 */
	private T data;

	/**
	 * 默认构造函数
	 */
	@SuppressWarnings("unchecked")
	public BaseResult() {
		super();
		this.code = ErrorCodeEnum.NO_ERROR.getCode();
		this.message = ErrorCodeEnum.NO_ERROR.getDefaultMessage();
		this.data = (T) new HashMap<>();
	}

	/**
	 *
	 * @param data
	 */
	public BaseResult(T data) {
		super();
		this.code = ErrorCodeEnum.NO_ERROR.getCode();
		this.message = ErrorCodeEnum.NO_ERROR.getDefaultMessage();
		this.data = data;
	}

	/**
	 *
	 * @param errorCodeEnum
	 */
	public BaseResult(ErrorCodeEnum errorCodeEnum) {
		super();
		this.code = errorCodeEnum.getCode();
		this.message = errorCodeEnum.getDefaultMessage();
	}

	/**
	 *
	 * @param errorCodeEnum
	 */
	public BaseResult(ErrorCodeEnum errorCodeEnum, String... params) {
		super();
		this.code = errorCodeEnum.getCode();
		this.message = errorCodeEnum.getMessage(params);
	}

	// /**
	// *
	// * @param errorCodeEnum
	// * @param data
	// */
	// public BaseResult(ErrorCodeEnum errorCodeEnum, T data) {
	// super();
	// this.code = errorCodeEnum.getCode();
	// this.message = errorCodeEnum.getDefaultMessage();
	// this.data = data;
	// }

	/**
	 *
	 * @param errorCode
	 * @param errorMessage
	 */
	public BaseResult(String errorCode, String errorMessage) {
		super();
		this.code = errorCode;
		this.message = errorMessage;
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
	 * Setter method for property <tt>code</tt>.
	 *
	 * @param code
	 *            value to be assigned to property code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Getter method for property <tt>message</tt>.
	 *
	 * @return property value of message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter method for property <tt>message</tt>.
	 *
	 * @param message
	 *            value to be assigned to property message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter method for property <tt>dal</tt>.
	 *
	 * @return property value of dal
	 */
	public T getData() {
		return data;
	}

	/**
	 * Setter method for property <tt>dal</tt>.
	 *
	 * @param data
	 *            value to be assigned to property dal
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return JsonConverterUtil.serialize(this);
	}

	/**
	 * Sets the error enum.
	 *
	 * @param errorCodeEnum
	 *            the new error enum
	 */
	public void setErrorEnum(ErrorCodeEnum errorCodeEnum) {
		this.code = errorCodeEnum.getCode();
		this.message = errorCodeEnum.getDefaultMessage();
	}

}
