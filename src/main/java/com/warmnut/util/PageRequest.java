package com.warmnut.util;
/**
 * @author 作者:zhaoxiaozhou
 * @version 创建时间：2020-10-27 18:36:06
   * 说明
 */
public class PageRequest {
	private String  keyword;
	 /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
    
    /** 排序的字段 */
	private String sort;

	/** 排序的方式: ASC/DESC */
	private String order;
    
    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
	public String getSort() {
		if(sort == null || sort.equals("")) {
			sort = "id";
		}
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		if(order == null || order.equals("")) {
			order = "DESC";
		}
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
