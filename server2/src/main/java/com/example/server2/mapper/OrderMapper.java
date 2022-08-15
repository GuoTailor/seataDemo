package com.example.server2.mapper;

import com.example.server2.model.Order;

/**
* @author GYH
* @description 针对表【order】的数据库操作Mapper
* @createDate 2022-06-20 17:57:26
* @Entity com.example.server2.model.Order
*/
public interface OrderMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

}
