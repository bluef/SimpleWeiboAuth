package org.bluef.simpleweiboauth.interfaces;

import android.os.Bundle;

public interface ISNSBinder {
	public void auth();

	public void handleResult(String result);
	public void handleResult(String result, Bundle extra);

	public String getSource();
}