package com.example.server1.mapper;

import com.example.server1.model.Account;

/**
* @author GYH
* @description 针对表【account】的数据库操作Mapper
* @createDate 2022-06-20 17:50:28
* @Entity com.example.server1.model.Account
*/
public interface AccountMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

}
