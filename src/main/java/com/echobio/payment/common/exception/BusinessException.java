package com.echobio.payment.common.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -8528344063878985082L;

    private String errMsg;
    private Throwable e;

    public BusinessException() {
        super();
    }

    public BusinessException(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
    }

    public BusinessException(String errMsg, Throwable e) {
        super(errMsg, e);
        this.errMsg = errMsg;
        this.e = e;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public Throwable getE() {
        return e;
    }
}
