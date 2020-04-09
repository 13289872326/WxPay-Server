package com.xn.service.user.impl;

import com.xn.dao.user.IncomeInfoDao;
import com.xn.service.user.IncomeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Date 2020/4/3 22:34
 * @Author LHS
 * @ClassName IncomeInfoServiceImpl
 * @Description :...
 */
@Service("incomeInfoService")
@Component
public class IncomeInfoServiceImpl implements IncomeInfoService {

    @Autowired
    @Resource
    IncomeInfoDao incomeInfoDao;

    @Override
    public Map addNewIncomeLog(Map map) {
        return null;
    }

    @Override
    public List<Map<String ,Object>> getUserIncomeInfoList(Map map) {

        return null;
    }


    @Override
    public Map getUserIncomeInfoCount(Map map) {
        return null;
    }

    @Override
    public List<Map<String ,Object>> getLowUserIncomeInfoCount(Map map) {
        return null;
    }
}
