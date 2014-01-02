package goofy2.swably.zh;

import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.sso.SsoHandler;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import goofy2.swably.Utils;

public class Start extends goofy2.swably.StartBase {
	private SsoHandler mSsoHandler;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.start);
//        disableSliding();
//
//    	Button btnSkip = (Button) this.findViewById(R.id.btnSkip);
//    	btnSkip.setTypeface(mLightFont);
//    	btnSkip.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View arg0) {
//				startActivity(new Intent(getApplicationContext(), Home.class));
//				finish();
//			}
//        });

        
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
    }


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
    	if(requestCode == Start.REQUEST_CODE_OTHER)
    		super.onActivityResult(requestCode, resultCode, data);
    	else{
        	Utils.logV(Start.this, "onActivityResult requestCode: " + requestCode);
        	// assume it's from weibo
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
    
}
