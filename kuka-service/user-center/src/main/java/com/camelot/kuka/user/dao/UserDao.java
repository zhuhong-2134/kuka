package com.camelot.kuka.user.dao;

import com.camelot.kuka.user.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

	List<User> findList(User user);
}
