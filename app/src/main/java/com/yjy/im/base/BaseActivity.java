package com.yjy.im.base;

import java.util.Stack;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

	//存储“我的空间”文件路径
	public static Stack<String> stackFilePath = null;
	protected Context mContext = null;
	private ProgressDialog pg = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		pg = new ProgressDialog(mContext);
	}

	public ProgressDialog getProgressDialog() {
		return pg;
	}


}
