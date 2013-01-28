package org.bluef.simpleweiboauth.layouts;

import org.bluef.simpleweiboauth.R;
import org.bluef.simpleweiboauth.helpers.MainLayoutListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainLayout extends LinearLayout {
	private Button btnBind;
	private TextView textView;
	
	private MainLayoutListener listener;

	public MainLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setText(String value) {
		if (textView != null) {
			textView.setText(value);
		}
	}

	public void setMainLayoutListener(MainLayoutListener value) {
		listener = value;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (btnBind == null) {
			textView = (TextView) findViewById(R.id.textView);
			btnBind = (Button) findViewById(R.id.btnBind);
			btnBind.setOnClickListener(btnBindClickListener);
		}

		super.onLayout(changed, l, t, r, b);
	}

	OnClickListener btnBindClickListener = new OnClickListener() {
		public void onClick(View v) {
			listener.onBind();
		}
	};
}