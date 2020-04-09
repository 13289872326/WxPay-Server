package com.xn.service.user.impl;

import com.xn.dao.user.BalanceDao;
import com.xn.service.user.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Date 2020/4/3 22:26
 * @Author LHS
 * @ClassName BalanceServiceImpl
 * @Description :...
 */
@Service("balanceService")
public class BalanceServiceImpl implements BalanceService {
    @Autowired
    @Resource
    BalanceDao balanceDao;

    @Override
    public Map getUserAccountInfo(Map map) {
        return null;
    }

    @Override
    public Map getLowUserAccountInfo(Map map) {
        return null;
    }

    @Override
    public Map updateUserAccountInfo(Map map) {
        return null;
    }
}
