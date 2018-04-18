package com.bob.tool;

import java.util.HashMap;

import com.bob.iinterface.INormal;
import com.bob.iinterface.IThread;
import com.bob.xp.XPMain;

public final class StateControl extends Thread implements INormal{
	private HashMap<String,IThread> mThreadMap;
	public StateControl()
	{
		mThreadMap = new HashMap<>();
	}
	public boolean isEnableThread(String name)
	{
		return mThreadMap.get(name)!=null;
	}
	public void putThread(IThread task,String taskName)
	{
		
		if(mThreadMap.containsKey(taskName))
		{
			mThreadMap.get(taskName).close();
		}
		
		mThreadMap.put(taskName, task);
		new Thread((Runnable)task).start();
	}
	@Override
	public void run() {
		while(true)
		{
		
			//InstallServer

			XPMain.window.setCheck(1, isEnableThread("filetrantask12594")&&isEnableThread("filetrantask12593")&&isEnableThread("udpbroacast")&&isEnableThread("udprec"));
			
			XPMain.window.setCheck(3,isEnableThread("connectapp"));
			
			
			Tool.sleep(500);
			
		}
		
	}
	@Override
	public void inormalback(int id, Object result, int... exParams) {
		//线程结束回调，result:线程key
		if(id==10001)
		{
			String key = (String) result;
			if(mThreadMap.containsKey(key))
			{
				if(!mThreadMap.get(key).isCloseed()){
				  mThreadMap.get(key).close();
				}
				mThreadMap.remove(key);
			}
			
		}
		
	}

}
