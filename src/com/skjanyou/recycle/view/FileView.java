package com.skjanyou.recycle.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JWindow;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import sun.awt.shell.ShellFolder;

import com.skjanyou.recycle.utils.XMLParseUtil;
import com.skjanyou.recycle.view.ui.LeftScrollBarUI;
import com.skjanyou.recycle.view.ui.RightScrollBarUI;
import com.skjanyou.view.view.SkjanyouJFrame;



public class FileView extends SkjanyouJFrame{
	private static final long serialVersionUID = "FileView".hashCode();
	public static int EXIT_NOTHING = 0;
	public static int EXIT_TRAY = 1;
	
	private static  FileView view;
	private CenterListener centerListener = new CenterListener();
	private GridBagLayout gridbag = new GridBagLayout();
	private FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
    private GridBagConstraints c = new GridBagConstraints();
	
	private JPanel left_jp = new JPanel();
	private JScrollPane left_jsp = new JScrollPane(left_jp);

	private static JPanel center_jp = new JPanel();
	private JScrollPane center_jsp = new JScrollPane(center_jp);
	
	private Color bg_color = new Color(251,252,252);
	
	public FileView(BufferedImage image){
		super(image);
		initView();
	}
	public FileView(){
		initView();
	}
	
	private void initView(){
		this.view = this;
		this.setTitleJp(SkjanyouJPanel);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Insets taskSet = Toolkit.getDefaultToolkit().getScreenInsets((new JWindow()).getGraphicsConfiguration());
		int width = (int) screen.getWidth();
		int height = (int) screen.getHeight() - taskSet.bottom;
		super.setSize(width, height);
		this.setBackground(bg_color);
		this.setLayout(gridbag);
		this.add(left_jsp);
		this.add(center_jsp);
		c.fill = GridBagConstraints.BOTH;
		initLeftView();initRightView();
		initLeft();
		initRight();
		initListener();
	}
	
	private void initLeft(){
        c.weighty = 1;
        c.weightx = 1;
        c.gridwidth = 1;
        left_jp.setSize(80, 0);
        left_jp.setPreferredSize(new Dimension(80, 1000));
        gridbag.setConstraints(left_jsp, c);
	}
	
