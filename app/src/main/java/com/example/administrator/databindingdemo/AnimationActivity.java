package com.example.administrator.databindingdemo;

import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.ViewGroup;

import com.example.administrator.databindingdemo.databinding.ActivityAnimationBinding;

public class AnimationActivity extends AppCompatActivity {
	ActivityAnimationBinding mBinding;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this,R.layout.activity_animation);
		mBinding.setPresenter(new AnimatorPresenter());
		mBinding.addOnRebindCallback(new OnRebindCallback() {
			@Override
			public boolean onPreBind(ViewDataBinding binding) {
				ViewGroup viewGroup = (ViewGroup) binding.getRoot();
				TransitionManager.beginDelayedTransition(viewGroup);
				return true;
			}
		});
	}
	public class AnimatorPresenter{
		public void onCheckedChanged(boolean isChecked){
			mBinding.setShowImage(isChecked);
		}
	}
}
