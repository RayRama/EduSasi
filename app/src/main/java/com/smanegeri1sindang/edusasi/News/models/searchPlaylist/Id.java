package com.smanegeri1sindang.edusasi.News.models.searchPlaylist;


import com.google.gson.annotations.SerializedName;

public class Id{

	@SerializedName("playlistId")
	private String playlistId;

	@SerializedName("kind")
	private String kind;

	public void setPlaylistId(String playlistId){
		this.playlistId = playlistId;
	}

	public String getPlaylistId(){
		return playlistId;
	}

	public void setKind(String kind){
		this.kind = kind;
	}

	public String getKind(){
		return kind;
	}

	@Override
 	public String toString(){
		return 
			"Id{" + 
			"playlistId = '" + playlistId + '\'' + 
			",kind = '" + kind + '\'' + 
			"}";
		}
}