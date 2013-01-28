package org.bluef.simpleweiboauth;

import org.bluef.simpleweiboauth.enumerable.OAuthAccessToken;
import org.bluef.simpleweiboauth.helpers.MainLayoutListener;
import org.bluef.simpleweiboauth.layouts.MainLayout;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	private static final String CONSUMER_KEY = "966056985";
	private static final String REDIRECT_URL = "http://www.sina.com";

	private MainLayout mainLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mainLayout = (MainLayout) findViewById(R.id.mainLayout);

		mainLayout.setMainLayoutListener(mainLayoutListener);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, requestCode + " " + resultCode);

		String resultStr = data.getStringExtra("result");

		if (data.getBundleExtra("extra") != null) {
			Bundle bundle = data.getBundleExtra("extra");
			String accessToken = bundle.getString("access_token");
			String expiresIn = bundle.getString("expires_in");

			OAuthAccessToken token = new OAuthAccessToken();
			token.setToken(accessToken);
			token.setExpiresIn(expiresIn);

			String date = new java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new java.util.Date(token.getExpiresTime()));

			resultStr += "\ntoken: " + accessToken + "\nexpiresTime: " + date;
		}

		mainLayout.setText(resultStr);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		if (item.getItemId() == R.id.exit) {
			finish();
		}
		
		return true;
	}

	private void bind() {
		Intent i = new Intent(this, OAuthActivity.class);
		i.putExtra("redirect_uri", REDIRECT_URL);
		i.putExtra("consumer_key", CONSUMER_KEY);
		
		startActivityForResult(i, 1);
	}

	MainLayoutListener mainLayoutListener = new MainLayoutListener() {
		@Override
		public void onBind() {
			bind();
		}
	};

}
