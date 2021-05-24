package com.warmnut.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * @author 作者:zhaoxiaozhou
 * @version 创建时间：2020-10-22 11:14:40
   * 说明
 */
public class UserInfo implements Serializable,UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3645808952459256768L;
	private Integer id;		
	private Integer roleId;	
	private String jobNumber;
	private String position;
	private String departmentName;
	/**记录状态:1-正常;2-已删除*/
	private Boolean itemStatus;
	private Integer departmentId;
	private Date deleteTime;
	private String name;
	private String password;
	private Boolean deleteablel;
	private String phone;
	private String email;
	private Integer gender;	
	private Date createTime;
	private Date modifyTime;
	private Date lastLoginTime;	
	private List<GrantedAuthority> authorities;
	
	private List<Map<String,Object>> routers;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getDeleteablel() {
		return deleteablel;
	}
	public void setDeleteablel(Boolean deleteablel) {
		this.deleteablel = deleteablel;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return "";
	}
	
//	public String getDepartmentName() {
//		if(departmentId != null) {
//			DepartmentCacheService bean = new DepartmentCacheService();
//			S_Department findById = bean.findById(departmentId);
//			if(findById != null) {
//				return findById.getName();
//			}
//			
//		}
//		return "";
//	}

	
	public String getDepartmentName() {
		return departmentName;
	}
	public List<Map<String, Object>> getRouters() {
		return routers;
	}
	public void setRouters(List<Map<String, Object>> routers) {
		this.routers = routers;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}


	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public Boolean getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(Boolean itemStatus) {
		this.itemStatus = itemStatus;
	}
	public Date getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}	
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}	
	
	public boolean hasPermission(String permission) {		
		for(GrantedAuthority authority : authorities) {
			if(authority.getAuthority().equals(permission)) {
				return true;
			}
		}
		return false;
		
	}
	
	
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return authorities;
	}
	@Override
	public String getUsername() {		
		return name;
	}
	@Override
	public boolean isAccountNonExpired() {//账户是否未过期		
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {//账户是否未锁定		
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {		
		return true;
	}
	@Override
	public boolean isEnabled() {		
		return true;
	}
}
