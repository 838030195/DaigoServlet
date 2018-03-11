package com.Util;

public class Order {
	
	public static int NORMAL = 0; //已发布
	public static int RECEIVED = 1; //已接单
	public static int INVALIDATE = 2; //已取消
	public static int COMPLETE = 3; //已完成
	
	private User sender;
	
	private User receiver;
	
	private int state;
	
	private String title;
	
	private String requestTime;
	
	private String publicContent;
	
	private String privateContent;
	
	private String contact;
	
	private String price;
	
	private String orderId;
	
	private String acceptTime;
	
	private String completeTime;
	
	
	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getPublicContent() {
		return publicContent;
	}

	public void setPublicContent(String publicContent) {
		this.publicContent = publicContent;
	}

	public String getPrivateContent() {
		return privateContent;
	}

	public void setPrivateContent(String privateContent) {
		this.privateContent = privateContent;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	
	
	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	
	
	

}
