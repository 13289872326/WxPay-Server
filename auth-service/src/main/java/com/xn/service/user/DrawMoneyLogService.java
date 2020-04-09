package com.xn.service.user;

import java.util.List;
import java.util.Map;

/**
 * @Date 2020/4/5 11:11
 * @Author LHS
 * @ClassName DrawMoneyLogService
 * @Description :...
 */
public interface DrawMoneyLogService {
    //查询某一时间用户提现记录
    List<Map> getUserDrawLogByTime(String data);

    //查询某一时间用户提现记录(后期分页)
    List<Map> getUserDrawLogByUserId(String UserId);


}
