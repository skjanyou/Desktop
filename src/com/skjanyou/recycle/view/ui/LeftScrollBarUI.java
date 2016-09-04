package com.skjanyou.recycle.view.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class LeftScrollBarUI extends BasicScrollBarUI {
	/*�Զ��廬�����ı�����ɫ*/
	@Override
	protected void configureScrollBarColors() {
		trackColor = Color.white;
	}
	
	/*�Զ��廬��*/
	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		g.translate(thumbBounds.x,thumbBounds.y);
		g.setColor(new Color(212,208,200));
//		g.drawRoundRect(8, 0, 6, thumbBounds.height - 1, 5, 5);
		g.drawRect(0, 0, (int)thumbBounds.getWidth() - 1, (int)thumbBounds.getHeight());
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.addRenderingHints(rh);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		
//		g2.fillRoundRect(8, 0, 6, thumbBounds.height - 1, 5, 5);
		g.fillRect(0, 0, (int)thumbBounds.getWidth() - 1, (int)thumbBounds.getHeight());
	}
	
	/*�Զ���ײ���ť*/
	@Override
	protected JButton createIncreaseButton(int orientation) {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(0, 0));
		return button;
	}
	
	/*�Զ��嶥����ť*/
	@Override
	protected JButton createDecreaseButton(int orientation) {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(0, 0));
		return button;
	}

}
