package com.wan.POJO;

/**
 * @author wanfeng
 * @date 2021/3/22 20:20
 */
public class PageHelper {
	private int state;
	private int page;
	private int size;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
