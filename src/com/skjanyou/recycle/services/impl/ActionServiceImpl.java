package com.skjanyou.recycle.services.impl;

import java.util.List;
import java.util.Random;

import com.skjanyou.recycle.pojo.Action;
import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.services.ActionService;

public class ActionServiceImpl implements ActionService {
	private Random rand = new Random();
	
	@Override
	public Action getActionByRandom() {
		Action action =  Config.actionCollection.getActions().
				get(rand.nextInt(Config.actionCollection.getActions().size()));
		return action;
	}

	@Override
	public Action getActionByName(String name) {
		return Config.actionCollection.getActionByName(name);
	}

	@Override
	public Action getActionById(String id) {
		return Config.actionCollection.getActionById(id);
	}

	@Override
	public List<Action> getActionByType(String type) {
		return Config.actionCollection.getActionByType(type);
	}
}
