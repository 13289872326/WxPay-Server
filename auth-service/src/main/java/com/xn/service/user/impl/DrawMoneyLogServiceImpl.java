package com.xn.service.user.impl;

import com.xn.dao.user.DrawMoneyLogDao;
import com.xn.service.user.DrawMoneyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Date 2020/4/5 11:12
 * @Author LHS
 * @ClassName DrawMoneyLogServiceImpl
 * @Description :...
 */
@Service("drawMoneyLogService")
@Component
public class DrawMoneyLogServiceImpl implements DrawMoneyLogService {

    @Autowired
    @Resource
    DrawMoneyLogDao  drawMoneyLogDao;

    @Override
    public List<Map> getUserDrawLogByTime(String data) {
        return null;
    }

    @Override
    public List<Map> getUserDrawLogByUserId(String UserId) {
        List<Map> userDrawMoneyLogList = drawMoneyLogDao.getUserDrawMoneyLogList(UserId);
        return userDrawMoneyLogList;
    }
}
