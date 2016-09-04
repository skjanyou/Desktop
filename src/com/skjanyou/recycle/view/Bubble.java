package com.skjanyou.recycle.view;

import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JWindow;

import com.skjanyou.utils.ImageUtil;
import com.skjanyou.utils.StringUtil;
import com.sun.awt.AWTUtilities;


/**气泡**/
public class Bubble extends JWindow implements Runnable{
	private static final long serialVersionUID = "Bubble".hashCode();
	private boolean isFirst = true;
	private int width = 300;
	private int height;
	private BufferedImage image;
	private int x,y;
	
	private int dispose_time;
	

	private JLabel content;
	/**dispose_time单位为秒**/
	public Bubble(BufferedImage img,String str,int x,int y,int dispose_time){
		this.image = img;
		this.setLayout(null);
		this.x = x;
		this.y = y;
		this.dispose_time = dispose_time;
		content = new JLabel(StringUtil.getAStr(str, 300 / 14));
		content.setFont(new Font("隶书", 0, 15));
		int len = content.getText().length();
		int w = len * 14;
		int line = 1;
		height = 0;
		if(w > width){
			line = w / width + 1;
		}else{
			width = w;
		}
		height = line * 20;
		content.setBounds(10, 0, width - 10, height);
		this.setSize(width + 20, height + 20);
		this.setLocation(x - getWidth() + 80, y + 60);
		AWTUtilities.setWindowShape(this, getImageShape(ImageUtil.contract(image, this.getHeight() + 30, this.getWidth() + 10,BufferedImage.TYPE_4BYTE_ABGR_PRE)));
		AWTUtilities.setWindowOpacity(this, 0.7f);
		this.getContentPane().add(content);
		setAlwaysOnTop(true);
		setVisible(true);
	}

	private Shape getImageShape(Image img){
		ArrayList<Integer> x = new ArrayList<Integer>();
		ArrayList<Integer> y = new ArrayList<Integer>();
		int width = img.getWidth(null);
		int height = img.getHeight(null);

		PixelGrabber pgr = new PixelGrabber(img, 0, 0, -1, -1, true);
		try {
			pgr.grabPixels();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int pixels[] =  (int[])pgr.getPixels();

		for (int i = 0;i < pixels.length;i++) {
			int alpha = getAlpha(pixels[i]);
			if(alpha == 0){
				continue;
			}else{
				x.add(i%width > 0 ? i%width-1:0);
				y.add(i%width == 0 ? (i == 0 ? 0 : i/width-1): i/width);
			}
		}

		int[][] matrix = new int[height][width];
		for(int i = 0;i < height;i++){
			for(int j = 0;j < width;j++){
				matrix[i][j] = 0;
			}
		}

		for(int c = 0;c < x.size();c++){
			matrix[y.get(c)][x.get(c)] = 1;
		}

		Area rec = new Area();
		int temp = 0;

		for(int i = 0;i < height;i++){
			for(int j = 0;j < width;j++){
				if(matrix[i][j] == 1){
					if(temp == 0){
						temp = j;
					}else if(j == width){
						if(temp == 0){
							Rectangle rectemp = new Rectangle(j,i,1,1);
							rec.add(new Area(rectemp));
						}else{
							Rectangle rectemp = new Rectangle(temp,i,j - temp,1);
							rec.add(new Area(rectemp));
							temp = 0;
						}
					}
				}else{
					if(temp != 0){
						Rectangle rectemp = new Rectangle(temp,i,j - temp,1);
						rec.add(new Area(rectemp));
						temp = 0;
					}
				}
			}
			temp = 0;
		}
		return rec;
	}
	private int getAlpha(int pixel){
		return (pixel >> 24)&0xff;
	}
	
	@Override
	public void setVisible(boolean b) {
		if(isFirst){
		}
		super.setVisible(b);
	}

//	public static void main(String[] args) {
//		Bubble b = new Bubble(ImageUtil.getImage("images/bubble/bubble1.png"),"这里是一句话",800,500);
//		b.getContentPane().setBackground(Color.WHITE);
//		b.setVisible(true);
//	}

	@Override
	public void run() {
		try {
			Thread.currentThread().sleep(dispose_time * 1000);
			this.dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
