package com.pinkfry.tech.Tezzo.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
public class LoginModel implements Serializable {

	@SerializedName("msg")
	private List<MsgItem> msg;

	@SerializedName("success")
	private boolean success;

	public void setMsg(List<MsgItem> msg){
		this.msg = msg;
	}

	public List<MsgItem> getMsg(){
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
			"LoginModel{" + 
			"msg = '" + msg + '\'' + 
			",success = '" + success + '\'' + 
			"}";
		}
}