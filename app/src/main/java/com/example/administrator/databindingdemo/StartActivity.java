package com.example.administrator.databindingdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
	}

	public void gotoBasicDemo(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	public void gotoListDemo(View view) {
		Intent intent = new Intent(this, ListActivity.class);
		startActivity(intent);
	}

	public void gotoCustomDemo(View view) {
		Intent intent = new Intent(this, CustomActivity.class);
		startActivity(intent);
	}

	public void goToTwoWay(View view) {
		Intent intent = new Intent(this, TowWayBindingActivity.class);
		startActivity(intent);
	}

	public void gotoLambdaDemo(View view) {
		Intent intent = new Intent(this, LambdaActivity.class);
		startActivity(intent);
	}

	public void gotoAnimationDemo(View view) {
		Intent intent = new Intent(this, AnimationActivity.class);
		startActivity(intent);
	}
}
