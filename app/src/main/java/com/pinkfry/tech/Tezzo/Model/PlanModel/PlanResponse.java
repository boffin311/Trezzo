package com.pinkfry.tech.Tezzo.Model.PlanModel;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.pinkfry.tech.Tezzo.Model.PlanModel.DataItem;

public class PlanResponse implements Serializable {

	@SerializedName("msg")
	private String msg;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("success")
	private boolean success;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
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
			"PlanResponse{" + 
			"msg = '" + msg + '\'' + 
			",data = '" + data + '\'' + 
			",success = '" + success + '\'' + 
			"}";
		}
}