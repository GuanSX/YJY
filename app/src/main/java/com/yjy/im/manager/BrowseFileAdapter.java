package com.yjy.im.manager;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.example.yjy.R;
import com.yjy.im.model.ViewHolder_BrowseFileItem;
import com.yjy.im.utils.IconUtil;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class BrowseFileAdapter extends BaseAdapter{
	
	private LayoutInflater lf;
	private Bitmap floder, def, music, video, photo, doc, xls, ppt, txt, pdf;
	private List<String> items;
	private List<String> paths;
	private static HashMap<Integer, Boolean> isSelected;
	private CheckBox item_cb;
	private ListView listView;
	private String TAG="BrowseFileAdapter";
	private Context mContext;
	private IconUtil myiconutil;
	
	public BrowseFileAdapter(Context context, List<String> it, List<String> pa){
		lf = LayoutInflater.from(context);
		items = it;
		paths = pa;
		isSelected = new HashMap<Integer, Boolean>();
		this.mContext=context;
		//初始化isSelected
		initData();
		
	}
	
	private void initData(){
		for(int i = 0; i < items.size(); i ++){
			getIsSelected().put(i, false);
		}
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder_BrowseFileItem holder=null;
		if(convertView == null){
			convertView = lf.inflate(R.layout.view_file_item, null);
			holder = new ViewHolder_BrowseFileItem();
			holder.name = (TextView) convertView.findViewById(R.id.file_name);
			holder.icon = (ImageView) convertView.findViewById(R.id.file_icon);
			holder.item_cb = (CheckBox) convertView.findViewById(R.id.item_cb);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder_BrowseFileItem) convertView.getTag();
		}
		File f = new File(paths.get(position).toString());

		//=====设置文件名==
		holder.name.setText(items.get(position));
		//====设置文件图标=====
		if(f.isDirectory()){
			//若为文件，则无选中标记
			holder.item_cb.setVisibility(View.GONE);
			holder.icon.setImageBitmap(IconUtil.getIcon(mContext,"floder"));
		}
		else{
			String temp = f.getName().toString();
			String fType = temp.substring(temp.lastIndexOf(".") + 1, temp.length()).toLowerCase();
			holder.icon.setImageBitmap(IconUtil.getIcon(mContext,fType));
			//如果是文件，则在末尾有选择图标
			holder.item_cb.setVisibility(View.VISIBLE);
			holder.item_cb.setChecked(getIsSelected().get(position));
		}
		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		BrowseFileAdapter.isSelected = isSelected;
	}

	public ListView getListView() {
		return listView;
	}

	public void setListView(ListView listview) {
		this.listView = listview;
	}



}
