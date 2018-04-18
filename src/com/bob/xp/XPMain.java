package com.bob.xp;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bob.fuction.installserver.FileTranTask;
import com.bob.fuction.installserver.UDBRec;
import com.bob.fuction.installserver.UDPBroacast;
import com.bob.fuction.kbevent.ConnectApp;
import com.bob.fuction.searching.Searching;
import com.bob.iinterface.INormal;
import com.bob.tool.StateControl;
import com.bob.tool.Tool;
import com.bob.view.XPWindow;

public class XPMain{
  public static XPWindow window;
	private static FileTranTask bleotaServer;
	private static FileTranTask updateserver;
	private static  UDPBroacast udpBroacast;
	private static UDBRec udpRec;
	private static HashMap<Integer,List<String>> iniMap;
	private static StateControl mStateControl;
	public static void main(String[] args)
	{
		iniMap = new HashMap<>();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					
					
					
					 window = new XPWindow();
					window.getJFrame().setVisible(true);
					mStateControl = new StateControl();
					window.addNotify(mStateControl);
					mStateControl.start();
					window.addNotify(new INormal() {
						
						@Override
						public void inormalback(int fuctionid, Object result, int... exParams) {
							
							/*InstallServer
							 * 
							 * result:List<String>,�����ı�����ַ���
							 * exParams:
							 *   0����ť����
							 *   >0:��ť����,0��ʼ
							 * 
							 * **/
							if(fuctionid==1)
							{
								
								if(exParams[0]==1)//��ť�ص�
								{
									if(exParams[1]==0)//�������������ť
									{
										if(mStateControl.isEnableThread("filetrantask12594")&&mStateControl.isEnableThread("filetrantask12593")
												&&mStateControl.isEnableThread("udpbroacast")&&mStateControl.isEnableThread("udprec"))
										{
											Tool.dialog("�����Ѿ�������");
											return;
										}
										
										
									    List<String> contents = (List<String>) result;
									    window.appLog("start file server");
								         bleotaServer = new FileTranTask(12594);
								        bleotaServer.setPath(contents.get(2));
								        //Thread thread = new Thread(bleotaServer,"filetrantask12594");
								    
								        
								        //update thread BLEOTAServer bleotaServer = new BLEOTAServer(12594);
								        window.appLog("start update server");
								         updateserver = new FileTranTask(12593);
								        updateserver.setPath(contents.get(1));
								        //Thread updateinstall = new Thread(updateserver,"filetrantask12593");
								        
								        window.appLog("start broast server");
								         udpBroacast = new UDPBroacast();
								        udpBroacast.setVer(contents.get(0));
								        udpBroacast.setIP(contents.get(3), contents.get(2));
								        //Thread thread2 = new Thread(udpBroacast,"udpbroacast");
								      
								        
								         window.appLog("start broast rec");
								         udpRec = new UDBRec();
								        //Thread udpRecThread = new Thread(udpRec,"udprec");
								        
								        
								        
								        
								        mStateControl.putThread(bleotaServer,"filetrantask12594");
								        mStateControl.putThread(updateserver,"filetrantask12593");
								        mStateControl.putThread(udpBroacast,"udpbroacast");
								        mStateControl.putThread(udpRec,"udprec");
									        
									        
									}
									
									else if(exParams[1]==1)//��������¡���ť
									{
										if(!mStateControl.isEnableThread("filetrantask12594")||!mStateControl.isEnableThread("filetrantask12593")
												||!mStateControl.isEnableThread("udpbroacast")||!mStateControl.isEnableThread("udprec"))
										{
											Tool.dialog("����δ������");
											return;
										}
										
										
										List<String> editsStrings = (List<String>) result;
										
										String appVersion = editsStrings.get(0);
										String appPath = editsStrings.get(1);
										String pushPath = editsStrings.get(2);
										String broastIP = editsStrings.get(3);
										
		
										
										udpBroacast.setVer(appVersion);
										bleotaServer.setPath(pushPath);
										updateserver.setPath(appPath);
										udpBroacast.setIP(broastIP, pushPath);
										
										window.appLog("update ok");
										
										
									}
									else if(exParams[1]==2)//��������ء���ť
									{
										if(iniMap.isEmpty())
										{
											Tool.dialog("���ȼ��������ļ���");
											return;
										}
										
										if(!mStateControl.isEnableThread("filetrantask12594")||!mStateControl.isEnableThread("filetrantask12593")
												||!mStateControl.isEnableThread("udpbroacast")||!mStateControl.isEnableThread("udprec"))
										{
											bleotaServer = new FileTranTask(12594);
									        //Thread thread = new Thread(bleotaServer,"filetrantask12594");
									    
									        
									        //update thread BLEOTAServer bleotaServer = new BLEOTAServer(12594);
									        window.appLog("start update server");
									         updateserver = new FileTranTask(12593);
									        //Thread updateinstall = new Thread(updateserver,"filetrantask12593");
									        
									        window.appLog("start broast server");
									         udpBroacast = new UDPBroacast();
									        //Thread thread2 = new Thread(udpBroacast,"udpbroacast");
									      
									        
									         window.appLog("start broast rec");
									         udpRec = new UDBRec();
									        //Thread udpRecThread = new Thread(udpRec,"udprec");
									        
									        
									        
									        
									            mStateControl.putThread(bleotaServer,"filetrantask12594");
										        mStateControl.putThread(updateserver,"filetrantask12593");
										        mStateControl.putThread(udpBroacast,"udpbroacast");
										        mStateControl.putThread(udpRec,"udprec");
										        
									        
										}
										String appVersion = iniMap.get(fuctionid).get(0);
										String appPath = iniMap.get(fuctionid).get(1);
										String pushPath = iniMap.get(fuctionid).get(2);
										String broastIP = iniMap.get(fuctionid).get(3);
										
										window.setEditText(0, appVersion);
										window.setEditText(1, appPath);
										window.setEditText(2, pushPath);
										window.setEditText(3, broastIP);
										
										udpBroacast.setVer(appVersion);
										bleotaServer.setPath(pushPath);
										updateserver.setPath(appPath);
										udpBroacast.setIP(broastIP, pushPath);
										
										
									}
								}
								   
							}
							else if(fuctionid==2)
							{
								
								if(exParams[0]==1)
								{
									if(exParams[1]==0)
									{
										
	
										List<String> editsStrings = (List<String>) result;
										String sourcedir = editsStrings.get(0);
										String keys = editsStrings.get(1);
										String outdir = editsStrings.get(2);
										
										new Thread(new Searching(sourcedir,outdir , keys.split("`", -1))).start();
									}
									else if(exParams[1]==1)
									{
										String sourcedir = iniMap.get(fuctionid).get(0);
										String keys = iniMap.get(fuctionid).get(1);
										String outdir = iniMap.get(fuctionid).get(2);
				
										window.setEditText(0, sourcedir);
										window.setEditText(1, keys);
										window.setEditText(2, outdir);
										
										
										new Thread(new Searching(sourcedir,outdir , keys.split("`", -1))).start();
									}
								}
								
								
								
							}
							else if(fuctionid==3)
							{
								if(exParams[0]==1)
								{
									if(exParams[1]==0)
									{
										
										if(mStateControl.isEnableThread("connectapp"))
										{
											Tool.dialog("�����ѿ�����");
											return;
										}
										
										List<String> editsStrings = (List<String>) result;
										String ip = editsStrings.get(0);
							
										
										ConnectApp connectApp = new ConnectApp(ip);
										mStateControl.putThread(connectApp, "connectapp");
										
										
									}
									else if(exParams[1]==1)
									{
										if(mStateControl.isEnableThread("connectapp"))
										{
											Tool.dialog("�����ѿ�����");
											return;
										}
										if(iniMap.isEmpty())
										{
											Tool.dialog("���ȼ��������ļ���");
											return;
										}
										
										String ip = iniMap.get(fuctionid).get(0);
										window.setEditText(0, ip);

										ConnectApp connectApp = new ConnectApp(ip);
										mStateControl.putThread(connectApp, "connectapp");
									}
								}
							}
							else if(fuctionid==10000)//��������
							{
						
					
									try {
										iniMap.clear();	
										window.appLog("ready to loading the ini file");
					        			FileReader reader = new FileReader("./ini.txt");
					            		BufferedReader bufferedReader = new BufferedReader(reader);
					            		String line;
					            		List<String> data = new ArrayList<>();
					            		int fuction = -1;
					            		while((line=bufferedReader.readLine())!=null)
					            		{
					            			if(line.startsWith("fuction"))
					            			{
					            				int fuction_ = Integer.parseInt(line.substring(line.indexOf(':')+1));
					            				if(fuction==-1)
					            				{
					            					fuction = fuction_;
					            					continue;
					            				}
					            				else{
					            				
					            					List<String> data_ = new ArrayList<>();
					            					data_.addAll(data);
					            					iniMap.put(fuction, data_);
					            					window.setEditBuff(fuction,data_);
					            					data.clear();
					            					fuction = fuction_;
					            				}
					            			}
					            			else{
					            				data.add(line);
					            			}
					            			
					            		}
					            		   bufferedReader.close();
					            		   window.appLog("loading the ini file ok");
					            		
					            		 /*   Thread thread = new Thread(bleotaServer);
									        thread.start();
									        
									        Thread updateinstall = new Thread(updateserver);
									        updateinstall.start();
									        
									        Thread thread2 = new Thread(udpBroacast);
									        thread2.start();
									        
									        Thread udpRecThread = new Thread(udpRec);
									        udpRecThread.start();*/
									        
									        
					            		
					        		}catch(Exception e)
					        		{
					        			e.printStackTrace();
					        		}
									
								
							}
							else if(fuctionid==10002)//��ȡ���
							{
								try{
									FileReader reader = new FileReader("./result.txt");
				            		BufferedReader bufferedReader = new BufferedReader(reader);
				            		String line;
									while((line=bufferedReader.readLine())!=null)
				            		{
				            		
				            		 window.appLog(line);
				            			
				            		}
				            		   bufferedReader.close();
									
								}catch(Exception e)
								{
									e.printStackTrace();
									window.appLog("only read from ./result.txt");
								}
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
	}

	
}
