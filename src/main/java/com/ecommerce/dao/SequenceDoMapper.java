package com.ecommerce.dao;

import com.ecommerce.dataobject.SequenceDo;

public interface SequenceDoMapper {
    int deleteByPrimaryKey(String name);

    int insert(SequenceDo record);

    int insertSelective(SequenceDo record);

    SequenceDo selectByPrimaryKey(String name);

    SequenceDo getSequenceByName(String name);

    int updateByPrimaryKeySelective(SequenceDo record);

    int updateByPrimaryKey(SequenceDo record);
}