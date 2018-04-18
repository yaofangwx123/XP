package com.bob.fuction.kbevent;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;

import javax.swing.JFrame;


public class MouseCenter extends Thread{
	private volatile int count = 0;
	private int x_center;
	private int y_center;
	private MFrame frame;
	public static boolean waitResetCenter;
	private boolean enable = true;
	private boolean flag = true;
	public MouseCenter(MFrame frame)
	{
		this.x_center = frame.getZoomx()/2;
		this.y_center = frame.getZoomy()/2;
		this.frame = frame;
	}
	public void setEnable(boolean flag)
	{
		enable = flag;
	}
	@Override
	public void run() {
		Robot robot = null;
		long time = 0;
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(flag)
		{
			try {
				Thread.sleep(10);
				count++;
				if(count==10)
				{
					count = 0;
				    if(!enable)
				    {
				    	continue;
				    }
					//frame.setToScreen("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX:"+x_center+","+y_center);
					 //new PlaySound("D:\\projectdata\\sound\\offline.wav").start();
					waitResetCenter = true;
					Thread.sleep(20);
					for(int i=0;i<15;i++)
					robot.mouseMove(frame.getZoomx()/2, frame.getZoomy()/2);
					
					 x_center = MouseInfo.getPointerInfo().getLocation().x;
		             y_center = MouseInfo.getPointerInfo().getLocation().y;
		             time = System.currentTimeMillis();
					while(x_center!=960&&y_center!=540&&(System.currentTimeMillis()-time)<5000);
					waitResetCenter = false;
	              
	                
	               // Tool.log("mouse point update:"+x_center+","+y_center);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void close()
	{
		flag = false;
	}
    public void reset()
    {
    	count = 0;
    }
    public int getBaseX()
    {
    	return x_center;
    }
    public int getBaseY()
    {
    	return y_center;
    }
}
