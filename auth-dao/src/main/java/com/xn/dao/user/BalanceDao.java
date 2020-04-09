package com.xn.dao.user;


import com.xn.domain.user.Balance;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface BalanceDao {

    //获取个人钱包信息
    Balance getUserBlance(@Param("userId") String userId);

    void insertUserBalance(Balance balance);

    void updateBlance(Map<String, Object> balance);

}
