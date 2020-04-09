package com.xn.service.user;

import java.util.Map;

/**
 * @Date 2020/4/3 22:25
 * @Author LHS
 * @ClassName BalanceService
 * @Description :...
 */
public interface BalanceService {
    //获取当前用户余额信息
    Map getUserAccountInfo(Map map);
    //获取下级用户余额信息
    Map getLowUserAccountInfo(Map map);
    //更新用户余额
    Map updateUserAccountInfo(Map map);

}
