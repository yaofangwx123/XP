package com.bob.fuction.installserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

import com.bob.iinterface.IThread;
import com.bob.xp.XPMain;

public class UDBRec implements Runnable,IThread{
	DatagramSocket responseSocket;
	private boolean flag_close;
	@Override
	public void run() {
		
		XPMain.window.appLog("rec server start ok!");
		try {
			responseSocket = new DatagramSocket(12597);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true)
		{
			try{
				byte[] buf = new byte[1024];
		         final DatagramPacket packet = new DatagramPacket(buf, buf.length);
		          
		         responseSocket.receive(packet);
		         byte[] recData = packet.getData();
		         final String recS = new String(recData,0,packet.getLength(),"utf-8");
		         XPMain.window.appLog("send file "+recS+" to "+packet.getAddress().getHostAddress());
		         
	                //ip:192.168.18.255
	                new Thread(new Runnable() {
	                    @Override
	                    public void run() {

	                      try {
	                    	  final String ip = packet.getAddress().getHostAddress();
	                    	    Socket socket = new Socket(ip,12599);
	                    	    socket.setTcpNoDelay(true);
	        	                final OutputStream outputStream = socket.getOutputStream();
	                          File file = new File("./files/"+recS);
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
	                          socket.close();
	                          XPMain.window.appLog("      send list file ok:"+ip);
	                      }catch (Exception e)
	                      {
	                          e.printStackTrace();
	                          
	                      }

	                    }
	                }).start();
		         
		         
		        
			}catch(Exception e){
				e.printStackTrace();
				close();
				return;
			}
			
		}
		
	}

	@Override
	public void close() {
		flag_close = true;
		XPMain.window.appLog("close UDPRec");
		responseSocket.close();
		XPMain.window.notifyAllEx(10001, "udprec");
		
	}
	@Override
	public boolean isCloseed() {
		// TODO Auto-generated method stub
		return flag_close;
	}

}
