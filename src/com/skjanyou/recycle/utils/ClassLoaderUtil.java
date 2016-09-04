package com.skjanyou.recycle.utils;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import com.skjanyou.plugins.Plugins;


public class ClassLoaderUtil {
	
	public static Plugins getPluginsInstance(String filepath,String packagepath) throws Exception{
		ClassLoader parent = Plugins.class.getClassLoader();    //定义好的插件接口类
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(filepath).toURL()}, parent);   //外部jar文件
		Plugins plugins = (Plugins) classLoader.loadClass(packagepath).newInstance();  //实现了插件接口类的类
		return plugins;
	}
	
}
