package com.example.administrator.databindingdemo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by sunrongxin on 2017/7/11.
 */

public class FormModel extends BaseObservable {
	private String name;
	private String password;
	@Bindable
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
		notifyPropertyChanged(BR.name);
	}

	@Bindable
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		notifyPropertyChanged(BR.password);
	}

	public FormModel(String name, String password) {
		this.name = name;
		this.password = password;

	}
}
