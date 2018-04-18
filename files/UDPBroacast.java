package com.example.cgodawson.test;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPBroacast implements Runnable{
    private String ip;
    private String path;
    private String installVer;
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
			DatagramSocket detectSocket = new DatagramSocket(12595);
			
			File freeFile = new File("./files");
			File[] files = freeFile.listFiles();
			StringBuilder sb = new StringBuilder();
			for(File file:files)
			{
				
				sb.append(file.getName()+"#");
				
			}
			String freeFiles = sb.toString();
			byte[] freeFilesData = freeFiles.getBytes();
			
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
			}
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
