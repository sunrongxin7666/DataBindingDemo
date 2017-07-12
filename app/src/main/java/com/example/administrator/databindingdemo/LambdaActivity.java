package com.example.administrator.databindingdemo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.databindingdemo.databinding.ActivityLambdaBinding;

public class LambdaActivity extends AppCompatActivity {

	ActivityLambdaBinding mBinding;
	public class Presenter{
		public void onStudentClick(Student student){
			Toast.makeText(LambdaActivity.this, student.toString(), Toast.LENGTH_SHORT)
					.show();
		}
		public void onStudentLongClick(Student student, Context context){
			Toast.makeText(LambdaActivity.this, "LongClick: "+student.toString(), Toast.LENGTH_SHORT)
					.show();
		}
		public void onFocusChanged(Student student){
			Toast.makeText(LambdaActivity.this, "onFocusChanged", Toast.LENGTH_SHORT)
					.show();
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil
				           .setContentView(this,R.layout.activity_lambda);
		mBinding.setPresenter(new Presenter());
		mBinding.setStudent(new Student("guo","xiaopang"));

	}
}
