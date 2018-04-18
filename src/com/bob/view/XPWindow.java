package com.bob.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.bob.iinterface.INormal;

public class XPWindow implements ActionListener{

	private JFrame frame;
	private JTextField mEt1;
	private JTextField mEt2;
	private JTextField mEt3;
	private JTextField mEt4;
	private JLabel mTv1;
	private JLabel mTv2;
	private JLabel mTv3;
	private JPanel mPanel1;
	private JPanel mPanel2;
	private JPanel mPanel3;
	private JPanel mPanel4;
	private JCheckBox mCBoxXPSer;
	private JCheckBox mCBoxInsSer;
	private JCheckBox mCBoxNatSer;
	private JCheckBox mCBoxKBSer;
	private JLabel mTv4;
	private JPanel mPanel_btg_1;
	private JButton mbtg1_1;
	private JButton mbtg1_2;
	private JButton mbtg1_3;
	private JPanel mPanel_btg_2;
	private JButton mbtg2_1;
	private JButton mbtg2_2;
	private JButton mbtg2_3;
	/**
	 * Launch the application.
	 */
	private int zoomx;
	private int zoomy;
	private List<INormal> callbacks;
	private List<String> mEditStrings;
	private JTextArea mTexrArea;
	private int mLogCount;
	private JComboBox mFuctionList;
	private int mFuction;
	private JPanel panel_2;
	private JButton mBtClear;
	private JButton mBtLoadIni;
	private JButton mBtResult;
	private HashMap<Integer,List<String>> mEditStringBuffMap;
    private List<JTextField> mTextFields;
	/**
	 * Create the application.
	 */
	public XPWindow() {
		initialize();
	}
	
	
    public JFrame getJFrame()
    {
    	return frame;
    }
    
