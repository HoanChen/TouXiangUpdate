package com.tiqiuzhe.touxiangupdate.bean;

/**
 * Created by JZ.W on 20/4/2016.
 * Description:
 */
public class Person {
    private String name;
    private int id;
    private int avatar;
    private boolean checked;

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Person(String name, int id, int avatar) {
        this.name = name;
        this.id = id;
        this.avatar = avatar;
    }
}
