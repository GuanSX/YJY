package com.yjy.activity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Exchanger;

import com.example.yjy.R;
import com.yjy.im.base.*;
import com.yjy.im.manager.*;

import com.yjy.im.service.*;
import com.yjy.im.utils.*;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TransFileActivity extends BaseActivity {


	private Button btn_back;
	private TextView title_string;
	private Button btn_function;
	private UploadLogService logService;
	private ListView trans_list;
	private TransFileAdapter adapter;
	private List<String> names;
	private List<Integer> sizes;
	private List<Integer> curSizes;
	private List<Integer> ids;
	private String TAG = "TransFileActivity";
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int curSize = msg.getData().getInt("curSize");
			int id = msg.getData().getInt("id");
			int size = msg.getData().getInt("size");
			adapter.updateView(id, curSize, size);
			adapter.notifyDataSetChanged();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trans_upload);
		Init();

		//logService = new UploadLogService(this);

	}

	private void Init() {

		//标题栏三个控件
		btn_back = (Button) this.findViewById(R.id.ui_title_back_btn);
		btn_function = (Button) this.findViewById(R.id.ui_title_function_btn);
		title_string = (TextView) this.findViewById(R.id.ui_title_word_tv);

		trans_list = (ListView) this.findViewById(R.id.trans_list);

		//设置标题栏
		btn_back.setVisibility(View.VISIBLE);
		btn_function.setVisibility(View.GONE);
		title_string.setText("粤教云客户端——文件传输列表");

		//取消按钮监听事件
		btn_back.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				TransFileActivity.this.finish();
			}

		});

		String what = getIntent().getStringExtra("what");

		if (what.equals(Constant.STRING_DOWNLOAD)) {
			DownloadFile();
		} else if (what.equals(Constant.STRING_UPLOAD)) {
			UploadFile();
		}


	}

	//保留误删
	private void Init1() {
		//========获取参数===========
		// 用Serializable方法在activity之间传递List<Object>参数
		List<String> paths = (List<String>) getIntent().getSerializableExtra(
				"paths");//将多个文件的路径存储到List中穿过来
		String levelId = getIntent().getStringExtra("levelId");
		String userId = getIntent().getStringExtra("userId");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("levelId", levelId);
		params.put("userId", userId);
		//params.put("money", 0);//课件上传是默认为免费课件。

		names = new ArrayList<String>();
		ids = new ArrayList<Integer>();
		sizes = new ArrayList<Integer>();//每个文件的大小
		curSizes = new ArrayList<Integer>();
		List<File> files = new ArrayList<File>();
		for (int i = 0; i < paths.size(); i++) {
			ids.add(i);
			File file = new File(paths.get(i));
			if (!file.exists()) {
				Log.e(TAG, paths.get(i) + "file 不存在");
				continue;
			}
			names.add(file.getName());
			files.add(file);

			Log.e(TAG, "filepath:" + file.getPath());

			sizes.add((int) file.length());
			curSizes.add(0);
		}
		adapter = new TransFileAdapter(this, ids, names, sizes, curSizes);
		trans_list.setAdapter(adapter);

		myUploadFile(files, params);
	}

	//保留误删
	private void myUploadFile(final List<File> uploadfiles, final HashMap<String, String> params) {
		new Thread(new Runnable() {
			@Override
			public void run() {

				int i = 0;
				for (File file : uploadfiles) {
					int responsecode = HttpClientUtil.uploadFile(file, WebLinksUtil
							.UPLOADFILE_URL, params);

					// 现在是一次性显示是否上传成功，即把文件的长度传递过去，就显示上传完毕
					Message msg = new Message();
					if (responsecode == 200) {
						msg.getData().putInt("curSize",
								(int) uploadfiles.get(i).length());
						msg.getData().putInt("id", i);
						mHandler.sendMessage(msg);
					}
					// 以后要改进，实时监视上传的进度，即实时地把已经上传的文件长度（UploadFileUtil.currentLength）传递过去

					i++;
				}

			}
		}).start();

	}

	private void UploadFile() {
		//========获取参数===========
		// 用Serializable方法在activity之间传递List<Object>参数
		List<String> paths = (List<String>) getIntent().getSerializableExtra(
				"paths");//将多个文件的路径存储到List中穿过来
		String levelId = getIntent().getStringExtra("levelId");
		String userId = getIntent().getStringExtra("userId");
		final HashMap<String, String> params = new HashMap<String, String>();
		params.put("levelId", levelId);
		params.put("userId", userId);
		//params.put("money", 0);//课件上传是默认为免费课件。


		names = new ArrayList<String>();
		ids = new ArrayList<Integer>();
		sizes = new ArrayList<Integer>();//每个文件的大小
		curSizes = new ArrayList<Integer>();
		final List<File> files = new ArrayList<File>();
		for (int i = 0; i < paths.size(); i++) {
			ids.add(i);
			File file = new File(paths.get(i));
			if (!file.exists()) {
				Log.e(TAG, paths.get(i) + "file 不存在");
				continue;
			}
			names.add(file.getName());
			files.add(file);

			Log.e(TAG, "filepath:" + file.getPath());

			sizes.add((int) file.length());
			curSizes.add(0);
		}
		adapter = new TransFileAdapter(this, ids, names, sizes, curSizes);
		trans_list.setAdapter(adapter);

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < ids.size(); i++) {
					int currentLength = 0;

					HashMap<String, Object> map = HttpClientUtil.Upload(files.get(i).getName(),
							WebLinksUtil
									.UPLOADFILE_URL, params);
					if (null != map) {
						DataOutputStream out = (DataOutputStream) map.get("DataOutputStream");
						HttpURLConnection conn = (HttpURLConnection) map.get("HttpURLConnection");
						byte[] end_data = (byte[]) map.get("end_data");

						Log.e(TAG,(map.get("end_data")).toString());

						try {
							InputStream is = new FileInputStream(files.get(i));
							byte[] bytes = new byte[1024];
							int len = 0;
							if(files.get(i).canRead())
							{
								Log.e(TAG,files.get(i).getName()+"文件可读");
							}else
							{
								Log.e(TAG,files.get(i).getName()+"文件不可读");
							}
							while ((len = is.read(bytes)) != -1) {
								out.write(bytes, 0, len);
								currentLength += len;
								Log.e(TAG, Integer.toString(len));
								//curSizes.add(i,currentLength);
								Message message = new Message();
								message.getData().putInt("id", i);
								message.getData().putInt("curSize", currentLength);
								message.getData().putInt("size", (int) files.get(i).length());
								mHandler.sendMessage(message);

							}
							is.close();
							out.write(end_data);
							out.flush();

							int responsecode = conn.getResponseCode();
							Log.e(TAG, "response code:" + responsecode);
							if (responsecode == 200) {
								Log.e(TAG, "request success");
								StringBuffer sb1 = new StringBuffer();
								int ss;
								InputStream inputStream = conn.getInputStream();
								while ((ss = inputStream.read()) != -1) {
									sb1.append((char) ss);
								}
								String result = sb1.toString();
								Log.e(TAG, "result : " + result);
							} else {
								Log.e(TAG, "request error " + conn.getResponseMessage());
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						Log.e(TAG, "upload map is null");
					}


				}
			}
		}).start();


	}

	private void DownloadFile() {


		Log.e(TAG, "DownlaodFile");

		final List<String> urls = new ArrayList<String>();
		final List<String> names = new ArrayList<String>();
		final List<Integer> sizes = new ArrayList<Integer>();//每个文件的大小
		final List<Integer> curSizes = new ArrayList<Integer>();
		final List<Integer> ids = new ArrayList<Integer>();

		urls.add(getIntent().getStringExtra("downPath"));
		names.add(getIntent().getStringExtra("fileName"));
		sizes.add(1);
		curSizes.add(0);
		ids.add(0);
		adapter = new TransFileAdapter(mContext, ids, names, sizes, curSizes);
		trans_list.setAdapter(adapter);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					List<Object> list = HttpClientUtil.DownLoad(urls.get(0));
					int size = 1;
					if (list != null) {
						Long length = Long.valueOf(list.get(0).toString());
						size = Integer.parseInt(length.toString());//获取文件长度

					}

					InputStream is = (InputStream) list.get(1);//获取流
					FileOutputStream fileOutputStream = null;

					File file = FileUtils.createFile(Constant.DOWNLOADDIR, names.get(0));

					fileOutputStream = new FileOutputStream(file);
					byte[] buf = new byte[1024];
					int ch = -1;
					int downloadLength = 0;
					while ((ch = is.read(buf)) != -1) {
						fileOutputStream.write(buf, 0, ch);
						downloadLength += ch;
						Message message = new Message();
						message.getData().putInt("id", 0);
						message.getData().putInt("curSize", downloadLength);
						message.getData().putInt("size", size);
						mHandler.sendMessage(message);

					}

					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					Log.e(TAG, "下载成功");


				} catch (Exception e) {
					e.printStackTrace();
				}


			}
		}).start();

	}


	// 返回键添加监听,从注册界面返回主界面
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//startActivity(new Intent(mContext, MainActivity.class));
			TransFileActivity.this.finish();
		}
		return false;
	}

}
