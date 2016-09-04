package com.skjanyou.recycle.pojo;

import java.io.Serializable;

import com.skjanyou.plugins.Plugins;


public class PluginsEntry implements Serializable{
	private static final long serialVersionUID = "PluginsEntry".hashCode();
	private String id = "";
	private String name = "";
	private String filepath = "";
	private String packagepath = "";
	private String single = "ÊÇ";
	private Plugins plugins;
	private boolean isStarted = false;
	
	public PluginsEntry(String id, String name, String filepath,
			String packagepath, String single) {
		this.id = id;
		this.name = name;
		this.filepath = filepath;
		this.packagepath = packagepath;
		this.single = single;
	}
	public PluginsEntry(String id, String name, String filepath,
			String packagepath, String single, Plugins plugins) {
		this.id = id;
		this.name = name;
		this.filepath = filepath;
		this.packagepath = packagepath;
		this.single = single;
		this.plugins = plugins;
	}
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
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getPackagepath() {
		return packagepath;
	}
	public void setPackagepath(String packagepath) {
		this.packagepath = packagepath;
	}
	public Plugins getPlugins() {
		return plugins;
	}
	public void setPlugins(Plugins plugins) {
		this.plugins = plugins;
	}
	public String getSingle() {
		return single;
	}
	public void setSingle(String single) {
		this.single = single;
	}
	public boolean isStarted() {
		return isStarted;
	}
	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}
	
}
