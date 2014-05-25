package goofy2.swably.zh;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import goofy2.swably.Const;
import goofy2.swably.Utils;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import goofy2.swably.SwablyApplication;

public class ApplicationZh extends SwablyApplication {
	
	@Override
	protected void setConst(){
		Const.DNS_URL = "http://zh.swably.com/account/dns?format=json";
		Const.DEFAULT_MAIN_HOST = "zh.swably.com";
		Const.DEFAULT_UPLOAD_HOST = "uploadzh.swably.com";
		super.setConst();
		Const.APP_NAME = "万宝乐";
		Const.APK_FOLEDER_NAME = "wanbaole";
		Const.LOAD_FONTS = false;
		Const.LANG = "zh";
//		Const.UPGRADE_URL = "/downloads/swably_zh.apk";
		
		Const.BROADCAST_CACHE_APPS_PROGRESS = "goofy2.swably.zh.CACHE_APPS_PROGRESS";
		Const.BROADCAST_DOWNLOAD_PROGRESS = "goofy2.swably.zh.DOWNLOAD_PROGRESS";
		Const.BROADCAST_UPLOAD_PROGRESS = "goofy2.swably.zh.UPLOAD_PROGRESS";
		Const.BROADCAST_REVIEW_DELETED = "goofy2.swably.zh.REVIEW_DELETED";
		Const.BROADCAST_REVIEW_ADDED = "goofy2.swably.zh.REVIEW_ADDED";
		Const.BROADCAST_STAR_ADDED = "goofy2.swably.zh.STAR_ADDED";
		Const.BROADCAST_STAR_DELETED = "goofy2.swably.zh.STAR_DELETED";
		Const.BROADCAST_LIKE_ADDED = "goofy2.swably.zh.LIKE_ADDED";
		Const.BROADCAST_LIKE_DELETED = "goofy2.swably.zh.LIKE_DELETED";
		Const.BROADCAST_FOLLOW_ADDED = "goofy2.swably.zh.FOLLOW_ADDED";
		Const.BROADCAST_FOLLOW_DELETED = "goofy2.swably.zh.FOLLOW_DELETED";
		Const.BROADCAST_REFRESH_APP = "goofy2.swably.zh.REVIEW_APP";
		Const.BROADCAST_REFRESH_USER = "goofy2.swably.zh.REVIEW_USER";
		Const.BROADCAST_FINISH = "goofy2.swably.zh.FINISH";
		
		Const.SHARE_PRIVATE_ACTIVITY = SharePrivateActivity.class;
		Const.SHARE_PUBLIC_ACTIVITY = SharePublicActivity.class;
		Const.START_ACTIVITY = Start.class;
		
		regToWx();
	}
	
	private void regToWx(){
		IWXAPI api = WXAPIFactory.createWXAPI(this, goofy2.swably.zh.Const.WECHAT_APP_ID, false);
//		api.unregisterApp();
		boolean ret = api.registerApp(goofy2.swably.zh.Const.WECHAT_APP_ID);
//	    Toast.makeText(getApplicationContext(), ret ? "true" : "false", Toast.LENGTH_SHORT).show();
	}

}
