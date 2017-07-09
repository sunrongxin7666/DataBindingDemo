package com.example.administrator.databindingdemo;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by sunrongxin on 2017/7/9.
 */

public class BindingViewHolder<T extends ViewDataBinding>
		extends RecyclerView.ViewHolder {

	private T mBinding;

	public BindingViewHolder(T binding) {
		//获得layout标签下，那个真正的view
		super(binding.getRoot());
		mBinding = binding;
	}

	public T getBinding() {
		return mBinding;
	}
}
