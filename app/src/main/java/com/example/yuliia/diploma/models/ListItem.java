package com.example.yuliia.diploma.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ListItem {
    private int ItemId;
    private String ItemName;
    int ItemBought;
    int ItemChecked;
    private String ItemNote;
    private Date DateAdded;

    public ListItem(int itemId, String ItemName, int itemBought, int itemChecked, String itemNote, Date dateAdded) {
        this.ItemId = itemId;
        this.ItemName = ItemName;
        this.ItemBought = itemBought;
        this.ItemChecked = itemChecked;
        this.ItemNote = itemNote;
        this.DateAdded = dateAdded;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public int isItemBought() {
        return ItemBought;
    }

    public void setItemBought(int itemBought) {
        ItemBought = itemBought;
    }

    public int isItemChecked() {
        return ItemChecked;
    }

    public void setItemChecked(int itemChecked) {
        ItemChecked = itemChecked;
    }

    public String getItemNote() {
        return ItemNote;
    }

    public void setItemNote(String itemNote) {
        ItemNote = itemNote;
    }

    public String getDateAdded() {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return format.format(DateAdded);
        //return DateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        DateAdded = dateAdded;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

}