	private void initRight(){
		int width = getWidth();
		int height = getHeight();
		c.weightx = (width - left_jp.getWidth()) / left_jp.getWidth();
		c.gridwidth = 1;
		center_jp.setPreferredSize(new Dimension(width - left_jp.getWidth(), height * 10));
		center_jp.removeAll();
		try {
//			FileButton fb1 = new FileButton(new File("E:\\游戏\\其他\\InnocentSinner4zsb\\hinokakera.exe"));
//			FileButton fb2 = new FileButton(new File("E:\\游戏\\其他\\街机\\WinKawaks.exe"));
//			center_jp.add(fb1);
//			center_jp.add(fb2);
			List<FileButton> list = XMLParseUtil.loadFileButton();
			if(list != null){
				for (FileButton fileButton : list) {
					center_jp.add(fileButton);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		gridbag.setConstraints(center_jsp, c);
	}
	private void initLeftView(){
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
		fl.setHgap(0);
		fl.setVgap(0);
		left_jp.setLayout(fl);
		left_jp.setBackground(bg_color);
        left_jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );    //y轴的滚动条
        left_jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );  //x轴的滚动条
        left_jsp.getVerticalScrollBar().setUI(new LeftScrollBarUI());
        left_jsp.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(127,127,127)));
        left_jp.add(new LeftButton("top1","c:/2.png"));
        left_jp.add(new LeftButton("top1","c:/2.png"));
	}
	private void initRightView(){
		fl.setHgap(5);
		center_jp.setLayout(fl);
		center_jp.setBackground(Color.WHITE);
		center_jsp.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, new Color(127,127,127)));
		center_jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );    //y轴的滚动条
		center_jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );  //x轴的滚动条
		center_jsp.getVerticalScrollBar().setUI(new RightScrollBarUI());
	}
	
	private void initListener(){
		 new DropTarget(center_jp, centerListener);
		 center_jp.addMouseListener(centerListener);
	}
	
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		initLeft();initRight();
	}
	private class CenterListener extends DropTarget implements MouseListener,DropTargetListener{
		private static final long serialVersionUID = 1L;

		@Override
		public void dragEnter(DropTargetDragEvent dtde) {
		}

		@Override
		public void dragOver(DropTargetDragEvent dtde) {
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
		}

		@Override
		public void dragExit(DropTargetEvent dte) {
		}

		@SuppressWarnings("unchecked")
		@Override
		public void drop(DropTargetDropEvent dtde) {
			dtde.acceptDrop(DnDConstants.ACTION_LINK);
			Transferable tr = dtde.getTransferable();
			try {
				List<File> files = (List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);
				for (File file : files) {
					FileButton fb = new FileButton(file);
					center_jp.add(fb);
					System.out.println(file.getName());
					center_jp.validate();
					center_jp.repaint();
				}
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						Component[] cs = center_jp.getComponents();
						List<FileButton> list = new ArrayList<FileButton>();
						for (Component c : cs) {
							if(c instanceof FileButton){
								list.add((FileButton)c);
							}
						}
						try {
							XMLParseUtil.saveFileButton(list);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int md = e.getModifiers();
			switch (md) {
			case InputEvent.BUTTON3_MASK:
				//可以添加一个菜单
				break;
			case InputEvent.BUTTON1_MASK:
				if(e.getClickCount() == 2){
					//双击事件可以给彩蛋
					center_jp.setLayout(fl);
					center_jp.validate();
				}
				break;
			default:
				break;
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	public class LeftButton extends JToggleButton implements Serializable{
		private static final long serialVersionUID = 5142999382637888183L;
		private LeftButton left = this;
		private LeftButton lb = this;
		private Color focusColor = null;
		private Color selectedColor = null;
		private Color blurColor = null;
		private Color borderColor = new Color(230, 236, 238);
		private int width = 80;
		private int height = 80;
		
		private LeftButtonListener listener = new LeftButtonListener();
		public LeftButton(String name,String imgPath){
			super(name);
			this.setIcon(new ImageIcon(imgPath));
			initView();
			initListener();
		}
		private void initView(){
			this.setBackground(blurColor);
			this.setPreferredSize(new Dimension(width, height));
			this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, borderColor));
			this.setFont(new Font("微软雅黑",14,12));
			this.setToolTipText(this.getText());
			this.setFocusPainted(false);
			this.setVerticalTextPosition(SwingConstants.BOTTOM);
			this.setHorizontalTextPosition(SwingConstants.CENTER);
		}
		private void initListener(){
			this.addMouseListener(listener);
			this.addMouseMotionListener(listener);
		}
		
		private class LeftButtonListener implements MouseMotionListener,MouseListener{
			@Override
			public void mouseDragged(MouseEvent e) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Component[] cs = left_jp.getComponents();
				for (Component component : cs) {
					if(component instanceof LeftButton){
						LeftButton l = (LeftButton) component;
						l.setSelected(false);
					}
				}
				left.setSelected(true);
				int md = e.getModifiers();
				switch (md) {
				case InputEvent.BUTTON3_MASK:
					//可以添加一个菜单
					break;
				case InputEvent.BUTTON1_MASK:
					break;
				default:
					break;
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		}
		
	}
	
	
	public static class FileButton extends JButton implements Serializable{
		private static final long serialVersionUID = 6611183874773056543L;
		private FileButton fb = this;
		private MoveListener listener = new MoveListener();
		private int exit_type = 0;               //0无反应，1最小化
		private Point pre_point = new Point();  //用来记录鼠标移动前点击的坐标
		private Point end_point = new Point();  //用来记录鼠标移动后点击的坐标
		private boolean isPressed = true;       //用于事件监听使用，按下鼠标左键时，可以移动
		private boolean isMove = true;        //是否可以移动，当此项为false时，窗体不能移动
		private File file = null;
		
		/**65*75**/
		private int width = 80;
		private int height = 90;
		private String filePath = null;
		private String[] para = null;
		
		public FileButton(File file) throws Exception{
			this(file,EXIT_TRAY,null,null);
		}
		
		public FileButton(File file ,int exit_type,String[] para,Icon icon) throws Exception{
			this.file = file;
			ShellFolder shell = ShellFolder.getShellFolder(file);
			this.setText(file.getName());
			this.filePath = file.getAbsolutePath();
			this.exit_type = exit_type;
			if(icon == null){
				Image img = shell.getIcon(true);
				this.setIcon(new ImageIcon(img));
			}else{
				this.setIcon(icon);
			}
			initView();	
			initListener();
		}
		
		public FileButton(String text, Icon icon) {
			super(text, icon);
			initView();
			initListener();
		}
		
		public void setPara(String[] para){
			this.para = para;
		}
		
		public void initView(){
			setBounds(5, 5, width, height);
			this.setFont(new Font("微软雅黑",14,12));
			this.setToolTipText(this.getText());
			this.setPreferredSize(new Dimension(width, height));
			this.setContentAreaFilled(false);
			this.setFocusPainted(false);
			this.setVerticalTextPosition(SwingConstants.BOTTOM);
			this.setHorizontalTextPosition(SwingConstants.CENTER);
			this.setHorizontalAlignment(SwingConstants.LEFT);
		}
		public void initListener(){
			this.addMouseListener(listener);
			this.addMouseMotionListener(listener);
		}
		
		private class MoveListener  implements MouseMotionListener,MouseListener{

			@Override
			public void mouseDragged(MouseEvent e) {
				if(isPressed && isMove)
				{
					int x = (int)(fb.getX() + e.getX() - pre_point.getX());
					int y = (int) (fb.getY() + e.getY() - pre_point.getY());
					end_point = new Point(x,y);
					fb.setLocation(end_point);
					center_jp.setLayout(null);
					
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int md = e.getModifiers();
				switch (md) {
				case InputEvent.BUTTON3_MASK:
					//可以添加一个菜单
					break;
				case InputEvent.BUTTON1_MASK:
					try {
						if(exit_type == EXIT_NOTHING){
						}else if(exit_type == EXIT_TRAY){
							view.setVisible(false);
						}
						Process process = null;
						String[] cmdarray = new String[]{filePath};
						if(para != null && para.length > 0){
							cmdarray = new String[para.length + 1];
							for(int i = 0;i < para.length; i++){
								cmdarray[i] = para[i];
							}
							cmdarray[para.length] = filePath;
							
						}
						process = Runtime.getRuntime().exec(cmdarray);
					} catch (IOException e1) {
						//有异常则提醒用户添加参数才能运行程序
						e1.printStackTrace();
					} 
					break;
				default:
					break;
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				isPressed = true;
				pre_point = new Point(e.getX(),e.getY());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				isPressed = false;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		}

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}
		
	}


}
