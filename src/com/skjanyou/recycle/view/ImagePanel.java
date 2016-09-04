package com.skjanyou.recycle.view;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.geom.Area;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.skjanyou.recycle.pojo.Parameter;


public class ImagePanel extends JPanel {
	private static final long serialVersionUID = "ImagePanel".hashCode();
	private Image img;
	public ImagePanel(){
		this.setOpaque(false);
		//				init();
//				initShape();
	}


	private void init(){
		MediaTracker mt = new MediaTracker(this);
		img = Toolkit.getDefaultToolkit().createImage("images/role/shime1.png");
		mt.addImage(img, 0);
		try {
			mt.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//		System.out.println(img.getWidth(null) + "," + img.getHeight(null));
	}

	private void initShape(){
		this.setSize(img.getWidth(null), img.getHeight(null));
		this.setOpaque(false);
		//		AWTUtilities.setWindowShape(this, getImageShape(img));
		//		AWTUtilities.setWindowOpacity(this, 0.1f);
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
	public void paint(Graphics g) {
		int bold = 5; 
		//以下5行为降锯齿代码
		((Graphics2D)g).setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));   
		((Graphics2D)g).setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));   
		((Graphics2D)g).setStroke(new BasicStroke(bold));   
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);   
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//		super.paint(g);
		if(img != null){
			g.drawImage(img,(Parameter.window.getWidth() - img.getWidth(null))/2,(Parameter.window.getHeight() - img.getHeight(null))/2,null);
		}
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
}