	public int getZoomx() {
		return zoomx;
	}
	public int getZoomy() {
		return zoomy;
	}
	public void addNotify(INormal callback)
	{
		callbacks.add(callback);
	}
	public void removeNotify(INormal callback)
	{
		callbacks.remove(callback);
	}
	public void notifyAllEx(int id,Object obj,int... exParams)
	{
		for(INormal callback:callbacks)
		{
			callback.inormalback(id, obj,exParams);
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mTextFields = new ArrayList<>();
		mEditStringBuffMap = new HashMap<>();
		callbacks = new ArrayList();
		mEditStrings = new ArrayList<String>();
		
		frame = new JFrame();
		ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("com/bob/res/ppbicon.png"));
		frame.setIconImage(imageIcon.getImage());
		zoomx = Toolkit.getDefaultToolkit().getScreenSize().width;
		zoomy = Toolkit.getDefaultToolkit().getScreenSize().height;
		frame.setBounds(zoomx/4, zoomy/4, zoomx/2, zoomy/2);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel label2 = new JLabel("\u5C0F\u70AE\u96C6\u6210");
		label2.setFont(new Font("隶书", Font.PLAIN, 25));
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(label2, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u663E\u793A", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(192, 192, 192)));
		panel.setBackground(Color.BLACK);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		panel.add(scrollPane);
		
		mTexrArea = new JTextArea();
		mTexrArea.setText("welcome to XPTool version:2018416^_^");
		mTexrArea.setBorder(null);
		mTexrArea.setMargin(new Insets(0, 0, 0, 0));
		mTexrArea.setEditable(false);
		mTexrArea.setForeground(Color.WHITE);
		mTexrArea.setBackground(Color.BLACK);
		scrollPane.setViewportView(mTexrArea);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u529F\u80FD", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		mFuctionList = new JComboBox();
		mFuctionList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 mFuction = mFuctionList.getSelectedIndex();
				switchFuction();
			}
		});
		
		mFuctionList.setMinimumSize(new Dimension(0, 0));
		mFuctionList.setMaximumSize(new Dimension(3000, 25));
		mFuctionList.setFont(new Font("宋体", Font.PLAIN, 12));
		mFuctionList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		mFuctionList.setBorder(new EmptyBorder(0, 0, 0, 0));
		mFuctionList.setAutoscrolls(true);
		mFuctionList.setAlignmentY(0.0f);
		mFuctionList.setAlignmentX(0.0f);
		mFuctionList.setPreferredSize(new Dimension(0, 0));
		mFuctionList.setModel(new DefaultComboBoxModel(new String[] {"XPServer", "InstallServer", "SearchingKeys", "KBEvent", "NativeServer"}));
		panel_1.add(mFuctionList);
		
		panel_2 = new JPanel();
		panel_2.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		mBtClear = new JButton("\u6E05\u5C4F");
		mBtClear.addActionListener(this);
		panel_2.add(mBtClear);
		
		mBtLoadIni = new JButton("\u52A0\u8F7D");
		mBtLoadIni.addActionListener(this);
		panel_2.add(mBtLoadIni);
		
		mBtResult = new JButton("结果");
		mBtResult.addActionListener(this);
		panel_2.add(mBtResult);
		
		mPanel1 = new JPanel();
		mPanel1.setAlignmentY(Component.TOP_ALIGNMENT);
		mPanel1.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_1.add(mPanel1);
		mPanel1.setLayout(new BoxLayout(mPanel1, BoxLayout.Y_AXIS));
		
		mTv1 = new JLabel("\u6587\u672C\u6846A");
		mPanel1.add(mTv1);
		
		mEt1 = new JTextField();
		mEt1.setMaximumSize(new Dimension(2147483647, 25));
		mEt1.setAlignmentY(Component.TOP_ALIGNMENT);
		mEt1.setAlignmentX(Component.LEFT_ALIGNMENT);
		mPanel1.add(mEt1);
		mEt1.setColumns(10);
		
		mPanel2 = new JPanel();
		panel_1.add(mPanel2);
		mPanel2.setLayout(new BoxLayout(mPanel2, BoxLayout.Y_AXIS));
		
		mTv2 = new JLabel("\u6587\u672C\u6846B");
		mPanel2.add(mTv2);
		
		mEt2 = new JTextField();
		mEt2.setAlignmentX(Component.LEFT_ALIGNMENT);
		mEt2.setMaximumSize(new Dimension(2147483647, 25));
		mPanel2.add(mEt2);
		mEt2.setColumns(10);
		
		mPanel3 = new JPanel();
		panel_1.add(mPanel3);
		mPanel3.setLayout(new BoxLayout(mPanel3, BoxLayout.Y_AXIS));
		
		mTv3 = new JLabel("\u6587\u672C\u6846C");
		mPanel3.add(mTv3);
		
		mEt3 = new JTextField();
		mEt3.setAlignmentX(Component.LEFT_ALIGNMENT);
		mEt3.setMaximumSize(new Dimension(2147483647, 25));
		mPanel3.add(mEt3);
		mEt3.setColumns(10);
		
		mPanel4 = new JPanel();
		panel_1.add(mPanel4);
		mPanel4.setLayout(new BoxLayout(mPanel4, BoxLayout.Y_AXIS));
		
		mTv4 = new JLabel("\u6587\u672C\u6846D");
		mPanel4.add(mTv4);
		
		mEt4 = new JTextField();
		mEt4.setMaximumSize(new Dimension(2147483647, 25));
		mEt4.setAlignmentX(Component.LEFT_ALIGNMENT);
		mPanel4.add(mEt4);
		mEt4.setColumns(10);
		
		mPanel_btg_1 = new JPanel();
		mPanel_btg_1.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_1.add(mPanel_btg_1);
		mPanel_btg_1.setLayout(new BoxLayout(mPanel_btg_1, BoxLayout.X_AXIS));
		
		mbtg1_1 = new JButton("button1");
		mPanel_btg_1.add(mbtg1_1);
		
		mbtg1_2 = new JButton("button2");
		mPanel_btg_1.add(mbtg1_2);
		
		mbtg1_3 = new JButton("button3");
		mPanel_btg_1.add(mbtg1_3);
		
		
		mPanel_btg_2 = new JPanel();
		mPanel_btg_2.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_1.add(mPanel_btg_2);
		mPanel_btg_2.setLayout(new BoxLayout(mPanel_btg_2, BoxLayout.X_AXIS));
		
		mbtg2_1 = new JButton("button1");
		mPanel_btg_2.add(mbtg2_1);
		
		mbtg2_2 = new JButton("button2");
		mPanel_btg_2.add(mbtg2_2);
		
		mbtg2_3 = new JButton("button3");
		mPanel_btg_2.add(mbtg2_3);
		
		
		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		frame.getContentPane().add(panel_6, BorderLayout.SOUTH);
		
		mCBoxXPSer = new JCheckBox("XPServer");
		mCBoxXPSer.setIcon(new ImageIcon(XPWindow.class.getResource("/com/bob/res/circle_error.png")));
		panel_6.add(mCBoxXPSer);
		
		mCBoxInsSer = new JCheckBox("InstallServer");
		mCBoxInsSer.setIcon(new ImageIcon(XPWindow.class.getResource("/com/bob/res/circle_error.png")));
		panel_6.add(mCBoxInsSer);
		
		mCBoxNatSer = new JCheckBox("NativeServer");
		mCBoxNatSer.setIcon(new ImageIcon(XPWindow.class.getResource("/com/bob/res/circle_error.png")));
		panel_6.add(mCBoxNatSer);
		
		
		mCBoxKBSer = new JCheckBox("KBServer");
		mCBoxKBSer.setIcon(new ImageIcon(XPWindow.class.getResource("/com/bob/res/circle_error.png")));
		panel_6.add(mCBoxKBSer);
		
		mFuctionList.setSelectedIndex(1);
		

		
		mbtg1_1.addActionListener(this);
		mbtg1_2.addActionListener(this);
		mbtg1_3.addActionListener(this);
		mbtg2_1.addActionListener(this);
		mbtg2_2.addActionListener(this);
		mbtg2_3.addActionListener(this);
		mTextFields.add(mEt1);
		mTextFields.add(mEt2);
		mTextFields.add(mEt3);
		mTextFields.add(mEt4);
	}
  private void switchFuction()
  {
	  initViews();
	  switch(mFuction)
	  {
		  default:
			  mPanel1.setVisible(false);
			  mPanel2.setVisible(false);
			  mPanel3.setVisible(false);
			  mPanel4.setVisible(false);
			  
			  mPanel_btg_1.setVisible(false);
			  mPanel_btg_2.setVisible(false);
			  break;
		  case 1:
			  mPanel1.setVisible(true);
			  mPanel2.setVisible(true);
			  mPanel3.setVisible(true);
			  mPanel4.setVisible(true);
			  
			  mTv1.setText("APP版本");
			  mTv2.setText("升级APP路径");
			  mTv3.setText("推送文件路径");
			  mTv4.setText("广播地址");
			  
			  mbtg1_1.setText("开启");
			  mbtg1_2.setText("更新");
			  mbtg1_3.setText("读取");
			  
			  mPanel_btg_1.setVisible(true);
			  mPanel_btg_2.setVisible(false);
			  break;  
		  case 2:
			  mPanel1.setVisible(true);
			  mPanel2.setVisible(true);
			  mPanel3.setVisible(true);
			  mPanel4.setVisible(true);
			  
			  mTv1.setText("检索目录");
			  mTv2.setText("关键字(`隔开)");
			  mTv3.setText("输出路径");
			  mTv4.setVisible(false);
			  
			  mbtg1_1.setText("开始");
			  mbtg1_2.setText("读取");
			  mbtg1_3.setVisible(false);
			  mEt4.setVisible(false);
			  mPanel_btg_1.setVisible(true);
			  mPanel_btg_2.setVisible(false);
			  break;  
		  case 3:
			  mPanel1.setVisible(true);
			  mPanel2.setVisible(false);
			  mPanel3.setVisible(false);
			  mPanel4.setVisible(false);
			  
			  mTv1.setText("APP IP地址");
			  mTv2.setVisible(false);
			  mTv3.setVisible(false);
			  mTv4.setVisible(false);
			  
			  mbtg1_1.setText("开始");
			  mbtg1_2.setText("读取");
			  mbtg1_3.setVisible(false);
			  mEt4.setVisible(false);
			  mPanel_btg_1.setVisible(true);
			  mPanel_btg_2.setVisible(false);
			  break;  
			  
	  }
	  
	  Object obj = mEditStringBuffMap.get(mFuction);
	  if(obj!=null)
	  {
		  List<String> contents = (List<String>)obj;
		  for(int i=0;i<contents.size();i++)
		  {
			  JTextField j = mTextFields.get(i);
			  j.setText(contents.get(i));
		  }
	  }
	  else {
		  List<String> contents = new ArrayList<>();
		  for(int i=0;i<mTextFields.size();i++)
		  {
			  JTextField j = mTextFields.get(i);
			  j.setText("");
			  contents.add("");
		  }
		  
		  mEditStringBuffMap.put(mFuction, contents);
	  }
  }
  public void setEditBuff()
  {
	  List<String> contents = new ArrayList<>();
	  for(int i=0;i<mTextFields.size();i++)
	  {
		  JTextField j = mTextFields.get(i);
		  contents.add(j.getText());
	  }
	  mEditStringBuffMap.put(mFuction, contents);
  }
  public void setEditBuff(int fuction,List<String> contents)
  {
	  mEditStringBuffMap.put(fuction, contents);
  }
  private void initViews()
  {
	    mEt1.setVisible(true);
		mEt2.setVisible(true);
		mEt3.setVisible(true);
		mEt4.setVisible(true);
		    mTv1.setVisible(true);
		mTv2.setVisible(true);
		mTv3.setVisible(true);
		mPanel1.setVisible(true);
		mPanel2.setVisible(true);
		mPanel3.setVisible(true);
		mPanel4.setVisible(true);
		mCBoxXPSer.setVisible(true);
		mCBoxInsSer.setVisible(true);
		mCBoxNatSer.setVisible(true);
		mCBoxKBSer.setVisible(true);
		mTv4.setVisible(true);
		mPanel_btg_1.setVisible(true);
		mbtg1_1.setVisible(true);
		mbtg1_2.setVisible(true);
		mbtg1_3.setVisible(true);
		mPanel_btg_2.setVisible(true);
		mbtg2_1.setVisible(true);
		mbtg2_2.setVisible(true);
		mbtg2_3.setVisible(true);
  }
	public void actionPerformed(ActionEvent event) { 
		Object source = event.getSource();
		mEditStrings.clear();
		mEditStrings.add(mEt1.getText());
		mEditStrings.add(mEt2.getText());
		mEditStrings.add(mEt3.getText());
		mEditStrings.add(mEt4.getText());
		
		
		setEditBuff();
		
		if(source==mbtg1_1)
		{
			notifyAllEx(mFuction, mEditStrings,1,0);
		}
		else if(source==mbtg1_2)
		{
			notifyAllEx(mFuction, mEditStrings,1,1);
		}
		else if(source==mbtg1_3)
		{
			notifyAllEx(mFuction, mEditStrings,1,2);
		}
		else if(source==mbtg2_1)
		{
			notifyAllEx(mFuction, mEditStrings,1,3);
		}
		else if(source==mbtg2_2)
		{
			notifyAllEx(mFuction, mEditStrings,1,4);
		}
		else if(source==mbtg2_3)
		{
			notifyAllEx(mFuction, mEditStrings,1,5);
		}
		
		else if(source==mBtClear)//-1
		{
			setLog("");
		}
		else if(source==mBtLoadIni)
		{
			notifyAllEx(10000, null);
		}
		else if(source==mBtResult)
		{
			notifyAllEx(10002, null);
		}
   }
   public void setEditText(int position,String msg)
   {
	   if(position==0)
	   {
		   mEt1.setText(msg);
	   }
	   else if(position==1)
	   {
		   mEt2.setText(msg);
	   }
	   else if(position==2)
	   {
		   mEt3.setText(msg);
	   }
	   else if(position==3)
	   {
		   mEt4.setText(msg);
	   }
   }
   public void setCheck(int position,boolean flag)
   {
	   
	   Icon icon = new ImageIcon(flag?getClass().getClassLoader().getResource("com/bob/res/circle_correct.png"):getClass().getClassLoader().getResource("com/bob/res/circle_error.png"));
	   if(position==0)
	   {
		  mCBoxXPSer.setSelected(flag);
		  mCBoxXPSer.setIcon(icon);
	   }
	   else if(position==1)
	   {
		   mCBoxInsSer.setSelected(flag);
		   mCBoxInsSer.setIcon(icon);
	   }
	   else if(position==2)
	   {
		   mCBoxNatSer.setSelected(flag);
		   mCBoxNatSer.setIcon(icon);
	   }
	   else if(position==3)
	   {
		   mCBoxKBSer.setSelected(flag);
		   mCBoxKBSer.setIcon(icon);
	   }
   }
   public void appLog(String msg)
   {
	   mLogCount++;
	   mTexrArea.append(msg+"\n");
   }
   public void setLog(String msg)
   {
	   mLogCount = 0;
	   mTexrArea.setText(msg+"\n");
   }
   public int getLogCount()
   {
	   return mLogCount;
   }
}
