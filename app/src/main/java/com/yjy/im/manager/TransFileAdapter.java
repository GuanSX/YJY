package com.yjy.im.manager;

import java.util.List;

import com.example.yjy.R;
import com.yjy.im.model.ViewHolder_UploadFileItem;
import com.yjy.im.utils.IconUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TransFileAdapter extends BaseAdapter {

	private LayoutInflater lf;
	private Bitmap def, music, video, photo, doc, xls, ppt, txt, pdf;
	private List<String> names;
	private List<Integer> sizes;
	private List<Integer> curSizes;
	private List<Integer> ids;
	private ListView listView;
	private Context mContext;

	ViewHolder_UploadFileItem viewUploadFile;

	public TransFileAdapter(Context context, List<Integer> ids, List<String> names,
							List<Integer> sizes, List<Integer> curSizes) {
		lf = LayoutInflater.from(context);
		this.ids = ids;
		this.names = names;
		this.sizes = sizes;
		this.curSizes = curSizes;
		mContext=context;

	}

	public void updateView(int id, int size) {
		this.curSizes.set(id, size);
	}
	public void updateView(int id, int curSize,int size) {
		this.curSizes.set(id, curSize);
		this.sizes.set(id,size);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return names.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return names.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = lf.inflate(R.layout.view_trans_file_item, null);
			viewUploadFile = new ViewHolder_UploadFileItem();
			viewUploadFile.name = (TextView) convertView.findViewById(R.id.file_name);
			viewUploadFile.icon = (ImageView) convertView.findViewById(R.id.file_icon);
			viewUploadFile.uploadbar = (ProgressBar) convertView.findViewById(R.id.uploadbar);
			viewUploadFile.result = (TextView) convertView.findViewById(R.id.result);
			convertView.setTag(viewUploadFile);
		} else {
			viewUploadFile = (ViewHolder_UploadFileItem) convertView.getTag();
		}
		viewUploadFile.uploadbar.setMax((int) sizes.get(position));
		viewUploadFile.uploadbar.setProgress(curSizes.get(position));
		float num = (float) curSizes.get(position) / (float) sizes.get(position);
		int result = (int) (num * 100);
		if (viewUploadFile.uploadbar.getProgress() == viewUploadFile.uploadbar.getMax()) {
			viewUploadFile.result.setText("完成");
		} else if (viewUploadFile.uploadbar.getProgress() == 0) {
			viewUploadFile.result.setText("等待");
		} else {
			viewUploadFile.result.setText(result + "%");
		}
		String name = names.get(position);
		if (name.length() > 16) {
			viewUploadFile.name.setText(name.substring(0, 6) + "..." +
					name.substring(name.length() - 6, name.length()));
		} else {
			viewUploadFile.name.setText(name);
		}

		//设置文件图标
		String temp = names.get(position).toString();
		String fType = temp.substring(temp.lastIndexOf(".") + 1, temp.length()).toLowerCase();
		viewUploadFile.icon.setImageBitmap(IconUtil.getIcon(mContext,fType));
		return convertView;
	}


}
