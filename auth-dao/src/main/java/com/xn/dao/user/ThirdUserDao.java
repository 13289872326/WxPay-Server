package com.xn.dao.user;

import com.xn.domain.user.ThirdUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Date 2018/12/25 14:28
 * @Author LHS
 * @ClassName ThirdUserDao
 * @Description 第三方用户实体dao
 */
public interface ThirdUserDao {

    ThirdUserEntity getThirdUserEntityByUserId(@Param("userId") Long userId);

    ThirdUserEntity getThirdUserEntityByThirdUserId(@Param("tUserId") Long userId);

    ThirdUserEntity getThirdUserEntityByThirdAccount(@Param("account") String account, @Param("userId") Long userId, @Param("plateformType") String plateformType);

    void bindThirdUser(Map<String, Object> map);

    List<Map<String, Object>> getAllBindThirdAccount(@Param("userId") Long userId);

    void updateAuthTokenInfo(Map<String, Object> map);

    void updateAuthToken(Map<String, Object> map);

    void updateThirdUseState(Map<String, Object> map);
}
