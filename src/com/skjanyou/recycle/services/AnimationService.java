package com.skjanyou.recycle.services;

import java.util.List;

import com.skjanyou.recycle.pojo.Action;

public interface AnimationService {
	public List<String> getImg(Action action);
	public Action getAction(int index);
}
