package com.example.mrcook.restapi.foodrecipe.responses.detail;

import com.google.gson.annotations.SerializedName;

public class NeedItemItem{

	@SerializedName("thumb_item")
	private String thumbItem;

	@SerializedName("item_name")
	private String itemName;

	public String getThumbItem(){
		return thumbItem;
	}

	public String getItemName(){
		return itemName;
	}
}