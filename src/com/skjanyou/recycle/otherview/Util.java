package com.skjanyou.recycle.otherview;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;


public final class Util
{


	public static Color[] getColors(Color c1, Color c2, Color c3, int count)
	{
		Color[] cs = new Color[count];
		int half = count / 2;
		float addR = (c2.getRed() - c1.getRed()) * 1.0F / half;
		float addG = (c2.getGreen() - c1.getGreen()) * 1.0F / half;
		float addB = (c2.getBlue() - c1.getBlue()) * 1.0F / half;

		int r = c1.getRed();
		int g = c1.getGreen();
		int b = c1.getBlue();
		for (int i = 0; i < half; i++) {
			cs[i] = new Color((int)(r + i * addR), (int)(g + i * addG), (int)(b + i * addB));
		}
		addR = (c3.getRed() - c2.getRed()) * 1.0F / half;
		addG = (c3.getGreen() - c2.getGreen()) * 1.0F / half;
		addB = (c3.getBlue() - c2.getBlue()) * 1.0F / half;
		r = c2.getRed();
		g = c2.getGreen();
		b = c2.getBlue();
		for (int i = half; i < count; i++) {
			cs[i] = new Color((int)(r + (i - half) * addR), (int)(g + (i - half) * addG), (int)(b + (i - half) * addB));
		}
		return cs;
	}

	public static ImageIcon createColorIcon(Color c, int width, int height)
	{
		BufferedImage bi = createImage(c, width, height);
		return new ImageIcon(bi);
	}

	public static BufferedImage createImage(Color c, int width, int height)
	{
		BufferedImage bi = new BufferedImage(width, height, 1);
		Graphics2D g = bi.createGraphics();
		g.setColor(c);
		g.fillRect(0, 0, width, height);
		g.setColor(new Color(128, 128, 128));
		g.drawRect(0, 0, width - 1, height - 1);
		g.setColor(new Color(236, 233, 216));
		g.drawRect(1, 1, width - 3, height - 3);
		return bi;
	}


	public static byte[] getBytesFromInt(int i)
	{
		byte[] data = new byte[4];
		data[0] = ((byte)(i & 0xFF));
		data[1] = ((byte)(i >> 8 & 0xFF));
		data[2] = ((byte)(i >> 16 & 0xFF));
		data[3] = ((byte)(i >> 24 & 0xFF));
		return data;
	}


	public static String convertString(String source, String encoding)
	{
		try
		{
			byte[] data = source.getBytes("ISO8859-1");
			return new String(data, encoding);
		}
		catch (UnsupportedEncodingException ex) {}
		return source;
	}

	public static String convertString(String source, String sourceEnc, String distEnc)
	{
		try
		{
			byte[] data = source.getBytes(sourceEnc);
			return new String(data, distEnc);
		}
		catch (UnsupportedEncodingException ex) {}
		return source;
	}

	public static int getInt(byte[] data)
	{
		if (data.length != 4) {
			throw new IllegalArgumentException("数组长度非法,要长度为4!");
		}
		return data[0] & 0xFF | (data[1] & 0xFF) << 8 | (data[2] & 0xFF) << 16 | (data[3] & 0xFF) << 24;
	}

	public static long getLong(byte[] data)
	{
		if (data.length != 8) {
			throw new IllegalArgumentException("数组长度非法,要长度为4!");
		}
		return data[0] & 0xFF | (data[1] & 0xFF) << 8 | (data[2] & 0xFF) << 16 | (data[3] & 0xFF) << 24 | (data[4] & 0xFF) << 32 | (data[5] & 0xFF) << 40 | (data[6] & 0xFF) << 48 | (data[7] & 0xFF) << 56;
	}


	public static String getExtName(String path)
	{
		return path.substring(path.lastIndexOf(".") + 1);
	}

	public static int getDistance(Rectangle rec1, Rectangle rec2)
	{
		if (rec1.intersects(rec2)) {
			return 2147483647;
		}
		int x1 = (int)rec1.getCenterX();
		int y1 = (int)rec1.getCenterY();
		int x2 = (int)rec2.getCenterX();
		int y2 = (int)rec2.getCenterY();
		int dis1 = Math.abs(x1 - x2) - rec1.width / 2 - rec2.width / 2;
		int dis2 = Math.abs(y1 - y2) - rec1.height / 2 - rec2.height / 2;
		return Math.max(dis1, dis2) - 1;
	}


	public static JButton createJButton(String name, String cmd, ActionListener listener)
	{
		Image[] icons = getImages(name, 3);
		JButton jb = new JButton();
		jb.setBorderPainted(false);
		jb.setFocusPainted(false);
		jb.setContentAreaFilled(false);
		jb.setDoubleBuffered(true);
		jb.setIcon(new ImageIcon(icons[0]));
		jb.setRolloverIcon(new ImageIcon(icons[1]));
		jb.setPressedIcon(new ImageIcon(icons[2]));
		jb.setOpaque(false);
		jb.setFocusable(false);
		jb.setActionCommand(cmd);
		jb.setCursor(Cursor.getPredefinedCursor(12));
		jb.addActionListener(listener);
		return jb;
	}

