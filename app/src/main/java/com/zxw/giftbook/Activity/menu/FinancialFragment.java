package com.zxw.giftbook.Activity.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.zxw.giftbook.R;

import pri.zxw.library.base.BaseFragment;

/**
 * Created by Administrator on 2017/2/14.
 */

public class FinancialFragment extends BaseFragment {
View view;
WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.f_financial, container, false);
        initView();
        initTool();
        initListener();
        return view;
    }
   void initView(){
       webView=(WebView)view.findViewById(R.id.f_financial_wv);
       webView.loadUrl("https://www.libugj.cn/apiFinancialSupermarketController.do?list");
    }
    void  initTool(){

    }
    void initListener(){

    }

    @Override
    public String getFragmentName() {
        return null;
    }

    @Override
    public boolean getIsSpecial() {
        return false;
    }
}
