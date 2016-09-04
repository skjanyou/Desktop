package com.skjanyou.recycle.pojo;

import java.util.ArrayList;
import java.util.List;


/**�����ϼ�**/
public class ActionCollection {
	private String name;                    //�����ϼ�����
	private String id;                          //���
	private int interval;                      //����
	
	private List<Action> actions = new ArrayList<Action>();
	
	public Action getActionByName(String name){
		if(name == null){throw new RuntimeException("���ֲ���Ϊ��");}
		Action result = null;
		for (Action action : actions) {
			if(action != null){
				String acName = action.getName();
				if(name.equals(acName)){
					result = action;
					break;
				}
			}
		}
		return result;
	}
	
	public Action getActionById(String id){
		if(id == null){throw new RuntimeException("��Ų���Ϊ��");}
		Action result = null;
		for (Action action : actions) {
			if(action != null){
				String acId = action.getId();
				if(id.equals(acId)){
					result = action;
					break;
				}
			}
		}
		return result;
	}
	
	
	public List<Action> getActionByType(String type){
		if(type == null){throw new RuntimeException("���Ͳ���Ϊ��");}
		List<Action> result = new ArrayList<Action>();
		for (Action action : actions) {
			if(action != null){
				String acType = action.getType();
				if(type.equals(acType)){
					result.add(action);
					continue;
				}
			}
		}
		return result;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public List<Action> getActions() {
		return actions;
	}
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
	
}
