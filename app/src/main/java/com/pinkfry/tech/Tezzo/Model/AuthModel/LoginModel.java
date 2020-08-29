package com.pinkfry.tech.Tezzo.Model.AuthModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
public class LoginModel implements Serializable {

	@SerializedName("msg")
	private List<MsgItem> msg;

	@SerializedName("success")
	private boolean success;
	@SerializedName("data")
	private  String data;

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

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
			",data= '" +data+ '\'' +
			"}";
		}
}