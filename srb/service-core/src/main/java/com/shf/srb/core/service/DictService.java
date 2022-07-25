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
}
