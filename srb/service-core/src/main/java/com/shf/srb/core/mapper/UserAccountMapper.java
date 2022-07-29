package com.shf.srb.core.mapper;

import com.shf.srb.core.pojo.entity.UserAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * 用户账户 Mapper 接口
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface UserAccountMapper extends BaseMapper<UserAccount> {

    /**
     * 转账
     * @param bindCode
     * @param amount
     * @param freezeAmount
     */
    void updateAccount(@Param("bindCode") String bindCode, @Param("amount") BigDecimal amount, @Param("freezeAmount") BigDecimal freezeAmount);
}
