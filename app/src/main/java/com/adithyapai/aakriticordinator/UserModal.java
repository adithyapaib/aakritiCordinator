package com.adithyapai.aakriticordinator;

public class UserModal {
    private String qid, id, name, email, password, imgsrc, transactionid, balance;

    public UserModal(String qid, String id, String name, String email, String password, String imgsrc, String transactionid, String balance) {
        this.qid = qid;
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.imgsrc = imgsrc;
        this.transactionid = transactionid;
        this.balance = balance;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getTransactionid() {
        return transactionid;
    }


    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }






}
