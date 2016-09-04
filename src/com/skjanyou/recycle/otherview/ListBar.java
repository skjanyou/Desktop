package com.skjanyou.recycle.otherview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicListUI;

public class ListBar extends JPanel implements MouseListener, MouseMotionListener{

	private static final long serialVersionUID = "ListBar".hashCode();
	private Vector<String> names;
	private Map<String, Component> map;
	private int onIndex = -1;
	private JPanel content;
	private JScrollPane jScrollPane1;
	private JList list;

	public ListBar()
	{
		initComponents();
		initOther();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initOther()
	{
		this.map = new HashMap();
		this.names = new Vector();
		this.list.setUI(new YOYOListUI());
		this.list.setFixedCellHeight(25);
		this.list.addMouseListener(this);
		this.list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				if (!e.getValueIsAdjusting()) {
					ListBar.this.updateSelected();
				}
			}
		});
		this.list.addMouseMotionListener(this);
		this.list.setSelectionMode(0);
	}

	public int getBarComponentCount()
	{
		return this.map.size();
	}

	public Component getBarComponent(int index)
	{
		return (Component)this.map.get(this.names.get(index));
	}

	public Component getComponent(String name)
	{
		return (Component)this.map.get(name);
	}

	private void updateSelected()
	{
		Object value = this.list.getSelectedValue();
		if (value != null)
		{
			String name = value.toString();
			Component com = (Component)this.map.get(name);
			if (com != null)
			{
				this.content.removeAll();
				this.content.add(com, "Center");

				SwingUtilities.updateComponentTreeUI(this.content);
			}
		}
	}

	public void addComponent(String name, Component com)
	{
		if (!this.names.contains(name)) {
			this.names.add(name);
		}
		Dimension size = com.getPreferredSize();
		Dimension me = this.content.getPreferredSize();
		me.width = (size.width > me.width ? size.width : me.width);
		me.height = (size.height > me.height ? size.height : me.height);
		this.content.setPreferredSize(me);
		this.map.put(name, com);
		this.list.setListData(this.names);
	}

	public void setSelectedComponent(String name)
	{
		Component com = (Component)this.map.get(name);
		if (com != null)
		{
			this.list.setSelectedValue(name, true);
			this.content.removeAll();
			this.content.add(com, "Center");

			SwingUtilities.updateComponentTreeUI(this.content);
		}
	}

	public void removeComponent(String name) {}

	public void removeComponent(Component com) {}

	public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	private class YOYOListUI
	extends BasicListUI
	{
		private Image line;
		private Image select;

		public YOYOListUI()
		{
			try
			{
				this.line = ImageIO.read(getClass().getResource("line.png"));
				this.select = ImageIO.read(getClass().getResource("select.png"));
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}

		protected void paintCell(Graphics g, int row, Rectangle rowBounds, ListCellRenderer cellRenderer, ListModel dataModel, ListSelectionModel selModel, int leadIndex)
		{
			int width = rowBounds.width;
			int height = rowBounds.height;
			int x = rowBounds.x;
			int y = rowBounds.y;
			g.translate(x, y);
			String s = dataModel.getElementAt(row).toString();
			g.setColor(Color.BLACK);
			Util.drawString(g, s, (width - Util.getStringWidth(s, g)) / 2, (height - Util.getStringHeight(s, g)) / 2);
			if (selModel.isSelectedIndex(row))
			{
				g.setColor(new Color(48, 106, 198));
				g.fillRect(0, 0, rowBounds.width, rowBounds.height);
				g.setColor(Color.WHITE);
				Util.drawString(g, s, (width - Util.getStringWidth(s, g)) / 2, (height - Util.getStringHeight(s, g)) / 2);
				g.drawImage(this.line, (width - this.line.getWidth(this.list)) / 2, height - 1, this.list);
				g.drawImage(this.select, 2, (height - this.select.getHeight(this.list)) / 2, this.list);
				g.setColor(new Color(120, 149, 226));
				g.drawRect(0, 0, width - 1, height - 1);
				g.setColor(new Color(192, 192, 255));
				g.drawLine(0, height - 1, width, height - 1);
			}
			else
			{
				g.drawImage(this.line, (width - this.line.getWidth(this.list)) / 2, height - 1, this.list);
			}
			if (row == ListBar.this.onIndex)
			{
				g.setColor(new Color(151, 180, 226));
				g.fillRect(0, 0, rowBounds.width, rowBounds.height);
				g.setColor(Color.WHITE);
				Util.drawString(g, s, (width - Util.getStringWidth(s, g)) / 2, (height - Util.getStringHeight(s, g)) / 2);
				g.setColor(new Color(120, 149, 226));
				g.drawLine(0, height - 1, width, height - 1);
			}
			g.translate(0 - x, 0 - y);
		}
	}

	public void mouseExited(MouseEvent e)
	{
		this.onIndex = -1;
		this.list.repaint();
	}

	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e)
	{
		this.onIndex = this.list.locationToIndex(e.getPoint());
		this.list.repaint();
	}

	@SuppressWarnings("serial")
	private void initComponents()
	{
		this.jScrollPane1 = new JScrollPane();
		this.list = new JList();
		this.content = new JPanel();

		this.jScrollPane1.setBorder(BorderFactory.createEtchedBorder());
		this.list.setModel(new AbstractListModel()
		{
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

			public int getSize()
			{
				return this.strings.length;
			}

			public Object getElementAt(int i)
			{
				return this.strings[i];
			}
		});
		this.jScrollPane1.setViewportView(this.list);

		this.content.setBorder(BorderFactory.createEtchedBorder());
		this.content.setLayout(new BorderLayout());

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
				addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -2, 105, -2).
				addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
				addComponent(this.content, -1, 520, 32767).addContainerGap()));

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
				addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().
				addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jScrollPane1, GroupLayout.Alignment.LEADING, -1, 396, 32767).
				addComponent(this.content, GroupLayout.Alignment.LEADING, -1, 396, 32767)).addContainerGap()));
	
		
		
		
	}
}