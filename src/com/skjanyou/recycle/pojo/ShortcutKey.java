package com.skjanyou.recycle.pojo;

public class ShortcutKey {
	private String name;
	private String keyCode1;
	private String keyCode2;
	
	public ShortcutKey(String name, String keyCode1, String keyCode2) {
		super();
		this.name = name;
		this.keyCode1 = keyCode1;
		this.keyCode2 = keyCode2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKeyCode1() {
		return keyCode1;
	}
	public void setKeyCode1(String keyCode1) {
		this.keyCode1 = keyCode1;
	}
	public String getKeyCode2() {
		return keyCode2;
	}
	public void setKeyCode2(String keyCode2) {
		this.keyCode2 = keyCode2;
	}
	
}
