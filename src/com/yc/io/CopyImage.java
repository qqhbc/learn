package com.yc.io;


import org.junit.Test;

import java.io.*;

public class CopyImage {
    @Test
    public void copyPhoto() throws Exception {
        // try with resource
        try (InputStream input = new BufferedInputStream(new FileInputStream("E:\\image\\hello.jpg"));
             OutputStream output = new BufferedOutputStream(new FileOutputStream("F:\\image\\world.jpg"))) {
            byte[] data = new byte[1024];
            int temp;
            while ((temp = input.read(data)) != -1) {
                output.write(data, 0, temp);
            }
        }

    }
}
