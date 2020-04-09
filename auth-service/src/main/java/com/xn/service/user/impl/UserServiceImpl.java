package com.xn.service.user.impl;

import com.xn.dao.user.BalanceDao;
import com.xn.dao.user.IncomeInfoDao;
import com.xn.dao.user.UserDao;
import com.xn.domain.user.Balance;
import com.xn.domain.user.User;
import com.xn.service.user.IncomeInfoService;
import com.xn.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2018/12/25 14:15
 * @Author LHS
 * @ClassName UserServiceImpl
 * @Description :...
 */
@Service("userService")
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    @Resource
    UserDao userDao;
    @Autowired
    @Resource
    BalanceDao balanceDao;
    @Autowired
    @Resource
    IncomeInfoDao incomeInfoDao;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    RedisTemplate redisTemplate;

    @Value("${serverDivident}")
    public Double serverDivident = 0.1;

    @Value("${highDivident}")
    public Double highDivident = 0.6;

    @Value("${superiorDivident}")
    public Double superiorDivident = 0.3;

    @Value("${vipPrice}")
    public int vipPrice = 99;

    @Value("${agentPrice}")
    public int agentPrice = 299;


    @Override
    public Map<String, Object> userLogin(Map map) {
        String openId = (String) map.get("openId");
        User openid = userDao.selectUserInf(openId);
        String highLevel = (String) map.get("highLevel");
        User hUser = userDao.selectUserInf(highLevel);
        Map<String, Object> ma = new HashMap<>();
        logger.info("用户登录接口服务层调用成功");
        if (openid == null) {
            User user = new User();
            user.setId((String) map.get("openId"));
            user.setNickName((String) map.get("nickName"));
            user.setUserName((String) map.get("nickName"));
            user.setImgUrl((String) map.get("avatarUrl"));
            user.setOpenId((String) map.get("openId"));
            if (hUser != null) {
                user.setHighLevel(highLevel);
            } else {
                user.setHighLevel("0");
            }
            user.setRemainNum(5);
            user.setRoleType(1);//1普通用户 2.会员 3.代理
            int i = userDao.insertUser(user);
            if (i == 1) {
                ma.put("code", 200);
                ma.put("message", "欢迎您新用户");
                ma.put("error", "");
                ma.put("data", user);
            } else {
                ma.put("code", 303);
                ma.put("message", "用户信息记录失败");
                ma.put("error", "");
                ma.put("data", "");
            }
            return ma;
        } else {
            ma.put("code", 201);
            ma.put("message", "欢迎登录");
            ma.put("error", "");
            ma.put("data", openid);
        }
        return ma;
    }

    @Override
    public Map getUserAccountInfo(Map map) {
        Map<String, Object> result = new HashMap();
        String userId = (String) map.get("userId");
        logger.info("用户ID-------------------------------------------》》》》" + userId);
        User user = userDao.selectUserInf(userId);
        if (user == (null)) {
            result.put("code", 304);
            result.put("message", "用户信息错误，请勿非法操作");
            result.put("error", "非法请求，请勿违法操作！！");
            result.put("data", "{}");
        } else {
            Map<String, Object> dataMap = new HashMap();
            //拿到下级用户List
            List<Map<String, Object>> maps = userDao.selectLowUserList(userId);
            if (!maps.isEmpty()) {
                dataMap.put("LowUserList", maps);
            } else {
                dataMap.put("LowUserList", "{}");
            }
            //拿到下下级用户总数
            int i = userDao.selectLowlowUserListCount(userId);
            dataMap.put("lowlowUserCount", i);
            //获取当前用户账户余额
            Balance userBlance = balanceDao.getUserBlance(userId);
            if (userBlance == null) {
                userBlance = new Balance();
                userBlance.setBalan(0);
                userBlance.setTotalRevenue(0);
                userBlance.setUpdateDate(new Date());
                userBlance.setUserId(userId);
                balanceDao.insertUserBalance(userBlance);
            }
            dataMap.put("blance", userBlance);
            result.put("code", 200);
            result.put("message", "用户信息获取成功！！");
            result.put("error", "");
            result.put("data", dataMap);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getUserList(Map map) {
        List<Map<String, Object>> userId = userDao.selectLowUserList((String) map.get("userId"));
        return userId;
    }

    @Override
    public Map getUserListCount(String userId) {
        int i = userDao.selectLowlowUserListCount(userId);
        Map restMap = new HashMap();
        restMap.put("countUser",i);
        return restMap;
    }

    @Override
    public Map updateUserInfo(Map map) {
        logger.info("控制层方法调用+++++++++++++++++++++++++++++++++++++++>>");
        //  获取到用户更新类型
        Map restMap = new HashMap();
        switch ((Integer) map.get("upType")) {
            case 1:
                //更新用户剩余次数
                User user = userDao.selectUserInf(map.get("userId").toString());
                //获取角色类型
                int roleType = user.getRoleType();
                //获取剩余次数
                int remainNum = user.getRemainNum();
                if (roleType == 1) {
                    if (remainNum < 1) {//次数小于1 返回接口调用失败
                        restMap.put("code", -1);
                        return restMap;
                    } else {//否则更新用户信息
                        user.setRemainNum(remainNum - 1);
                        userDao.updateUserInf(user);
                    }
                }
                restMap.put("code", 1);
                return restMap;
            case 2:
                break;
            case 3:
                break;
        }
        restMap.put("code", -1);
        return restMap;
    }





    //用户成为VIP 给上级以及上上级用户，系统等进行收益分配
    @Override
    public Map updateUserRoleType(Map map) {
        Map ma = new HashMap();
        //去到当前用户角色类型
        int roleType = Integer.parseInt((String) map.get("roleType"));
        int changeType = Integer.parseInt((String) map.get("changeType"));
        if (roleType == changeType || changeType < roleType) {
            ma.put("code", 230);
            ma.put("message", "信息更新异常，请确定后重试！！");
            ma.put("error", "");
            ma.put("data", "{}");
            return ma;
        }
        map.put("roleType",changeType);
        int i = userDao.updateUser(map);
        if (i > 0) {
            ma.put("code", 200);
            ma.put("message", "用户角色信息更新成功");
            ma.put("error", "");
            ma.put("data", ma);

            drawMoney(map);


        } else {
            ma.put("code", 303);
            ma.put("message", "用户角色信息更新失败");
            ma.put("error", "");
            ma.put("data", "{}");
        }
        return ma;
    }

    //提供用户收益分配算法
    public int drawMoney(Map map) {
        int changeType = Integer.parseInt((String) map.get("changeType"));
        String userId = (String) map.get("openId");
        String highLevel = (String) map.get("highLevel");

        //所有收益人股份集合
        Map<String, String> incMap = new HashMap();
        User user = userDao.selectUserInf(highLevel);
        String highLevel1 = "";
        //没有上层用户ID
        if (highLevel==null||highLevel.equals("")||highLevel.length() < 30 || user == null) {
            //顶层用户，没用上级只需要给商家全部抽成即可
            map.put("server", 100);
        } else {
            //有上级用户ID，获取上级用户
            highLevel1 = user.getHighLevel();
            if (highLevel1 == null || highLevel1.equals("") || highLevel1.length() < 30) {//没有上上级且只有上级，给上级与系统进行受益分配
                map.put("highUser", 60);
                map.put("server", 40);
            } else {
                //有上级，且还有上上级
                map.put("highUser", 60);
                map.put("server", 30);
                map.put("superiorDivident", 10);
            }
        }

        Map<String, Object> sqlMap = new HashMap();
        switch (incMap.size()) {
            case 1:
                //商家100%收益
                sqlMap.put("bfId", "0");
                if (changeType == 2) {
                    sqlMap.put("profit", vipPrice);
                } else {
                    sqlMap.put("profit", agentPrice);
                }
                sqlMap.put("ctbId", userId);
                incomeInfoDao.addNewIncomeInfo(sqlMap);
                break;
            case 2:
                sqlMap.put("bfId", "0");
                double v = serverDivident + highDivident;
                if (changeType == 2) {
                    sqlMap.put("profit", vipPrice * v);
                } else {
                    sqlMap.put("profit", agentPrice * v);
                }
                sqlMap.put("ctbId", userId);
                incomeInfoDao.addNewIncomeInfo(sqlMap);

                sqlMap = new HashMap();
                sqlMap.put("bfId", highLevel);
                if (changeType == 2) {
                    sqlMap.put("profit", vipPrice * highDivident);
                } else {
                    sqlMap.put("profit", agentPrice * highDivident);
                }
                sqlMap.put("ctbId", userId);
                incomeInfoDao.addNewIncomeInfo(sqlMap);
                break;
            case 3:
                sqlMap.put("bfId", "0");
                if (changeType == 2) {
                    sqlMap.put("profit", vipPrice * serverDivident);
                } else {
                    sqlMap.put("profit", agentPrice * serverDivident);
                }
                sqlMap.put("ctbId", userId);
                incomeInfoDao.addNewIncomeInfo(sqlMap);


                sqlMap = new HashMap();
                sqlMap.put("bfId", highLevel);
                if (changeType == 2) {
                    sqlMap.put("profit", vipPrice * highDivident);
                } else {
                    sqlMap.put("profit", agentPrice * highDivident);
                }
                sqlMap.put("ctbId", userId);
                incomeInfoDao.addNewIncomeInfo(sqlMap);


                sqlMap = new HashMap();
                sqlMap.put("bfId", highLevel1);
                if (changeType == 2) {
                    sqlMap.put("profit", vipPrice * superiorDivident);
                } else {
                    sqlMap.put("profit", agentPrice * superiorDivident);
                }
                sqlMap.put("ctbId", userId);
                incomeInfoDao.addNewIncomeInfo(sqlMap);
                break;

        }

        return 1;
    }


    public static void main(String[] args) {
        int i = 99;
        System.out.println("" + i * 0.6);
    }

}