package com.yjy.im.manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.yjy.R;
import com.yjy.activity.EditCoursewareActivity;
import com.yjy.activity.TransFileActivity;
import com.yjy.fragment.CoursewareListFragment;
import com.yjy.fragment.DownloadRecordFragment;
import com.yjy.fragment.UploadRecordFragment;
import com.yjy.im.base.BaseFragment;
import com.yjy.im.base.Constant;
import com.yjy.im.comm.ALoginActivity;
import com.yjy.im.framework.volley.VolleyErrorHelper;
import com.yjy.im.framework.volley.VolleyRequest;
import com.yjy.im.model.Courseware;
import com.yjy.im.model.User;
import com.yjy.im.model.ViewHolder_CoursewareItem;
import com.yjy.im.utils.DialogUtil;
import com.yjy.im.utils.IconUtil;
import com.yjy.im.utils.VolleyUtil;
import com.yjy.im.utils.WebLinksUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Guan on 2016/5/24.
 */
public class CoursewareItemAdapter extends BaseAdapter {
	private List<Courseware> items;
	private LayoutInflater mInflater;
	private Context mContext;
	private BaseFragment myfragment;
	private View.OnClickListener editbtnListener;
	private View.OnClickListener refreshListener;
	private View.OnClickListener morebtnListener;

	private View.OnClickListener menuItemListener;//点击底部弹出框的选项时触发事件的监听器
	private View.OnTouchListener touchListener;//点击其他弹出框以外的区域时事件的监听器

	public PopupWindow pop;

	private String TAG = "CoursewareItemAdapter";
	private int selectedposition;


	public CoursewareItemAdapter(Context context, List<Courseware> items) {
		this.items = items;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		//Init();
	}

	public CoursewareItemAdapter(Context context, Fragment myFragment, List<Courseware> items) {
		this.items = items;
		this.mContext = context;
		this.myfragment = (BaseFragment) myFragment;
		mInflater = LayoutInflater.from(context);
		Init();
	}

	private void Init()
	{
		//初始化popupwindow的两个Listener：menuItemListener、touchListener
		InitListenserForPop();
		//点击item上的button，显示popupwindow
		pop= MenuManager.getMoreMenu(mContext,menuItemListener,touchListener);

	}

	public void setItem(int position,Courseware courseware)
	{
		items.set(position,courseware);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Courseware courseware = items.get(position);
		ViewHolder_CoursewareItem holder = null;

		if (convertView == null) {
			holder = new ViewHolder_CoursewareItem();

			convertView = mInflater.inflate(R.layout.view_courseware_item, null);
			holder.avater = (ImageView) convertView.findViewById(R.id.item_courseware_icon);
			holder.coursewareName = (TextView) convertView.findViewById(R.id.item_courseware_name);
			holder.level = (TextView) convertView.findViewById(R.id.item_courseware_level);
			holder.cost = (TextView) convertView.findViewById(R.id.item_courseware_cost);
			holder.newicon = (TextView) convertView.findViewById(R.id.item_courseware_newicon);
			holder.time = (TextView) convertView.findViewById(R.id.item_courseware_info);
			holder.more = (Button) convertView.findViewById(R.id.item_more_btn);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder_CoursewareItem) convertView.getTag();
		}

		//====设置文件名
		holder.coursewareName.setText(courseware.getOriginalName());
		holder.level.setText("级别" + courseware.getLevel().getLevelId());
		holder.cost.setText(courseware.getMoney() + " 元");

		//====设置课件图标
		if (myfragment instanceof CoursewareListFragment
				|| myfragment instanceof DownloadRecordFragment) {
			//如果当前fragment是“课件列表”或者“下载记录”，则显示文件类型的图标
			//String ftype=courseware
			holder.avater.setImageBitmap(IconUtil.getIcon(mContext, courseware.getFileType()));

		} else if (myfragment instanceof UploadRecordFragment) {
			//如果当前fragment是“上传记录”，则显示课件状态的图标（“published”和“unpublished”）
			if (courseware.isPublished()) {
				holder.avater.setImageBitmap(IconUtil.getPublishedIcon(mContext));
			} else {
				holder.avater.setImageBitmap(IconUtil.getunPublishedIcon(mContext));
			}
		}

