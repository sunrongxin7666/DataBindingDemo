package com.example.administrator.databindingdemo;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.databindingdemo.databinding.ActivityCustomBinding;

import java.util.Random;

public class CustomActivity extends AppCompatActivity {

	ActivityCustomBinding mBinding;
	Random mRandom = new Random(System.currentTimeMillis());
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this,R.layout.activity_custom);
		Student student = new Student("guo","xiaopang","https://avatars2.githubusercontent.com/u/1106500?v=2&s=460");
		mBinding.setStudent(student);
		mBinding.setV(mRandom.nextBoolean());
	}
}
