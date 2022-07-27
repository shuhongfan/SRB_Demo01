package com.shf.srb.core.service;

import com.shf.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface DictService extends IService<Dict> {

    /**
     * Excel数据的导入
     * @param inputStream
     */
    void importData(InputStream inputStream);

    /**
     * Excel数据的导出
     * @return
     */
    List listDictData();

    /**
     * 根据上级id获取子节点数据列表
     * @param parentId
     * @return
     */
    List<Dict> listByParentId(Long parentId);

    /**
     * 根据dictCode获取下级节点
     * @param dictCode
     * @return
     */
    List<Dict> findByDictCode(String dictCode);

    /**
     * 根据dict_code获取parent_id，根据parent_id和value得到name值
     * @param dictCode
     * @param value
     * @return
     */
    String getNameByParentDictCodeAndValue(String dictCode, Integer value);
}
