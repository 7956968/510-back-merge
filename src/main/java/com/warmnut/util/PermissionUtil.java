package com.warmnut.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.warmnut.bean.Permission;
import com.warmnut.enumerate.PermissionType;

/**
 * @author 作者:zhaoxiaozhou
 * @version 创建时间：2020-10-25 11:01:37
   * 说明
 */
public class PermissionUtil {

	/***
	 * pid 父id
	 * allList 所有数据库查出来的list数据
	 */
	public static List<Map<String,Object>> getChild(int pid , List<Permission> allList){
	        List<Map<String,Object>> childList = new ArrayList<>();//用于保存子节点的list
	        for(Permission ms : allList){
	            if(pid==ms.getPid() && (ms.getType().equals(PermissionType.MENU.getKey()) || ms.getType().equals(PermissionType.PAGE.getKey()))){//判断传入的父id是否等于自身的，如果是，就说明自己是子节点
	                Map<String,Object> map = new HashMap<>();
	                map.put("id",ms.getId());
	                map.put("name",ms.getName());
	                map.put("path",ms.getPath());
	                map.put("component",ms.getComponent());
	                map.put("redirect",ms.getRedirect());
	                map.put("alwaysshow",ms.getAlwaysshow());
	                map.put("hidden",ms.getHidden());
	                map.put("clickable",ms.getClickable());
	                map.put("meta", setMeta(ms,allList));
	               // map.put("hasChildren",false);
	                //if()
	                //map.put("children", new Object[]{});
	                childList.add(map); //加入子节点
	            }
	        }
	        for(Map<String,Object> map : childList){//遍历子节点，继续递归判断每个子节点是否还含有子节点
	            List<Map<String,Object>> tList = getChild(Integer.parseInt(String.valueOf(map.get("id")))  , allList);
//	            if(!tList.isEmpty()){
//	                map.put("hasChildren",true);
//	            }
	            if(tList != null && tList.size() >0) {
	            	map.put("children" , tList);
	            }
	            
	        }
	        return childList;
	    }
	
//	private static Map<String, Object> setMeta(Permission bean){
//		Map<String, Object> map = new HashMap<>();
//         map.put("title",bean.getTitle());
//         map.put("nocache",bean.getNocache());
//         map.put("icon",bean.getIcon());
//		return map;
//	}
	private static Map<String, Object> setMeta(Permission bean,List<Permission> list){
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> btns = new ArrayList<>();
		for(Permission permission : list) {
			if(permission.getPid() == bean.getId() && permission.getType().equals(PermissionType.BUTTON.getKey())) {
				Map<String, Object> btn = new HashMap<>();
				btn.put("name", permission.getTitle());
				btn.put("methodd", permission.getMethod());
				btn.put("icon", permission.getIcon());
				btns.add(btn);
			}
		}
         map.put("title",bean.getTitle());
         map.put("nocache",bean.getNocache());
         map.put("icon",bean.getIcon());
         map.put("btnPermission", btns);
//         if(btns.size() > 0) {
//        	 
//         }
		return map;
	}
}
