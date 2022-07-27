package com.shf.srb.core.service;

import com.shf.srb.core.pojo.entity.BorrowerAttach;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.srb.core.pojo.vo.BorrowerAttachVO;

import java.util.List;

/**
 * <p>
 * 借款人上传资源表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface BorrowerAttachService extends IService<BorrowerAttach> {

    /**
     * 根据id获取附件VO列表
     * @param id
     * @return
     */
    List<BorrowerAttachVO> selectBorrowerAttachVOList(Long id);
}
