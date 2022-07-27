package com.shf.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.srb.core.pojo.entity.BorrowerAttach;
import com.shf.srb.core.mapper.BorrowerAttachMapper;
import com.shf.srb.core.pojo.vo.BorrowerAttachVO;
import com.shf.srb.core.service.BorrowerAttachService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 借款人上传资源表 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Service
public class BorrowerAttachServiceImpl extends ServiceImpl<BorrowerAttachMapper, BorrowerAttach> implements BorrowerAttachService {

    /**
     * 根据id获取附件VO列表
     * @param id
     * @return
     */
    @Override
    public List<BorrowerAttachVO> selectBorrowerAttachVOList(Long id) {
        QueryWrapper<BorrowerAttach> borrowerAttachQueryWrapper = new QueryWrapper<>();
        borrowerAttachQueryWrapper.eq("borrower_id", id);

        List<BorrowerAttach> borrowerAttaches = baseMapper.selectList(borrowerAttachQueryWrapper);

        ArrayList<BorrowerAttachVO> borrowerAttachVOS = new ArrayList<>();
        borrowerAttaches.forEach(borrowerAttach -> {
            BorrowerAttachVO borrowerAttachVO = new BorrowerAttachVO();
            BeanUtils.copyProperties(borrowerAttach, borrowerAttachVO);
            borrowerAttachVOS.add(borrowerAttachVO);
        });

        return borrowerAttachVOS;
    }
}
