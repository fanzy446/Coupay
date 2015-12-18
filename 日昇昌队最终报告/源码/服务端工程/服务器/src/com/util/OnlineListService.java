package com.util;

import java.util.HashMap;
import java.util.Map;

public class OnlineListService {

	/**
	 * ������������ӵ�ǰ�û�
	 * @param application
	 * @param name 
	 */
	public static void add(Map<String, Object> application, String name){
		
		@SuppressWarnings("unchecked")
		HashMap<String, Boolean> map = (HashMap<String,Boolean>) application.get("onlineList");
		map.put(name, true);
	}
	
	/**
	 * ɾ����ǰ�û�
	 * @param application
	 */
	public static void delete(Map<String, Object> application, String name){
		@SuppressWarnings("unchecked")
		HashMap<String, Boolean> map = (HashMap<String,Boolean>) application.get("onlineList");
		map.remove(name);
	}
	
	//�����������ϲ��ҵ�ǰ�û��Ƿ����
	public static boolean search(Map<String, Object> application, String name){
		@SuppressWarnings("unchecked")
		HashMap<String,Boolean> onlineList = (HashMap<String,Boolean>) application.get("onlineList");
		if(onlineList.get(name) != null)
			return true;
		return false;
	}
	
	/**
	 * ��������
	 * @param application
	 */
	public static void create(Map<String, Object> application){
		HashMap<String, Boolean> onlineList = new HashMap<String, Boolean>();
		application.put("onlineList", onlineList);
	}
	
}
