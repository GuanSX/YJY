package com.yjy.im.alipay;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yjy.im.framework.volley.VolleyErrorHelper;
import com.yjy.im.framework.volley.VolleyRequest;
import com.yjy.im.framework.volley.VolleyUtil;


import java.util.HashMap;
import java.util.Map;

public class PayActivity extends FragmentActivity {

	// 商户PID,商户收款账号
	private String PARTNER, SELLER, sign, notify_url, app_id, appenv,
			out_trade_no, return_url;

	private static final int SDK_PAY_FLAG = 1;

	private String subject = "", body = "", total_fee = "";

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				final String result = payResult.getResult();

				final String resultStatus = payResult.getResultStatus();
				final String memo = payResult.getMemo();

				Map<String,String> params=new HashMap<>();

				params.put("resultStatus", resultStatus);
				params.put("memo", memo);
				params.put("result", result);


				VolleyRequest vr=new VolleyRequest(Request.Method.POST,"http://alipay.hhit.com.cn/alipay/servlet/MobileSynResponseServlet",params,new Response.Listener() {
					@Override
					public void onResponse(Object o) {

						if(o!=null){
							
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

						String m= VolleyErrorHelper.getMessage(volleyError, PayActivity.this);
						Log.v("--->>>>-----",m);

					}
				});


				VolleyUtil.getVolleyUtil(PayActivity.this).addToRequestQueue(vr, "PayActivity");




				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT)
							.show();
					Intent intent = new Intent(PayActivity.this,
							MyOrderActivity.class);
					startActivity(intent);
					finish();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_pay);
		Intent intent = getIntent();
		subject = intent.getStringExtra("subject");
		body = intent.getStringExtra("body");
		total_fee = intent.getStringExtra("total_fee");
		PARTNER = intent.getStringExtra("partner");
		SELLER = intent.getStringExtra("seller_id");
		sign = intent.getStringExtra("sign");
		notify_url = intent.getStringExtra("notify_url");
		app_id = intent.getStringExtra("app_id");
		appenv = intent.getStringExtra("appenv");
		out_trade_no = intent.getStringExtra("out_trade_no");
		return_url = intent.getStringExtra("return_url");
		alipay();
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	private void alipay() {
		// 订单
		String orderInfo = getOrderInfo();

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(PayActivity.this);

				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
				finish();
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * create the order info. 创建订单信息 ！顺序不要改变
	 */
	public String getOrderInfo() {

		// 参数编码， 固定值
		String orderInfo = "_input_charset=\"utf-8\"";

		// 客户端号
		if (appenv != null && !appenv.equals("")) {
			orderInfo += "&app_id=" + "\"" + app_id + "\"";
		}

		// 客户端来源
		if (appenv != null && !appenv.equals("")) {
			orderInfo += "&appenv=" + "\"" + appenv + "\"";
		}

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + notify_url + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + out_trade_no + "\"";

		// 签约合作者身份ID
		orderInfo += "&partner=" + "\"" + PARTNER + "\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + total_fee + "\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		// orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		if (return_url != null && !return_url.equals("")) {
			orderInfo += "&return_url=\"" + return_url + "\"";
		}

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";
		return orderInfo;
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

}
