package com.pinkfry.tech.Tezzo.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataItem implements Serializable {

	@SerializedName("plan_time")
	private int planTime;

	@SerializedName("gymId")
	private String gymId;

	@SerializedName("__v")
	private int V;

	@SerializedName("plan_price")
	private int planPrice;

	@SerializedName("_id")
	private String id;

	@SerializedName("plan_id")
	private String planId;

	@SerializedName("plan_name")
	private String planName;

	@SerializedName("plan_status")
	private int planStatus;

	public void setPlanTime(int planTime){
		this.planTime = planTime;
	}

	public int getPlanTime(){
		return planTime;
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

	public void setPlanPrice(int planPrice){
		this.planPrice = planPrice;
	}

	public int getPlanPrice(){
		return planPrice;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPlanId(String planId){
		this.planId = planId;
	}

	public String getPlanId(){
		return planId;
	}

	public void setPlanName(String planName){
		this.planName = planName;
	}

	public String getPlanName(){
		return planName;
	}

	public void setPlanStatus(int planStatus){
		this.planStatus = planStatus;
	}

	public int getPlanStatus(){
		return planStatus;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"plan_time = '" + planTime + '\'' + 
			",gymId = '" + gymId + '\'' + 
			",__v = '" + V + '\'' + 
			",plan_price = '" + planPrice + '\'' + 
			",_id = '" + id + '\'' + 
			",plan_id = '" + planId + '\'' + 
			",plan_name = '" + planName + '\'' + 
			",plan_status = '" + planStatus + '\'' + 
			"}";
		}
}