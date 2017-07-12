package com.example.administrator.databindingdemo;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.databindingdemo.databinding.ActivityTowWayBindingBinding;

public class TowWayBindingActivity extends AppCompatActivity {

	ActivityTowWayBindingBinding mBinding;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.
			setContentView(this,R.layout.activity_tow_way_binding);
		FormModel model = new FormModel("sun", "12345");
		mBinding.setModel(model);
		model.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
			@Override
			public void onPropertyChanged(Observable sender, int propertyId) {
				Toast.makeText(TowWayBindingActivity.this, String.valueOf(propertyId),
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}
