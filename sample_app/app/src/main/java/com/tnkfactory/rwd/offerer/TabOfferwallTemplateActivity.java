package com.tnkfactory.rwd.offerer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.tnkfactory.ad.AdListType;
import com.tnkfactory.ad.TemplateLayoutUtils;
import com.tnkfactory.ad.TnkLayout;
import com.tnkfactory.ad.TnkSession;

import java.util.ArrayList;

public class TabOfferwallTemplateActivity extends AppCompatActivity {

    private TnkLayout tnkLayout = TemplateLayoutUtils.getBlueStyle_01();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_offerwall_template);

        // Sample 스피너 설정
        setLayoutStyleSpinner();

        // 광고 목록 띄우기 - Activity
        setShowTabOfferwallButton();

        // AdListView
        setShowEmbedTabOfferwallButton();
    }

    // Sample 스피너 설정
    private void setLayoutStyleSpinner() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Blue Tab Style 01 (Basic_Square/Square)");
        arrayList.add("Blue Tab Style 02 (Basic_Square/Button)");
        arrayList.add("Blue Tab Style 03 (Basic_Ellipse/Square)");
        arrayList.add("Blue Tab Style 04 (Basic_Ellipse/Button)");
        arrayList.add("Blue Tab Style 05 (Tall_Square/Square)");
        arrayList.add("Blue Tab Style 06 (Tall_Square/Button)");
        arrayList.add("Blue Tab Style 07 (Tall_Ellipse/Square)");
        arrayList.add("Blue Tab Style 08 (Tall_Ellipse/Button)");
        arrayList.add("Red Tab Style 01 (Basic_Square/Square)");
        arrayList.add("Red Tab Style 02 (Basic_Square/Button)");
        arrayList.add("Red Tab Style 03 (Basic_Ellipse/Square)");
        arrayList.add("Red Tab Style 04 (Basic_Ellipse/Button)");
        arrayList.add("Red Tab Style 05 (Tall_Square/Square)");
        arrayList.add("Red Tab Style 06 (Tall_Square/Button)");
        arrayList.add("Red Tab Style 07 (Tall_Ellipse/Square)");
        arrayList.add("Red Tab Style 08 (Tall_Ellipse/Button)");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(TabOfferwallTemplateActivity.this,
                android.R.layout.simple_spinner_dropdown_item, arrayList);

        Spinner spinner = findViewById(R.id.spinner_style);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        tnkLayout = TemplateLayoutUtils.getBlueTabStyle_01();
                        break;
                    case 1:
                        tnkLayout = TemplateLayoutUtils.getBlueTabStyle_02();
                        break;
                    case 2:
                        tnkLayout = TemplateLayoutUtils.getBlueTabStyle_03();
                        break;
                    case 3:
                        tnkLayout = TemplateLayoutUtils.getBlueTabStyle_04();
                        break;
                    case 4:
                        tnkLayout = TemplateLayoutUtils.getBlueTabStyle_05();
                        break;
                    case 5:
                        tnkLayout = TemplateLayoutUtils.getBlueTabStyle_06();
                        break;
                    case 6:
                        tnkLayout = TemplateLayoutUtils.getBlueTabStyle_07();
                        break;
                    case 7:
                        tnkLayout = TemplateLayoutUtils.getBlueTabStyle_08();
                        break;
                    case 8:
                        tnkLayout = TemplateLayoutUtils.getRedTabStyle_01();
                        break;
                    case 9:
                        tnkLayout = TemplateLayoutUtils.getRedTabStyle_02();
                        break;
                    case 10:
                        tnkLayout = TemplateLayoutUtils.getRedTabStyle_03();
                        break;
                    case 11:
                        tnkLayout = TemplateLayoutUtils.getRedTabStyle_04();
                        break;
                    case 12:
                        tnkLayout = TemplateLayoutUtils.getRedTabStyle_05();
                        break;
                    case 13:
                        tnkLayout = TemplateLayoutUtils.getRedTabStyle_06();
                        break;
                    case 14:
                        tnkLayout = TemplateLayoutUtils.getRedTabStyle_07();
                        break;
                    case 15:
                        tnkLayout = TemplateLayoutUtils.getRedTabStyle_08();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // 광고 목록 띄우기 - Activity
    private void setShowTabOfferwallButton() {
        Button button = findViewById(R.id.btn_show);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TnkSession.showAdListByType(
                        TabOfferwallTemplateActivity.this,
                        "Title",
                        tnkLayout,
                        AdListType.ALL,
                        AdListType.PPI,
                        AdListType.CPS)
                ;
            }
        });
    }

    private void setShowEmbedTabOfferwallButton() {
        Button button = findViewById(R.id.btn_show_embed);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabOfferwallTemplateActivity.this, TabOfferwallEmbedActivity.class);
                // 선택한 템플릿 레이아웃 전달
                intent.putExtra("tnk_layout", tnkLayout);

                startActivity(intent);
            }
        });
    }
}