		//===设置“更多”按钮是否可见
		if (myfragment instanceof UploadRecordFragment) {

			holder.more.setVisibility(View.VISIBLE);
			//并添加监听
			final View finalConvertView = convertView;
			holder.more.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(!pop.isShowing())
					{
						pop.showAtLocation(finalConvertView, Gravity.BOTTOM, 0, 0);//显示popupwindow
					}
					selectedposition=position;//时刻更新所点击的item的position
				}
			});

		} else {
			holder.more.setVisibility(View.GONE);
		}

		return convertView;
	}

	/**
	 * 初始化popupwindow的Listenser
	 *
	 */
	private void InitListenserForPop() {
		//点击底部弹出popupwindow的选项时触发事件的监听器
		final Intent intent=new Intent();
		menuItemListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.menu_courseware_download_btn:
						pop.dismiss();
						intent.setClass(mContext, TransFileActivity.class);
						intent.putExtra("what", Constant.STRING_DOWNLOAD);
						intent.putExtra("downPath",items.get(selectedposition).getDownPath());
						intent.putExtra("fileName",items.get(selectedposition).getOriginalName());
						mContext.startActivity(intent);
						//下载
						break;
					case R.id.menu_courseware_refresh_btn:
						//刷新
						break;
					case R.id.menu_courseware_delete_btn:
						//删除
						pop.dismiss();
						deleteCourseware(items.get(selectedposition).getDocId(),selectedposition);
						break;
					case R.id.menu_courseware_edit_btn:
						//编辑
						pop.dismiss();
						intent.setClass(mContext, EditCoursewareActivity.class);
						intent.putExtra("courseware",items.get(selectedposition));
						intent.putExtra("position",selectedposition);
						myfragment.startActivityForResult(intent,Constant.POPITEM_EDIT);
						break;
					case R.id.menu_courseware_cancel_btn:
						break;
				}
			}
		};

		//点击其他弹出框以外的区域时事件的监听器
		touchListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					pop.dismiss();
				}
				return false;
			}
		};

	}

	private void deleteCourseware(String docId, final int position)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("docId", docId);

		final ProgressDialog dialog = DialogUtil.getCircularProgressDialog(
				mContext, "正在删除...");

		VolleyRequest vr = new VolleyRequest(Request.Method.POST,
				WebLinksUtil.DELETECOURSEWARE, params, new Response.Listener() {

			@Override
			// 连接成功了会这样做
			public void onResponse(Object o) {

				if (null != o) {
					String result = o.toString();
					Log.e(TAG, "http response： " + result);
					JSONTokener jsonParser = new JSONTokener(result);

					try {
						//解析JSON
						JSONObject re = (JSONObject) jsonParser.nextValue();

						if (re.getString("result").equals("true")) {
							Toast.makeText(mContext,"删除成功！",Toast.LENGTH_SHORT);
							items.remove(position);
							myfragment.fragmentResult(Constant.POPITEM_DELETE,null);
						} else {
							Toast.makeText(mContext,
									"删除失败",
									Toast.LENGTH_SHORT).show();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

				dialog.dismiss();
			}
		}, new Response.ErrorListener() {
			// 连接失败了会这样执行
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				dialog.dismiss();
				String m = VolleyErrorHelper.getMessage(volleyError,
						mContext);
				//Log.e(TAG, m);

			}
		});

		// 加入，开始执行
		VolleyUtil.getVolleyUtil(mContext)
				.addToRequestQueue(vr, TAG);
	}

	private void refreshCourseware()
	{

	}


}
