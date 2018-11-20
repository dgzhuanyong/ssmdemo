package com.py.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.py.dao.SysPrivilegeMapper;
import com.py.dao.SysUserMapper;
import com.py.entity.SysPrivilege;
import com.py.entity.SysUser;

@Service
public class SysUserService {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysPrivilegeMapper sysPrivilegeMapper;
	
	
	public int insert(SysUser record) {
		return sysUserMapper.insertSelective(record);
	}
	
	
	public int update(SysUser record) {
		return sysUserMapper.updateByPrimaryKeySelective(record);
	}
	
	
	public int delete(Integer id) {
		return sysUserMapper.deleteByPrimaryKey(id);
	}
	
	
	public SysUser selectByPrimaryKey(Integer id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}
	
	
	/**
     * 登录查询
     * @param loginName
     * @return
     */
	public SysUser selectByLoginName(String loginName) {
		return sysUserMapper.selectByLoginName(loginName);
	}
	
	/**
     * 根据条件查询列表
     * @param searchMap
     * @return
     */
	public List<SysUser> selectConditionList(Map<String, Object> searchMap){
		return sysUserMapper.selectConditionList(searchMap);
	}
	
	/**
     * 检测是否重复
     * @param searchMap
     * @return
     */
	public long checkRepeat(Map<String, Object> searchMap) {
		return sysUserMapper.checkRepeat(searchMap);
	}
	
	
	
	
	
	
	/**
	 * 根据用户id获取菜单列表
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getMenuByUserId(String loginName,Integer userId){
		Map<String,Object> resultMap = Maps.newHashMap();
		List<SysPrivilege> menuList=null;
		if("admin".equals(loginName)) {
			menuList=sysPrivilegeMapper.selectMenuAll();
		}else {
			menuList = sysPrivilegeMapper.selectMenuByUserId(userId);
		}
		//暂遍历3级菜单
		List<SysPrivilege> menu1 = new ArrayList<SysPrivilege>();
		List<SysPrivilege> menu2 = new ArrayList<SysPrivilege>();
		List<SysPrivilege> menu3 = new ArrayList<SysPrivilege>();
//		List<SysPrivilege> menu4 = new ArrayList<SysPrivilege>();
//		List<SysPrivilege> menu5 = new ArrayList<SysPrivilege>();
//		List<SysPrivilege> menu6 = new ArrayList<SysPrivilege>();
		//一级菜单
		if(menuList!=null && menuList.size() > 0) {
			for (int i = 0; i < menuList.size(); i++) {
				SysPrivilege sysPrivilege = menuList.get(i);
				if(sysPrivilege.getpId() == 0){
					menu1.add(sysPrivilege);
				}
			}
			menuList.removeAll(menu1);
			resultMap.put("menu1", menu1);
		}
		//二级菜单
		if(menuList!=null && menuList.size() > 0){
			for (int i = 0; i < menu1.size(); i++) {
				SysPrivilege sysPrivilege = menu1.get(i);
				for (int j = 0; j < menuList.size(); j++) {
					SysPrivilege p = menuList.get(j);
					if(sysPrivilege.getId() == p.getpId()){
						menu2.add(p);
					}
				}
			}
			menuList.removeAll(menu2);
			resultMap.put("menu2", menu2);
		}
		//三级菜单
		if(menuList!=null && menuList.size() > 0){
			for (int i = 0; i < menu2.size(); i++) {
				SysPrivilege sysPrivilege = menu2.get(i);
				for (int j = 0; j < menuList.size(); j++) {
					SysPrivilege p = menuList.get(j);
					if(sysPrivilege.getId() == p.getpId()){
						menu3.add(p);
					}
				}
			}
			menuList.removeAll(menu3);
			resultMap.put("menu3", menu3);
		}
		//四级菜单
//		if(menuList!=null && menuList.size() > 0){
//			for (int i = 0; i < menu3.size(); i++) {
//				privilege = new Privilege();
//				privilege = menu3.get(i);
//				for (int j = 0; j < menuList.size(); j++) {
//					Privilege p = menuList.get(j);
//					if(privilege.getId() == p.getParentId()){
//						menu4.add(p);
//					}
//				}
//			}
//			menuList.removeAll(menu4);
//			resultMap.put("menu4", menu4);
//		}
//		//五级菜单
//		if(menuList!=null && menuList.size() > 0){
//			for (int i = 0; i < menu4.size(); i++) {
//				privilege = new Privilege();
//				privilege = menu4.get(i);
//				for (int j = 0; j < menuList.size(); j++) {
//					Privilege p = menuList.get(j);
//					if(privilege.getId() == p.getParentId()){
//						menu5.add(p);
//					}
//				}
//			}
//			menuList.removeAll(menu5);
//			resultMap.put("menu5", menu5);
//		}
//		//六级菜单
//		if(menuList!=null && menuList.size() > 0){
//			for (int i = 0; i < menu5.size(); i++) {
//				privilege = new Privilege();
//				privilege = menu5.get(i);
//				for (int j = 0; j < menuList.size(); j++) {
//					Privilege p = menuList.get(j);
//					if(privilege.getId() == p.getParentId()){
//						menu6.add(p);
//					}
//				}
//			}
//			resultMap.put("menu6", menu6);
//		}
		return resultMap;
	}
	
	
	/**
     * 查询全部权限
     * @return
     */
	public List<SysPrivilege> selectPrivilegeAll(){
		return sysPrivilegeMapper.selectPrivilegeAll();
	}
	
	/**
     * 查询用户权限
     * @param userId
     * @return
     */
	public List<SysPrivilege> selectPrivilegeByUserId(Integer userId){
		return sysPrivilegeMapper.selectPrivilegeByUserId(userId);
	}
	
}
