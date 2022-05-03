package com.yc.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class ServerSocket {
    public static void main(String[] args) throws Exception {
        java.net.ServerSocket serverSocket = new java.net.ServerSocket(9999);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        byte[] bytes = new byte[1024];
        int msgLen;
        StringBuilder sb = new StringBuilder();
         while ((msgLen = dataInputStream.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, msgLen));
        }
        System.out.println("接受请求信息 " + LocalDateTime.now() + "\n" + sb.toString());

        TimeUnit.SECONDS.sleep(1);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.write(("hi world" + socket.getInetAddress()).getBytes(StandardCharsets.UTF_8));
        dataOutputStream.close();

        TimeUnit.SECONDS.sleep(20);
        inputStream.close();

        socket.close();
        serverSocket.close();

    }
}
