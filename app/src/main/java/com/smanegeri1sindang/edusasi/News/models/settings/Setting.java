package com.smanegeri1sindang.edusasi.News.models.settings;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Setting {

	@SerializedName("update_body")
	private String updateBody;

	@SerializedName("app_version")
	private int appVersion;

	@SerializedName("settings_saved")
	private boolean settingsSaved;

	@SerializedName("app_title")
	private String appTitle;

	@SerializedName("user_key")
	private Object userKey;

	@SerializedName("update_title")
	private String updateTitle;

	@SerializedName("slider_cat")
	private String sliderCat;

	@SerializedName("categories")
	private List<String> categories;

	@SerializedName("force_update")
	private boolean forceUpdate;

	@SerializedName("slider_enabled")
	private boolean sliderEnabled;

	@SerializedName("sections")
	private List<SectionsItem> sections;

	public void setUpdateBody(String updateBody){
		this.updateBody = updateBody;
	}

	public String getUpdateBody(){
		return updateBody;
	}

	public void setAppVersion(int appVersion){
		this.appVersion = appVersion;
	}

	public int getAppVersion(){
		return appVersion;
	}

	public void setSettingsSaved(boolean settingsSaved){
		this.settingsSaved = settingsSaved;
	}

	public boolean isSettingsSaved(){
		return settingsSaved;
	}

	public void setAppTitle(String appTitle){
		this.appTitle = appTitle;
	}

	public String getAppTitle(){
		return appTitle;
	}

	public void setUserKey(Object userKey){
		this.userKey = userKey;
	}

	public Object getUserKey(){
		return userKey;
	}

	public void setUpdateTitle(String updateTitle){
		this.updateTitle = updateTitle;
	}

	public String getUpdateTitle(){
		return updateTitle;
	}

	public void setSliderCat(String sliderCat){
		this.sliderCat = sliderCat;
	}

	public String getSliderCat(){
		return sliderCat;
	}

	public void setCategories(List<String> categories){
		this.categories = categories;
	}

	public List<String> getCategories(){
		return categories;
	}

	public void setForceUpdate(boolean forceUpdate){
		this.forceUpdate = forceUpdate;
	}

	public boolean isForceUpdate(){
		return forceUpdate;
	}

	public void setSliderEnabled(boolean sliderEnabled){
		this.sliderEnabled = sliderEnabled;
	}

	public boolean isSliderEnabled(){
		return sliderEnabled;
	}

	public void setSections(List<SectionsItem> sections){
		this.sections = sections;
	}

	public List<SectionsItem> getSections(){
		return sections;
	}
}