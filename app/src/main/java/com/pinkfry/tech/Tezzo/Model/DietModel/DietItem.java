package com.pinkfry.tech.Tezzo.Model.DietModel;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;


public class DietItem implements Serializable {

	@SerializedName("item")
	private List<ItemItem> item;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	public void setItem(List<ItemItem> item){
		this.item = item;
	}

	public List<ItemItem> getItem(){
		return item;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	@Override
 	public String toString(){
		return 
			"DietItem{" + 
			"item = '" + item + '\'' + 
			",id = '" + id + '\'' + 
			",time = '" + time + '\'' + 
			"}";
		}
}