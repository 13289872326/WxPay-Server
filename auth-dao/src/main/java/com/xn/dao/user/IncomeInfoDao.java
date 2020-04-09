package com.xn.dao.user;


import com.xn.domain.user.IncomeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IncomeInfoDao {

    //插入新的收益信息
    int addNewIncomeInfo(Map map);

    //获取个人收益列表
    List<Map> getUserIncoList(@Param("userId") String userId);

    //获取总收益
    Map incoCount(@Param("userId") String highLevel);

    //获取个人收益列表
    List<Map> getLowUserIncoList(@Param("userId") String userId);

}
