package com.example.mrcook.restapi.foodrecipe.responses.search;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseFoodSearch{

	@SerializedName("method")
	private String method;

	@SerializedName("results")
	private List<ResultsItem> results;

	@SerializedName("status")
	private boolean status;

	public String getMethod(){
		return method;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public boolean isStatus(){
		return status;
	}
}