package com.example.administrator.databindingdemo;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by sunrongxin on 2017/7/9.
 */

public class DemoBindingAdapter {

	@BindingAdapter({"imageUrl","placeholder"})
	public static void loadImageFromUrl(ImageView imageView, String url, Drawable drawable){
		Glide.with(imageView.getContext())
				.load(url)
				.placeholder(drawable)
				.into(imageView);
	}
}
