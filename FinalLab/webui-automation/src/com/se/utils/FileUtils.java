package com.se.utils;
import com.se.TestDriver;

import java.io.File;

import static com.se.utils.UtilsSet.waitUntilNotNullOrFalse;

public class FileUtils {
    public static File checkFileExists(String fileName) {
        File file = null;
        try {
            file = new File(buildFilePath(
                    TestDriver.getDownloadDirectory(),
                    fileName
            ));
            waitUntilNotNullOrFalse(file::exists);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
        return file;
    }

    public static void deleteFile(String fileName) {
        try {
            var file = new File(buildFilePath(
                    TestDriver.getDownloadDirectory(),
                    fileName
            ));
            waitUntilNotNullOrFalse(file::exists);
            waitUntilNotNullOrFalse(file::delete);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static String buildFilePath(String... parts) {
        var filePathBuilder = new StringBuilder();

        for (var part : parts) {
            filePathBuilder.append(part);
            if (!part.endsWith(File.separator)) {
                filePathBuilder.append(File.separator);
            }
        }

        return filePathBuilder.toString();
    }

    public static void createDirectoryIfNotExists(String path) {
        var directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
