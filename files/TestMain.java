package com.example.cgodawson.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.rmi.server.ExportException;
import java.util.Scanner;
public class TestMain {
    public static void main(String[] args)
    {
        System.out.println("--hello server--");
        //new SysData().start(
        System.out.println("start file server");
        BLEOTAServer bleotaServer = new BLEOTAServer(12594);
        Thread thread = new Thread(bleotaServer);
        thread.start();
        
        //update thread BLEOTAServer bleotaServer = new BLEOTAServer(12594);
        System.out.println("start update server");
        BLEOTAServer updateserver = new BLEOTAServer(12593);
        Thread updateinstall = new Thread(updateserver);
        updateinstall.start();
        
        UDPBroacast udpBroacast = new UDPBroacast();
        Thread thread2 = new Thread(udpBroacast);
        thread2.start();
        
        InputStream in = System.in;
        Scanner scanner = new Scanner(in);
        System.out.println("if u want to exit,please enter \"exit\",u must to fill the file ini.txt and it is at the current pathroute");
        while(true)
        {
        	
        	String s = scanner.nextLine();
        	if(s.equals("exit"))
        	{
        		scanner.close();
        		System.exit(0);
        	}
        	/*else if(s.startsWith("path:")){
        		int index = s.indexOf(':');
        		s = s.substring(index+1,s.length());
        	   System.out.println("path change:"+s);
        	   bleotaServer.setPath(s);
        	}
        	
        	else if(s.startsWith("ip:")){
        		int index = s.indexOf(':');
        		s = s.substring(index+1,s.length());
        		
        		if(bleotaServer.getPath()!=null) {
	        	   System.out.println("ip change:"+s);
	        	   udpBroacast.setIP(s,bleotaServer.getPath());
        		}
        		else {
        			 System.out.println("first need to set the path");
        		}
        	}
        	else*/
        		
        	else if(s.startsWith("txt"))
        	{
        		try {
        			System.out.println("use the ini file");
        			FileReader reader = new FileReader("./ini.txt");
            		BufferedReader bufferedReader = new BufferedReader(reader);
            		String line;
            		int i = 0;
            		while((line=bufferedReader.readLine())!=null)
            		{
            			if(i==0)
            			{
            				System.out.println("tool version:"+line);
            	            udpBroacast.setVer(line);
                    		
            			}
            			else if(i==1)
            			{
            				updateserver.setPath(line);
            				System.out.println("install update path:"+line);
            			}
            			else if(i==2)
            			{
            			   System.out.println("ini path:"+line);
                    	   bleotaServer.setPath(line);
            			}
            			else if(i==3)
            			{
            				System.out.println("ini ip:"+line);
            	          udpBroacast.setIP(line,bleotaServer.getPath());
                    		
            			}
            	
            			i++;
            			
            		}
            		bufferedReader.close();
        		}catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}
        	else {
        		System.out.println("error command!\n    "+s);
        	}
        	
        }
    }
}
