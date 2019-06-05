package com.ecommerce.controller;

import com.ecommerce.error.BusinessException;
import com.ecommerce.error.EmBusinesError;
import com.ecommerce.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public  static final   String  CONTENT_TYPE_FROMED="application/x-www-form-urlencoded";

    //定义exceptionhandler 解决为背controller 吸收的异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public  Object handlerException(HttpServletRequest request, Exception ex){
        Map<String,Object> responseData=new HashMap<>();
        if( ex instanceof BusinessException){
            BusinessException businessException=(BusinessException)ex;
            responseData.put("errCode",businessException.getErrCode());
            responseData.put("errMsg",businessException.getErrMsg());
        }else{
            responseData.put("errCode", EmBusinesError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg",EmBusinesError.UNKNOWN_ERROR.getErrMsg());
        }

        return CommonReturnType.create(responseData,"fail");
    }

}
