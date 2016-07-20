package net.dontdrinkandroot.example.angularrestspringsecurity;

/**
 * @ClassName: AccountException
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tjf
 * @date 2016年7月9日上午11:50:05
 * @Version V1.00
 */
public class AccountException extends RuntimeException {

	private static final long serialVersionUID = 2847209155049772998L;
	
    public AccountException() {
        super();
    }

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountException(Throwable cause) {
        super(cause);
    }

    protected AccountException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
