package com.pinkfry.tech.Tezzo.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MsgDiet implements Serializable {

    @SerializedName("diet")
    private List<DietItem> diet;

    @SerializedName("_id")
    private String id;
    @SerializedName("diet_name")
    private String dietName;
    @SerializedName("gymId")
    private String gymId;
    @SerializedName("diet_id")
    private String dietId;

    @Override
    public String toString() {
        return "MsgDiet{" +
                "diet=" + diet +
                ", _id='" + id + '\'' +
                ", dietName='" + dietName + '\'' +
                ", gymId='" + gymId + '\'' +
                ", dietId='" + dietId + '\'' +
                '}';
    }

    public void setDiet(List<DietItem> diet) {
        this.diet = diet;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }

    public void setGymId(String gymId) {
        this.gymId = gymId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    public List<DietItem> getDiet() {
        return diet;
    }

    public String getId() {
        return id;
    }

    public String getDietName() {
        return dietName;
    }

    public String getGymId() {
        return gymId;
    }

    public String getDietId() {
        return dietId;
    }

    public MsgDiet(List<DietItem> diet, String id, String dietName, String gymId, String dietId) {
        this.diet = diet;
        this.id = id;
        this.dietName = dietName;
        this.gymId = gymId;
        this.dietId = dietId;
    }
}
