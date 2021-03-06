package com.example.administrator.databindingdemo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Administrator on 2017-7-7.
 */

public class Student extends BaseObservable{
    private String firstName;
    private String lastName;
    private Boolean isMan = true;
    private String avatar;



    //ObservableChar fullName; //如果只要几个字段是要通知变化的可以这样做。
    //ObservableArrayMap mArrayMap;
    //ObservableArrayList mList;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        //提示该属性刷新了
        notifyPropertyChanged(BR.firstName);
        //提示所有的属性都刷新
        //notifyAll();
    }

    //注解来提示Binding生成这个字段
    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Student(String firstName, String lastName, Boolean isMan) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isMan = isMan;
    }

    public Student(String firstName, String lastName, String url) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = url;
    }

    @Override
    public String toString() {
        return "Student{" +
                       "firstName='" + firstName + '\'' +
                       ", lastName='" + lastName + '\'' +
                       ", isMan=" + isMan +
                       '}';
    }

    public Boolean getMan() {
        return isMan;
    }

    public void setMan(Boolean man) {
        isMan = man;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        notifyChange();
    }
}
