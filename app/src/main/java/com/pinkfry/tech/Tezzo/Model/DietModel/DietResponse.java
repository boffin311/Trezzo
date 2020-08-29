package com.pinkfry.tech.Tezzo.Model.DietModel;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DietResponse implements Serializable {

	@SerializedName("msg")
	private List<MsgDiet> msg;

	@SerializedName("success")
	private boolean success;

	public void setMsg(List<MsgDiet> msg){
		this.msg = msg;
	}

	public List<MsgDiet> getMsg(){
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
			"DietResponse{" + 
			"msg = '" + msg + '\'' + 
			",success = '" + success + '\'' + 
			"}";
		}
}