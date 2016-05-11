package com.tiqiuzhe.touxiangupdate.bean;

//表示菜类（经过烹调的蔬菜、蛋品、肉类等）
public class Dish {

	private String imgUrl; // 图片地址
	private String name; // 菜名
	private String price; // 菜价

	public Dish(String imgUrl, String name, String price) {
		this.imgUrl = imgUrl;
		this.name = name;
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
