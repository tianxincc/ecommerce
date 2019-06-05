package com.ecommerce.dao;

import com.ecommerce.dataobject.PasswordDo;

public interface PasswordDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PasswordDo record);

    int insertSelective(PasswordDo record);

    PasswordDo selectByPrimaryKey(Integer id);
    PasswordDo selectByUserId(Integer id);

    int updateByPrimaryKeySelective(PasswordDo record);

    int updateByPrimaryKey(PasswordDo record);
}