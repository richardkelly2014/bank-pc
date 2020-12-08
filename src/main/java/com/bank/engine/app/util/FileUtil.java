package com.bank.engine.app.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@UtilityClass
@Slf4j
public class FileUtil {


    /**
     * 文件路径转URL
     *
     * @param path
     * @return
     */
    public static URL createURL(String path) {
        try {

            File file = new File(path);
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            log.error("{}", e);
        } catch (IOException e) {
            log.error("{}", e);
        }
        return null;
    }

}
