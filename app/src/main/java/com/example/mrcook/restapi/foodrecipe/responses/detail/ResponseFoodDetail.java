package com.example.mrcook.restapi.foodrecipe.responses.detail;

import com.google.gson.annotations.SerializedName;

public class ResponseFoodDetail{

	@SerializedName("method")
	private String method;

	@SerializedName("results")
	private Results results;

	@SerializedName("status")
	private boolean status;

	public String getMethod(){
		return method;
	}

	public Results getResults(){
		return results;
	}

	public boolean isStatus(){
		return status;
	}
}