package com.skjanyou.recycle.services.impl;

import java.util.List;

import com.skjanyou.recycle.pojo.Action;
import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.services.AnimationService;

public class AnimationServiceImpl implements AnimationService {

	@Override
	public List<String> getImg(Action action) {
		return action.getImagePath();
	}

	@Override
	public Action getAction(int index) {
		return Config.actionCollection.getActions().get(index);
	}

}
