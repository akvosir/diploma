package com.example.yuliia.diploma.models;

import java.util.Date;

public class WishList {
    private int ListId;
    private String UserId;
    private String ListName;
    private Date DateCreated;
    private int IsPrivate;

    public WishList(int listId, String userId, String listName, Date dateCreated, int isPrivate) {
        this.ListId = listId;
        this.UserId = userId;
        this.ListName = listName;
        this.DateCreated = dateCreated;
        this.IsPrivate = isPrivate;
    }

    public int getListId() {
        return ListId;
    }

    public void setListId(int listId) {
        ListId = listId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getListName() {
        return ListName;
    }

    public void setListName(String listName) {
        ListName = listName;
    }

    public Date getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        DateCreated = dateCreated;
    }

    public int isPrivate() {
        return IsPrivate;
    }

    public void setPrivate(int aPrivate) {
        IsPrivate = aPrivate;
    }
}
