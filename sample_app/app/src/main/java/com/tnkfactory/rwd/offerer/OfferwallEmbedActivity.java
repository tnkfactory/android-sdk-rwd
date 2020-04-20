package com.tnkfactory.rwd.offerer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.tnkfactory.ad.AdListView;
import com.tnkfactory.ad.TnkLayout;
import com.tnkfactory.ad.TnkSession;

public class OfferwallEmbedActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.activity_with_offerwall);

    	// 전달 받은 템플릿 레이아웃
    	Intent intent = getIntent();
    	TnkLayout layout = intent.getParcelableExtra("tnk_layout");
    	
    	ViewGroup viewGroup = findViewById(R.id.adlist);

    	// AdListView 생성
    	AdListView offerwallView = TnkSession.createAdListView(this, layout);
    	offerwallView.setTitle("your title here.");

    	viewGroup.addView(offerwallView);

    	// 리스트 로드
    	offerwallView.loadAdList();
	}
}
