package com.skjanyou.recycle.services;

import java.util.List;

import com.skjanyou.recycle.pojo.Action;

public interface ActionService {
	public Action getActionByRandom();
	public Action getActionByName(String name);
	public Action getActionById(String id);
	public List<Action> getActionByType(String type);
}
