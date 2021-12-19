package com.yc.io;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FileUtil {
    public static void addFile(String path) throws IOException {
        int index = path.lastIndexOf(File.separator);
        File filePath = new File(path.substring(0, index));
        File file = new File(path);
        if (!filePath.exists()) {
            if (filePath.mkdirs()) {
                if (file.createNewFile()) {
                    System.out.println("success");
                }
            }
        }

    }

    public static void deleteFile(String path) {
        File file1 = new File(path);
        if (file1.exists()) {
            if (file1.isDirectory()) {
                File[] files = file1.listFiles();
                for (File file : Objects.requireNonNull(files)) {
                    if (file.isDirectory()) {
                        deleteFile(file.getPath());
                    } else if (file.isFile()) {
                        if (file.delete()) {
                            System.out.println(file);
                        }
                    }
                }
            }
            if (file1.delete()) {
                System.out.println(file1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FileUtil.deleteFile("F:" + File.separator + "hello");
    }
}
