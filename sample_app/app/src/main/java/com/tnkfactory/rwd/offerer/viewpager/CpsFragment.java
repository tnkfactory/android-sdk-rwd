package com.tnkfactory.rwd.offerer.viewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tnkfactory.ad.AdListType;
import com.tnkfactory.ad.AdListView;
import com.tnkfactory.ad.TemplateLayoutUtils;
import com.tnkfactory.ad.TnkLayout;
import com.tnkfactory.ad.TnkSession;
import com.tnkfactory.rwd.offerer.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CpsFragment extends Fragment {

    private View rootView;

    public static CpsFragment newInstance() {

        Bundle args = new Bundle();

        CpsFragment fragment = new CpsFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cps, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 커스텀 레이아웃 설정
        TnkLayout customLayout = TemplateLayoutUtils.getBlueStyle_01();
        customLayout.adwall.layout = R.layout.offerwall_layout_blue;

        // AdListView 생성
        int adListViewId = 101; // 하나의 액티비티에서 오퍼월을 2개 이상 사용할 경우 각 오퍼월마다 중복되지 않도록 뷰 ID를 지정해주어야 합니다. (필수)
        AdListView offerwallView = TnkSession.createAdListView(getActivity(), customLayout, false, null, adListViewId);
        offerwallView.setAdListType(AdListType.CPS);
        offerwallView.setTitle("your title here.");

        ((ViewGroup) rootView).addView(offerwallView);
    }
}
