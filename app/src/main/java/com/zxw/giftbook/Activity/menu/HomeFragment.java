package com.zxw.giftbook.Activity.menu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zxw.giftbook.Activity.GiftMoneyAddAct;
import com.zxw.giftbook.Activity.SettingAct;
import com.zxw.giftbook.Activity.entitiy.MembergiftmoneyEntity;
import com.zxw.giftbook.R;
import com.zxw.giftbook.adapter.HomeJournalAccountAdapter;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.ComParamsAddTool;
import com.zxw.giftbook.utils.MenuSettingViewInit;

import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import pri.zxw.library.base.MyPullToRefreshBaseFragment;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.NetReturnMapHandler;
import pri.zxw.library.tool.ServicesTool;
import pri.zxw.library.tool.WebGetDataTool;
import pri.zxw.library.view.TitleBar;

/**
 * @作者 送礼记录
 * @类 首页
 * Created by lenovo on 2016-07-15.
 */
public class HomeFragment  extends MyPullToRefreshBaseFragment {
    TitleBar titleBar;
    View view;
    ServicesTool mServicesTool;
    HomeJournalAccountAdapter adapter;
    PullToRefreshListView listView;
    public static final String ADD_URL="apiMembergiftmoneyCtrl.do?doAdd";
    public static final String GET_DATA_URL="apiMembergiftmoneyCtrl.do?datagrid";
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_DATA_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                Type type=new TypeToken<List<MembergiftmoneyEntity>>(){}.getType();
                MessageHandlerTool.MessageInfo msgInfo = messageHandlerTool.handler(msg,HomeFragment.this,adapter,listView,type);
            }
            else if(msg.what==LOAD_CODE)
            {
                listView.setRefreshing(true);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.f_home_journal_account, container, false);
        initView();
        initTool();
        initListener();
       listLoad(mHandler);
        return view;
    }

    @Override
    public String getFragmentName() {
        return null;
    }

    @Override
    public boolean getIsSpecial() {
        return false;
    }

    public void initView()
    {
        titleBar=(TitleBar) view.findViewById(R.id.f_home_journal_account_title_bar);
        listView=(PullToRefreshListView)view.findViewById(R.id.f_home_journal_account_lv);
    }
    void initTool()
    {
        mServicesTool=new ServicesTool(NetworkConfig.api_url,getActivity(),mHandler);
        adapter=new HomeJournalAccountAdapter(getActivity());
        listView.setAdapter(adapter);
        this.initListener(listView,adapter);
    }
    public void initListener()
    {
        titleBar.setRightClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), GiftMoneyAddAct.class);
                startActivityForResult(intent,GET_ADD_CODE);
            }
        });
    }


    @Override
    public void getWebData() {
        Map<String,String> params= ComParamsAddTool.getPageParam(this);
        mServicesTool.doPostAndalysisData(GET_DATA_URL,params,GET_DATA_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==GET_ADD_CODE&&resultCode==1)
        {
            listView.setRefreshing(true);
        }
    }
}
