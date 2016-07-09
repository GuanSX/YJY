package com.yjy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.yjy.R;
import com.yjy.activity.EditCoursewareActivity;
import com.yjy.activity.HomeActivity;
import com.yjy.im.base.Constant;
import com.yjy.im.base.Current;
import com.yjy.im.base.BaseFragment;
import com.yjy.im.manager.CoursewareItemAdapter;
import com.yjy.im.manager.MenuManager;
import com.yjy.im.model.Courseware;
import com.yjy.im.utils.ShowMsgUtil;

import java.util.ArrayList;
import java.util.List;

public class UploadRecordFragment extends BaseFragment {

	//private LinearLayout noCoursewareLinearLayout;
	public CoursewareItemAdapter uploadrecordadapter;
	private ListView uploadrecord;
	private LinearLayout noitem;
	private SwipeRefreshLayout swiperefreshlayout;
	private List<Courseware> mylist;
	private AdapterView.OnItemClickListener clickListener;
	private AdapterView.OnItemLongClickListener longClickListener;
	private View.OnClickListener menuItenListener;//点击底部弹出框的选项时触发事件的监听器
	private View.OnTouchListener touchListener;//点击其他弹出框以外的区域时事件的监听器
	private String TAG = "UploadRecordFragment";

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
	Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_uploadrecord, container,
				false);
		Init(view);
		return view;
	}

	private void Init(View view) {
		uploadrecord = (ListView) (view.findViewById(R.id.list_uploadrecord));
		noitem = (LinearLayout) view.findViewById(R.id.nouploadrecord_layout);
		swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id
				.uploadrecord_swiperefresh_layout);

		mylist = new ArrayList<Courseware>();

		//设置下拉刷新
		swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadAllCoursewareList(mylist, null, Current.getCurrentuser().getId(), -1);
				//uploadrecordadapter.notifyDataSetChanged();
			}
		});
		swiperefreshlayout.setColorScheme(android.R.color.holo_green_dark
				, android.R.color.holo_green_light, android.R.color.holo_red_light);

		InitListener();


		loadAllCoursewareList(mylist, null, Current.getCurrentuser().getId(), -1);
	}

	@Override
	protected void doLoadAllCoursewareListSuccess() {
		swiperefreshlayout.setRefreshing(false);

		if (mylist.size() == 0) {
			//没记录时
			noitem.setVisibility(View.VISIBLE);
			uploadrecord.setVisibility(View.INVISIBLE);
		} else {
			//有记录时
			noitem.setVisibility(View.GONE);
			uploadrecord.setVisibility(View.VISIBLE);

			ShowMsgUtil.show(((HomeActivity) getActivity()).getContext(), "刷新了一下 upload");
			this.uploadrecordadapter = new CoursewareItemAdapter(((HomeActivity) getActivity())
					.getContext(), Current.getCurrentfragment(),
					this.mylist);
			uploadrecord.setAdapter(uploadrecordadapter);

			/*
			*
			* 如果ListView中的单个Item的view中存在checkbox，button等view，会导致ListView.setOnItemClickListener无效，
			* 事件会被子View捕获到，ListView无法捕获处理该事件.
			* 解决方法：在checkbox、button对应的view处加
			* android:focusable="false"
			* android:clickable="false"
			* android:focusableInTouchMode="false"
			* 其中focusable是关键
			 */
			uploadrecord.setOnItemClickListener(clickListener);
			uploadrecord.setOnItemLongClickListener(longClickListener);
		}


	}


	//初始化点击item的监听事件
	private void InitListener() {

		this.clickListener = new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ShowMsgUtil.show(getActivity(), "upload " +
						"record点击了一下");
				Log.e(TAG, "upload record点击了一下");
			}
		};

		longClickListener = new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long
					id) {
				ShowMsgUtil.show(getActivity(), "upload " +
						"record点击了一下" +
						" " +
						"long");
				Log.e(TAG, "upload record点击了一下 long");
				return true;
			}
		};
	}


	@Override
	//用于接收打开的activit结束后返回的数据、、
	//fragment的startActivityforResult方法由item中more按钮触发的popupWindow中的按钮触发
	// （该方法写在了MenuManager）
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		Log.e(TAG, "requestcode:" + Integer.toString(requestCode) + "\t resultcode:" + Integer
				.toString(resultCode));
		if (requestCode == Constant.POPITEM_EDIT && resultCode == getActivity().RESULT_OK) {
			int position = data.getIntExtra("position", -1);
			Courseware c=(Courseware) data.getExtras().get("courseware");
			//mylist.set(position, (Courseware) data.getExtras().get("courseware"));
			Log.e(TAG,c.getMoney());
			uploadrecordadapter.setItem(position,c );
			uploadrecordadapter.notifyDataSetChanged();


			if (requestCode == Constant.requestcode_uploadrecordfragment_download && resultCode ==
					Constant.resultcode_uploadrecordfragment) {

			} else if (requestCode == Constant.requestcode_uploadrecordfragment_refresh &&
					resultCode == Constant.resultcode_uploadrecordfragment) {

			} else if (requestCode == Constant.requestcode_uploadrecordfragment_edit &&
					resultCode ==

					Constant.resultcode_uploadrecordfragment) {
				Log.e(TAG, "get result success");
			}
		}

	}

	public void myStartActivityForResult(Intent intent,int requestcode)
	{
		this.startActivityForResult(intent,requestcode);
	}


	@Override
	public void fragmentResult(int resultCode, Bundle bundle) {
		//super.fragmentResult(resultCode, bundle);
		//删除某一个item后刷新listview
		if (resultCode == Constant.POPITEM_DELETE) {
			uploadrecordadapter.notifyDataSetChanged();
		}
	}
}
