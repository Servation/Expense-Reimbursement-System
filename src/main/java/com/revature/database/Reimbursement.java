package com.revature.database;


import javax.persistence.*;


@Entity
@Table(name = "reimbursement")
public class Reimbursement {
    @Column(name = "Reimbursement_ID")
    private int reimbursement_ID;
    @Column(name = "User_ID")
    private int user_ID;
    @Column(name = "Title")
    private String title;
    @Column(name = "Amount")
    private double amount;
    @Column(name = "Detail")
    private String detail;
    @Column(name = "Date")
    private String date;
    @Column(name = "Status")
    private String status;

    public Reimbursement(){}

    public Reimbursement(int user_ID, String title, double amount, String detail, String date, String status) {
        this.user_ID = user_ID;
        this.title = title;
        this.amount = amount;
        this.detail = detail;
        this.date = date;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
