package com.smanegeri1sindang.edusasi.News.models.playlist;

import com.google.gson.annotations.SerializedName;

public class ContentDetails{

	@SerializedName("itemCount")
	private int itemCount;

	public void setItemCount(int itemCount){
		this.itemCount = itemCount;
	}

	public int getItemCount(){
		return itemCount;
	}

	@Override
 	public String toString(){
		return 
			"ContentDetails{" + 
			"itemCount = '" + itemCount + '\'' + 
			"}";
		}
}