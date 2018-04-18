package com.bob.fuction.kbevent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.bob.iinterface.IThread;
import com.bob.tool.ByteArrayList;
import com.bob.tool.Hex;
import com.bob.tool.Tool;
import com.bob.xp.XPMain;

public class ConnectApp  implements Runnable,IThread{
	private BufferedWriter buffWriter;
	private int x_center;
	private int y_center;
	private boolean flag_close;
	private String ip;
	private Socket socket;
	private  MFrame frame;
	private MouseCenter mouseCenter;
	final ArrayList<Integer> keys = new ArrayList<Integer>();
	private static int lastx;
	private static int lasty;
	public ConnectApp(String ip)
	{

		this.ip = ip;
	}
	public void run() {
		try
		{
			XPMain.window.appLog("start connectApp "+ip+"  12700");
			 socket = new Socket(ip,12700);
			
			buffWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			XPMain.window.appLog("connect the APP ok!");
			   frame = new MFrame();
				x_center = frame.getZoomx()/2;
				y_center = frame.getZoomy()/2;
			   mouseCenter = new MouseCenter(frame);
			  mouseCenter.start();
			  addKeyListener();
			   sendCenter();
			
			socket.getInputStream().read();
		}catch(Exception e)
		{
			e.printStackTrace();
			XPMain.window.appLog("connect the APP fail!");
		}
		close();
	}
   public void write(String cmd)
   {
	  try{
		  if(buffWriter==null)
		  {
			  return;
		  }
		   buffWriter.write(cmd+"\n");
		   buffWriter.flush();
	  }catch(Exception e)
	  {
		  e.printStackTrace();
	  }
   }
   private void sendCenter()
   {
	   ByteArrayList data = new ByteArrayList();
	   data.add((byte)0x01);
	   data.add(Hex.fromIntB(x_center));
	   data.add(Hex.fromIntB(y_center));
	   write(Hex.toString(data.all2Bytes()));
   }
   private void addKeyListener()
   {
	   frame.get().addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent event) {
				// TODO Auto-generated method stub
				//Tool.log(event.paramString());
				
			}
			
			public void keyReleased(KeyEvent event) {
				// TODO Auto-generated method stub
				//Tool.log(event.paramString());
				
				//frame.setToScreen(event.paramString());
			    //Tool.log(event.paramString());
				ByteArrayList data = new ByteArrayList();
				data.add((byte)0x0A);
				int code = event.getKeyCode()-36;
				if(code==-4)
				{
					code = 62;
				}
				else if(code==-20)
				{
					if(event.getKeyLocation()==KeyEvent.KEY_LOCATION_LEFT)
					{
						code = 59;
					}
					else {
						code = 60;
					}
				}
				else if(code==-19)
				{
					if(event.getKeyLocation()==KeyEvent.KEY_LOCATION_LEFT)
					{
						code = 113;
					}
					else {
						code = 114;
					}
				}
				else if(code==-18)
				{
					if(event.getKeyLocation()==KeyEvent.KEY_LOCATION_LEFT)
					{
						code = 57;
					}
					else {
						code = 58;
					}
				}
				else if(code==-26)
				{
					code = 66;
				}
				else if(code>=12&&code<=21)
				{
					code = code - 5;
				}
				keys.remove((Object)code);
				data.add((byte)code);
				data.add((byte)1);
				write(Hex.toString(data.all2Bytes()));
			}
			
