package goofy2.swably.zh;

//import com.tencent.weibo.sdk.android.api.util.Util;
//import com.tencent.weibo.sdk.android.component.Authorize;
//import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
//import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
//import com.tencent.weibo.sdk.android.component.sso.WeiboToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.sso.SsoHandler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import goofy2.swably.Utils;

public class Start extends goofy2.swably.StartBase {
	private static final int REQUEST_CODE_QQ_WEBAUTH = 59372;
	private SsoHandler mSsoHandler;
//	private OnAuthListener qqAuthListener;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	View btnSina = this.findViewById(R.id.btnSina);
    	btnSina.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
//		   			signInWIth("sina");
			    Weibo weibo;
			    weibo = Weibo.getInstance("2808291982", "http://zh.swably.com/connections/accept/sina");
                mSsoHandler =new SsoHandler(Start.this, weibo);
                mSsoHandler.authorize( new WeiboAuthDialogListener());
			}
    	});
    	Utils.setTouchAnim(this, btnSina);

    	View btnQq = this.findViewById(R.id.btnQq);
    	btnQq.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
		   			signInWIth("qq");
//				auth(Const.QQ_APP_KEY, Const.QQ_APP_SECRET); // qq weibo sso has bug, onAuthPass is not called after auth successfully
			}
    	});
    	Utils.setTouchAnim(this, btnQq);
    
//    	qqAuthListener = new OnAuthListener() {
//
//			//如果当前设备没有安装腾讯微博客户端，走这里
//			@Override
//			public void onWeiBoNotInstalled() {
//				Toast.makeText(Start.this, getString(R.string.onWeiBoNotInstalled), Toast.LENGTH_SHORT).show();
//				Intent i = new Intent(Start.this,Authorize.class);
//				startActivityForResult(i, Start.REQUEST_CODE_QQ_WEBAUTH);
//			}
//
//			//如果当前设备没安装指定版本的微博客户端，走这里
//			@Override
//			public void onWeiboVersionMisMatch() {
//				Toast.makeText(Start.this, getString(R.string.onWeiboVersionMisMatch), Toast.LENGTH_SHORT).show();
//				Intent i = new Intent(Start.this,Authorize.class);
//				startActivityForResult(i, Start.REQUEST_CODE_QQ_WEBAUTH);
//			}
//
//			//如果授权失败，走这里
//			@Override
//			public void onAuthFail(int result, String err) {
//				Toast.makeText(Start.this, "result : " + result, 1000)
//						.show();
//			}
//
//			//授权成功，走这里
//			//授权成功后，所有的授权信息是存放在WeiboToken对象里面的，可以根据具体的使用场景，将授权信息存放到自己期望的位置，
//			//在这里，存放到了applicationcontext中
//			@Override
//			public void onAuthPassed(String name, WeiboToken token) {
//				Toast.makeText(Start.this, "passed", 1000).show();
////    					//
////    					Util.saveSharePersistent(context, "ACCESS_TOKEN", token.accessToken);
////    					Util.saveSharePersistent(context, "EXPIRES_IN", String.valueOf(token.expiresIn));
////    					Util.saveSharePersistent(context, "OPEN_ID", token.openID);
//////    					Util.saveSharePersistent(context, "OPEN_KEY", token.omasKey);
////    					Util.saveSharePersistent(context, "REFRESH_TOKEN", "");
//////    					Util.saveSharePersistent(context, "NAME", name);
//////    					Util.saveSharePersistent(context, "NICK", name);
////    					Util.saveSharePersistent(context, "CLIENT_ID", Util.getConfig().getProperty("APP_KEY"));
////    					Util.saveSharePersistent(context, "AUTHORIZETIME",
////    							String.valueOf(System.currentTimeMillis() / 1000l));
//				onGetAccessToken("qq", token.accessToken);
//			}
//    	};
    }

//	private void auth(long appid, String app_secket) {
//		final Context context = this.getApplicationContext();
//		//注册当前应用的appid和appkeysec，并指定一个OnAuthListener
//		//OnAuthListener在授权过程中实施监听
//		AuthHelper.register(this, appid, app_secket, qqAuthListener);
//
//		AuthHelper.auth(this, "");
//	}

	class WeiboAuthDialogListener implements WeiboAuthListener {
	    public final static int REQUEST_CODE = 32793; // not sure if this code will not change.

		@Override
		public void onComplete(Bundle values) {
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
        	onGetAccessToken("sina", token);
		}

		@Override
		public void onError(WeiboDialogError e) {
        	Log.d("", Const.APP_NAME + " WeiboAuthDialogListener onError: " + e.getMessage());
        	Utils.showToast(Start.this, e.getMessage());
		}

		@Override
		public void onCancel() {
		}

		@Override
		public void onWeiboException(WeiboException e) {
        	Log.d("", Const.APP_NAME + " WeiboAuthDialogListener onWeiboException: " + e.getMessage());
        	Utils.showToast(Start.this, e.getMessage());
		}

	}
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == Start.REQUEST_CODE_OTHER){
    		super.onActivityResult(requestCode, resultCode, data);
//    	}else if(requestCode == Start.REQUEST_CODE_QQ_WEBAUTH){
//            String name = data.getStringExtra("name");
//            WeiboToken token = (WeiboToken)data.getExtras().get("token");
//            if (!Utils.isEmpty(name) && token != null) {
//            	qqAuthListener.onAuthPassed(name, token);
//            }
    	}else{
        	Utils.logV(Start.this, "onActivityResult requestCode: " + requestCode);
        	// assume it's from weibo
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
    
}
