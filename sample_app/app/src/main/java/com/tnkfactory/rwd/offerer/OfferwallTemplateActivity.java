package com.tnkfactory.rwd.offerer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.tnkfactory.ad.TemplateLayoutUtils;
import com.tnkfactory.ad.TnkLayout;
import com.tnkfactory.ad.TnkSession;

import java.util.ArrayList;

public class OfferwallTemplateActivity extends AppCompatActivity {

    private TnkLayout tnkLayout = TemplateLayoutUtils.getBlueStyle_01();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offerwall_template);

        // Sample 스피너 설정
        setLayoutStyleSpinner();

        // 광고 목록 띄우기 - Activity
        setShowOfferwallButton();

        // 광고 목록 띄우기 - View
        setShowPopupOfferwallButton();

        // AdListView
        setShowEmbedOfferwallButton();
    }

    // Sample 스피너 설정
    private void setLayoutStyleSpinner() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Blue Style 01 (Basic_Square/Square)");
        arrayList.add("Blue Style 02 (Basic_Square/Button)");
        arrayList.add("Blue Style 03 (Basic_Ellipse/Square)");
        arrayList.add("Blue Style 04 (Basic_Ellipse/Button)");
        arrayList.add("Blue Style 05 (Tall_Square/Square)");
        arrayList.add("Blue Style 06 (Tall_Square/Button)");
        arrayList.add("Blue Style 07 (Tall_Ellipse/Square)");
        arrayList.add("Blue Style 08 (Tall_Ellipse/Button)");
        arrayList.add("Red Style 01 (Basic_Square/Square)");
        arrayList.add("Red Style 02 (Basic_Square/Button)");
        arrayList.add("Red Style 03 (Basic_Ellipse/Square)");
        arrayList.add("Red Style 04 (Basic_Ellipse/Button)");
        arrayList.add("Red Style 05 (Tall_Square/Square)");
        arrayList.add("Red Style 06 (Tall_Square/Button)");
        arrayList.add("Red Style 07 (Tall_Ellipse/Square)");
        arrayList.add("Red Style 08 (Tall_Ellipse/Button)");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(OfferwallTemplateActivity.this,
                android.R.layout.simple_spinner_dropdown_item, arrayList);

        Spinner spinner = findViewById(R.id.spinner_style);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        tnkLayout = TemplateLayoutUtils.getBlueStyle_01();
                        break;
                    case 1:
                        tnkLayout = TemplateLayoutUtils.getBlueStyle_02();
                        break;
                    case 2:
                        tnkLayout = TemplateLayoutUtils.getBlueStyle_03();
                        break;
                    case 3:
                        tnkLayout = TemplateLayoutUtils.getBlueStyle_04();
                        break;
                    case 4:
                        tnkLayout = TemplateLayoutUtils.getBlueStyle_05();
                        break;
                    case 5:
                        tnkLayout = TemplateLayoutUtils.getBlueStyle_06();
                        break;
                    case 6:
                        tnkLayout = TemplateLayoutUtils.getBlueStyle_07();
                        break;
                    case 7:
                        tnkLayout = TemplateLayoutUtils.getBlueStyle_08();
                        break;
                    case 8:
                        tnkLayout = TemplateLayoutUtils.getRedStyle_01();
                        break;
                    case 9:
                        tnkLayout = TemplateLayoutUtils.getRedStyle_02();
                        break;
                    case 10:
                        tnkLayout = TemplateLayoutUtils.getRedStyle_03();
                        break;
                    case 11:
                        tnkLayout = TemplateLayoutUtils.getRedStyle_04();
                        break;
                    case 12:
                        tnkLayout = TemplateLayoutUtils.getRedStyle_05();
                        break;
                    case 13:
                        tnkLayout = TemplateLayoutUtils.getRedStyle_06();
                        break;
                    case 14:
                        tnkLayout = TemplateLayoutUtils.getRedStyle_07();
                        break;
                    case 15:
                        tnkLayout = TemplateLayoutUtils.getRedStyle_08();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // 광고 목록 띄우기 - Activity
    private void setShowOfferwallButton() {
        Button button = findViewById(R.id.btn_show);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TnkSession.showAdList(OfferwallTemplateActivity.this, "Title", tnkLayout);
            }
        });
    }

    // 광고 목록 띄우기 - View
    private void setShowPopupOfferwallButton() {
        Button button = findViewById(R.id.btn_show_popup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TnkSession.popupAdList(OfferwallTemplateActivity.this, "Title", null, tnkLayout);
            }
        });
    }

    // AdListView
    private void setShowEmbedOfferwallButton() {
        Button button = findViewById(R.id.btn_show_embed);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfferwallTemplateActivity.this, OfferwallEmbedActivity.class);
                // 선택한 템플릿 레이아웃 전달
                intent.putExtra("tnk_layout", tnkLayout);

                startActivity(intent);
            }
        });
    }
}
