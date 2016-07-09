package com.yjy.im.manager;

import com.example.yjy.R;
import com.yjy.im.base.BaseFragment;
import com.yjy.im.model.Courseware;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import org.xbill.DNS.CNAMERecord;

import static java.util.Collections.swap;


/**
 * 用于管理底部弹出的PopupWindow的类
 */
public final class MenuManager {
	private static PopupWindow pop = null;
	private static String TAG = "MenuManager";
	private static String[] allChatListItemName = new String[]{"发起聊天", "清空列表"};
	private static int[] allChatListItemImage = new int[]{R.drawable.menu_new_chat, R.drawable
			.menu_clear_sessionlist};

//	public static String[] getAllChatListItemName(){
//		return allChatListItemName;
//	}

	/**
	 * 聊天列表menu
	 * @param activity
	 * @param listener
	 * @param touchListener
	 * @param keyListener
	 * @return
	 */
//	public static PopupWindow getMenu(Activity activity, OnClickListener listener,
//			OnTouchListener touchListener,OnKeyListener keyListener) {
//
//		View view = activity.getLayoutInflater().inflate(R.layout.custom_menu, null);
//		pop = new PopupWindow(view,ViewGroup.LayoutParams.FILL_PARENT,
//				ViewGroup.LayoutParams.WRAP_CONTENT);
//
//		pop.setAnimationStyle(R.style.popwin_anim_style);
//		pop.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.custom_menu_bg));
//		pop.setFocusable(true);
//		pop.setTouchable(true);
//		pop.setOutsideTouchable(true);
//		view.setFocusableInTouchMode(true);
//		pop.setTouchInterceptor(touchListener);
//		view.setOnKeyListener(keyListener);
//
//		LinearLayout menuItem1 = (LinearLayout)view.findViewById(R.id.menu_item1);
//		LinearLayout menuItem2= (LinearLayout)view.findViewById(R.id.menu_item2);
//		ImageView menuItemImage1 = (ImageView)view.findViewById(R.id.menu_item1_image);
//		ImageView menuItemImage2 = (ImageView)view.findViewById(R.id.menu_item2_image);
//		TextView menuItemText1 = (TextView)view.findViewById(R.id.menu_item1_text);
//		TextView menuItemText2 = (TextView)view.findViewById(R.id.menu_item2_text);
//
//		menuItemText1.setText(allChatListItemName[0]);
//		menuItemText2.setText(allChatListItemName[1]);
//		menuItemImage1.setImageResource(allChatListItemImage[0]);
//		menuItemImage2.setImageResource(allChatListItemImage[1]);
//
//		menuItem1.setOnClickListener(listener);
//		menuItem2.setOnClickListener(listener);
//
//		return pop;
//
//	}

	/**
	 * 设置好友信息menu
	 *
	 * @param activity
	 * @param listener
	 * @param touchListener
	 * @param keyListener
	 * @return
	 */
	public static PopupWindow getOperaterMenu(Activity activity, OnClickListener listener,
											  OnTouchListener touchListener, OnKeyListener
													  keyListener) {

//		View view = activity.getLayoutInflater().inflate(R.layout.detail_info_operater, null);
//		pop = new PopupWindow(view,ViewGroup.LayoutParams.FILL_PARENT,
//				ViewGroup.LayoutParams.WRAP_CONTENT);
//		
//		pop.setAnimationStyle(R.style.popwin_anim_style);
//		pop.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.custom_menu_bg));
//		pop.setFocusable(true);
//		pop.setTouchable(true);
//		pop.setOutsideTouchable(true);
//		view.setFocusableInTouchMode(true); 
//		pop.setTouchInterceptor(touchListener);
//		view.setOnKeyListener(keyListener);
//
//		
//		Button nickNameButton = (Button) view.findViewById(R.id.detail_info_operator_nickname_btn);
//		Button blackButton = (Button) view.findViewById(R.id.detail_info_operator_black_btn);
//		Button deleteButton = (Button) view.findViewById(R.id.detail_info_operator_delete_btn);
//		Button cancleButton = (Button) view.findViewById(R.id.detail_info_operator_cancel_btn);
//		
//		nickNameButton.setOnClickListener(listener);
//		blackButton.setOnClickListener(listener);
//		deleteButton.setOnClickListener(listener);
//		cancleButton.setOnClickListener(listener);
//
//		return pop;
		return null;

	}

