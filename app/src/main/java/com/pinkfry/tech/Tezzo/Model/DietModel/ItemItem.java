package com.pinkfry.tech.Tezzo.Model.DietModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ItemItem implements Serializable {

	@SerializedName("color")
	private String color;

	@SerializedName("id")
	private double id;

	@SerializedName("value")
	private String value;

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	public void setId(double id){
		this.id = id;
	}

	public double getId(){
		return id;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"ItemItem{" + 
			"color = '" + color + '\'' + 
			",id = '" + id + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}