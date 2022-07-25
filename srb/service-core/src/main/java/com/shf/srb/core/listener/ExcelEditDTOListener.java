package com.shf.srb.core.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.shf.srb.core.mapper.DictMapper;
import com.shf.srb.core.pojo.dto.ExcelDictDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class ExcelEditDTOListener extends AnalysisEventListener<ExcelDictDTO> {

    private DictMapper dictMapper;

    public ExcelEditDTOListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    //    数据列表
    List<ExcelDictDTO> list = new ArrayList<ExcelDictDTO>();

    // 每5条记录存储一次数据
    private static final int BATCH_COUNT = 5;

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param excelDictDTO
     * @param analysisContext
     */
    @Override
    public void invoke(ExcelDictDTO excelDictDTO, AnalysisContext analysisContext) {
        log.info("解析到一条记录：{}", excelDictDTO);

//        将数据存入数据列表
        list.add(excelDictDTO);
        if (list.size() >= BATCH_COUNT) {
//        调用mapper层的save方法
            saveData();
            list.clear();
        }

    }

    private void saveData() {
        log.info("{} 条数据被存储到数据库....", list.size());

        // 调用mapper层的save方法
        dictMapper.insertBatch(list);

        log.info("{} 条数据被存储到数据库成功!", list.size());
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//        当最后剩余的数据不足BATCH_COUNT，我们最终一次性存储剩余数据
        saveData();

        log.info("所有数据解析完成");
    }
}