	/**
	 * 设置上传文件menu
	 *
	 * @param activity
	 * @param listener
	 * @param touchListener
	 * @return
	 */
	public static PopupWindow getUploadFileMenu(Activity activity, OnClickListener listener,
												OnTouchListener touchListener) {

		View view = activity.getLayoutInflater().inflate(R.layout.menu_courseware_type, null);
		pop = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		pop.setAnimationStyle(R.style.Theme_popwin_anim_style);
		//pop.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable
		// .custom_menu_bg));
		pop.setFocusable(true);
		pop.setTouchable(true);
		pop.setOutsideTouchable(true);
		view.setFocusableInTouchMode(true);
		pop.setTouchInterceptor(touchListener);

		Button picButton = (Button) view.findViewById(R.id.menu_courseware_photo_btn);
		Button videoButton = (Button) view.findViewById(R.id.menu_courseware_video_btn);
		Button docButton = (Button) view.findViewById(R.id.menu_courseware_doc_btn);
		Button musicButton = (Button) view.findViewById(R.id.menu_courseware_music_btn);
		Button apkButton = (Button) view.findViewById(R.id.menu_courseware_apk_btn);
		Button fileButton = (Button) view.findViewById(R.id.menu_courseware_file_btn);
		//Button camaraButton = (Button) view.findViewById(R.id
		// .courseware_avater_dialog_notlocal_btn);
		Button cancleButton = (Button) view.findViewById(R.id.menu_courseware_type_cancel_btn);

		picButton.setOnClickListener(listener);
		videoButton.setOnClickListener(listener);
		docButton.setOnClickListener(listener);
		musicButton.setOnClickListener(listener);
		apkButton.setOnClickListener(listener);
		fileButton.setOnClickListener(listener);
		cancleButton.setOnClickListener(listener);

		return pop;


	}

	public static PopupWindow getUploadFileMenu(final Context context, OnClickListener
			onClickListener, OnTouchListener onTouchListener) {

		Log.e("getUploadFileMenu", "getUploadFileMenu");

		View view = ((Activity) context).getLayoutInflater().inflate(R.layout
				.menu_courseware_type, null);
		pop = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);


		pop.setAnimationStyle(R.style.Theme_popwin_anim_style);

		pop.setFocusable(true);
		pop.setTouchable(true);
		pop.setOutsideTouchable(true);
		view.setFocusableInTouchMode(true);
		pop.setTouchInterceptor(onTouchListener);

		Button picButton = (Button) view.findViewById(R.id.menu_courseware_photo_btn);
		Button videoButton = (Button) view.findViewById(R.id.menu_courseware_video_btn);
		Button docButton = (Button) view.findViewById(R.id.menu_courseware_doc_btn);
		Button musicButton = (Button) view.findViewById(R.id.menu_courseware_music_btn);
		Button apkButton = (Button) view.findViewById(R.id.menu_courseware_apk_btn);
		Button fileButton = (Button) view.findViewById(R.id.menu_courseware_file_btn);
		Button cancleButton = (Button) view.findViewById(R.id.menu_courseware_type_cancel_btn);

		picButton.setOnClickListener(onClickListener);
		videoButton.setOnClickListener(onClickListener);
		docButton.setOnClickListener(onClickListener);
		musicButton.setOnClickListener(onClickListener);
		apkButton.setOnClickListener(onClickListener);
		fileButton.setOnClickListener(onClickListener);
		cancleButton.setOnClickListener(onClickListener);

		return pop;


	}


	/**
	 * 参数意义：哪一个Activity中的哪一个fragment中的list中的第几个item
	 *
	 * @param activity
	 * @return 返回由这个item对应的popupwindow
	 */
	public static PopupWindow getMoreMenu(final Context activity, OnClickListener onClickListener,
										  OnTouchListener onTouchListener) {

		Log.e("getMoreMenu","getMoreMenu");

		View view = ((Activity) activity).getLayoutInflater().inflate(R.layout
				.menu_courseware_more, null);

		pop=new PopupWindow(view,ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		pop.setAnimationStyle(R.style.Theme_popwin_anim_style);
		pop.setFocusable(true);
		pop.setTouchable(true);
		//加上上面两句，可以在触发Back键或者点击在popupWindow以外的区域时让其dismiss
		pop.setOutsideTouchable(true);//======第一句
		view.setFocusableInTouchMode(true);
		pop.setBackgroundDrawable(new BitmapDrawable());//=======第二句
		pop.setTouchInterceptor(onTouchListener);


		Button editBtn = (Button) view.findViewById(R.id.menu_courseware_edit_btn);
		Button refreshBtn = (Button) view.findViewById(R.id.menu_courseware_refresh_btn);
		Button deleteBtn=(Button)view.findViewById(R.id.menu_courseware_delete_btn);
		Button downloadBtn = (Button) view.findViewById(R.id.menu_courseware_download_btn);
		Button cancleButton = (Button) view.findViewById(R.id.menu_courseware_cancel_btn);

		editBtn.setOnClickListener(onClickListener);
		refreshBtn.setOnClickListener(onClickListener);
		deleteBtn.setOnClickListener(onClickListener);
		downloadBtn.setOnClickListener(onClickListener);
		cancleButton.setOnClickListener(onClickListener);

		return pop;
	}


}
