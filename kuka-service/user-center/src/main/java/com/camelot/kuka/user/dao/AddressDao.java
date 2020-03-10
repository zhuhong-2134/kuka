package com.camelot.kuka.user.dao;

import com.camelot.kuka.user.model.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressDao {

    List<Address> selectAll();

    List<Address> qeuryListByCodes(@Param("array") List<String> codes);
}
