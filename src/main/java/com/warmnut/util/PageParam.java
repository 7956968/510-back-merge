package com.warmnut.util;

/**
 * 分页相关的参数,请求列表类的接口参数对象需要继承该类
 * 
 * @author zhaoxiaozhou
 *
 */
public class PageParam {
	private Integer start;
	private Integer length;

	/** 排序的字段 */
	private String sort;

	/** 排序的方式: ASC/DESC */
	private String order;

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageParam [start=");
		builder.append(start);
		builder.append(", length=");
		builder.append(length);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", order=");
		builder.append(order);
		builder.append("]");
		return builder.toString();
	}

}
