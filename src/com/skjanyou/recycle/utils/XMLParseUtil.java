package com.skjanyou.recycle.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.skjanyou.recycle.pojo.Action;
import com.skjanyou.recycle.pojo.ActionCollection;
import com.skjanyou.recycle.pojo.PluginsEntry;
import com.skjanyou.recycle.pojo.ShortcutKey;
import com.skjanyou.recycle.view.FileView.FileButton;
import com.skjanyou.utils.Entry;

public class XMLParseUtil extends com.skjanyou.utils.XMLParseUtil {

	public XMLParseUtil(String xmlPath) {
		super(xmlPath);
	}
	
	/**
	 * 
	 * 以下部分可以供外部直接调用
	 * 加载动作.xml文件
	 * @throws Exception 
	 * 
	 */

	public static List<ActionCollection> loadActionCollections() throws Exception{
		List<ActionCollection> acs = new ArrayList<ActionCollection>();
		Document actions = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("config/动作.xml"));
		Entry entry = new Entry(actions.getDocumentElement());
		for (final Entry list_entry : entry.selectChildren("动作合集")) {
			Map<String,String> list_map = list_entry.getAttributes();
			ActionCollection ac = new ActionCollection();
			ac.setName(list_map.get("名字"));
			ac.setId(list_map.get("编号"));
			ac.setInterval(Integer.parseInt(list_map.get("间隔").trim()));

			for (final Entry action_entry : list_entry.selectChildren("动作")) {
				Map<String,String> action_map = action_entry.getAttributes();
				Action action = new Action();
				action.setName(action_map.get("名字"));
				action.setType(action_map.get("运动"));
				action.setId(action_map.get("编号"));
				action.setInterval(Integer.parseInt(action_map.get("间隔").trim()));
				for(final Entry img_entry : action_entry.selectChildren("贴图")){
					Map<String,String>  img = img_entry.getAttributes();
					String path = img.get("路径");
					action.getImagePath().add(path);
				}
				ac.getActions().add(action);
			}
			acs.add(ac);
		}
		return acs;
	}

	/**
	 * 
	 * 以下部分可以供外部直接调用
	 * 加载动作.xml文件
	 * @throws Exception 
	 * 
	 */

	public static ActionCollection loadActionCollection() throws Exception{
		ActionCollection ac = new ActionCollection();
		Document actions = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("config/动作.xml"));
		Entry entry = new Entry(actions.getDocumentElement());
		for (final Entry list_entry : entry.selectChildren("动作合集")) {
			Map<String,String> list_map = list_entry.getAttributes();
			ac.setName(list_map.get("名字"));
			ac.setId(list_map.get("编号"));
			ac.setInterval(Integer.parseInt(list_map.get("间隔").trim()));

			for (final Entry action_entry : list_entry.selectChildren("动作")) {
				Map<String,String> action_map = action_entry.getAttributes();
				Action action = new Action();
				action.setName(action_map.get("名字"));
				action.setType(action_map.get("运动"));
				action.setId(action_map.get("编号"));
				action.setInterval(Integer.parseInt(action_map.get("间隔").trim()));
				for(final Entry img_entry : action_entry.selectChildren("贴图")){
					Map<String,String>  img = img_entry.getAttributes();
					String path = img.get("路径");
					action.getImagePath().add(path);
				}
				ac.getActions().add(action);
			}
		}
		return ac;
	}

	/**
	 * 获取外部定义好的快捷键
	 * **/
	public static  List<ShortcutKey> loadShortcutKey() throws Exception{
		List<ShortcutKey> shrotcutKeys = new ArrayList<ShortcutKey>(); 
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("config/快捷键.xml"));
		Entry entry = new Entry(doc.getDocumentElement());
		for (final Entry list_entry : entry.selectChildren("快捷键")) {
			Map<String,String> map = list_entry.getAttributes();
			String name =  map.get("名字");
			String keyCode1 = map.get("按键1");
			String keyCode2 = map.get("按键2");
			shrotcutKeys.add(new ShortcutKey(name, keyCode1, keyCode2));
		}
		return shrotcutKeys;
	}

	public static List<PluginsEntry> loadPlugins() throws Exception{
		List<PluginsEntry> result = new ArrayList<PluginsEntry>();
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("plugins/插件.xml"));
		Entry entry = new Entry(doc.getDocumentElement());
		for (final Entry list_entry : entry.selectChildren("插件")) {
			Map<String,String> map = list_entry.getAttributes();
			String name =  map.get("名字");
			String filepath = map.get("文件路径");
			String packagepath = map.get("包路径");
			String id = map.get("编号");
			String single = map.get("单例");
			PluginsEntry pe = new PluginsEntry(id, name, filepath, packagepath,single);
			result.add(pe);
		}

		return result;
	}

	public static void saveShortcutKey(List<ShortcutKey> list) throws Exception{
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element element = doc.createElement("Root");
		for (ShortcutKey key : list) {

			Element shortcutKey = doc.createElement("快捷键");
			shortcutKey.setAttribute("名字", key.getName());
			shortcutKey.setAttribute("按键1", key.getKeyCode1());
			shortcutKey.setAttribute("按键2", key.getKeyCode2());
			element.appendChild(shortcutKey);

		}

		doc.appendChild(element);

		doc2XmlFile(doc,"config/快捷键.xml");
	}
	
	public static void saveFileButton(List<FileButton> list) throws Exception{
		if(list == null){
			return;
		}
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element element = doc.createElement("Root");
		for (FileButton fileButton : list) {
			String path = fileButton.getFile().getAbsolutePath();
			int x = fileButton.getLocation().x;
			int y = fileButton.getLocation().y;
			Element file = doc.createElement("文件");
			file.setAttribute("路径", path);
			file.setAttribute("横坐标", x + "");
			file.setAttribute("纵坐标", y + "");
			element.appendChild(file);
		}
		doc.appendChild(element);
		doc2XmlFile(doc, "config/程序参数.xml");
	}
	
	public static List<FileButton> loadFileButton() throws Exception{
		List<FileButton> result = new ArrayList<FileButton>();
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("config/程序参数.xml"));
		Entry entry = new Entry(doc.getDocumentElement());
		for (final Entry list_entry : entry.selectChildren("文件")) {
			Map<String,String> map = list_entry.getAttributes();
			String path = map.get("路径");
			try{
				FileButton fb = new FileButton(new File(path));
				result.add(fb);
			}catch(Exception e){
				System.out.println(e.getMessage());
				continue;
			}
		}
		return result;
	}
}
