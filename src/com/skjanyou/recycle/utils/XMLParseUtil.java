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
	 * ���²��ֿ��Թ��ⲿֱ�ӵ���
	 * ���ض���.xml�ļ�
	 * @throws Exception 
	 * 
	 */

	public static List<ActionCollection> loadActionCollections() throws Exception{
		List<ActionCollection> acs = new ArrayList<ActionCollection>();
		Document actions = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("config/����.xml"));
		Entry entry = new Entry(actions.getDocumentElement());
		for (final Entry list_entry : entry.selectChildren("�����ϼ�")) {
			Map<String,String> list_map = list_entry.getAttributes();
			ActionCollection ac = new ActionCollection();
			ac.setName(list_map.get("����"));
			ac.setId(list_map.get("���"));
			ac.setInterval(Integer.parseInt(list_map.get("���").trim()));

			for (final Entry action_entry : list_entry.selectChildren("����")) {
				Map<String,String> action_map = action_entry.getAttributes();
				Action action = new Action();
				action.setName(action_map.get("����"));
				action.setType(action_map.get("�˶�"));
				action.setId(action_map.get("���"));
				action.setInterval(Integer.parseInt(action_map.get("���").trim()));
				for(final Entry img_entry : action_entry.selectChildren("��ͼ")){
					Map<String,String>  img = img_entry.getAttributes();
					String path = img.get("·��");
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
	 * ���²��ֿ��Թ��ⲿֱ�ӵ���
	 * ���ض���.xml�ļ�
	 * @throws Exception 
	 * 
	 */

	public static ActionCollection loadActionCollection() throws Exception{
		ActionCollection ac = new ActionCollection();
		Document actions = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("config/����.xml"));
		Entry entry = new Entry(actions.getDocumentElement());
		for (final Entry list_entry : entry.selectChildren("�����ϼ�")) {
			Map<String,String> list_map = list_entry.getAttributes();
			ac.setName(list_map.get("����"));
			ac.setId(list_map.get("���"));
			ac.setInterval(Integer.parseInt(list_map.get("���").trim()));

			for (final Entry action_entry : list_entry.selectChildren("����")) {
				Map<String,String> action_map = action_entry.getAttributes();
				Action action = new Action();
				action.setName(action_map.get("����"));
				action.setType(action_map.get("�˶�"));
				action.setId(action_map.get("���"));
				action.setInterval(Integer.parseInt(action_map.get("���").trim()));
				for(final Entry img_entry : action_entry.selectChildren("��ͼ")){
					Map<String,String>  img = img_entry.getAttributes();
					String path = img.get("·��");
					action.getImagePath().add(path);
				}
				ac.getActions().add(action);
			}
		}
		return ac;
	}

	/**
	 * ��ȡ�ⲿ����õĿ�ݼ�
	 * **/
	public static  List<ShortcutKey> loadShortcutKey() throws Exception{
		List<ShortcutKey> shrotcutKeys = new ArrayList<ShortcutKey>(); 
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("config/��ݼ�.xml"));
		Entry entry = new Entry(doc.getDocumentElement());
		for (final Entry list_entry : entry.selectChildren("��ݼ�")) {
			Map<String,String> map = list_entry.getAttributes();
			String name =  map.get("����");
			String keyCode1 = map.get("����1");
			String keyCode2 = map.get("����2");
			shrotcutKeys.add(new ShortcutKey(name, keyCode1, keyCode2));
		}
		return shrotcutKeys;
	}

	public static List<PluginsEntry> loadPlugins() throws Exception{
		List<PluginsEntry> result = new ArrayList<PluginsEntry>();
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("plugins/���.xml"));
		Entry entry = new Entry(doc.getDocumentElement());
		for (final Entry list_entry : entry.selectChildren("���")) {
			Map<String,String> map = list_entry.getAttributes();
			String name =  map.get("����");
			String filepath = map.get("�ļ�·��");
			String packagepath = map.get("��·��");
			String id = map.get("���");
			String single = map.get("����");
			PluginsEntry pe = new PluginsEntry(id, name, filepath, packagepath,single);
			result.add(pe);
		}

		return result;
	}

	public static void saveShortcutKey(List<ShortcutKey> list) throws Exception{
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element element = doc.createElement("Root");
		for (ShortcutKey key : list) {

			Element shortcutKey = doc.createElement("��ݼ�");
			shortcutKey.setAttribute("����", key.getName());
			shortcutKey.setAttribute("����1", key.getKeyCode1());
			shortcutKey.setAttribute("����2", key.getKeyCode2());
			element.appendChild(shortcutKey);

		}

		doc.appendChild(element);

		doc2XmlFile(doc,"config/��ݼ�.xml");
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
			Element file = doc.createElement("�ļ�");
			file.setAttribute("·��", path);
			file.setAttribute("������", x + "");
			file.setAttribute("������", y + "");
			element.appendChild(file);
		}
		doc.appendChild(element);
		doc2XmlFile(doc, "config/�������.xml");
	}
	
	public static List<FileButton> loadFileButton() throws Exception{
		List<FileButton> result = new ArrayList<FileButton>();
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("config/�������.xml"));
		Entry entry = new Entry(doc.getDocumentElement());
		for (final Entry list_entry : entry.selectChildren("�ļ�")) {
			Map<String,String> map = list_entry.getAttributes();
			String path = map.get("·��");
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
