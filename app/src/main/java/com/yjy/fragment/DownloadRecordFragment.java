package com.yjy.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.yjy.R;
import com.yjy.activity.HomeActivity;
import com.yjy.im.base.BaseFragment;
import com.yjy.im.manager.CoursewareItemAdapter;
import com.yjy.im.model.Courseware;
import com.yjy.im.utils.ShowMsgUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guan on 2016/5/26.
 */
public class DownloadRecordFragment extends BaseFragment {

	private ListView downloadrecord;
	private LinearLayout noitem;
	private SwipeRefreshLayout swiperefreshlayout;

	private List<Courseware> mylist;
	private CoursewareItemAdapter downloadrecordadapter;

	private AdapterView.OnItemClickListener clickListener;
	private AdapterView.OnItemLongClickListener longClickListener;

	private boolean t=false;

	Handler mhandler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//super.handleMessage(msg);
			//downloadrecord.setVisibility(View.);
			if(t)
			{
				t=false;
			}else
			{
				t=true;
			}
			//noitem.setVisibility(View.VISIBLE);
			Test();
		}
	};

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
	Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_downloadrecord, container, false);
		Init(view);
		return view;
	}

	private void Init(View view) {
		downloadrecord = (ListView) (view.findViewById(R.id.list_downloadrecord));
		noitem = (LinearLayout) view.findViewById(R.id.nodownloadrecord_layout);
		swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id
				.downloadrecord_swiperefresh_layout);


		mylist = new ArrayList<Courseware>();

		swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				//loadAllCoursewareList(mylist, "null");
				mhandler.sendMessageDelayed(new Message(),2000);


			}
		});
		swiperefreshlayout.setColorScheme(android.R.color.holo_green_dark
				, android.R.color.holo_green_light, android.R.color.holo_red_light);
		//loadAllCoursewareList(mylist, "null");

		InitListener();

	}

	protected  void Test()
	{
		swiperefreshlayout.setRefreshing(false);
		if(t)
		{
			//有记录时
			noitem.setVisibility(View.GONE);
			downloadrecord.setVisibility(View.VISIBLE);

			downloadrecord.setOnItemClickListener(clickListener);
			downloadrecord.setOnItemLongClickListener(longClickListener);
		}else
		{
			//没记录时
			downloadrecord.setVisibility(View.INVISIBLE);
			noitem.setVisibility(View.VISIBLE);
		}

	}


	//初始化点击item的监听事件
	private void InitListener()
	{
		clickListener=new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ShowMsgUtil.show(((HomeActivity)getActivity()).getContext(),"download record点击了一下");
			}
		};

		longClickListener=new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long
					id) {
				ShowMsgUtil.show(((HomeActivity)getActivity()).getContext(),"download record点击了一下 long");
				return true;
			}
		};
	}
}
