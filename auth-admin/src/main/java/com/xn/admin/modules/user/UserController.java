package com.xn.admin.modules.user;

import com.xn.admin.common.utils.HttpUtils;
import com.xn.admin.common.utils.MD5Utils;
import com.xn.admin.common.utils.RedisCacheUtils;
import com.xn.admin.common.utils.StringUtils;
import com.xn.common.utils.HttpUtil;
import com.xn.domain.user.DrawMoneyLog;
import com.xn.service.user.BalanceService;
import com.xn.service.user.DrawMoneyLogService;
import com.xn.service.user.IncomeInfoService;
import com.xn.service.user.UserService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * @Date 2018/12/25 11:33
 * @Author LHS
 * @ClassName UserController
 * @Description 处理用户注册登录请求
 */
@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    //appId
    @Value("${APPID}")
    String appId = "";
    //APP-secret
    @Value("${Secret}")
    String appSecret = "";
    //商户ID
    @Value("${MCH_ID}")
    String mch_Id = "";
    //商户秘钥（平台自定义配值）
    @Value("${MCH_KEY}")
    String mch_key = "";
    //微信服务器端登录code换openId
    @Value("${AUTH_CODE_URL}")
    String AUTH_CODE_URL = "https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={SECRET}&js_code={JSCODE}&grant_type=authorization_code";
    //微信支付结果回调
    @Value("${wxPayCallBackUrl}")
    String wxPayCallBackUrl = "";
    //统一下单API（生成预付单）
    @Value("${WX_ORDER_CREAT}")
    String wxCreatOrderAPI = "";
    //企业退款API（提现）
    @Value("${wxPayBackUrl}")
    String wxPayBackUrl = "";
    //会员价格
    @Value("${vipPrice}")
    String vipPrice = "";
    //代理价格
    @Value("${agentPrice}")
    String agentPrice = "";
    @Autowired
    RedisCacheUtils redisCacheUtils;

    @Autowired
    @Resource(name = "userService")
    UserService userService;

    @Autowired
    @Resource(name = "balanceService")
    BalanceService balanceService;

    @Autowired
    @Resource(name = "incomeInfoService")
    IncomeInfoService incomeInfoService;


    @Autowired
    @Resource(name = "drawMoneyLogService")
    DrawMoneyLogService drawMoneyLogService;

    @RequestMapping("/creatOrder")
    public Map creatOrder(@RequestParam("openId") String openId,
                          @RequestParam("payType") String payType,
                          @RequestParam("roleType") String roleType,
                          HttpServletRequest request) {
        HashMap resultMap = new HashMap();
        //payType 0.支付会员  1.成为推广
        //支付金额（分）
        String total_fee = "";
        if (roleType.equals("1") && payType.equals("0")) {
            //游客成为会员
            total_fee = vipPrice;
        } else if (roleType.equals("1") && payType.equals("1")) {
            //游客成为推广
            total_fee = agentPrice;

        } else if (roleType.equals("2") && payType.equals("1")) {
            //会员成为代理
            total_fee = agentPrice;
        } else {
            logger.info("当前角色类型---------------------》》》》" + roleType);
            logger.info("当前角色类型---------------------》》》》" + payType);
            logger.error("当前角色与购买角色重复无虚购买-----------------------------------------------------------》》》》");
            logger.error("服务器安全警告-------------------------------》》： 异常的请求逻辑");
            resultMap.put("code",403);
            resultMap.put("data","{}");
            resultMap.put("message","服务器安全警告:异常的请求逻辑!!");
            resultMap.put("error","当前角色与购买角色重复无虚购买");
            return resultMap;
        }
        //随机字符串
        String randomString = StringUtils.getRandomString(32);
        //数字签名
        String sign = "";
        //商品描述
        String body = "短视频去水印解析，无限次数服务";
        //商品订单号
        String orderId = openId.substring(0, 5) + System.currentTimeMillis();
        //微信支付结果回调结果通知URL
        String callbackurl = wxPayCallBackUrl.replace("{openId}", openId).replace("{orderId}", orderId);
        //交易类型 H5交易   APP交易  JSAPI小程序交易  微信交易
        String trade_type = "JSAPI";
        String spbill_create_ip = HttpUtils.getIp(request);

        SortedMap<String, Object> params = new TreeMap<>();
        params.put("appid", appId);
        params.put("mch_id", mch_Id);
        params.put("nonce_str", randomString);
        params.put("body", body);
        params.put("out_trade_no", orderId);
        params.put("openid", openId);
        params.put("total_fee", total_fee);
        params.put("spbill_create_ip", spbill_create_ip);
        params.put("notify_url", callbackurl);
        params.put("trade_type", trade_type);
        sign = sortMapTools(params);
        params.put("sign",sign );


        String xml = "<xml>" + "<appid>" + appId + "</appid>" + "<body>" + body + "</body>" +
                "" + "<mch_id>" + mch_Id + "</mch_id>" + "<nonce_str>" + randomString + "</nonce_str>" +
                "<notify_url>" + callbackurl + "</notify_url>" + "<openid>" + openId + "</openid>" +
                "<out_trade_no>" + orderId + "</out_trade_no>" + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>" +
                "<total_fee>" + Integer.parseInt(total_fee) + "" + "</total_fee>" + "<trade_type>" + trade_type + "</trade_type>" +
                "<sign>" + sign + "</sign>" + "</xml>";

        String result = "";
        System.out.println("xml---->" + xml);
        try {
            result = HttpUtil.doPost(wxCreatOrderAPI, xml);
        } catch (Exception e) {
            System.out.println("统一下单接口情求错误----------------------------------->" + result);
            e.printStackTrace();
        }
        System.out.println("result----------------------------------->" + result);
        String randomString1 = StringUtils.getRandomString(32);
        if(result.contains("prepay_id")&&result.contains("<return_code><![CDATA[SUCCESS]]></return_code>")){
            String prepay_id = result.substring(result.indexOf("<prepay_id>"), result.indexOf("</prepay_id>")).substring(20,56);
            Long timeStamp=System.currentTimeMillis();
            SortedMap  map=new TreeMap();
            map.put("appId",appId);
            map.put("timeStamp",timeStamp);
            map.put("nonceStr",randomString1);
            map.put("package","prepay_id="+prepay_id);
            map.put("signType","MD5");
            String reSign = sortMapTools(map);
            System.out.println("解析订单ID值--------------------------->>>>"+prepay_id);
            System.out.println("二次签名字符串------------------------->>>>"+reSign);
            map.put("paySign",reSign);
            map.put("prepay_id=",prepay_id);
            resultMap.put("code",200);
            resultMap.put("data",map);
            resultMap.put("message","请求成功");
            resultMap.put("error","{}");
            return resultMap;
        }
        resultMap.put("code",202);
        resultMap.put("data","{}");
        resultMap.put("message","微信支付下单失败");
        resultMap.put("error","{}");
        return resultMap;
    }
    @RequestMapping("/{openId}/{orderId}/wpayCalbak")
    public Map wxPayCallBack(@PathVariable("openId") String openId, @PathVariable("orderId") String orderId) {

        logger.debug("微信支付成功------------回调openId----------------》》》》"+openId);
        logger.debug("微信支付成功------------回调orderId----------------》》》》"+orderId);
        //对支付回调进行处理

        return new HashMap();
    }


    //用户登录，获取用户信息
    @RequestMapping("/auth_code")
    public Map authCode(@RequestParam("auth_code") String js_code) {
        logger.info("APPID--------------------------------->" + appId);
        logger.info("SECRET--------------------------------->" + appSecret);
        logger.info("JSCODE--------------------------------->" + js_code);
        String replace = AUTH_CODE_URL.replace("APPID", appId).replace("SECRET", appSecret).replace("JSCODE", js_code);

        logger.info("服务器端请求WX官方认证----url----》" + replace);
        JSONObject s = JSONObject.fromObject(HttpUtil.doGet(replace));
        Map map = new HashMap();

        if (s.containsKey("errcode")) {
            map.put("code", 204);
            map.put("message", "用户信息登录认证失败");
            map.put("error", "");
        } else if (s.containsKey("openid")) {
            map.put("code", 200);
            map.put("message", "用户信息登录认证成功");
            map.put("error", "");
        }
        map.put("data", s);
        logger.info("用户登录状态认证接口调用成功,返回结果：-----------------------》》》》" + s.toString());
        return map;
    }

    //用户登录，获取用户信息
    @RequestMapping("/login")
    @PostMapping
    public Map login(@RequestParam("openId") String openId,
                     @RequestParam("imgUrl") String imgUrl, @RequestParam("userName") String userName,
                     @RequestParam("highLevel") String highLevel) {
        Map map = new HashMap();
        map.put("openId", openId);
        map.put("avatarUrl", imgUrl);
        map.put("nickName", userName);
        map.put("highLevel", highLevel);
        Map<String, Object> ss = userService.userLogin(map);
        logger.info("用户登录接口调用成功");
        return ss;
    }

    //成为VIP
    @RequestMapping("/goVip")
    @PostMapping
    public Map goVip(@RequestParam("openId") String openId,
                     @RequestParam("highLevel") String highLevel,
                     @RequestParam("imgUrl") String imgUrl,
                     @RequestParam("userName") String userName,
                     @RequestParam("roleType") String roleType) {
        logger.info("openId=================================================>>>"+openId);
        logger.info("highLevel=================================================>>>"+highLevel);
        logger.info("imgUrl=================================================>>>"+imgUrl);
        logger.info("userName=================================================>>>"+userName);
        logger.info("roleType=================================================>>>"+roleType);

        Map map = new HashMap();
        map.put("openId", openId);
        map.put("highLevel", highLevel);
        map.put("avatarUrl", imgUrl);
        map.put("nickName", userName);
        map.put("roleType", roleType);
        map.put("changeType", "" + 2);
        Map<String, Object> ss = userService.updateUserRoleType(map);
        logger.info("会员角色更新接口调用成功");
        return ss;
    }

    //成为代理
    @RequestMapping("/chenUsrRol")
    @PostMapping
    public Map goTgVip(@RequestParam("openId") String openId,
                       @RequestParam("highLevel") String highLevel,
                       @RequestParam("imgUrl") String imgUrl,
                       @RequestParam("userName") String userName,
                       @RequestParam("roleType") String roleType) {
        Map map = new HashMap();
        map.put("openId", openId);
        map.put("highLevel", highLevel);
        map.put("avatarUrl", imgUrl);
        map.put("nickName", userName);
        map.put("roleType", roleType);
        map.put("changeType", "" + 3);
        Map<String, Object> ss = userService.updateUserRoleType(map);
        System.out.println(ss.size());
        logger.info("代理角色更新接口调用成功");
        return ss;
    }

    //用户提现接口
    @RequestMapping("/withdraw")
    @PostMapping
    public Map withdraw(@RequestParam("openId") String openId,
                        @RequestParam("imgUrl") String imgUrl, @RequestParam("userName") String userName, @RequestParam("activeType") int activeType) {
        Map objMap = new HashMap();
        objMap.put("code", 200);
        logger.info("用户余额体现接口调用成功");
        return objMap;
    }

    //获取用户钱包信息
    @RequestMapping("/getBalance")
    @PostMapping
    public Map getProceeds(@RequestParam("openId") String openId,
                           @RequestParam("highLevel") String highLevel) {
        Map map = new HashMap();
        map.put("userId", openId);
        map.put("highLevel", highLevel);
        Map userAccountInfo = userService.getUserAccountInfo(map);
        logger.info("获取用户钱包信息接口调用成功");
        Map res = new HashMap();
        res.put("code", 200);
        res.put("message", "获取受益列表成功");
        res.put("error", "");
        res.put("data", userAccountInfo);
        return res;
    }

    //获取用户下级列表，及下下级人数
    @RequestMapping("/getTeam")
    @PostMapping
    public Map getTeam(@RequestParam("openId") String openId) {
        Map map = new HashMap();
        map.put("userId", openId);
        List userAccountInfo = userService.getUserList(map);
        map.put("userList", userAccountInfo);
        map.put("popNum", userService.getUserListCount(openId));
        logger.info("获取用户钱包信息接口调用成功");
        Map res = new HashMap();
        res.put("code", 200);
        res.put("message", "获取受益列表成功");
        res.put("error", "");
        res.put("data", map);
        return res;
    }

    //获取当前用户收益列表
    @RequestMapping("/getIncomeList")
    @PostMapping
    public Map getIncomeList(@RequestParam("openId") String openId) {
        Map map = new HashMap();
        map.put("userId", openId);
        List<Map<String, Object>> userIncomeInfoList = incomeInfoService.getUserIncomeInfoList(map);
        Map userIncomeInfoCount = incomeInfoService.getUserIncomeInfoCount(map);
        map.put("userList", userIncomeInfoList);
        map.put("countMoney", userIncomeInfoCount);
        logger.info("获取用户钱包信息接口调用成功");
        Map res = new HashMap();
        res.put("code", 200);
        res.put("message", "获取受益列表成功");
        res.put("error", "");
        res.put("data", map);

        return res;
    }

    //获取当前用户提现列表
    @RequestMapping("/getDrawLogList")
    @PostMapping
    public Map getDrawLogList(@RequestParam("openId") String openId) {
        List<Map> userDrawLogByUserId = drawMoneyLogService.getUserDrawLogByUserId(openId);
        logger.info("获取用户钱包信息接口调用成功");
        Map res = new HashMap();
        res.put("code", 200);
        res.put("message", "获取受益列表成功");
        res.put("error", "");
        res.put("data", userDrawLogByUserId);
        return res;
    }


    public Map getOpenId(String appid, String secret, String js_code, String grant_type) {

        AUTH_CODE_URL.replace("{APPID}", appid).replace("{SECRET}", secret).replace("{JSCODE}", js_code);
        JSONObject s = JSONObject.fromObject(HttpUtil.doGet(AUTH_CODE_URL));
        s.get("openid");
        s.get("session_key");
        s.get("unionid");
        s.get("errcode");
        s.get("errmsg");
        return s;
    }

    public static void main(String[] args) {

        String s =   "<prepay_id><![CDATA[wx09085637543248e4d209266e1529614800]]></prepay_id>";
        String substring = s.substring(s.indexOf("<prepay_id>"), s.indexOf("</prepay_id>"));
        System.out.println(substring);
        System.out.println(substring.substring(20,56));

    }



    public   String sortMapTools(SortedMap<String, Object> params) {
        String  sign="";
        Set<String> strings = params.keySet();
        ArrayList<String> arr=new ArrayList<>();
        arr.addAll(strings);
        StringBuffer str=new StringBuffer();
        for (int i = 0; i < arr.size(); i++) {

            str.append(arr.get(i)+"="+params.get(arr.get(i))+"&");
        }
        str.append("key="+mch_key);
        String willSignStr = str.toString();
        sign = MD5Utils.StringMD5Encode(willSignStr).toUpperCase();
        System.out.println(willSignStr);
        System.out.println(sign);
        return sign;
    }


}
