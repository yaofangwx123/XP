package com.example.cgodawson.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by CG_Dawson on 2018/3/14.
 */

public class BLEOTAServer implements Runnable{
   private ServerSocket serverSocket;
   private int port;
   private String path;
   public BLEOTAServer(int port)
   {
       this.port = port;
   }
   public void setPath(String path)
   {
	   this.path = path;
   }
   public String getPath()
   {
	   return path;
   }
 
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while(true)
            {
                Socket socket = serverSocket.accept();
                System.out.print("");
                final String ip = socket.getInetAddress().getHostAddress();
                System.out.println(getCurTime()+"\n      "+ip);
                final OutputStream outputStream = socket.getOutputStream();
                //ip:192.168.18.255
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                      try {
                          File file = new File(path);
                          FileInputStream fileInputStream = new FileInputStream(file);
                          byte[] buff = new byte[4096];
                          int len = 0;
                          while((len=fileInputStream.read(buff))>0)
                          {
                              outputStream.write(buff,0,len);
                              outputStream.flush();
                          }
                          outputStream.close();
                          fileInputStream.close();
                          System.out.println("      send ok:"+ip);
                      }catch (Exception e)
                      {
                          e.printStackTrace();
                          try {
                        	  if(outputStream!=null)
							     outputStream.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                      }

                    }
                }).start();

            }

        }catch (Exception e)
        {
         e.printStackTrace();
        }
    }
    public static String getCurTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }


}
