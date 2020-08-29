package com.pinkfry.tech.Tezzo.Model.AttendanceModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AttendanceModel implements Serializable {

	@SerializedName("msg")
	private String msg;

	@SerializedName("success")
	private boolean success;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	@Override
 	public String toString(){
		return 
			"AttendanceModel{" + 
			"msg = '" + msg + '\'' + 
			",success = '" + success + '\'' + 
			"}";
		}
}