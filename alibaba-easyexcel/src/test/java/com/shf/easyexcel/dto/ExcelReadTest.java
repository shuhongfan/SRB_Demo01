package com.shf.easyexcel.dto;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.shf.easyexcel.listener.ExcelStudentDTOListener;
import org.junit.Test;

import java.io.File;

public class ExcelReadTest {
    /**
     * 最简单的读
     */
    @Test
    public void simpleReadXlsx() {

        String fileName = "C:\\Users\\shf\\Documents\\CODE\\SRB_Demo01\\alibaba-easyexcel\\src\\test\\java\\com\\shf\\easyexcel\\dto\\simpleWrite.xlsx";
        // 这里默认读取第一个sheet
        EasyExcel
                .read(fileName, ExcelStudentDTO.class, new ExcelStudentDTOListener())
                .sheet()
                .doRead();
    }

    @Test
    public void simpleReadXls() {

        String fileName = "d:/excel/simpleWrite.xls";
        EasyExcel
                .read(fileName, ExcelStudentDTO.class, new ExcelStudentDTOListener())
                .excelType(ExcelTypeEnum.XLS)
                .sheet()
                .doRead();
    }
}