			public void keyPressed(KeyEvent event) {
				// TODO Auto-generated method stub
			
				int code = event.getKeyCode()-36;
				if(code==-4)
				{
					code = 62;
				}
				else if(code==-20)
				{
					if(event.getKeyLocation()==KeyEvent.KEY_LOCATION_LEFT)
					{
						code = 59;
					}
					else {
						code = 60;
					}
				}
				else if(code==-19)
				{
					if(event.getKeyLocation()==KeyEvent.KEY_LOCATION_LEFT)
					{
						code = 113;
					}
					else {
						code = 114;
					}
				}
				else if(code==-18)
				{
					if(event.getKeyLocation()==KeyEvent.KEY_LOCATION_LEFT)
					{
						code = 57;
					}
					else {
						code = 58;
					}
				}
				else if(code==-26)
				{
					code = 66;
				}
				else if(code>=12&&code<=21)
				{
					code = code - 5;
				}
				
				
				if(keys.contains(code))
				{
					return;
				}
				keys.add(code);
				ByteArrayList data = new ByteArrayList();
				data.add((byte)0x0A);
				
				
				data.add((byte)code);
				data.add((byte)0);
				write(Hex.toString(data.all2Bytes()));
			}
		});
		 
		 frame.get().addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent event) {
				// TODO Auto-generated method stub
				//Tool.log(event.paramString());
				
				
				ByteArrayList data = new ByteArrayList();
				data.add((byte)0x0A);
				int code = event.getButton();
				if(code==1)
				{
					code = 24;
				}
				else if(code==3)
				{
					code = 25;
				}
				
				keys.remove((Object)code);
				data.add((byte)code);
				data.add((byte)1);
				write(Hex.toString(data.all2Bytes()));
			}
			
			public void mousePressed(MouseEvent event) {
				// TODO Auto-generated method stub
				//Tool.log(event.paramString());
				int code = event.getButton();
				if(code==1)
				{
					code = 24;
				}
				else if(code==2)
				{
					//System.exit(0);
					close();
				}
				else if(code==3)
				{
					code = 25;
				}
				
				if(keys.contains(code))
				{
					return;
				}
				keys.add(code);
				ByteArrayList data = new ByteArrayList();
				data.add((byte)0x0A);
				
				
				data.add((byte)code);
				data.add((byte)0);
				write(Hex.toString(data.all2Bytes()));
				
			}
			
			public void mouseExited(MouseEvent event) {
				//// TODO Auto-generated method stub
				//Tool.log(event.paramString());
				mouseCenter.setEnable(false);
			}
			
			public void mouseEntered(MouseEvent event) {
				// TODO Auto-generated method stub
				mouseCenter.setEnable(true);
			}
			
			public void mouseClicked(MouseEvent event) {
				// TODO Auto-generated method stub
				//Tool.log(event.paramString());
			}
		});
		 

		 
		 frame.get().addMouseMotionListener(new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent event) {
				// TODO Auto-generated method stub
				//Tool.log(event.paramString());
				mouseCenter.reset();
				int x1 = event.getXOnScreen();
				int y1 = event.getYOnScreen();
				//frame.setToScreen(x1+","+y1);
				if(MouseCenter.waitResetCenter){
					
					//frame.setToScreen("return-----");
					lastx = 0;
					lasty = 0;
					return;
				}
			
				//frame.setToScreen("ok-----:"+x1+","+y1);
				
				/*if((x1<=975&&x1>=940)&&(y1<=560&&y1>=520))
				{
					return;
				}*/
				int x = event.getXOnScreen()-mouseCenter.getBaseX();
				int y = event.getYOnScreen()-mouseCenter.getBaseY();
				
				//Tool.log(x+","+y);
				
				
				
				ByteArrayList data = new ByteArrayList();
				data.add((byte)0x0B);
				data.add(Hex.fromIntB(x-lastx));
				data.add(Hex.fromIntB(y-lasty));
				
					lastx = x;
					lasty = y;
				
				
				write(Hex.toString(data.all2Bytes()));
				
				
				
				
				/*if(x<=0||x>=limitX||y<=0||y>limitY)
				{
					try {
						Robot robot = new Robot();
						robot.mouseMove(frame.getZoomx()/2, frame.getZoomy()/2);
					} catch (AWTException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}*/
				
				
			}
			
			public void mouseDragged(MouseEvent event) {
				// TODO Auto-generated method stub
				//Tool.log(event.paramString());
			}
		});
   }
@Override
public void close() {
	flag_close = true;
	if(mouseCenter!=null)
	mouseCenter.close();
	if(frame!=null)
	frame.dispose();
	try {
		if(socket!=null){
		   socket.close();
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	XPMain.window.notifyAllEx(10001, "connectapp");
}
@Override
public boolean isCloseed() {
	// TODO Auto-generated method stub
	return flag_close;
}
}
