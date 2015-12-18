package com.util;

import java.util.HashMap;
import java.util.Map;

public class OnlineListService {

	/**
	 * 往在线链表添加当前用户
	 * @param application
	 * @param name 
	 */
	public static void add(Map<String, Object> application, String name){
		
		@SuppressWarnings("unchecked")
		HashMap<String, Boolean> map = (HashMap<String,Boolean>) application.get("onlineList");
		map.put(name, true);
	}
	
	/**
	 * 删除当前用户
	 * @param application
	 */
	public static void delete(Map<String, Object> application, String name){
		@SuppressWarnings("unchecked")
		HashMap<String, Boolean> map = (HashMap<String,Boolean>) application.get("onlineList");
		map.remove(name);
	}
	
	//在在线链表上查找当前用户是否存在
	public static boolean search(Map<String, Object> application, String name){
		@SuppressWarnings("unchecked")
		HashMap<String,Boolean> onlineList = (HashMap<String,Boolean>) application.get("onlineList");
		if(onlineList.get(name) != null)
			return true;
		return false;
	}
	
	/**
	 * 创建链表
	 * @param application
	 */
	public static void create(Map<String, Object> application){
		HashMap<String, Boolean> onlineList = new HashMap<String, Boolean>();
		application.put("onlineList", onlineList);
	}
	
}
