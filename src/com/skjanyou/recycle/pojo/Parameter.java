package com.skjanyou.recycle.pojo;

import java.io.Serializable;

import com.skjanyou.recycle.listener.CombineListener;
import com.skjanyou.recycle.otherview.OptionDialog;
import com.skjanyou.recycle.listener.MyHotkeyListener;
import com.skjanyou.recycle.view.ImagePanel;
import com.skjanyou.recycle.view.InputField;
import com.skjanyou.recycle.view.KeyRecordField;
import com.skjanyou.recycle.view.KeySetView;
import com.skjanyou.recycle.view.RecycleWindow;

/**存放关于窗口的一些参数等**/
public class Parameter implements Serializable {
	private static final long serialVersionUID = "Parameter".hashCode();
	public static RecycleWindow window = null;
	public static ImagePanel imagePanel = null;
	public static InputField inputField = null;
	public static KeyRecordField keyrecordField = null;     //已废弃，使用KeySetView代替
	public static KeySetView keySetView = null;
	public static OptionDialog optionDialog = null;
	
	
	public static double screen_width = 0;  //屏幕宽度
	public static double screen_height = 0; //屏幕高度
	public static int taskHeight = 0;    //底部任务栏高度
	
	public static int mouse_x = 0;     //鼠标当前x坐标
	public static int mouse_y = 0;     //鼠标当前y坐标
	
	public static CombineListener combineListener = null;  //混合监听器，包括鼠标，键盘监听
	public static MyHotkeyListener hotkeyListener = null;  //全局热键监听器
}
