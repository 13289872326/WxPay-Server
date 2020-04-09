package com.xn.service.user;

import java.util.List;
import java.util.Map;

/**
 * @Date 2020/4/3 22:27
 * @Author LHS
 * @ClassName IncomeInfoService
 * @Description :...
 */
public interface IncomeInfoService {
    //新增收益
    Map addNewIncomeLog(Map map);

    //获取当前用户收益列表（分页）
    List<Map<String ,Object>> getUserIncomeInfoList(Map map);

    //获取当前用户收益统计
    Map getUserIncomeInfoCount(Map map);

    //获取当前用户下级用户收益统计
    List<Map<String ,Object>> getLowUserIncomeInfoCount(Map map);

}
