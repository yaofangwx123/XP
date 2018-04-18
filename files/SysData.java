package com.example.cgodawson.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by CG_Dawson on 2018/3/6.
 */

public class SysData extends Thread {
    private List<byte[]> appList = new ArrayList<>();
    private List<byte[]> bkList = new ArrayList<>();
    private String rootpath = "D:\\adb\\sysdata-4(A-B)\\";
    @Override
    public void run() {
       try {
           File app_data_file = new File(rootpath+"app_data.txt");
           File bk_data_file = new File(rootpath+"bk_data.txt");
           BufferedReader app_bufferedReader = new BufferedReader(new FileReader(app_data_file));
           BufferedReader bk_bufferedReader = new BufferedReader(new FileReader(bk_data_file));

           BufferedWriter app_bufferedWriter = new BufferedWriter(new FileWriter(rootpath+"app_new.txt"));
           BufferedWriter bk_bufferedWriter = new BufferedWriter(new FileWriter(rootpath+"bk_new.txt"));

           String app_line,bk_line;

           while ((app_line=app_bufferedReader.readLine())!=null)
           {
               if(app_line.startsWith("write"))
               {
                   app_bufferedWriter.write(app_line);
                   app_bufferedWriter.newLine();

               }
           }

           while ((bk_line=bk_bufferedReader.readLine())!=null)
           {
               if(bk_line.startsWith("in")&&!bk_line.contains("in:c 0 c 0 0 0 32 0 "))
               {
                   bk_bufferedWriter.write(bk_line);
                   bk_bufferedWriter.newLine();
               }
           }
           bk_bufferedReader.close();
           app_bufferedReader.close();
           app_bufferedWriter.close();
           bk_bufferedWriter.close();

           //----------------------------------------------------
           BufferedReader app_bufferedReader_ = new BufferedReader(new FileReader(rootpath+"app_new.txt"));
           BufferedReader bk_bufferedReader_ = new BufferedReader(new FileReader(rootpath+"bk_new.txt"));

           while ((app_line=app_bufferedReader_.readLine())!=null)
           {
               if(app_line.startsWith("write"))
               {
                   app_line = app_line.trim();
                   int index1 = app_line.indexOf('>')+2;
                   int index2 = app_line.indexOf('=')-2;
                   String hexData = app_line.substring(index1,index2);
                   byte[] data = Hex.parse(hexData);
                   // System.out.println("app:"+Hex.toString(data));
                   appList.add(data);
               }
           }

           while ((bk_line=bk_bufferedReader_.readLine())!=null)
           {
               if(bk_line.startsWith("in"))
               {
                   bk_line = bk_line.trim();
                   int index1 = bk_line.indexOf(':')+1;
                   int index2 = bk_line.length();
                   String hexData = bk_line.substring(index1,index2);

                   String[] sp = hexData.split(" ",-1);
                   StringBuilder sb = new StringBuilder();
                   for(int i=0;i<sp.length;i++)
                   {
                       if(sp[i].length()==1)
                       {
                           sp[i] = "0" + sp[i];
                       }
                       sb.append(sp[i]);
                   }
                   hexData = sb.toString().trim();
                   byte[] data = Hex.parse(hexData);
                   //System.out.println("bk:"+Hex.toString(data));
                   bkList.add(data);
               }
           }

           bk_bufferedReader_.close();
           app_bufferedReader_.close();

           System.out.println("get the data ok:"+appList.size()+","+bkList.size());


        /**数据比对
         * */
           int size = bkList.size();
           boolean isNeedToOrder = true;
           for(int i=0;i<size;i++)
           {
               if(!Arrays.equals(appList.get(i),bkList.get(i)))
               {
                   System.out.println(i+"   "+Hex.toString(appList.get(i))+"   "+Hex.toString(bkList.get(i)));
                   isNeedToOrder = false;
               }

           }

           if(isNeedToOrder)
           {
               int size2 = appList.size();
               short st = 0;
               for(int i=0;i<size2;i++)
               {
                   byte[] bytes = appList.get(i);
                   int r1 = ((int) bytes[0]) & 0xff;
                   int r0 = ((int) bytes[1]) & 0xff;
                   short s = (short) (r0 << 8 | r1);
                   if(i>0){
                       if(s-st!=1)
                       {
                           System.out.println("---------->app lose:"+i);
                           break;
                       }
                   }
                   st = s;
               }

               size2 = bkList.size();
               st = 0;
               for(int i=0;i<size2;i++)
               {
                   byte[] bytes = bkList.get(i);
                   int r1 = ((int) bytes[0]) & 0xff;
                   int r0 = ((int) bytes[1]) & 0xff;
                   short s = (short) (r0 << 8 | r1);
                   if(i>0){
                       if(s-st!=1)
                       {
                           System.out.println("-------------->bk lose:"+i);
                           break;
                       }
                   }
                   st = s;
               }


           }




       }catch (Exception e)
       {
           e.printStackTrace();
       }
    }
}
/**
 * appFile:8859行
 * bkFile:4428行
 * */