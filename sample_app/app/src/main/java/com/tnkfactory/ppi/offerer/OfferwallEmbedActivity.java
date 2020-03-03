package com.tnkfactory.ppi.offerer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.tnkfactory.ad.AdListView;
import com.tnkfactory.ad.TnkLayout;
import com.tnkfactory.ad.TnkSession;

/**
 * You can embed tnk offerwall view into your own activities.
 * @author kimhd
 *
 */
public class OfferwallEmbedActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.activity_with_offerwall);
    	
    	Intent intent = getIntent();
    	TnkLayout layout = intent.getParcelableExtra("tnk_layout");
    	
    	ViewGroup viewGroup = (ViewGroup)findViewById(R.id.adlist);
        
    	AdListView offerwallView = TnkSession.createAdListView(this, layout);
    	offerwallView.setTitle("your title here.");
    	
    	viewGroup.addView(offerwallView);
    	
    	// write your logic for UI-controls that you added in AdListView.
    	Button eventButton = (Button)offerwallView.findViewById(R.id.event_button);
    	if (eventButton != null) {
	    	eventButton.setVisibility(View.VISIBLE);
	    	eventButton.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// your own logic  here
				}
	    		
	    	});
    	}
    	
    	Button closeButton = (Button)offerwallView.findViewById(R.id.close_button);
    	if (closeButton != null) {
	    	closeButton.setVisibility(View.VISIBLE);
	    	closeButton.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					finish();
				}
	    		
	    	});
    	}
    	
    	offerwallView.loadAdList();
	}
  

}
