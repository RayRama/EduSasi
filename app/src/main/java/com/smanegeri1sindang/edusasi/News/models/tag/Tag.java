package com.smanegeri1sindang.edusasi.News.models.tag;

import com.google.gson.annotations.SerializedName;

public class Tag {

	@SerializedName("count")
	private int count;

	@SerializedName("link")
	private String link;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("taxonomy")
	private String taxonomy;

	@SerializedName("slug")
	private String slug;

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTaxonomy(String taxonomy){
		this.taxonomy = taxonomy;
	}

	public String getTaxonomy(){
		return taxonomy;
	}

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	@Override
 	public String toString(){
		return 
			"Tag{" + 
			"count = '" + count + '\'' + 
			",link = '" + link + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",taxonomy = '" + taxonomy + '\'' + 
			",slug = '" + slug + '\'' + 
			"}";
		}
}