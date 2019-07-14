package com.ecommerce.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {

    //检验结果是否有错
    private  boolean hasErrors=false;

    //存放错误信息的map
    private Map<String,String> errorMagMap = new HashMap<>();

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMagMap() {
        return errorMagMap;
    }

    public void setErrorMagMap(Map<String, String> errorMagMap) {
        this.errorMagMap = errorMagMap;
    }

    //实现通用的通过格式化字符串信息获取错误结果的msg
    public String getError(){
        return  StringUtils.join(errorMagMap.values().toArray(),",");
    }
}
