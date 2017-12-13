package com.yudianbank.utils;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
@Component
public class OfficeToPdf {
    @Autowired
    ConverterUtils converterUtils;
    /**
     * 使Office2003-2007全部格式的文档(.doc|.docx|.xls|.xlsx|.ppt|.pptx) 转化为pdf文件<br>
     *
     * @param inputFilePath
     *            源文件路径，如："e:/test.docx"
     * @param outputFilePath
     *            目标文件路径，如："e:/test_docx.pdf"
     * @return
     */
    public  boolean openOfficeToPDF(String inputFilePath, String outputFilePath) {
        return office2pdf(inputFilePath, outputFilePath);
    }



    /**
     * 连接OpenOffice.org 并且启动OpenOffice.org
     *
     * @return
     */
    /*public  OfficeManager getOfficeManager() {
        DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
        // 获取OpenOffice.org 3的安装目录
        String officeHome = openOfficePath;
        config.setOfficeHome(officeHome);
        // 启动OpenOffice的服务
        OfficeManager officeManager = config.buildOfficeManager();
        officeManager.start();
        return officeManager;
    }*/

    /**
     * 转换文件
     *
     * @param inputFile
     * @param outputFilePath_end
     * @param inputFilePath
     * @param outputFilePath
     * @param converter
     */
    public static void converterFile(File inputFile, String outputFilePath_end,
                                     String inputFilePath, String outputFilePath,
                                     OfficeDocumentConverter converter) {
        File outputFile = new File(outputFilePath_end);
        // 假如目标路径不存在,则新建该路径
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        converter.convert(inputFile, outputFile);
    }

    /**
     * 使Office2003-2007全部格式的文档(.doc|.docx|.xls|.xlsx|.ppt|.pptx) 转化为pdf文件
     *
     * @param inputFilePath
     *            源文件路径，如："e:/test.docx"
     * @param outputFilePath
     *            目标文件路径，如："e:/test_docx.pdf"
     * @return
     */
    public  boolean office2pdf(String inputFilePath, String outputFilePath) {
        boolean flag = false;
        OfficeDocumentConverter converter = converterUtils.getDocumentConverter();
        if (null != inputFilePath) {
            File inputFile = new File(inputFilePath);
            // 判断目标文件路径是否为空
            if (null == outputFilePath) {
                // 转换后的文件路径
                String outputFilePath_end = getOutputFilePath(inputFilePath);
                if (inputFile.exists()) {// 找不到源文件, 则返回
                    converterFile(inputFile, outputFilePath_end, inputFilePath,
                            outputFilePath, converter);
                    flag = true;
                }
            } else {
                if (inputFile.exists()) {// 找不到源文件, 则返回
                    converterFile(inputFile, outputFilePath, inputFilePath,
                            outputFilePath, converter);
                    flag = true;
                }
            }
//            officeManager.stop();
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 获取输出文件
     *
     * @param inputFilePath
     * @return
     */
    public static String getOutputFilePath(String inputFilePath) {
        String outputFilePath = inputFilePath.replaceAll("."
                + getPostfix(inputFilePath), ".pdf");
        return outputFilePath;
    }

    /**
     * 获取inputFilePath的后缀名，如："e:/test.pptx"的后缀名为："pptx"
     *
     * @param inputFilePath
     * @return
     */
    public static String getPostfix(String inputFilePath) {
        return inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1);
    }

}
