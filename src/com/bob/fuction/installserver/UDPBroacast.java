package com.bob.fuction.installserver;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DecimalFormat;

import com.bob.iinterface.IThread;
import com.bob.tool.ByteArrayList;
import com.bob.xp.XPMain;

public class UDPBroacast implements Runnable,IThread{
    private String ip;
    private String path;
    private String installVer;
    DatagramSocket detectSocket;
	private boolean flag_close;
    public void setIP(String ip,String path)
    {
    	this.ip = ip;
    	this.path = path;
    	
    }
    public void setVer(String ver)
    {
    	this.installVer = ver;
    }
	@Override
	public void run() {
		
		try {
			 detectSocket = new DatagramSocket(12595);
			
			XPMain.window.appLog("start UDPBroacast");
			int update_file_count = 0;
			byte[] freeFilesData = freefiles();
			while(true)
			{
				System.out.print("");
				if(ip==null)
				{
					continue;
				}
				File file = new File(path);
	            InetAddress hostAddress = InetAddress.getByName(ip);
	            byte[] buf = file.getName().getBytes();
	            ByteArrayList byteArrayList = new ByteArrayList();
	            byteArrayList.add(installVer.getBytes());
	            byteArrayList.add(buf);
	            byteArrayList.add("#".getBytes());
	            byteArrayList.add(freeFilesData);
	          
	            
	            byte[] sendData = byteArrayList.all2Bytes();
	            DatagramPacket out = new DatagramPacket(sendData,sendData.length, hostAddress, 12596);
	            detectSocket.send(out);
	            Thread.sleep(500);
	            update_file_count++;
	            if(update_file_count==20)
	            {
	            	update_file_count = 0;
	            	freeFilesData = freefiles();
	            	
	            }
			}
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   close();
		
	}
	
	private byte[] freefiles() {
		File freeFile = new File("./files");
		File[] files = freeFile.listFiles();
		StringBuilder sb = new StringBuilder();
		for(File file:files)
		{
			double len = file.length()/1.0d/1024/1024;
			DecimalFormat d = new DecimalFormat("0.0000");
			
			sb.append(file.getName()+"["+d.format(len)+"M]#");
			
		}
		String freeFiles = sb.toString();
		byte[] freeFilesData = null;
		try {
			freeFilesData = freeFiles.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return freeFilesData;
	}
	@Override
	public void close() {
		flag_close = true;
		XPMain.window.appLog("close UDPBroacast");
		detectSocket.close();
		XPMain.window.notifyAllEx(10001, "udpbroacast");
	}
	@Override
	public boolean isCloseed() {
		// TODO Auto-generated method stub
		return flag_close;
	}
}
