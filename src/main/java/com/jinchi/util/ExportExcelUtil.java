package com.jinchi.util;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportExcelUtil<T> {

    String fileName;
    List<T> listContent = new ArrayList<>();
    Map<String,String> fieldAndColumn = new HashMap<>();
    SimpleDateFormat dateFormat ;

    public ExportExcelUtil() {
    }

    /********************
     * 暂时只支持Http输出流
     */
    public void exportExcel(HttpServletResponse response) {

        if(getListContent().size() == 0) {
            return ;
        }
        // 以下开始输出到EXCEL
        try {
            // 定义输出流，以便打开保存对话框______________________begin
            OutputStream os = response.getOutputStream();// 取得输出流
            response.reset();// 清空输出流
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(getFileName().getBytes("utf-8"), "ISO8859-1"));
            // 设定输出文件头
            response.setContentType("application/msexcel");// 定义输出类型
            // 定义输出流，以便打开保存对话框_______________________end

            /** **********创建工作簿************ */
            WritableWorkbook workbook = Workbook.createWorkbook(os);

            /** **********创建工作表************ */

            WritableSheet sheet = workbook.createSheet("Sheet1", 0);

            /** **********设置纵横打印（默认为纵打）、打印纸***************** */
            jxl.SheetSettings sheetset = sheet.getSettings();
            sheetset.setProtected(false);

            /** ************设置单元格字体************** */
            WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
            WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

            /** ************以下设置三种单元格样式，灵活备用************ */
            // 用于标题居中
            WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
            wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
            wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
            wcf_center.setWrap(false); // 文字是否换行

            // 用于正文居左
            WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
            wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
            wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
            wcf_left.setWrap(false); // 文字是否换行

            /** ***************以下是EXCEL开头大标题，暂时省略********************* */
            // sheet.mergeCells(0, 0, colWidth, 0);
            // sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
            
            
            /** ***************以下是EXCEL数据********************* */
            Stack<Class<?>> stack = new Stack<>();
            {
                Class<?>cl = getListContent().get(0).getClass();
                while(cl!=null&&cl!=Object.class) {
                    stack.push(cl);
                    cl=cl.getSuperclass();
                }
            }
            int columnNum = 0;
            while(!stack.isEmpty()) {
                Class<?>cl = stack.pop();
                for(Field v : cl.getDeclaredFields()) {
//                    ExportTable ex = v.getAnnotation(ExportTable.class);
//                    String columnName = getFieldAndColumn().get(v.getName());
//                    if(ex == null && columnName==null) continue;
//                    if(columnName == null) columnName = ex.columnName();
//                    v.setAccessible(true);
//                    sheet.addCell(new Label(columnNum, 0, columnName , wcf_center));
//                    int rowNum = 1;
//                    for(T t : getListContent()) {
//                        sheet.addCell(new Label(columnNum, rowNum, ObjToString(v.get(t)), wcf_left));
//                        rowNum++;
//                    }
//                    columnNum++;
                }
            }
            /** **********将以上缓存中的内容写到EXCEL文件中******** */
            workbook.write();
            /** *********关闭文件************* */
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        // 默认文件名是时间
        if (fileName == null || fileName.equals("")) {
            fileName = new Date().getTime() + ".xls";
        }
        return fileName;
    }

    public void setFileName(String fileName) {
        if (fileName != null && !fileName.endsWith(".xls")) {
            fileName += ".xls";
        }
        this.fileName = fileName;
    }
    
    public void addListContentEach(T t) {
        getListContent().add(t);
    }
    public void addFieldAndColumnEach(String fieldName, String columnName) {
        if(columnName == null) {
            columnName = fieldName;
        }
        getFieldAndColumn().put(fieldName, columnName);
    }

    public void reSetFieldAndColumn() {
        getFieldAndColumn().clear();
    }
    // 注解解析
//    public void processAnnotations(Class<?> cl) {
//        try {
//            Map<Integer, List<String[]>> temp = new TreeMap<>();
//            if(cl.getSuperclass() != Object.class) {
//                processAnnotations(cl.getSuperclass());
//            }
//            for (Field v : cl.getDeclaredFields()) {
//                ExportTable ex = v.getAnnotation(ExportTable.class);
//                if (ex != null) {
//                    String[] fieldAndCname = new String[] { v.getName(), ex.columnName()};
//                    if (temp.get(ex.sort()) == null) {
//                        temp.put(ex.sort(), new LinkedList<>());
//                    }
//                    temp.get(ex.sort()).add(fieldAndCname);
//                }
//            }
//            for (Integer sort : temp.keySet()) {
//                for (String[] fieldAndCname : temp.get(sort)) {
//                    addFieldAndColumnEach(fieldAndCname[0], fieldAndCname[1]);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public Map<String, String> getFieldAndColumn() {
        return fieldAndColumn;
    }

    public void setFieldAndColumn(Map<String, String> fieldAndColumn) {
        this.fieldAndColumn = fieldAndColumn;
    }
    public List<T> getListContent() {
        return listContent;
    }

    public void setListContent(List<T> listContent) {
        this.listContent = listContent;
    }

    public SimpleDateFormat getDateFormat() {
        if(dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    String ObjToString(Object obj) {
        if(obj == null) {
            return "";
        }
        if(obj instanceof java.util.Date) return getDateFormat().format(obj);
        return obj.toString();
    }

}