package com.shf.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.srb.core.listener.ExcelEditDTOListener;
import com.shf.srb.core.pojo.dto.ExcelDictDTO;
import com.shf.srb.core.pojo.entity.Dict;
import com.shf.srb.core.mapper.DictMapper;
import com.shf.srb.core.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Service
@Slf4j
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * Excel数据的导入
     *
     * @param inputStream
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(
                        inputStream,
                        ExcelDictDTO.class,
                        new ExcelEditDTOListener(baseMapper)
                )
                .sheet()
                .doRead();

        log.info("Excel导入成功");
    }

    /**
     * Excel数据的导出
     *
     * @return
     */
    @Override
    public List listDictData() {
        List<Dict> dictList = baseMapper.selectList(null);

//        创建ExcelDictDTO对象，将Dict列表转换成ExcelDictDTO对象
        ArrayList<ExcelDictDTO> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            ExcelDictDTO excelDictDTO = new ExcelDictDTO();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }

    /**
     * 根据上级id获取子节点数据列表
     *
     * @param parentId
     * @return
     */
    @Override
    public List<Dict> listByParentId(Long parentId) {
        List<Dict> dictList = null;

        try {
//        首先查询redis中是否存在数据列表
            dictList = (List<Dict>) redisTemplate.opsForValue().get("src:core:dictList" + parentId);
            if (dictList != null) {
                //        如果存在则从redis中直接返回数据列表
                log.info("从redis中获取数据列表");
                return dictList;
            }
        } catch (Exception e) {
            log.error("redis服务器异常：" + ExceptionUtils.getStackTrace(e));
        }

//        如果不存在则查询数据库
        log.info("从数据库中获取数据列表");
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("parent_id", parentId);
        dictList = baseMapper.selectList(dictQueryWrapper);
//        填充hasChildren字段
        dictList.forEach(dict -> {
//            判断当前节点是否有子节点，找到当前dict下级没有子节点
            boolean hasChildren = hasChildren(dict.getId());
            dict.setHasChildren(hasChildren);
        });

        try {
//        将数据存入redis
            redisTemplate.opsForValue().set("src:core:dictList" + parentId, dictList, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("redis服务器异常：" + ExceptionUtils.getStackTrace(e));
        }

//        返回数据列表
        return dictList;
    }

    /**
     * 根据dictCode获取下级节点
     * @param dictCode
     * @return
     */
    @Override
    public List<Dict> findByDictCode(String dictCode) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("dict_code", dictCode);
        Dict dict = baseMapper.selectOne(dictQueryWrapper);
        return listByParentId(dict.getId());
    }

    /**
     * 根据dict_code获取parent_id，根据parent_id和value得到name值
     * @param dictCode
     * @param value
     * @return
     */
    @Override
    public String getNameByParentDictCodeAndValue(String dictCode, Integer value) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("dict_code", dictCode);
        Dict parentDict = baseMapper.selectOne(dictQueryWrapper);

        if (parentDict == null) {
            return "";
        }

        dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper
                .eq("parent_id", parentDict.getId())
                .eq("value", value);

        Dict dict = baseMapper.selectOne(dictQueryWrapper);

        if (dict == null) {
            return "";
        }
        return dict.getName();
    }

    /**
     * 判断当前id所在的节点是否有子节点
     *
     * @param id
     * @return
     */
    private boolean hasChildren(Long id) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(dictQueryWrapper);
        if (count.intValue() > 0) {
            return true;
        }
        return false;
    }
}
