package com.example.administrator.databindingdemo;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.databindingdemo.databinding.ActivityListBinding;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

	ActivityListBinding mBinding;
	StudentAdapter mAdapter;

	public class Presenter{
		public void addItem(View view){
			mAdapter.add(new Student("Sun","Wukong"));
		}

		public void deleteItem(View view){
			mAdapter.delete();
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this,R.layout.activity_list);
		mBinding.setPresenter(new Presenter());

		mAdapter = new StudentAdapter(this);
		mBinding.recyclerView.setAdapter(mAdapter);
		mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
		mAdapter.setListener(new StudentAdapter.OnItemOnClickListener() {
			@Override
			public void onStudentClick(Student student) {
				Toast.makeText(ListActivity.this,student.toString(), Toast.LENGTH_SHORT).show();
			}
		});
		List<Student> list = new ArrayList<>();
		list.add(new Student("zhang","san"));
		list.add(new Student("li","si",false));
		list.add(new Student("han","meimei",false));
		mAdapter.addAll(list);
	}
}
