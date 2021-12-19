package com.yc.util;

public class ByteUtils {
    public static int byteToInt(byte[] b, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = start; i < end; i++) {
            int n = b[i] & 0xff;
            n <<= (--len) * 8;
            sum += n;
        }
        return sum;
    }

    // 3127
    public static byte[] intToByte(int value, int len) {
        byte[] b = new byte[len];
//        int offset = len;
//        for(int i=0;i<len;i++){
//            b[i] = (byte) (value >> (--offset) * 8);
//        }
        // 优先级
        for (int i = len; i > 0; i--) {
            b[i - 1] = (byte) (value >> (len - i) * 8);
        }
        return b;
    }

    public static String byteToString(byte[] b, int start, int len) {
        return new String(b, start, len);
    }

    public static byte[] stringToByte(String str) {
        return str.getBytes();
    }

    public static byte[] replaceByte(byte[] srcByte, int start, int len, byte[] targetByte) {
        byte[] newByte = new byte[srcByte.length + targetByte.length - len];
        System.arraycopy(srcByte, 0, newByte, 0, start);
        System.arraycopy(targetByte, 0, newByte, start, targetByte.length);
        System.arraycopy(srcByte, start + len, newByte, start + targetByte.length, srcByte.length - start - len);
        return newByte;
    }

    public static void main(String[] args) {
        byte[] srcByte = new byte[]{12, -1, 55, 66, 77, 88};
        byte[] targetByte = new byte[]{22, 44, 23};
        int i = byteToInt(srcByte, 0, 2);
        byte[] bytes = intToByte(3105, 4);
        System.out.println(12 | 0x100);
    }
}
