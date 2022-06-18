package com.example.mrcook.restapi.foodrecipe.responses.search;

import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("difficulty")
	private String difficulty;

	@SerializedName("times")
	private String times;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("title")
	private String title;

	@SerializedName("key")
	private String key;

	@SerializedName("serving")
	private String serving;

	public String getDifficulty(){
		return difficulty;
	}

	public String getTimes(){
		return times;
	}

	public String getThumb(){
		return thumb;
	}

	public String getTitle(){
		return title;
	}

	public String getKey(){
		return key;
	}

	public String getServing(){
		return serving;
	}
}