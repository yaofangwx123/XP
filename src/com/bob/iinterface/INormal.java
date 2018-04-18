package com.bob.iinterface;

public interface INormal {
	/*          mean          ID                    object                               param
	 *          按钮点击                 功能索引0~max      所有文本框返回值的List<String>             0:按钮，>0: 按钮索引1~max
	 * 
	 * **/
	void inormalback(int id,Object result,int... exParams);
}
