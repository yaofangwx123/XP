package com.bob.fuction.kbevent;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MFrame  extends JFrame{
	private JTextArea textArea;
	private int count = 0;
	private int zoomx;
	private int zoomy;
	public MFrame()
	{
		//setSize(Toolkit.getDefaultToolkit().getScreenSize()); 
		zoomx = Toolkit.getDefaultToolkit().getScreenSize().width;
		zoomy = Toolkit.getDefaultToolkit().getScreenSize().height;
		setSize(zoomx, zoomy);
		//setSize(10000, 10000);
		setLocation(0,0); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true); 
		setVisible(true);
		setLayout(null);
		textArea = new JTextArea();
		textArea.setBounds(0, 0, zoomx, zoomy);
		textArea.setRows(10000);
		textArea.setEditable(false);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE); 
		add(textArea);
		setToScreen("screen:"+zoomx+"*"+zoomy);
		
		try {
			Robot robot = new Robot();
			robot.mouseMove(zoomx/2, zoomy/2);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addWindowStateListener(new WindowStateListener() {
			
			public void windowStateChanged(WindowEvent arg0) {
				// TODO Auto-generated method stub
				//Tool.log("wind:"+arg0.paramString());
			}
		});
	}
	public int getZoomx()
	{
		return zoomx;
	}
	public int getZoomy()
	{
		return zoomy;
	}
	public void setToScreen(String text)
	{
		if(count++>50)
		{
			textArea.setText("");
			count = 0;
		}
		textArea.append(text+"\n");
	}
	public JTextArea get()
	{
		return textArea;
	}
}
