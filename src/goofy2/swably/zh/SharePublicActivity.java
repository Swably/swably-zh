package goofy2.swably.zh;

import org.json.JSONException;
import org.json.JSONObject;

import goofy2.swably.R;
import goofy2.swably.Utils;
import goofy2.swably.data.App;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.analytics.tracking.android.MapBuilder;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

public class SharePublicActivity extends goofy2.swably.SharePublicActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final String shareText = getIntent().getStringExtra(Const.KEY_TEXT);
		final String shareSubject = getIntent().getStringExtra(Const.KEY_SUBJECT);
		final String shareReview = getIntent().getStringExtra(Const.KEY_REVIEW);
		final String shareApp = getIntent().getStringExtra(Const.KEY_APP);

		//for wechat timeline
//		regToWx();
				
		
		btnPublic2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				WXTextObject textObj = new WXTextObject();
//				textObj.text = shareText;
//				
//				WXMediaMessage msg = new WXMediaMessage();
//				msg.mediaObject = textObj;
//				msg.description = shareText;
//				msg.title = shareSubject;
//				
//				SendMessageToWX.Req req = new SendMessageToWX.Req();
//				req.transaction = String.valueOf(System.currentTimeMillis());
//				req.message = msg;
//				req.scene = SendMessageToWX.Req.WXSceneTimeline;
//				
//				IWXAPI api = WXAPIFactory.createWXAPI(SharePublicActivity.this, Const.WECHAT_APP_ID, false);
//				api.sendReq(req);
				

				String title = null, description = null, url = null, iconUrl = null;
				Bitmap bitmap = null;
				
				if(shareApp != null){
					try {
						App app = new App(new JSONObject(shareApp));
						title = app.getName();
						url = Utils.genAppUrl(app);
						iconUrl = app.getIcon();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else if(shareReview != null){
					try {
						JSONObject review = new JSONObject(shareReview);
						App app = new App(review.optJSONObject("app"));
						title = "#" + app.getName() + " " + review.optString("content") + " -- @" + review.optJSONObject("user").optString("screen_name");
						url = Utils.genReviewUrl(review);
						iconUrl = app.getIcon();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					title = shareSubject;
					description = shareText;
				}
				
//				bitmap = Utils.getImageFromFile(getApplicationContext(), Utils.getImageFileName(iconUrl), 42, 42); // 微信要求thumbData不能超过32KB,即84*84
				bitmap = Utils.getImageFromFile(getApplicationContext(), Utils.getImageFileName(iconUrl), 84, 84); // 微信要求thumbData不能超过32KB,即84*84

				WXWebpageObject webpage = new WXWebpageObject();
				webpage.webpageUrl = url.replace("?r=share", "?r="+getString(R.string.share_public2_id));
				WXMediaMessage msg = new WXMediaMessage(webpage);
				msg.title = title;
				msg.description = description;
//				msg.setThumbImage(bitmap);
				if(bitmap != null) msg.thumbData = Utils.bmpToByteArray(bitmap, true);
				
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = String.valueOf(System.currentTimeMillis());
				req.message = msg;
				req.scene = SendMessageToWX.Req.WXSceneTimeline;

				IWXAPI api = WXAPIFactory.createWXAPI(SharePublicActivity.this, Const.WECHAT_APP_ID, false);
				api.sendReq(req);
				
				
				finish();
				tracker.send(MapBuilder.createEvent("ui_action", "button_press", getString(R.string.share_public2), null).build());
			}
		});
		

	}


}
