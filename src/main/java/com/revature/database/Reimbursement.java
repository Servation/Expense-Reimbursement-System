package com.revature.database;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

enum Status{
    Denied, Pending, Approved
}

@Entity
@Table(name = "reimbursement")
public class Reimbursement {
    private int reimbursement_ID;
    private int user_ID;
    private String title;
    private double amount;
    private String detail;
    private String date;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Reimbursement(){}

    public Reimbursement(int user_ID, String title, double amount, String detail, String date, String status) {
        this.user_ID = user_ID;
        this.title = title;
        this.amount = amount;
        this.detail = detail;
        this.date = date;
        this.status = Status.valueOf(status);
    }

    public int getReimbursement_ID() {
        return reimbursement_ID;
    }

    public void setReimbursement_ID(int reimbursement_ID) {
        this.reimbursement_ID = reimbursement_ID;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

    @Override
    public String toString() {
        return "Reimbursement ID: " + reimbursement_ID +
                "\tFrom User: " + user_ID +
                "\tFor: " + title +
                "\tAmount: " + amount +
                "\tDetail: " + detail +
                "\tDate: " + date +
                "\tStatus: " + status;
    }
}
