package com.pinkfry.tech.Tezzo.Model.NoticeModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NoticeModel implements Serializable {

	@SerializedName("date")
	private String date;

	@SerializedName("gymId")
	private String gymId;

	@SerializedName("__v")
	private int V;

	@SerializedName("description")
	private String description;

	@SerializedName("_id")
	private String id;

	@SerializedName("title")
	private String title;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setGymId(String gymId){
		this.gymId = gymId;
	}

	public String getGymId(){
		return gymId;
	}

	public void setV(int V){
		this.V = V;
	}

	public int getV(){
		return V;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"NoticeModel{" + 
			"date = '" + date + '\'' + 
			",gymId = '" + gymId + '\'' + 
			",__v = '" + V + '\'' + 
			",description = '" + description + '\'' + 
			",_id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}