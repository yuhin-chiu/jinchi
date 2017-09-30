package com.jinchi.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.jinchi.constant.FileConstant;
import com.jinchi.enums.ApiResponseEnum;
import com.jinchi.model.ApiResponse;

/**
 * @author yuxuanjiao
 * @date 2017年7月30日 下午9:02:29
 * @version 1.0
 */

public class FileUtil {
    
    /**
     * 如果没传入fileName参数，就生成随机值（不包含前面的斜杠）
     * 
     * @param file
     * @return
     */
    public static ApiResponse uploadFile(MultipartFile file) {
        return FileUtil.uploadFile(file, FileUtil.randomName(file.getOriginalFilename()), FileUtil.class);
    }

    /**
     * 如果传入了fileName参数，就按参数保存（不包含前面的斜杠）
     * 
     * @param file
     * @param fileName
     * @return
     */
    public static ApiResponse uploadFile(MultipartFile file, String fileName, Class clzss) {
        Logger LOGGER = LoggerFactory.getLogger(clzss);
        ApiResponse resp = new ApiResponse();
        BufferedOutputStream stream = null;
        try {
            byte[] bytes = file.getBytes();
            // 解决中文问题，linux下中文路径，图片显示问题
            stream = new BufferedOutputStream(
                    new FileOutputStream(FileUtil.createFileSafe(FileConstant.UPLOAD_FOLDER, fileName)));
            stream.write(bytes);
        } catch (Exception e) {
            resp.setCode(ApiResponseEnum.FILE_SAVE_FAILED.getCode());
            resp.setMsg(ApiResponseEnum.FILE_SAVE_FAILED.getContent());
            LOGGER.error("{}", "[uploadFile]Fail: FileName is " + fileName);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                resp.setCode(ApiResponseEnum.INTERNAL_ERROR.getCode());
                e.printStackTrace();
            }
        }
        LOGGER.info("{}", "[uploadFile]Success: FileName is " + fileName);
        resp.setData(fileName);
        return resp;
    }

    public static void downloadFile(String fileName, BufferedOutputStream out, Class clzss) throws IOException {

        File file = new File(FileConstant.UPLOAD_FOLDER, fileName);
        if (file.exists()) {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }

            out.flush();
            in.close();
        } else {
            Logger logger = LoggerFactory.getLogger(clzss);
            logger.error("[File download error]: File " + fileName + " does not exist!");
            out.flush();
        }
    }
    
    /**
     * 生成一个相同后缀的随机名字
     * 
     * @param fileName
     * @return
     */
    public static String randomName(String fileName) {
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + suffixName;
        return fileName;
    }
    private static File createFileSafe(File file, String fileName) {
        File rFile = new File(file, fileName);

        if (!rFile.getParentFile().exists()) {
            rFile.getParentFile().mkdirs();
        }

        return rFile;
    }
    
    
}
