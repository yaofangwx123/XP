package com.bob.fuction.searching;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.bob.tool.Tool;
import com.bob.xp.XPMain;

public class Searching implements Runnable{
	private String mSourceDir;
	private String mOutDir;
    private String[] mKeywords;
    public Searching(String sourcedir,String outdir,String[] keywords)
    {
    	this.mSourceDir = sourcedir;
    	this.mOutDir = outdir;
    	this.mKeywords = keywords;
    }
	@Override
	public void run() {
		try {
			long time = System.currentTimeMillis();
			List<File> result = new ArrayList<>();
			
			getFile(mSourceDir,result);
			
			int total = result.size();
			XPMain.window.appLog("Total: "+total);
			int index,totalFindCount = 0,fileIndex = 0;
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(mOutDir)));
			for(File file:result)
			{
				fileIndex++;
				XPMain.window.appLog("["+fileIndex+"/"+total+"]  "+file.getPath());
				index = 1;
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
				String line = null;
				while((line=bufferedReader.readLine())!=null)
				{
					for(String key:mKeywords)
					{
						
						if(line.toLowerCase().contains(key.toLowerCase())||line.toUpperCase().contains(key.toUpperCase())||line.contains(key))
						{
							
							bufferedWriter.write("------\nfile: "+file.getPath()+"\nkey: "+key+"\nindex: "+index+"\nline:"+line+"\n");
							totalFindCount++;
						}
					}
					
					index++;
				}
				bufferedReader.close();
			}
			bufferedWriter.close();
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			
			XPMain.window.appLog("----------------------------------------------------------\nfinish "+total+"\nfind "+totalFindCount+"\ntime "+decimalFormat.format((System.currentTimeMillis()-time)/1000f)+" seconds");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}	
 private void getFile(String path,List<File> result)
 {
	
	 File file = new File(path);
	 if(file.isDirectory())
	 {
		 File[] files = file.listFiles();
		 for(File f:files)
		 {
			 if(f.isDirectory())
			 {
				 getFile(f.getPath(),result);
			 }
			 else {
				 result.add(f);
			 }
		 }
		 
	 }
	 else {
		 result.add(new File(path));
	 }
 }

}
