package com.skjanyou.recycle.pojo;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**����**/
public class Action {
	private String id;               //���
	private String name;         //����
	private String type;           //����
	private int interval;           //����
	
	private List<String> imagePath = new ArrayList<String>();
	
	private Map<String,Image> imgs = new HashMap<String, Image>();

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public List<String> getImagePath() {
		return imagePath;
	}
	public void setImagePath(List<String> imagePath) {
		this.imagePath = imagePath;
	}
	public Map<String, Image> getImgs() {
		return imgs;
	}
	public void setImgs(Map<String, Image> imgs) {
		this.imgs = imgs;
	}
	
}
