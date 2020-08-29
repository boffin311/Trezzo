package com.pinkfry.tech.Tezzo.Model.WorkOutModel;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class WorkOutModel implements Serializable {

	@SerializedName("msg")
	private List<MsgWorkOut> msg;

	@SerializedName("success")
	private boolean success;

	public void setMsg(List<MsgWorkOut> msga){
		this.msg = msga;
	}

	public List<MsgWorkOut> getMsg(){
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
			"WorkOutModel{" + 
			"msga = '" + msg + '\'' +
			",success = '" + success + '\'' + 
			"}";
		}
}