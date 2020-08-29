package com.pinkfry.tech.Tezzo.Model.WorkOutModel;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SingleDayWorkoutModel implements Serializable {

	@SerializedName("workout_day")
	private String workoutDay;

	@SerializedName("workout")
	private List<WorkoutItem> workout;

	@SerializedName("id")
	private String id;

	@SerializedName("body_part")
	private List<List<String>> bodyPart;

	public void setWorkoutDay(String workoutDay){
		this.workoutDay = workoutDay;
	}

	public String getWorkoutDay(){
		return workoutDay;
	}

	public void setWorkout(List<WorkoutItem> workout){
		this.workout = workout;
	}

	public List<WorkoutItem> getWorkout(){
		return workout;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setBodyPart(List<List<String>> bodyPart){
		this.bodyPart = bodyPart;
	}

	public List<List<String>> getBodyPart(){
		return bodyPart;
	}

	@Override
 	public String toString(){
		return 
			"SingelDayWorkoutModel{" + 
			"workout_day = '" + workoutDay + '\'' + 
			",workout = '" + workout + '\'' + 
			",id = '" + id + '\'' + 
			",body_part = '" + bodyPart + '\'' + 
			"}";
		}
}