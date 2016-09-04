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

/**���ô��ڣ�����������**/
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
		this.setTitle("��������");
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
		this.bar.addComponent("��ݼ�����1", new ShortcutKeySetPanel());
		this.bar.addComponent("��ݼ�����2", new JPanel());
		this.bar.addComponent("��ݼ�����3", new JPanel());
		this.bar.addComponent("��ݼ�����4", new JPanel());
		this.bar.addComponent("��ݼ�����5", new JPanel());
		this.bar.addComponent("��ݼ�����6", new JPanel());
		this.bar.addComponent("��ݼ�����7", new JPanel());
		this.bar.addComponent("��ݼ�����8", new JPanel());
		this.bar.addComponent("��ݼ�����9", new JPanel());
		this.bar.addComponent("��ݼ�����10", new JPanel());
		this.bar.addComponent("��ݼ�����11", new JPanel());
		this.bar.addComponent("��ݼ�����12", new JPanel());
		this.bar.addComponent("��ݼ�����13", new JPanel());
		this.bar.addComponent("��ݼ�����14", new JPanel());
		this.bar.addComponent("��ݼ�����15", new JPanel());
		this.bar.addComponent("��ݼ�����16", new JPanel());
		this.bar.addComponent("��ݼ�����17", new JPanel());
		this.bar.addComponent("��ݼ�����18", new JPanel());
		this.bar.addComponent("��ݼ�����19", new JPanel());
		this.bar.addComponent("��ݼ�����20", new JPanel());
		this.bar.addComponent("��ݼ�����21", new JPanel());
		this.bar.setSelectedComponent("��ݼ�����1");
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
