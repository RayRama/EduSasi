package com.smanegeri1sindang.edusasi.News.models.comment;

import com.google.gson.annotations.SerializedName;

public class Content{

	@SerializedName("rendered")
	private String rendered;

	public void setRendered(String rendered){
		this.rendered = rendered;
	}

	public String getRendered(){
		return rendered;
	}
}