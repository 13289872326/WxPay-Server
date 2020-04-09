package com.xn.dao.user;

import com.xn.domain.user.DrawMoneyLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DrawMoneyLogDao {
    List<Map> getUserDrawMoneyLogList(@Param("userId") String userId);

    DrawMoneyLog getLastMoneyLog(@Param("userId") String userId);

    void updateLog(Map<String, Object> map);

}
