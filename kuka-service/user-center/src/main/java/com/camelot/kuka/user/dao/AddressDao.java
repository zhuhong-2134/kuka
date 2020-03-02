package com.camelot.kuka.user.dao;

import com.camelot.kuka.user.model.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressDao {

    List<Address> selectAll();
}
