package com.jinchi.constant;

import java.io.File;

/**
 * @author yuxuanjiao
 * @date 2017年7月30日 下午9:01:14 
 * @version 1.0
 */

public class FileConstant {

    public static final String UPLOAD_FOLDER_NAME = "files/";
    public static final File UPLOAD_FOLDER = new File(UPLOAD_FOLDER_NAME);
    public static final String DOWNLOAD_URL = "/api/download?fileName=";
    public static final String DOWNLOAD_URL_ID = "/api/download?fileId=";
    public static final String IMAGE_URL = "/api/image?fileName=";

    static {
        if (!UPLOAD_FOLDER.exists()) {
            UPLOAD_FOLDER.mkdir();
        }
    }
}
