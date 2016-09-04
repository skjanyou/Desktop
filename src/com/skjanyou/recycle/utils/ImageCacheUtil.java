package com.skjanyou.recycle.utils;

import java.awt.Image;
import java.util.List;

import com.skjanyou.recycle.pojo.Action;
import com.skjanyou.recycle.pojo.ActionCollection;
import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.utils.ImageUtil;

public class ImageCacheUtil {
	
	
	public static void loadImage(ActionCollection ac){
		List<Action> actions = ac.getActions();
		for(Action action : actions){
			if(action == null){
				continue;
			}
			List<String> imgPaths = action.getImagePath();
			for(String imgPath : imgPaths){
				if(imgPath == null){
					continue;
				}
				getImage(imgPath);
			}
		}
	}
	
	public static Image getImage(String imgPath){
		Image img = null;
		if((img = Config.imageCache.get(imgPath)) == null){
			img = ImageUtil.getImage(imgPath);
			Config.imageCache.put(imgPath, img);
		}
		return img;
	}
	
}
