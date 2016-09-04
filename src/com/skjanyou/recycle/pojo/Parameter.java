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

/**��Ź��ڴ��ڵ�һЩ������**/
public class Parameter implements Serializable {
	private static final long serialVersionUID = "Parameter".hashCode();
	public static RecycleWindow window = null;
	public static ImagePanel imagePanel = null;
	public static InputField inputField = null;
	public static KeyRecordField keyrecordField = null;     //�ѷ�����ʹ��KeySetView����
	public static KeySetView keySetView = null;
	public static OptionDialog optionDialog = null;
	
	
	public static double screen_width = 0;  //��Ļ���
	public static double screen_height = 0; //��Ļ�߶�
	public static int taskHeight = 0;    //�ײ��������߶�
	
	public static int mouse_x = 0;     //��굱ǰx����
	public static int mouse_y = 0;     //��굱ǰy����
	
	public static CombineListener combineListener = null;  //��ϼ�������������꣬���̼���
	public static MyHotkeyListener hotkeyListener = null;  //ȫ���ȼ�������
}
