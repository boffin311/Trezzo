package com.pinkfry.tech.Tezzo.Model.FeeModel;

public class PaymentReminderModel {
    String date_created;
    Long membership_no;
    String name;
    String next_due;
    int status;

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public void setMembership_no(Long membership_no) {
        this.membership_no = membership_no;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNext_due(String next_due) {
        this.next_due = next_due;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate_created() {
        return date_created;
    }

    public Long getMembership_no() {
        return membership_no;
    }

    public String getName() {
        return name;
    }

    public String getNext_due() {
        return next_due;
    }

    public int getStatus() {
        return status;
    }
    public PaymentReminderModel(){}
    public PaymentReminderModel(String date_created, Long membership_no, String name, String next_due, int status) {
        this.date_created = date_created;
        this.membership_no = membership_no;
        this.name = name;
        this.next_due = next_due;
        this.status = status;
    }
}
