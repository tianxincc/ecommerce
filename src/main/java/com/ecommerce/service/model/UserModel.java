package com.ecommerce.service.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserModel {
    private  int id;
    @NotBlank(message = "用户名不能为空")
    private  String name;

    @NotNull(message = "性别不能为空")
    private  String gender;

    @NotNull(message = "年龄不能为空")
    @Min(value = 0,message = "年龄最少不能为0岁")
    @Max(value = 150,message = "年龄最大不能超过150岁")
    private  String age;

    @NotNull(message = "手机号不能为空")
    private  String telphone;
    private  String registerMode;
    private  String thirdpartyId;

    @NotNull(message = "密码不能为空")
    private  String encrptPassword;

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode;
    }

    public String getThirdpartyId() {
        return thirdpartyId;
    }

    public void setThirdpartyId(String thirdpartyId) {
        this.thirdpartyId = thirdpartyId;
    }
}
