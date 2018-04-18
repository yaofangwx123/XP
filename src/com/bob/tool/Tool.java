package com.bob.tool;

import javax.swing.JOptionPane;

import com.bob.xp.XPMain;

public class Tool {
  public static void log(String msg)
  {
	  System.out.println(msg);
  }
  public static void dialog(String msg)
  {
	  JOptionPane.showMessageDialog(XPMain.window.getJFrame(), msg);
  }
  public static void sleep(long time)
  {
	  try {
		Thread.sleep(time);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}
