package com.xn.dao.user;

import com.xn.domain.user.User;
import com.xn.domain.user.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Date 2018/12/25 14:28
 * @Author LHS
 * @ClassName UserDao
 * @Description 统一认证用户dao
 */
public interface UserDao {

    //插入新用戶
    int insertNewUser(Map map);

    int insertUser(User map);

    //更新用戶部分信息
    int updateUser(Map map);

    //更新用戶全部信息
    int updateUserInf(User user);

    //查询用户信息
    User selectUserInf(@Param("userId") String userId);

    //查询下下级用户总数
    int selectLowlowUserListCount(@Param("userId") String userId);

    //查询下级用户列表
    List<Map<String, Object>> selectLowUserList(@Param("userId") String userId);

}
