package org.bluef.simpleweiboauth.layouts;

import org.bluef.simpleweiboauth.R;
import org.bluef.simpleweiboauth.helpers.OAuthLayoutListener;
import org.bluef.simpleweiboauth.helpers.UrlUtil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

public class OAuthLayout extends LinearLayout {
	private final static String TAG = "OAuthLayout";
	
	private Button btnCancel;
	private WebView webView;
	private ProgressDialog spinner;
	
	private OAuthLayoutListener listener;

	private String consumerKey = "";
	private String redirectUri = "";
	
	private Context context;

	public OAuthLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;
	}

	public void setOAuthLayoutListener(OAuthLayoutListener value) {
		listener = value;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (btnCancel == null) {
			try {
				initViews();
				initListener();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		super.onLayout(changed, l, t, r, b);
	}

	public void setConsumerKey(String value) {
		consumerKey = value;
	}

	public void setRedirectUri(String value) {
		redirectUri = value;
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initViews() {
		btnCancel = (Button) findViewById(R.id.btnCancel);

		webView = (WebView) findViewById(R.id.webView);

		webView.setWebViewClient(oAuthWebViewClient);
		webView.setHorizontalScrollBarEnabled(false);
		webView.setVerticalScrollBarEnabled(false);
		webView.getSettings().setJavaScriptEnabled(true);

		spinner = new ProgressDialog(getContext());
		spinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
		spinner.setMessage("Loading...");

		refreshWebView();
	}

	private void initListener() {
		btnCancel.setOnClickListener(btnCancelClickListener);
	}

	private void refreshWebView() {
		if (webView != null) {
			webView.loadUrl("https://open.weibo.cn/oauth2/authorize?client_id=" + consumerKey + "&response_type=token&redirect_uri=" + redirectUri + "&display=mobile");
		}
	}

	private void handleRedirectUrl(WebView view, String url) {
		Bundle values = UrlUtil.parseUrl(url);

		Log.d(TAG, url + " " + values.toString());

		String error = values.getString("error");
		String error_code = values.getString("error_code");

		if (error == null && error_code == null) {
			listener.onSuccess(values);
		} else if (error.equals("access_denied")) {
			// 用户或授权服务器拒绝授予数据访问权限
			listener.onDenied();
		} else {
			listener.onError();
		}
	}

	OnClickListener btnCancelClickListener = new OnClickListener() {
		public void onClick(View v) {
			listener.onCancel();
		}
	};

	WebViewClient oAuthWebViewClient = new WebViewClient() {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Log.d(TAG, "onPageStarted " + url);

			if (url.startsWith(redirectUri)) {
				handleRedirectUrl(view, url);
				view.stopLoading();
				return;
			}

			spinner.show();

			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			Log.d(TAG, "onPageFinished URL: " + url);
			super.onPageFinished(view, url);
			if (spinner.isShowing()) {
				spinner.dismiss();
			}
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.d(TAG, "Redirect URL: " + url);
			if (url.startsWith("sms:")) {  //针对webview里的短信注册流程，需要在此单独处理sms协议
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);  
				sendIntent.putExtra("address", url.replace("sms:", ""));  
				sendIntent.setType("vnd.android-dir/mms-sms");  
				context.startActivity(sendIntent);  
				return true;
			}  

			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			listener.onError();
		}
		
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			handler.proceed();
		}
	};
}