package com.skjanyou.recycle.utils;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import com.skjanyou.plugins.Plugins;


public class ClassLoaderUtil {
	
	public static Plugins getPluginsInstance(String filepath,String packagepath) throws Exception{
		ClassLoader parent = Plugins.class.getClassLoader();    //����õĲ���ӿ���
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(filepath).toURL()}, parent);   //�ⲿjar�ļ�
		Plugins plugins = (Plugins) classLoader.loadClass(packagepath).newInstance();  //ʵ���˲���ӿ������
		return plugins;
	}
	
}
