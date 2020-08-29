package com.pinkfry.tech.Tezzo.Model.WorkOutModel;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MsgWorkOut implements Serializable {

	@SerializedName("gymId")
	private String gymId;

	@SerializedName("workout")
	private List<SingleDayWorkoutModel> workout;

	@SerializedName("__v")
	private int V;

	@SerializedName("_id")
	private String id;

	@SerializedName("plan_name")
	private String planName;

	public void setGymId(String gymId){
		this.gymId = gymId;
	}

	public String getGymId(){
		return gymId;
	}

	public void setWorkout(List<SingleDayWorkoutModel> workout){
		this.workout = workout;
	}

	public List<SingleDayWorkoutModel> getWorkout(){
		return workout;
	}

	public void setV(int V){
		this.V = V;
	}

	public int getV(){
		return V;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPlanName(String planName){
		this.planName = planName;
	}

	public String getPlanName(){
		return planName;
	}

	@Override
 	public String toString(){
		return 
			"MsgItem{" +
			"gymId = '" + gymId + '\'' + 
			",workout = '" + workout + '\'' + 
			",__v = '" + V + '\'' + 
			",_id = '" + id + '\'' + 
			",plan_name = '" + planName + '\'' + 
			"}";
		}
}