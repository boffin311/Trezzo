package com.pinkfry.tech.Tezzo.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WorkoutItem implements Serializable {

	@SerializedName("workout_reps")
	private String workoutReps;

	@SerializedName("workout_name")
	private String workoutName;

	@SerializedName("arr_id")
	private String arrId;

	@SerializedName("workout_sets")
	private int workoutSets;

	@SerializedName("id")
	private String id;

	public void setWorkoutReps(String workoutReps){
		this.workoutReps = workoutReps;
	}

	public String getWorkoutReps(){
		return workoutReps;
	}

	public void setWorkoutName(String workoutName){
		this.workoutName = workoutName;
	}

	public String getWorkoutName(){
		return workoutName;
	}

	public void setArrId(String arrId){
		this.arrId = arrId;
	}

	public String getArrId(){
		return arrId;
	}

	public void setWorkoutSets(int workoutSets){
		this.workoutSets = workoutSets;
	}

	public int getWorkoutSets(){
		return workoutSets;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"WorkoutItem{" + 
			"workout_reps = '" + workoutReps + '\'' + 
			",workout_name = '" + workoutName + '\'' + 
			",arr_id = '" + arrId + '\'' + 
			",workout_sets = '" + workoutSets + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}