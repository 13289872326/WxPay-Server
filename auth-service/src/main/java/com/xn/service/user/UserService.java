package com.xn.service.user;

import com.xn.domain.user.User;

import java.net.SocketException;
import java.util.List;
import java.util.Map;

/**
 * @Date 2018/12/25 14:14
 * @Author LHS
 * @ClassName UserService
 * @Description :...
 */
public interface UserService {
    //用户登录
     Map<String, Object> userLogin(Map map);

    //获取用户信息
    Map getUserAccountInfo(Map map);

    //获取用户列表
     List<Map<String,Object>> getUserList(Map map);

     //获取下下级用户人数
     Map getUserListCount(String userId);

    //更新用户信息
     Map updateUserInfo(Map map);

     //更新用户角色信息
     Map updateUserRoleType(Map map);


//    Map authentication(Map<String, Object> map);
//
//    Map changePassword(Map<String, Object> map);
//
//    Map getVersionInfo(Map<String, Object> map);
//
//    Map getUserByUserName(String userName);
//
//    Map getUserByUserNameAndPwd(Map<String, Object> map);
//
//    Map registNewUser(Map<String, Object> map);
//
//    Map deferToken(Map<String, Object> map);
//
//    Map bindThirdUser(Map<String, Object> map) throws SocketException;
//
//    Map setUsedThirdAccount(Map<String, Object> map);
//
//    List<Map<String, Object>> getThirdUserList(long userId);


}
