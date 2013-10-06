package goofy2.swably.zh;

import goofy2.swably.R;
import goofy2.swably.Utils;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

public class SharePublicActivity extends goofy2.swably.SharePublicActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final String shareText = getIntent().getStringExtra(Const.KEY_TEXT);
//		final String shareSubject = getIntent().getStringExtra(Const.KEY_SUBJECT);

		//for wechat timeline
//		regToWx();
				
		
		btnPublic2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				WXTextObject textObj = new WXTextObject();
				textObj.text = shareText;
				
				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = textObj;
				msg.description = shareText;
				
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = String.valueOf(System.currentTimeMillis());
				req.message = msg;
				req.scene = SendMessageToWX.Req.WXSceneTimeline;
				
				IWXAPI api = WXAPIFactory.createWXAPI(SharePublicActivity.this, Const.WECHAT_APP_ID, false);
				api.sendReq(req);
				
				finish();
			}
		});
		

	}


}
