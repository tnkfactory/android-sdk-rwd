package com.tnkfactory.rwd.offerer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.tnkfactory.ad.AdListTabView;
import com.tnkfactory.ad.AdListType;
import com.tnkfactory.ad.TnkAdListener;
import com.tnkfactory.ad.TnkLayout;
import com.tnkfactory.ad.TnkSession;

public class TabOfferwallEmbedActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.activity_with_tab_offerwall);

    	// 전달 받은 템플릿 레이아웃
    	Intent intent = getIntent();
    	TnkLayout layout = intent.getParcelableExtra("tnk_layout");
    	
    	ViewGroup viewGroup = findViewById(R.id.adlist);

    	// AdListTabView 생성
    	AdListTabView offerwallView = TnkSession.createAdListTabView(this, layout, AdListType.ALL, AdListType.PPI, AdListType.CPS);
		offerwallView.setListener(new TnkAdListener() {
			@Override
			public void onClose(int type) {
				if (type == TnkAdListener.CLOSE_REFUSE_PRIVACY) {
					finish(); // 앱 종료
				}
			}

			@Override
			public void onShow() {

			}

			@Override
			public void onFailure(int errCode) {

			}

			@Override
			public void onLoad() {

			}
		});

    	viewGroup.addView(offerwallView);
	}
}
