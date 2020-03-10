package com.camelot.kuka.user.service;

import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.user.model.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AddressService {

    void queryAddress();


    Result<Map<String, String>> queryAddressMap(List<String> codes);
}
