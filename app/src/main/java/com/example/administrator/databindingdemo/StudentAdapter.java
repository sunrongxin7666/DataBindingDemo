package com.example.administrator.databindingdemo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sunrongxin on 2017/7/9.
 */

public class StudentAdapter extends RecyclerView.Adapter<BindingViewHolder>{
	private static final int ITEM_VIEW_TYPE_1 = 1;
	private static final int ITEM_VIEW_TYPE_2 = 2;

	private final LayoutInflater mInflater;
	private OnItemOnClickListener mListener;
	private List<Student> mList;

	public StudentAdapter(Context context){
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mList = new ArrayList<>();
	}

	//创建ViewHolder
	@Override
	public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ViewDataBinding binding;
		if(viewType == ITEM_VIEW_TYPE_1){
			binding = DataBindingUtil.inflate(mInflater,R.layout.item_student_man,parent,false);
		}
		else {
			binding =DataBindingUtil.inflate(mInflater, R.layout.item_student_woman,parent,false);
		}
		return new BindingViewHolder(binding);
	}

	@Override
	public int getItemViewType(int position) {
		if(mList.get(position).getMan()){
			return ITEM_VIEW_TYPE_1;
		}
		else {
			return ITEM_VIEW_TYPE_2;
		}
	}

	//绑定ViewHolder中各个属性的值；
	@Override
	public void onBindViewHolder(BindingViewHolder holder, int position) {
		final Student student = mList.get(position);
		holder.getBinding().setVariable(BR.item,student);
		holder.getBinding().executePendingBindings();
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mListener!=null){
					mListener.onStudentClick(student);
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	public void setListener(OnItemOnClickListener listener) {
		mListener = listener;
	}
	public OnItemOnClickListener getListener() {
		return mListener;
	}

	interface OnItemOnClickListener{
		void onStudentClick(Student student);
	}

	public void addAll(List<? extends Student> list){
		mList.addAll(list);
	}

	Random mRandom = new Random(System.currentTimeMillis());
	public void add(Student student){
		int position = mRandom.nextInt(mList.size()+1);
		mList.add(position,student);
		//动画效果
		notifyItemInserted(position);
	}

	public void delete(){
		if(mList.size()>0){
			int position = mRandom.nextInt(mList.size());
			mList.remove(position);
			//动画效果
			notifyItemRemoved(position);
		}
		else
			return;
	}
}
