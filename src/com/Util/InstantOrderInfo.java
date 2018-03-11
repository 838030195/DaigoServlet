package com.Util;

/**
 * 实时获取订单状态返回内容
 * @author jshaz
 *
 */

public class InstantOrderInfo {
	
	public static final int UNREAD = 0;
	public static final int READ = 1;
	
	private String title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	
}
