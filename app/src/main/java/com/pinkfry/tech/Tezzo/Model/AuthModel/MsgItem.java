package com.pinkfry.tech.Tezzo.Model.AuthModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MsgItem implements Serializable {

	@SerializedName("firstname")
	private String firstname;

	@SerializedName("address")
	private String address;

	@SerializedName("disease")
	private String disease;

	@SerializedName("next_due")
	private String nextDue;

	@SerializedName("gender")
	private String gender;

	@SerializedName("mobile_no")
	private String mobileNo;

	@SerializedName("membership_no")
	private String membershipNo;

	@SerializedName("weight")
	private String weight;

	@SerializedName("lastname")
	private String lastname;

	@SerializedName("password")
	private String password;

	@SerializedName("gymId")
	private String gymId;

	@SerializedName("dob")
	private String dob;

	@SerializedName("__v")
	private int V;

	@SerializedName("_id")
	private String id;

	@SerializedName("age")
	private int age;

	@SerializedName("email")
	private String email;

	@SerializedName("doj")
	private String doj;

	@SerializedName("status")
	private int status;

	@SerializedName("height")
	private String height;

	public void setFirstname(String firstname){
		this.firstname = firstname;
	}

	public String getFirstname(){
		return firstname;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setDisease(String disease){
		this.disease = disease;
	}

	public String getDisease(){
		return disease;
	}

	public void setNextDue(String nextDue){
		this.nextDue = nextDue;
	}

	public String getNextDue(){
		return nextDue;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setMobileNo(String mobileNo){
		this.mobileNo = mobileNo;
	}

	public String getMobileNo(){
		return mobileNo;
	}

	public void setMembershipNo(String membershipNo){
		this.membershipNo = membershipNo;
	}

	public String getMembershipNo(){
		return membershipNo;
	}

	public void setWeight(String weight){
		this.weight = weight;
	}

	public String getWeight(){
		return weight;
	}

	public void setLastname(String lastname){
		this.lastname = lastname;
	}

	public String getLastname(){
		return lastname;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setGymId(String gymId){
		this.gymId = gymId;
	}

	public String getGymId(){
		return gymId;
	}

	public void setDob(String dob){
		this.dob = dob;
	}

	public String getDob(){
		return dob;
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

	public void setAge(int age){
		this.age = age;
	}

	public int getAge(){
		return age;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setDoj(String doj){
		this.doj = doj;
	}

	public String getDoj(){
		return doj;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setHeight(String height){
		this.height = height;
	}

	public String getHeight(){
		return height;
	}

	@Override
 	public String toString(){
		return 
			"MsgItem{" + 
			"firstname = '" + firstname + '\'' + 
			",address = '" + address + '\'' + 
			",disease = '" + disease + '\'' + 
			",next_due = '" + nextDue + '\'' + 
			",gender = '" + gender + '\'' + 
			",mobile_no = '" + mobileNo + '\'' + 
			",membership_no = '" + membershipNo + '\'' + 
			",weight = '" + weight + '\'' + 
			",lastname = '" + lastname + '\'' + 
			",password = '" + password + '\'' + 
			",gymId = '" + gymId + '\'' + 
			",dob = '" + dob + '\'' + 
			",__v = '" + V + '\'' + 
			",_id = '" + id + '\'' + 
			",age = '" + age + '\'' + 
			",email = '" + email + '\'' + 
			",doj = '" + doj + '\'' + 
			",status = '" + status + '\'' + 
			",height = '" + height + '\'' + 
			"}";
		}
}