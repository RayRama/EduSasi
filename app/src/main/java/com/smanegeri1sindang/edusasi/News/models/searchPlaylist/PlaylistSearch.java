package com.smanegeri1sindang.edusasi.News.models.searchPlaylist;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaylistSearch{

	@SerializedName("regionCode")
	private String regionCode;

	@SerializedName("kind")
	private String kind;

	@SerializedName("pageInfo")
	private PageInfo pageInfo;

	@SerializedName("nextPageToken")
	private String nextPageToken;

	@SerializedName("etag")
	private String etag;

	@SerializedName("items")
	private List<ItemsItem> items;

	public String getNextPageToken() {
		return nextPageToken;
	}

	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}

	public void setRegionCode(String regionCode){
		this.regionCode = regionCode;
	}

	public String getRegionCode(){
		return regionCode;
	}

	public void setKind(String kind){
		this.kind = kind;
	}

	public String getKind(){
		return kind;
	}

	public void setPageInfo(PageInfo pageInfo){
		this.pageInfo = pageInfo;
	}

	public PageInfo getPageInfo(){
		return pageInfo;
	}

	public void setEtag(String etag){
		this.etag = etag;
	}

	public String getEtag(){
		return etag;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"PlaylistSearch{" + 
			"regionCode = '" + regionCode + '\'' + 
			",kind = '" + kind + '\'' + 
			",pageInfo = '" + pageInfo + '\'' + 
			",etag = '" + etag + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}