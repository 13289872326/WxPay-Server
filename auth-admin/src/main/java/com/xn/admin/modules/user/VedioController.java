package com.xn.admin.modules.user;

import com.xn.admin.common.utils.HttpUtils;
import com.xn.service.user.UserService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vedio")
public class VedioController {
    Logger logger = LoggerFactory.getLogger(VedioController.class);
    @Autowired
    @Resource
    UserService userService;

    @Value("${vedioAnylysisUrl}")
    public String url = "http://video.resolv.zhanghi.net/api/url";


    @PostMapping("/analysis")
    @Transactional
    public Map vedioAnalysis(@RequestParam("userId") String userId, @RequestParam("vedioUrl") String vedioUrl,
                             @RequestParam("roleType") String roleType, @RequestParam("readCunt") String remainNum) {

        //参数校验
        Map resMap = new HashMap();
        if (userId == null || roleType == null || vedioUrl == null) {
            logger.info("用户消息校验失败，参数错误！！！");
            resMap.put("code", "401");
            resMap.put("message", "无效的参数");
            resMap.put("data", "{}");
            return resMap;
        }
        Map map1 = new HashMap();
        map1.put("userId", userId);
        map1.put("roleType", roleType);
        map1.put("remainNum", remainNum);
        map1.put("url", vedioUrl);
        String reqRst = "";
        //        upType 1.更新用户剩余次数   2.更新用户角色类型   3.更新用户钱包信息
        map1.put("upType", 1);
        if (roleType.equals("1")) {
            logger.info("客户端用户角色为游客，进行次数认证！");
            Map reMap = userService.updateUserInfo(map1);
            int code = (Integer) reMap.get("code");
            if (code == -1) {
                logger.info("认证次数不足需要买服务");
                resMap.put("code", "300");
                resMap.put("message", "解析次数不足，成为VIP永久免费使用！！");
                resMap.put("data", "{}");
                logger.info("客户端用户角色为游客！！！");
                return resMap;
            }
        }
            try {
                map1.clear();
                map1.put("url",vedioUrl);
                reqRst = HttpUtils.doGet(url, map1);
            } catch (Exception e) {
                e.printStackTrace();
                return resMap;
            }
            if (reqRst.contains("videoUrl") && reqRst.contains("imgUrl") && reqRst.contains("title")) {
                JSONObject json = JSONObject.fromObject(reqRst);
                resMap.put("code", "200");
                resMap.put("message", "请求成功");
                resMap.put("data", json);
                JSONObject jsonObject = JSONObject.fromObject(resMap);
                logger.info("获取消息内容==================================>>>" + jsonObject.toString() + "<<<================================");
            } else {
                logger.info("视频解析失败==================================================================");
                resMap.put("code", "405");
                resMap.put("message", "视频地址不可用，解析失败！！");
                resMap.put("data", "{}");
            }

        return resMap;
    }

    public static void main(String[] args) {
        String reqRst = "";
        String url = "http://47.103.143.95/api/url";
        Map map1 = new HashMap();
        map1.put("url", "https://v.douyin.com/vM4F53/");
        try {
            reqRst = HttpUtils.doGet(url, map1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject json = JSONObject.fromObject(reqRst);
        System.out.print("获取消息内容==================================>>>" + json.toString() + "<<<================================");
    }




}
