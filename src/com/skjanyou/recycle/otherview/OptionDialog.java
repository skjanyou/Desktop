package com.skjanyou.recycle.otherview;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.pojo.Parameter;
import com.skjanyou.recycle.services.impl.ConfigServiceImpl;
import com.skjanyou.recycle.view.ShortcutKeySetPanel;
import com.skjanyou.utils.SwingUtil;

/**设置窗口，里面添加面板**/
public class OptionDialog extends JDialog{
	private static final long serialVersionUID = "OptionDialog".hashCode();
	private ListBar bar;
	public OptionDialog(Window owner) {
		super(owner);
	}

	public OptionDialog(Frame parent, boolean modal)
	{
		super(parent, modal);
		initComponents();
		setSize(770, 520);
		setResizable(false);
		SwingUtil.setCompontentsCenter(this,(float)Parameter.screen_width,(float)Parameter.screen_height);
		this.setTitle("参数配置");
	}

//	public OptionDialog(Dialog parent, boolean modal)
//	{
//		super(parent, modal);
//		initComponents();
//	}

	public void setSelected(String name)
	{
		this.bar.setSelectedComponent(name);
	}

	public void setVisible(boolean b)
	{
		if (b)
		{
			int count = this.bar.getBarComponentCount();
			for (int i = 0; i < count; i++)
			{
				Component com = this.bar.getBarComponent(i);
				if(com instanceof Initable){
					Initable init = (Initable) com;
					init.init();
				}
			}
		}
		super.setVisible(b);
	}

	private void initComponents()
	{
		this.bar = new ListBar();
		this.bar.addComponent("快捷键设置1", new ShortcutKeySetPanel());
		this.bar.addComponent("快捷键设置2", new JPanel());
		this.bar.addComponent("快捷键设置3", new JPanel());
		this.bar.addComponent("快捷键设置4", new JPanel());
		this.bar.addComponent("快捷键设置5", new JPanel());
		this.bar.addComponent("快捷键设置6", new JPanel());
		this.bar.addComponent("快捷键设置7", new JPanel());
		this.bar.addComponent("快捷键设置8", new JPanel());
		this.bar.addComponent("快捷键设置9", new JPanel());
		this.bar.addComponent("快捷键设置10", new JPanel());
		this.bar.addComponent("快捷键设置11", new JPanel());
		this.bar.addComponent("快捷键设置12", new JPanel());
		this.bar.addComponent("快捷键设置13", new JPanel());
		this.bar.addComponent("快捷键设置14", new JPanel());
		this.bar.addComponent("快捷键设置15", new JPanel());
		this.bar.addComponent("快捷键设置16", new JPanel());
		this.bar.addComponent("快捷键设置17", new JPanel());
		this.bar.addComponent("快捷键设置18", new JPanel());
		this.bar.addComponent("快捷键设置19", new JPanel());
		this.bar.addComponent("快捷键设置20", new JPanel());
		this.bar.addComponent("快捷键设置21", new JPanel());
		this.bar.setSelectedComponent("快捷键设置1");
		add(this.bar);
		pack();
	}
	
	public static void main(String[] args) {
		Config.shortcutKey = new ConfigServiceImpl().loadShortcutKey();
		String clazz = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		OptionDialog od = new OptionDialog(new JFrame(), true);
		od.setLocationRelativeTo(null);
		od.setVisible(true);
	}
}