	public static JToggleButton createJToggleButton(String name, String cmd, ActionListener listener, boolean selected)
	{
		Image[] icons = getImages(name, 3);
		JToggleButton jt = new JToggleButton();
		jt.setBorder(null);
		jt.setContentAreaFilled(false);
		jt.setFocusPainted(false);
		jt.setDoubleBuffered(true);
		jt.setIcon(new ImageIcon(icons[0]));
		jt.setRolloverIcon(new ImageIcon(icons[1]));
		jt.setSelectedIcon(new ImageIcon(icons[2]));
		jt.setOpaque(false);
		jt.setFocusable(false);
		jt.setActionCommand(cmd);
		jt.setSelected(selected);
		jt.setCursor(Cursor.getPredefinedCursor(12));
		jt.addActionListener(listener);
		return jt;
	}

	public static Image[] getImages(String who, int count)
	{
		Image[] imgs = new Image[3];
		MediaTracker mt = new MediaTracker(null);   //不清楚使用null可不可以
		Toolkit tk = Toolkit.getDefaultToolkit();
		for (int i = 1; i <= count; i++)
		{
			URL url = Util.class.getResource("/com/hadeslee/yoyoplayer/pic/" + who + i + ".png");
			imgs[(i - 1)] = tk.createImage(url);
			mt.addImage(imgs[(i - 1)], i);
		}
		try
		{
			mt.waitForAll();
		}
		catch (Exception exe)
		{
			exe.printStackTrace();
		}
		return imgs;
	}

	public static Image getImage(String name)
	{
		URL url = Util.class.getResource("/com/hadeslee/yoyoplayer/pic/" + name);
		Image im = Toolkit.getDefaultToolkit().createImage(url);
		try
		{
			MediaTracker mt = new MediaTracker(null);
			mt.addImage(im, 0);
			mt.waitForAll();
		}
		catch (Exception exe)
		{
			exe.printStackTrace();
		}
		return im;
	}

	public static Color getGradientColor(Color c1, Color c2, float f)
	{
		int deltaR = c2.getRed() - c1.getRed();
		int deltaG = c2.getGreen() - c1.getGreen();
		int deltaB = c2.getBlue() - c1.getBlue();
		int r1 = (int)(c1.getRed() + f * deltaR);
		int g1 = (int)(c1.getGreen() + f * deltaG);
		int b1 = (int)(c1.getBlue() + f * deltaB);
		Color c = new Color(r1, g1, b1);
		return c;
	}

	public static Color getColor(Color c1, Color c2)
	{
		int r = (c2.getRed() + c1.getRed()) / 2;
		int g = (c2.getGreen() + c1.getGreen()) / 2;
		int b = (c2.getBlue() + c1.getBlue()) / 2;
		return new Color(r, g, b);
	}

	public static int getStringHeight(String s, Graphics g)
	{
		return (int)g.getFontMetrics().getStringBounds(s, g).getHeight();
	}

	public static int getStringWidth(String s, Graphics g)
	{
		return (int)g.getFontMetrics().getStringBounds(s, g).getWidth();
	}

	public static void drawString(Graphics g, String s, int x, int y)
	{
		FontMetrics fm = g.getFontMetrics();
		int asc = fm.getAscent();
		g.drawString(s, x, y + asc);
	}

	public static void drawStringCenter(Graphics g, String s, int x, int y)
	{
		FontMetrics fm = g.getFontMetrics();
		int asc = fm.getAscent();
		int width = getStringWidth(s, g);
		g.drawString(s, x - width / 2, y + asc);
	}

	public static void drawStringRight(Graphics g, String s, int x, int y)
	{
		FontMetrics fm = g.getFontMetrics();
		int asc = fm.getAscent();
		int width = getStringWidth(s, g);
		g.drawString(s, x - width, y + asc);
	}

	public static String getType(File f)
	{
		String name = f.getName();
		return name.substring(name.lastIndexOf(".") + 1);
	}

	public static String getSongName(File f)
	{
		String name = f.getName();
		name = name.substring(0, name.lastIndexOf("."));
		return name;
	}

	public static String getSongName(String name)
	{
		try
		{
			int index = name.lastIndexOf(File.separator);
			return name.substring(index + 1, name.lastIndexOf("."));
		}
		catch (Exception exe) {}
		return name;
	}




	public static String secondToString(int sec)
	{
		DecimalFormat df = new DecimalFormat("00");
		StringBuilder sb = new StringBuilder();
		sb.append(df.format(sec / 60)).append(":").append(df.format(sec % 60));
		return sb.toString();
	}




	public static String htmlTrim(String str1)
	{
		String str = "";
		str = str1;

		str = str.replaceAll("</?[^>]+>", "");

		str = str.replaceAll("\\s", "");
		str = str.replaceAll("&nbsp;", "");
		str = str.replaceAll("&amp;", "&");
		str = str.replace(".", "");
		str = str.replace("\"", "‘");
		str = str.replace("'", "‘");
		return str;
	}


	static enum Test
	{
		Album,  TITLE;

		private Test() {}
	}

}

