package com.yjy.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.yjy.R;
import com.yjy.activity.HomeActivity;
import com.yjy.im.base.Current;
import com.yjy.im.base.BaseFragment;
import com.yjy.im.manager.CoursewareItemAdapter;
import com.yjy.im.model.Courseware;
import com.yjy.im.utils.ShowMsgUtil;

import java.util.ArrayList;
import java.util.List;

public class CoursewareListFragment extends BaseFragment {

	private ListView coursewareList;

	private LinearLayout nocourseware;
	private SwipeRefreshLayout swiperefreshlayout;

	private List<Courseware> mylist;
	private CoursewareItemAdapter coursewarelistadapter;

	private AdapterView.OnItemClickListener clickListener;
	private AdapterView.OnItemLongClickListener longClickListener;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}


	//重写onCreateView决定Fragemnt的布局
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_coursewarelist, container,
				false);
		Init(view);
		return view;
	}

	//找到fragment中的控件
	private void Init(View view) {
		this.coursewareList = (ListView) (view.findViewById(R.id.list_coursewarelist));
		this.nocourseware = (LinearLayout) view.findViewById(R.id.nocoursewarelist_layout);
		this.swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id
				.coursewarelist_swiperefresh_layout);

		mylist = new ArrayList<Courseware>();


		//添加下拉刷新效果
		swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadAllCoursewareList(mylist, Current.getCurrentuser().getLevel().getLevelId(),
						null, -1);
				//coursewarelistadapter.notifyDataSetChanged();
			}
		});
		swiperefreshlayout.setColorScheme(android.R.color.holo_green_dark
				, android.R.color.holo_green_light, android.R.color.holo_red_light);


		InitListener();


		loadAllCoursewareList(mylist, Current.getCurrentuser().getLevel().getLevelId(),
				null, -1);


	}


	@Override
	protected void doLoadAllCoursewareListSuccess() {
		swiperefreshlayout.setRefreshing(false);
		ShowMsgUtil.show(((HomeActivity) getActivity()).getContext(), "刷新了一下");
		if (mylist.size() == 0) {
			//没记录时
			nocourseware.setVisibility(View.VISIBLE);
			coursewareList.setVisibility(View.INVISIBLE);
		} else {
			//有记录时
			nocourseware.setVisibility(View.GONE);
			coursewareList.setVisibility(View.VISIBLE);

			this.coursewarelistadapter = new CoursewareItemAdapter(((HomeActivity) getActivity())
					.getContext(), Current.getCurrentfragment(),
					this.mylist);
			coursewareList.setAdapter(coursewarelistadapter);
			//为item设置监听
			coursewareList.setOnItemClickListener(clickListener);
			coursewareList.setOnItemLongClickListener(longClickListener);
		}


		// 在没有课件时，显示中间最大的上传按钮。

		// 如果角色是“教师”("1")
		if (Current.getCurrentuser().isTeacher) {

			// 如果列表为0
			if (mylist.size() == 0) {
				this.nocourseware.setVisibility(View.VISIBLE);
			}
		}
	}


	//初始化点击item的监听事件
	private void InitListener() {
		clickListener = new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ShowMsgUtil.show(((HomeActivity) getActivity()).getContext(), "coursewarelist " +
						"record点击了一下");
			}
		};

		longClickListener = new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long
					id) {
				ShowMsgUtil.show(((HomeActivity) getActivity()).getContext(), "coursewarelist " +
						"record点击了一下 long");
				return true;
			}
		};
	}


}
