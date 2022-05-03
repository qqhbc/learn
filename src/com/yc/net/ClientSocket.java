package com.yc.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class ClientSocket {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost", 9999);
        //socket.setSoTimeout(10);

        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(("hello world" + socket.getLocalAddress()));
        System.out.println("发送完毕。。。 等待响应" + LocalDateTime.now());


        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        byte[] bytes = new byte[1024];
        int msgLen;
        StringBuilder sb = new StringBuilder();
        while ((msgLen = dataInputStream.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, msgLen));
        }
        System.out.println(sb.toString() + "结束时间 " + LocalDateTime.now());
    }
}
