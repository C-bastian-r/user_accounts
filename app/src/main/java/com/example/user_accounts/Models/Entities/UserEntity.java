package com.example.user_accounts.Models.Entities;

public class UserEntity {
    private String userDocument;
    private String userNickName;
    private String userNames;
    private String userLastNames;
    private String password;


    public UserEntity() {
    }

    public UserEntity(String userDocument, String userNickName, String userNames, String userLastNames, String password) {
        this.userDocument = userDocument;
        this.userNickName = userNickName;
        this.userNames = userNames;
        this.userLastNames = userLastNames;
        this.password = password;
    }

    //region getters y setters

    public String getUserDocument() {
        return userDocument;
    }

    public void setUserDocument(String userDocument) {
        this.userDocument = userDocument;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public String getUserLastNames() {
        return userLastNames;
    }

    public void setUserLastNames(String userLastNames) {
        this.userLastNames = userLastNames;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //endregion


    @Override
    public String toString() {
        return "UserEntity{" +
                "userDocument='" + userDocument + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", userNames='" + userNames + '\'' +
                ", userLastNames='" + userLastNames + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
