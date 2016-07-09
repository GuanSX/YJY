package com.yjy.activity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.example.yjy.R;
import com.yjy.im.base.BaseActivity;
import com.yjy.im.base.Constant;
import com.yjy.im.base.Current;
import com.yjy.im.manager.BrowseFileAdapter;

import com.yjy.im.model.ViewHolder_BrowseFileItem;
import com.yjy.im.service.UploadLogService;
import com.yjy.im.utils.DialogUtil;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.content.Intent;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BrowseFileActivity extends BaseActivity {

	private Button back_btn; // 返回按钮
	private Button function_btn;
	private TextView mPath; // 标题栏显示的当前路径

	private Button select_all_btn; // 全选按钮
	private Button upload_btn; // 上传按钮
	private List<String> items = null; // 文件
	private List<String> paths = null; // 文件路径
	//private String rootPath = "/sdcard"; // 初始路径
	//private String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath(); // 获取SD卡的路径
	private String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
	//private String rootPath2 =Environment.get
	private String photoPath = rootPath+"/DCIM/camera"; // 照片路径
	private String videoPath = rootPath+"/DCIM/camera"; // 视频路径(在修改)
	private String curPath = null; // 当前路径
	private String parentPath = null; // 当前父路径

	private ListView file_list; // 列表
	private int checkNum = 0; // 记录选中数量
	private BrowseFileAdapter adapter;
	private boolean flag = false;
	private UploadLogService logService;
	private ProgressBar uploadbar;
	private List<File> lfile = null;
	private String type="";
	private String TAG="BrowseFileActivity";
	
	private OnClickListener backListener;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sdcard_file);
		Init();

	}

	private void Init() {
		curPath = rootPath; // 当前路径为根目录
		mPath = (TextView) this.findViewById(R.id.ui_title_word_tv);
		back_btn = (Button) findViewById(R.id.ui_title_back_btn);
		function_btn=(Button)findViewById(R.id.ui_title_function_btn) ;

		file_list = (ListView) this.findViewById(R.id.file_list);
		select_all_btn = (Button) findViewById(R.id.select_all_btn);
		upload_btn = (Button) findViewById(R.id.upload_btn);

		//=======设置标题========
		function_btn.setVisibility(View.GONE);
		back_btn.setVisibility(View.VISIBLE);

		// 找到sdcard下的文件列表
		Bundle bundle = this.getIntent().getExtras();
		type = bundle.getString("type");
		if (type.equals("file")) {
			getFileDir(rootPath);
		} else {
			lfile = new ArrayList<File>();
			doReadSDCardFile(rootPath, type);
			if (type.equals("photo")) {
				mPath.setText("图片");
			} else if (type.equals("video")) {
				mPath.setText("视频");
			} else if (type.equals("document")) {
				mPath.setText("文档");
			} else if (type.equals("music")) {
				mPath.setText("音乐");
			} else {
				mPath.setText("应用");
			}
		}
		



		//============按钮添加监听=============
		select_all_btn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				adapter.setListView(file_list);
				List<Integer> itemsIndex = new ArrayList<Integer>();
				// 全选
				if (!flag) {
					int temp = 0;
					for (int i = 0; i < paths.size(); i++) {
						File f = new File(paths.get(i));
						if (!f.isDirectory()) {
							adapter.getIsSelected().put(i, true);
							temp++;
							itemsIndex.add(i);
						}
					}
					if (temp != 0) {
						checkNum = temp;
						upload_btn.setText("上传" + checkNum);
						upload_btn.setTextColor(Color.RED);
						select_all_btn.setText("取消全选");
					}
					flag = true;
				}
				// 取消全选
				else {
					int temp = 0;
					for (int i = 0; i < paths.size(); i++) {
						File f = new File(paths.get(i));
						if (!f.isDirectory()) {
							adapter.getIsSelected().put(i, false);
							itemsIndex.add(i);
						}
					}
					checkNum = 0;
					upload_btn.setText("上传");
					upload_btn.setTextColor(Color.WHITE);
					select_all_btn.setText("全选");
					flag = false;
				}
				// 刷新ListView数据
				adapter.notifyDataSetChanged();
			}
		});

		back_btn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (type.equals("file")) {
					if (curPath.equals(rootPath)) {
						BrowseFileActivity.this.finish();
					} else {
						checkNum = 0;
						upload_btn.setText("上传");
						upload_btn.setTextColor(Color.WHITE);
						getFileDir(parentPath);
					}
				} else {
					BrowseFileActivity.this.finish();
				}
			}

		});

		upload_btn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 将选中的文件进行上传
				List<String> ufPath = new ArrayList<String>();
				for (int i = 0; i < paths.size(); i++) {
					File f = new File(paths.get(i));
					if (!f.isDirectory()) {
						if (adapter.getIsSelected().get(i)) {
							// 记录选中文件的路径
							ufPath.add(paths.get(i));
						}
					}
				}
				if (ufPath.size() != 0 && ufPath.get(0) != null) {
					Intent intent = new Intent();
					intent.setClass(BrowseFileActivity.this,
							TransFileActivity.class);

					//================传递参数========================
					// 用Serializable方法在activity之间传递List<Object>参数
					intent.putExtra("what", Constant.STRING_UPLOAD);
					intent.putExtra("paths", (Serializable) ufPath);
					intent.putExtra("levelId", Current.getCurrentuser().getLevel().getLevelId());
					intent.putExtra("userId",Current.getCurrentuser().getId());
					//intent.putExtra("money",3);//默认上传的文件是免费的，所以参数money不传递

					startActivity(intent);
					BrowseFileActivity.this.finish();
				} else {
					Toast.makeText(mContext, "请选择上传文件", Toast.LENGTH_SHORT)
							.show();
				}

				// uploadFile(uploadFile);
			}

		});

		file_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View v,
					int position, long id) {
				// TODO Auto-generated method stub
				File file = new File(paths.get(position));
				if (file.isDirectory()) {
					// 如果是文件夹就再执行getFileDir()
					checkNum = 0;
					upload_btn.setText("上传");
					upload_btn.setTextColor(Color.WHITE);
					select_all_btn.setText("全选");
					flag = false;
					getFileDir(paths.get(position));
				} else {
					// 如果是文件，则选中或取消选择
					ViewHolder_BrowseFileItem holder = (ViewHolder_BrowseFileItem) v.getTag();
					holder.item_cb.toggle();
					adapter.getIsSelected().put(position,
							holder.item_cb.isChecked());

					if (holder.item_cb.isChecked() == true) {
						checkNum++;
					} else {
						checkNum--;
					}
					if (checkNum != 0) {
						upload_btn.setText("上传" + checkNum);
						upload_btn.setTextColor(Color.RED);
					} else {
						upload_btn.setText("上传");
						upload_btn.setTextColor(Color.WHITE);
					}
				}
			}
		});
	}
	
	

	/**
	 * 列出当前目录下文件列表
	 * 
	 * @param filePath 当前路径
	 *
	 */
	public void getFileDir(String filePath) {

		items = new ArrayList<String>();
		List<String> items1 = new ArrayList<String>();
		List<String> items2 = new ArrayList<String>();
		paths = new ArrayList<String>();
		List<String> paths1 = new ArrayList<String>();
		List<String> paths2 = new ArrayList<String>();
		File f = new File(filePath);
		//f.listFiles().
		File[] files = f.listFiles();
		if (!filePath.equals(rootPath)) {
			parentPath = f.getParent();
		}
		curPath = filePath;
		if (curPath.length() > 20) {
			curPath = curPath.substring(0, 7)
					+ "..."
					+ curPath.substring(curPath.lastIndexOf("/"),
							curPath.length());
		}
		mPath.setText(curPath);
		for (int i = files.length - 1; i >= 0; i--) {
			File file = files[i];
			String name = file.getName();
			if (file.isDirectory()) {
				if (name.charAt(0) != '.') {
					if (name.length() > 16) {
						items1.add(name.substring(0, 4)
								+ "..."
								+ name.substring(name.length() - 6,
										name.length()));
					} else {
						items1.add(name);
					}
					paths1.add(file.getPath());
				}
			} else {
				if (name.charAt(0) != '.') {
					if (name.length() > 16) {
						items2.add(name.substring(0, 4)
								+ "..."
								+ name.substring(name.length() - 6,
										name.length()));
					} else {
						items2.add(name);
					}
					paths2.add(file.getPath());
				}
			}
		}
		Collections.sort(items1, String.CASE_INSENSITIVE_ORDER);
		Collections.sort(paths1, String.CASE_INSENSITIVE_ORDER);
		Collections.sort(items2, String.CASE_INSENSITIVE_ORDER);
		Collections.sort(paths2, String.CASE_INSENSITIVE_ORDER);
		items.addAll(items1);
		items.addAll(items2);
		paths.addAll(paths1);
		paths.addAll(paths2);
		adapter = new BrowseFileAdapter(this, items, paths);
		file_list.setAdapter(adapter);
	}

	/**
	 * 列出当前目录下类型文件 filePath 当前路径 所选类型：图片，视频，文档，音乐，应用
	 */
	public void doReadSDCardFile(final String filePath, final String type) {
		final ProgressDialog dialog= DialogUtil.getCircularProgressDialog(BrowseFileActivity.this,
				 "正在加载,请稍后...");
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				typeFile(filePath, type);
				// 服务端返回文件列表信息
				if (lfile != null) {
					return true;
				}
				// 返回信息为空，网络设置存在问题
				else {
					return false;
				}
			}

			@Override
			protected void onPostExecute(Boolean result) {
				//getProgressDialog().dismiss();
				dialog.dismiss();
				if (result) {
					typeFileSuccess();
				} else {
					Toast.makeText(mContext, "加载失败,请检查您网络设置.",
							Toast.LENGTH_SHORT).show();
				}
				super.onPostExecute(result);
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

		}.execute();
	}

	/**
	 * 根据类型显示文件列表
	 * 
	 * @param filePath
	 * @param type
	 */
	public void typeFile(String filePath, String type) {
		items = new ArrayList<String>();
		paths = new ArrayList<String>();
		File f = new File(filePath);
		File[] files = f.listFiles();
		if (type.equals("photo")) {
			photoFiles(files);
		} else if (type.equals("video")) {
			videoFiles(files);
		} else if (type.equals("document")) {
			docFiles(files);
		} else if (type.equals("music")) {
			musicFiles(files);
		} else {
			apkFiles(files);
		}
	}

	/**
	 * 更新列表
	 */
	public void typeFileSuccess() {
		for (int i = lfile.size() - 1; i >= 0; i--) {
			File file = lfile.get(i);
			String name = file.getName();
			if (name.charAt(0) != '.') {
				if (name.length() > 16) {
					items.add(name.substring(0, 4) + "..."
							+ name.substring(name.length() - 6, name.length()));
				} else {
					items.add(name);
				}
				paths.add(file.getPath());
			}
		}
		adapter = new BrowseFileAdapter(this, items, paths);
		file_list.setAdapter(adapter);
	}

	/**
	 * 图片列表
	 * 
	 * @param listFiles
	 */
	private void photoFiles(File[] listFiles) {
		if (listFiles != null) {
			for (int i = 0; i < listFiles.length; i++) {
				if (listFiles[i].isDirectory()) {
					photoFiles(listFiles[i].listFiles());
				} else {
					String name = listFiles[i].getName();
					String fType = name.substring(name.lastIndexOf(".") + 1,
							name.length()).toLowerCase();
					if (fType.equals("jpg") || fType.equals("gif")
							|| fType.equals("png") || fType.equals("jpeg")
							|| fType.equals("bmp")) {
						lfile.add(listFiles[i]);
					}
				}
			}
		}
	}

	/**
	 * 视频列表
	 * 
	 * @param listFiles
	 */
	private void videoFiles(File[] listFiles) {
		if (listFiles != null) {
			for (int i = 0; i < listFiles.length; i++) {
				if (listFiles[i].isDirectory()) {
					videoFiles(listFiles[i].listFiles());
				} else {
					String name = listFiles[i].getName();
					String fType = name.substring(name.lastIndexOf(".") + 1,
							name.length()).toLowerCase();
					if (fType.equals("3gp") || fType.equals("mp4")
							|| fType.equals("rmvb") || fType.equals("rm")
							|| fType.equals("avi") || fType.equals("mov")
							|| fType.equals("wmv") || fType.equals("mkv")
							|| fType.equals("asf")) {
						lfile.add(listFiles[i]);
					}
				}
			}
		}
	}

	/**
	 * 文档列表
	 * 
	 * @param listFiles
	 */
	private void docFiles(File[] listFiles) {
		if (listFiles != null) {
			for (int i = 0; i < listFiles.length; i++) {
				if (listFiles[i].isDirectory()) {
					docFiles(listFiles[i].listFiles());
				} else {
					String name = listFiles[i].getName();
					String fType = name.substring(name.lastIndexOf(".") + 1,
							name.length()).toLowerCase();
					if (fType.equals("doc") || fType.equals("docx")
							|| fType.equals("txt") || fType.equals("xls")
							|| fType.equals("xlsx") || fType.equals("ppt")
							|| fType.equals("pptx") || fType.equals("pdf")) {
						lfile.add(listFiles[i]);
					}
				}
			}
		}
	}

	/**
	 * 音乐列表
	 * 
	 * @param listFiles
	 */
	private void musicFiles(File[] listFiles) {
		if (listFiles != null) {
			for (int i = 0; i < listFiles.length; i++) {
				if (listFiles[i].isDirectory()) {
					musicFiles(listFiles[i].listFiles());
				} else {
					String name = listFiles[i].getName();
					String fType = name.substring(name.lastIndexOf(".") + 1,
							name.length()).toLowerCase();
					if (fType.equals("mp3") || fType.equals("wav")
							|| fType.equals("wma")) {
						lfile.add(listFiles[i]);
					}
				}
			}
		}
	}

	/**
	 * 应用列表
	 * 
	 * @param listFiles
	 */
	private void apkFiles(File[] listFiles) {
		if (listFiles != null) {
			for (int i = 0; i < listFiles.length; i++) {
				if (listFiles[i].isDirectory()) {
					apkFiles(listFiles[i].listFiles());
				} else {
					String name = listFiles[i].getName();
					String fType = name.substring(name.lastIndexOf(".") + 1,
							name.length()).toLowerCase();
					if (fType.equals("apk")) {
						lfile.add(listFiles[i]);
					}
				}
			}
		}
	}

	// 返回键添加监听,返回主界面
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (type.equals("file")) {
			if (curPath.equals(rootPath)) {
//				startActivity(new Intent(BrowseFileActivity.this,
//						HomeActivity.class));
				BrowseFileActivity.this.finish();
			} else {
				checkNum = 0;
				upload_btn.setText("上传");
				upload_btn.setTextColor(Color.WHITE);
				getFileDir(parentPath);
			}
		} else {
//			startActivity(new Intent(BrowseFileActivity.this,
//					HomeActivity.class));
			BrowseFileActivity.this.finish();
		}
		return false;
	}

}
