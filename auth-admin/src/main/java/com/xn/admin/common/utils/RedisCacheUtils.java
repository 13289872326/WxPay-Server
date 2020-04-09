package com.xn.admin.common.utils;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author LHS
 * @Description 用户信息缓存操类
 * @Date 2019/1/9 13:44
 **/
@Component("redisCacheUtils")
public class RedisCacheUtils {

    @Autowired
    private RedisTemplate redisTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 缓存登录用户信息
     *
     * @return
     */
    public void insertCacheLoginUserInfo(String key, Map map) {
        String userAuthToken = getUserAuthToken(map);
        if (userAuthToken != null) {
            Object cacheToken = redisTemplate.opsForValue().get(userAuthToken);
            if (cacheToken != null) {
                if (!key.equals(cacheToken)) {
                    expire(cacheToken.toString(), 1L);
                }
            }
            String tokenkey = getUserAuthToken(map);
            Object o = redisTemplate.opsForValue().get(tokenkey);
            if (o != null) {
                String s = o.toString();
                if (s.equals(key)) {
                    expire(s, 1L);
                }
            }
            setMap(key, map);
            redisTemplate.opsForValue().set(tokenkey, key, 4, TimeUnit.HOURS);
        }

    }

    /**
     * 更新登录用户信息
     *
     * @return
     */
    public boolean updateCacheLoginUserInfo(String key, Map map) {
        String userAuthToken = getUserAuthToken(map);
        String cacheToken = redisTemplate.opsForValue().get(userAuthToken).toString();
        if (key.equals(cacheToken)) {
            logger.info("用户缓存信息更新成功");
            addMap(key, map);
            return true;
        }
        return false;
    }


    /**
     * 认证的第三方用户信息
     *
     * @return
     */
    public boolean authCacheToken(String token, Map map) {

        if (map == null) {
            map = mget(token);
        }
        if (map.size() == 0) {
            expire(token, 1L);
            return false;
        }
        String userAuthToken = getUserAuthToken(map);
        Object s = redisTemplate.opsForValue().get(userAuthToken);
        if (token.equals(s) || s == null) {
            return true;
        }
        expire(token, 1L);
        return false;
    }

    /**
     * 缓存过期
     *
     * @return
     */
    public void exprieToken(String token) {
        expire(token, 1L);
    }


    /**
     * 将map写入缓存
     *
     * @param key
     * @param map
     * @param time 失效时间(秒)
     */
    public <T> void setMap(String key, Map<String, T> map, Long time) {

    }

    /**
     * 将map写入缓存
     *
     * @param key
     * @param map
     */
    @SuppressWarnings("unchecked")
    public <T> void setMap(String key, T map) {
        JSONObject obj = JSONObject.fromObject(map);
        redisTemplate.opsForValue().set(key, obj.toString());
    }


    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key
     * @param map
     */
    public <T> void addMap(String key, Map<String, T> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param value 值
     */
    public void addMap(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param obj   对象
     */
    public <T> void addMap(String key, String field, T obj) {
        redisTemplate.opsForHash().put(key, field, obj);
    }

    /**
     * 获取map缓存
     *
     * @param key
     * @return
     */
    public Map<String, Object> mget(String key) {
        Object o = redisTemplate.opsForValue().get(key);
        Map jsonObject = JSONObject.fromObject(o);
        return jsonObject;
    }

    /**
     * 获取map缓存
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> T getMap(String key, Class<T> clazz) {
        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(key);
        Map<String, String> map = boundHashOperations.entries();
        return (T) JSONObject.toBean(JSONObject.fromObject(map), clazz);
    }

    /**
     * 获取map缓存中的某个对象
     *
     * @param key
     * @param field
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapField(String key, String field, Class<T> clazz) {
        return (T) redisTemplate.boundHashOps(key).get(field);
    }

    /**
     * 删除map中的某个对象
     *
     * @param key   map对应的key
     * @param field map中该对象的key
     * @author lh
     * @date 2016年8月10日
     */
    public void delMapField(String key, String... field) {
        BoundHashOperations<String, String, ?> boundHashOperations = redisTemplate.boundHashOps(key);
        boundHashOperations.delete(field);
    }

    /**
     * 指定缓存的失效时间
     *
     * @param key  缓存KEY
     * @param time 失效时间(秒)
     * @author FangJun
     * @date 2016年8月14日
     */
    public void expire(String key, Long time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 添加set
     *
     * @param key
     * @param value
     */
    public void sadd(String key, String... value) {
        redisTemplate.boundSetOps(key).add(value);
    }

    /**
     * 删除set集合中的对象
     *
     * @param key
     * @param value
     */
    public void srem(String key, String... value) {
        redisTemplate.boundSetOps(key).remove(value);
    }

    /**
     * set重命名
     *
     * @param oldkey
     * @param newkey
     */
    public void srename(String oldkey, String newkey) {
        redisTemplate.boundSetOps(oldkey).rename(newkey);
    }


    /**
     * 模糊查询keys
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }


    /**
     * 获取用户缓存的最新token
     */
    public String getUserAuthToken(Map<String, Object> map) {
        if (map.containsKey("userId") && map.containsKey("tUserId") && map.containsKey("plateformType")) {
            String userId = map.get("userId").toString();
            String tUserId = map.get("tUserId").toString();
            String plateformType = map.get("plateformType").toString();
            return "auth:" + userId + ":" + tUserId + ":" + plateformType;
        }
        return null;
    }
}
