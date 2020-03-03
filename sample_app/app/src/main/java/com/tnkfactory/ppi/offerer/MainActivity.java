package com.tnkfactory.ppi.offerer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tnkfactory.ad.ServiceCallback;
import com.tnkfactory.ad.TnkLayout;
import com.tnkfactory.ad.TnkSession;
import com.tnkfactory.ad.TnkStyle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 유저 식별 값 설정 (필수)
        TnkSession.setUserName(MainActivity.this, "Test_User");

        // show PPI offer-wall as activity
        final Button showAdButton = findViewById(R.id.main_ad);
        showAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TnkStyle.clear();
                TnkSession.showAdList(MainActivity.this,"Your title here");
            }
        });

        // show PPI offer-wall as popup view
        final Button showAdPopupButton = findViewById(R.id.main_ad_popup);
        showAdPopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TnkStyle.clear();
                TnkSession.popupAdList(MainActivity.this,"Your title here");
            }
        });

        // show PPI interstitial Ad
        final Button showFadButton = findViewById(R.id.main_fad);
        showFadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TnkSession.prepareInterstitialAd(MainActivity.this, TnkSession.PPI);
                TnkSession.showInterstitialAd(MainActivity.this);
            }
        });

        // show PPI offer-wall with styled design as activity
        final Button showStyledAdButton = findViewById(R.id.main_ad_style);
        showStyledAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTnkStyleFull();
                TnkSession.showAdList(MainActivity.this,"Your title here");
            }
        });

        // show PPI offer-wall with styled design as popup view
        final Button showStyledAdPopupButton = findViewById(R.id.main_ad_style_popup);
        showStyledAdPopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTnkStylePopup();
                TnkSession.popupAdList(MainActivity.this,"Your title here", null);
            }
        });

        final Button showStyledAdEmbedButton = findViewById(R.id.main_ad_style_embed);
        showStyledAdEmbedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTnkStyleFull();
                Intent intent = new Intent(MainActivity.this, OfferwallEmbedActivity.class);
                startActivity(intent);
            }
        });

        // show PPI offer-wall with customzied design as activity
        final Button showCustomziedAdButton = findViewById(R.id.main_ad_custom);
        showCustomziedAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TnkSession.showAdList(MainActivity.this,"Your title here", makeLayout());
            }
        });

        // show PPI offer-wall with customzied design as popup view
        final Button showCustomziedAdPopupButton = findViewById(R.id.main_ad_custom_popup);
        showCustomziedAdPopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TnkSession.popupAdList(MainActivity.this,"Your title here", null, makePopupLayout());
            }
        });

        final Button showCustomziedAdEmbedButton = findViewById(R.id.main_ad_custom_embed);
        showCustomziedAdEmbedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OfferwallEmbedActivity.class);
                intent.putExtra("tnk_layout", makeLayout());
                startActivity(intent);
            }
        });

        // purchase item with tnk point
        final Button buyItemButton = findViewById(R.id.main_item);
        buyItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TnkSession.purchaseItem(MainActivity.this, 30, "item.00001", true, new ServiceCallback() {
                    @Override
                    public void onReturn(Context context, Object result) {
                        long[] ret = (long[])result;
                        Log.d("tnkad", "purchase result " + ret[0] + ", " + ret[1]);
                        TextView pointView = findViewById(R.id.main_point);
                        pointView.setText(String.valueOf(ret[0]));
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        final TextView pointView = findViewById(R.id.main_point);
        TnkSession.queryPoint(this, true, new ServiceCallback() {
            @Override
            public void onReturn(Context context, Object result) {
                Integer point = (Integer)result;
                pointView.setText(String.valueOf(point));
            }
        });
    }

    private TnkLayout makeLayout() {
        TnkLayout res = new TnkLayout();

        res.adwall.numColumnsPortrait = 2;
        res.adwall.numColumnsLandscape = 3;

        res.adwall.layout = R.layout.myofferwall_adlist;
        res.adwall.idTitle = R.id.offerwall_title;
        res.adwall.idList = R.id.offerwall_adlist;

        res.adwall.item.layout = R.layout.myofferwall_item;
        res.adwall.item.height = 150;
        res.adwall.item.idIcon = R.id.ad_icon;
        res.adwall.item.idTitle = R.id.ad_title;
        res.adwall.item.idSubtitle = R.id.ad_desc;
        res.adwall.item.idTag = R.id.ad_tag;
        res.adwall.item.colorBg = 0xffe5e5e5;

		res.adwall.item.bgItemEven = R.drawable.list_item_bg;
		res.adwall.item.bgItemOdd = R.drawable.list_item_bg2;

        res.adwall.item.tag.bgTagFree = R.drawable.az_list_bt_free;
        res.adwall.item.tag.bgTagPaid = R.drawable.az_list_bt_pay;
        res.adwall.item.tag.bgTagWeb = R.drawable.az_list_bt_web;
        res.adwall.item.tag.bgTagCheck = R.drawable.az_list_bt_install;

        res.adwall.item.tag.tcTagFree = 0xffffffff;
        res.adwall.item.tag.tcTagPaid = 0xffffffff;
        res.adwall.item.tag.tcTagWeb = 0xffffffff;
        res.adwall.item.tag.tcTagCheck = 0xffffffff;

        res.adwall.detail.layout = R.layout.myofferwall_detail;
        res.adwall.detail.idIcon = R.id.ad_icon;
        res.adwall.detail.idTitle = R.id.ad_title;
        res.adwall.detail.idSubtitle = R.id.ad_desc;
        res.adwall.detail.idTag = R.id.ad_tag;
        res.adwall.detail.idAction = R.id.ad_action;
        res.adwall.detail.idConfirm = R.id.ad_ok;
        res.adwall.detail.idCancel = R.id.ad_cancel;

        return res;
    }

    private TnkLayout makePopupLayout() {
        TnkLayout res = new TnkLayout();

        res.adwall.numColumnsPortrait = 2;
        res.adwall.numColumnsLandscape = 3;

        res.adwall.layout = R.layout.myofferwall_popup;
        res.adwall.idTitle = R.id.offerwall_title;
        res.adwall.idList = R.id.offerwall_adlist;
        res.adwall.idClose = R.id.close_button;

        res.adwall.item.layout = R.layout.myofferwall_item;
        res.adwall.item.height = 150;
        res.adwall.item.idIcon = R.id.ad_icon;
        res.adwall.item.idTitle = R.id.ad_title;
        res.adwall.item.idSubtitle = R.id.ad_desc;
        res.adwall.item.idTag = R.id.ad_tag;

        res.adwall.item.bgItemEven = R.drawable.list_item_bg;
        res.adwall.item.bgItemOdd = R.drawable.list_item_bg2;

        res.adwall.item.tag.bgTagFree = R.drawable.az_list_bt_free;
        res.adwall.item.tag.bgTagPaid = R.drawable.az_list_bt_pay;
        res.adwall.item.tag.bgTagWeb = R.drawable.az_list_bt_web;
        res.adwall.item.tag.bgTagCheck = R.drawable.az_list_bt_install;

        res.adwall.item.tag.tcTagFree = 0xffffffff;
        res.adwall.item.tag.tcTagPaid = 0xffffffff;
        res.adwall.item.tag.tcTagWeb = 0xffffffff;
        res.adwall.item.tag.tcTagCheck = 0xffffffff;

        res.adwall.detail.layout = R.layout.myofferwall_detail;
        res.adwall.detail.idIcon = R.id.ad_icon;
        res.adwall.detail.idTitle = R.id.ad_title;
        res.adwall.detail.idSubtitle = R.id.ad_desc;
        res.adwall.detail.idTag = R.id.ad_tag;
        res.adwall.detail.idAction = R.id.ad_action;
        res.adwall.detail.idConfirm = R.id.ad_ok;
        res.adwall.detail.idCancel = R.id.ad_cancel;

        return res;
    }

    private void setTnkStylePopup() {
        TnkStyle.clear();

        TnkStyle.AdWall.background = R.drawable.black_middle_bg;

        TnkStyle.AdWall.Header.background = R.drawable.black_upper_bg;
        TnkStyle.AdWall.Header.textColor = 0xffffffff;
        TnkStyle.AdWall.Header.textSize = 22;

        TnkStyle.AdWall.Footer.background = R.drawable.black_bottom_bg;
        TnkStyle.AdWall.Footer.textColor = 0xffffffff;
        TnkStyle.AdWall.Footer.textSize = 13;

        TnkStyle.AdWall.Item.Title.textSize = 16;
        TnkStyle.AdWall.Item.Subtitle.textColor = 0xffff871c;
        TnkStyle.AdWall.Item.Subtitle.textSize = 12;

        TnkStyle.AdWall.Item.Tag.Free.background = R.drawable.az_list_bt_free;
        TnkStyle.AdWall.Item.Tag.Free.textColor = 0xffffffff;
        TnkStyle.AdWall.Item.Tag.Paid.background = R.drawable.az_list_bt_pay;
        TnkStyle.AdWall.Item.Tag.Paid.textColor = 0xffffffff;
        TnkStyle.AdWall.Item.Tag.Web.background = R.drawable.az_list_bt_web;
        TnkStyle.AdWall.Item.Tag.Web.textColor = 0xffffffff;
        TnkStyle.AdWall.Item.Tag.Confirm.background = R.drawable.az_list_bt_install;
        TnkStyle.AdWall.Item.Tag.Confirm.textColor = 0xffffffff;
    }

    private void setTnkStyleFull() {
        TnkStyle.clear();

        TnkStyle.AdWall.Header.backgroundColor = 0xff000000;
        TnkStyle.AdWall.Header.textColor = 0xffffffff;
        TnkStyle.AdWall.Header.textSize = 22;

        TnkStyle.AdWall.Item.Title.textSize = 16;
        TnkStyle.AdWall.Item.Subtitle.textColor = 0xffff871c;
        TnkStyle.AdWall.Item.Subtitle.textSize = 12;

        TnkStyle.AdWall.Item.Tag.Free.background = R.drawable.az_list_bt_free;
        TnkStyle.AdWall.Item.Tag.Free.textColor = 0xffffffff;
        TnkStyle.AdWall.Item.Tag.Paid.background = R.drawable.az_list_bt_pay;
        TnkStyle.AdWall.Item.Tag.Paid.textColor = 0xffffffff;
        TnkStyle.AdWall.Item.Tag.Web.background = R.drawable.az_list_bt_web;
        TnkStyle.AdWall.Item.Tag.Web.textColor = 0xffffffff;
        TnkStyle.AdWall.Item.Tag.Confirm.background = R.drawable.az_list_bt_install;
        TnkStyle.AdWall.Item.Tag.Confirm.textColor = 0xffffffff;
    }
}
