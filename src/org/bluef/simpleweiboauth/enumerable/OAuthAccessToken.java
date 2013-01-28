package org.bluef.simpleweiboauth.enumerable;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * 姝ょ被灏佽浜嗏�access_token鈥濓紝鈥渆xpires_in鈥濓紝"refresh_token"锛�
 *骞舵彁渚涗簡浠栦滑鐨勭鐞嗗姛鑳�
 * @author luopeng (luopeng@staff.sina.com.cn)
 */
public class OAuthAccessToken {
	private String mAccessToken = "";
	private String mRefreshToken = "";
	private long mExpiresTime = 0;

//	private String mOauth_verifier = "";
//	protected String[] responseStr = null;
//	protected SecretKeySpec mSecretKeySpec;
	/**
	 * Oauth2AccessToken 鐨勬瀯閫犲嚱鏁�
	 */
	public OAuthAccessToken() {
	}
	/**
	 * 鏍规嵁鏈嶅姟鍣ㄨ繑鍥炵殑responsetext鐢熸垚Oauth2AccessToken 鐨勬瀯閫犲嚱鏁帮紝
	 * 姝ゆ柟娉曚細灏唕esponsetext閲岀殑鈥渁ccess_token鈥濓紝鈥渆xpires_in鈥濓紝"refresh_token"瑙ｆ瀽鍑烘潵
	 * @param responsetext 鏈嶅姟鍣ㄨ繑鍥炵殑responsetext
	 */
	public OAuthAccessToken(String responsetext) {
		if (responsetext != null) {
			if (responsetext.indexOf("{") >= 0) {
				try {
					JSONObject json = new JSONObject(responsetext);
					setToken(json.optString("access_token"));
					setExpiresIn(json.optString("expires_in"));
					setRefreshToken(json.optString("refresh_token"));
				} catch (JSONException e) {
					
				}
			}
		}
	}
	/**
	 * Oauth2AccessToken鐨勬瀯閫犲嚱鏁帮紝鏍规嵁accessToken 鍜宔xpires_in 鐢熸垚Oauth2AccessToken瀹炰緥
	 * @param accessToken  璁块棶浠ょ墝
	 * @param expires_in 鏈夋晥鏈燂紝鍗曚綅锛氭绉掞紱浠呭綋浠庢湇鍔″櫒鑾峰彇鍒癳xpires_in鏃堕�鐢紝琛ㄧず璺濈瓒呰繃璁よ瘉鏃堕棿杩樻湁澶氬皯绉�
	 */
	public OAuthAccessToken(String accessToken, Long expiresTime) {
		mAccessToken = accessToken;
		mExpiresTime = expiresTime;
	}
	/**
	 *  AccessToken鏄惁鏈夋晥,濡傛灉accessToken涓虹┖鎴栬�expiresTime杩囨湡锛岃繑鍥瀎alse锛屽惁鍒欒繑鍥瀟rue
	 *  @return 濡傛灉accessToken涓虹┖鎴栬�expiresTime杩囨湡锛岃繑鍥瀎alse锛屽惁鍒欒繑鍥瀟rue
	 */
	public boolean isSessionValid() {
		return (!TextUtils.isEmpty(mAccessToken) && (mExpiresTime == 0 || (System
				.currentTimeMillis() < mExpiresTime)));
	}
	/**
	 * 鑾峰彇accessToken
	 */
	public String getToken() {
		return this.mAccessToken;
	}
	/**
     * 鑾峰彇refreshToken
     */
	public String getRefreshToken() {
		return mRefreshToken;
	}
	/**
	 * 璁剧疆refreshToken
	 * @param mRefreshToken
	 */
	public void setRefreshToken(String mRefreshToken) {
		this.mRefreshToken = mRefreshToken;
	}
	/**
	 * 鑾峰彇瓒呮椂鏃堕棿锛屽崟浣� 姣锛岃〃绀轰粠鏍兼灄濞佹不鏃堕棿1970骞�1鏈�1鏃�0鏃�0鍒�0绉掕捣鑷崇幇鍦ㄧ殑鎬�姣鏁�
	 */
	public long getExpiresTime() {
		return mExpiresTime;
	}

	/**
	 * 璁剧疆杩囨湡鏃堕棿闀垮害鍊硷紝浠呭綋浠庢湇鍔″櫒鑾峰彇鍒版暟鎹椂浣跨敤姝ゆ柟娉�
	 *            
	 */
	public void setExpiresIn(String expiresIn) {
		if (expiresIn != null && !expiresIn.equals("0")) {
			setExpiresTime(System.currentTimeMillis() + Long.parseLong(expiresIn) * 1000);
		}
	}

	/**
	 * 璁剧疆杩囨湡鏃跺埢鐐�鏃堕棿鍊�
	 * @param mExpiresTime 鍗曚綅锛氭绉掞紝琛ㄧず浠庢牸鏋楀▉娌绘椂闂�970骞�1鏈�1鏃�0鏃�0鍒�0绉掕捣鑷崇幇鍦ㄧ殑鎬�姣鏁�
	 *            
	 */
	public void setExpiresTime(long mExpiresTime) {
		this.mExpiresTime = mExpiresTime;
	}
	/**
	 * 璁剧疆accessToken
	 * @param mToken
	 */
	public void setToken(String mToken) {
		this.mAccessToken = mToken;
	}
}