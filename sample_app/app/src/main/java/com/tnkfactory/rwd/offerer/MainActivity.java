package com.tnkfactory.rwd.offerer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tnkfactory.ad.Logger;
import com.tnkfactory.ad.ServiceCallback;
import com.tnkfactory.ad.TnkSession;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.enableLogging(true);

        // 유저 식별 값 설정
        TnkSession.setUserName(MainActivity.this, "Test_User");

        // 실행형 광고
        TnkSession.applicationStarted(this);

        // COPPA 설정 (true - ON / false - OFF)
        TnkSession.setCOPPA(MainActivity.this, false);

        // Sample Layout 초기화
        initLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final TextView pointView = findViewById(R.id.main_point);

        //Tnk 서버에 적립되어 있는 사용자 포인트 값 조회
        TnkSession.queryPoint(this, true, new ServiceCallback() {
            @Override
            public void onReturn(Context context, Object result) {
                Integer point = (Integer)result;
                pointView.setText(String.valueOf(point));
            }
        });
    }

    // Sample layout
    private void initLayout() {
        ListView listView = findViewById(R.id.list_main);
        ArrayList<MainListItem> itemList = new ArrayList<MainListItem>();

        itemList.add(MainListItem.HEADER_01);
        itemList.add(MainListItem.BASIC);
        itemList.add(MainListItem.POPUP_BASIC);
        itemList.add(MainListItem.EMBED_BASIC);
        itemList.add(MainListItem.TEMPLATE);

        itemList.add(MainListItem.HEADER_02);
        itemList.add(MainListItem.INTERSTITIAL_AD);


        MainListAdapter adapter = new MainListAdapter(this, itemList);
        listView.setAdapter(adapter);

        // List Click Event
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = null;

                MainListItem item = (MainListItem) view.getTag();
                switch (item) {
                    case BASIC:
                        TnkSession.showAdList(MainActivity.this, "Basic");
                        break;
                    case POPUP_BASIC:
                        TnkSession.popupAdList(MainActivity.this,  "Popup");
                        break;
                    case EMBED_BASIC:
                        intent = new Intent(MainActivity.this, OfferwallEmbedActivity.class);
                        break;
                    case TEMPLATE:
                        intent = new Intent(MainActivity.this, OfferwallTemplateActivity.class);
                        break;

                    case INTERSTITIAL_AD:
                        TnkSession.prepareInterstitialAd(MainActivity.this, TnkSession.PPI);
                        TnkSession.showInterstitialAd(MainActivity.this);
                        break;
                }

                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    // Sample list adapter
    public class MainListAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<MainListItem> list;

        public MainListAdapter(Context context, ArrayList<MainListItem> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            MainListItem data = list.get(position);
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(data.isHeader() == true ? R.layout.main_list_header : R.layout.main_list_item, null);
            }

            TextView txtItem = view.findViewById(R.id.text);
            txtItem.setText(data.getValue());
            view.setTag(data);

            return view;
        }
    }
}
