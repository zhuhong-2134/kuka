package com.camelot.kuka.backend.service;

import java.util.Map;

import com.camelot.kuka.backend.model.BlackIP;
import com.camelot.kuka.model.common.Page;

public interface BlackIPService {

	void save(BlackIP blackIP);

	void delete(String ip);

	Page<BlackIP> findBlackIPs(Map<String, Object> params);

}
