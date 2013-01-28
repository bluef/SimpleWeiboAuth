package org.bluef.simpleweiboauth;

import org.bluef.simpleweiboauth.helpers.OAuthLayoutListener;
import org.bluef.simpleweiboauth.layouts.OAuthLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class OAuthActivity extends Activity {
	private final static String TAG = "OAuthActivity";

	private OAuthLayout oauthLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.oauth);

		oauthLayout = (OAuthLayout) findViewById(R.id.oauthLayout);

		oauthLayout.setOAuthLayoutListener(oAuthLayoutListener);

		oauthLayout.setConsumerKey(getIntent().getStringExtra("consumer_key"));
		oauthLayout.setRedirectUri(getIntent().getStringExtra("redirect_uri"));
	}

	@Override
	public void onBackPressed() {
		oAuthLayoutListener.onCancel();
		return;
	}

	OAuthLayoutListener oAuthLayoutListener = new OAuthLayoutListener() {
		@Override
		public void onSuccess(Bundle bundle) {
			Log.d(TAG, "onSuccess");
			Intent returnIntent = new Intent();

			returnIntent.putExtra("result", "success");
			returnIntent.putExtra("extra", bundle);

			if (getParent() == null) {
				setResult(RESULT_OK, returnIntent);
			} else {
				getParent().setResult(RESULT_OK, returnIntent);
			}

			finish();
		}

		@Override
		public void onDenied() {
			Log.d(TAG, "onDenied");

			Intent returnIntent = new Intent();

			returnIntent.putExtra("result", "denied");

			if (getParent() == null) {
				setResult(RESULT_CANCELED, returnIntent);
			} else {
				getParent().setResult(RESULT_CANCELED, returnIntent);
			}

			finish();
		}

		@Override
		public void onCancel() {
			Log.d(TAG, "onCancel");

			Intent returnIntent = new Intent();

			returnIntent.putExtra("result", "cancel");

			if (getParent() == null) {
				setResult(RESULT_CANCELED, returnIntent);
			} else {
				getParent().setResult(RESULT_CANCELED, returnIntent);
			}

			finish();
		}

		@Override
		public void onError() {
			Log.d(TAG, "onError");

			Intent returnIntent = new Intent();

			returnIntent.putExtra("result", "error");

			if (getParent() == null) {
				setResult(RESULT_CANCELED, returnIntent);
			} else {
				getParent().setResult(RESULT_CANCELED, returnIntent);
			}

			finish();
		}
	};
}