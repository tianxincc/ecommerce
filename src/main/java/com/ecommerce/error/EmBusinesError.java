package com.ecommerce.error;

public enum EmBusinesError implements  CommonError {

    //定义通用错误类型00001
    PARAMETRT_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),

    //100000开头为用户相关错误定义
    USER_NOT_EXIST(20001,"用户不存在"),
    USER_lOGIN_FAIL(2002,"用户或者密码不正确")
    ;

    private  EmBusinesError(int errCode,String errMsg){
        this.errCode=errCode;
        this.errMsg=errMsg;
    }

    private  int errCode;
    private  String errMsg;


    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrmsg(String Errmsg) {
         this.errMsg=Errmsg;
         return this;
    }
}
