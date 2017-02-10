package com.zxw.giftbook.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zxw.giftbook.Activity.entitiy.GifttypeEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.R;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.ComParamsAddTool;

import org.json.JSONArray;

import java.util.Map;
import java.util.TreeMap;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.base.MyPullToRefreshBaseFragment;
import pri.zxw.library.listener.TitleOnClickListener;
import pri.zxw.library.tool.MessageHandlerTool;
import pri.zxw.library.tool.ServicesTool;
import pri.zxw.library.tool.ToastShowTool;
import pri.zxw.library.tool.dialogTools.DropDownBoxTool;
import pri.zxw.library.view.TitleBar;

/**
 * 礼金记录添加
 * Created by Administrator on 2016/11/8.
 */

public class GiftMoneyAddAct extends MyBaseActivity {
    boolean isSubmit=false;
    ServicesTool mServicesTool;
    TitleBar titleBar;
    public static final String GET_ADD_URL="apiGiftMoneyCtrl.do?doAdd";
    TreeMap<String,String> groupmembers=new TreeMap<>();
    TreeMap<String,String> sidekickerGroups=new TreeMap<>();
    TextView
    /**组类型*/
    typeTv;
    EditText nameEdit;
    EditText moneyEdit;
    TextView searchMemberTv;
    TextView addGiftTypeTv;
    String typeId,typeName;
    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what== MyPullToRefreshBaseFragment.GET_ADD_CODE)
            {
                MessageHandlerTool messageHandlerTool=new MessageHandlerTool();
                int ret=messageHandlerTool.handler(msg,GiftMoneyAddAct.this);
                if(ret==1)
                {
                    ToastShowTool.myToastShort(GiftMoneyAddAct.this,"添加成功！");
                    setResult(1);
                    finish();
                }else
                {
                    ToastShowTool.myToastShort(GiftMoneyAddAct.this,"添加失败！");
                }
                isSubmit=false;
            }else if(msg.what==MyPullToRefreshBaseFragment.GET_DATA_CODE) {
                MessageHandlerTool messageHandlerTool = new MessageHandlerTool();
                String data = messageHandlerTool.handlerData(msg, GiftMoneyAddAct.this);
                if (data.length() > 0) {
                    try {
                        org.json.JSONObject jsonObj = new org.json.JSONObject(data);
                        JSONArray sidekickerGroupJsons=jsonObj.getJSONArray("sidekickerGroups");
                        for (int i=0;sidekickerGroupJsons!=null&&i<sidekickerGroupJsons.length();i++)
                        {
                            org.json.JSONObject obj=(org.json.JSONObject)sidekickerGroupJsons.get(i);
                            sidekickerGroups.put(obj.getString("id"),obj.getString("groupname"));
                        }
                        JSONArray groupmemberJson=jsonObj.getJSONArray("groupmembers");
                        for (int i=0;groupmemberJson!=null&&i<groupmemberJson.length();i++)
                        {
                            org.json.JSONObject obj=(org.json.JSONObject)sidekickerGroupJsons.get(i);
                            sidekickerGroups.put(obj.getString("id"),obj.getString("groupmember"));
                        }

                    }catch (Exception e)
                    {
                    }
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_gift_money_add);
        initView();
        initTool();
        initListener();
        getDropDownData();
    }

    public void initView()
    {
        titleBar=(TitleBar) findViewById(R.id.act_gift_money_add_title_bar);
        nameEdit=(EditText) findViewById(R.id.act_gift_money_add_name_edit);
        searchMemberTv=(TextView) findViewById(R.id.act_gift_money_add_member_add_tv);
        moneyEdit=(EditText) findViewById(R.id.act_gift_money_add_money_edit);
        addGiftTypeTv=(TextView) findViewById(R.id.act_gift_money_add_type_add_tv);
        typeTv=(TextView) findViewById(R.id.act_gift_money_add_type_tv);
        if(typeId!=null)
            typeTv.setText(typeName);
    }
    void initTool()
    {
        mServicesTool=new ServicesTool(NetworkConfig.api_url,this,mHandler);
    }
    public void initListener()
    {
        titleBar.setLeftClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        titleBar.setRightClickListener(new TitleOnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        typeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showType();
            }
        });
    }
    public void submit()
    {
        if(nameEdit.getText().toString().trim().length()==0)
        {
            ToastShowTool.myToastShort(this,"请输入成员姓名！");
            return ;
        }
        if(moneyEdit.getText().toString().trim().length()==0)
        {
            ToastShowTool.myToastShort(this,"请输入礼金数量！");
            return ;
        }
        if(typeId==null)
        {
            ToastShowTool.myToastShort(this,"请选择礼金类型！");
            return ;
        }
        Map<String,String > params= ComParamsAddTool.getParam();
        params.put("gourpmemberid", FtpApplication.user.getId());
        params.put("groupmember", FtpApplication.user.getUsername());
        params.put("expendituretype", typeId);
        params.put("expendituretypename", typeName);
        params.put("totalmoney", "0");
        params.put("createBy", FtpApplication.user.getId());
        params.put("createName", FtpApplication.user.getUsername());
        if(moneyEdit.getText().toString().trim().length()>0)
            params.put("money", moneyEdit.getText().toString());
        if(isSubmit)
            return;
        isSubmit=true;
        params.put("isexpenditure","1");
        mServicesTool.doPostAndalysisData(GET_ADD_URL,params,MyPullToRefreshBaseFragment.GET_ADD_CODE);
    }

    public void getDropDownData()
    {
        Map<String,String > params= ComParamsAddTool.getParam();
        params.put("userid", FtpApplication.user.getId());
        mServicesTool.doPostAndalysisData("apiAllTypeCtrl.do?getAll",params,MyPullToRefreshBaseFragment.GET_DATA_CODE);
    }
    public void showType()
    {
        DropDownBoxTool tool = new DropDownBoxTool();
        tool.showDialog("选择礼金类型", sidekickerGroups, 1,
                this, typeTv, new DropDownBoxTool.Callback() {
                    @Override
                    public void complate(String key, String value) {
                        typeId=key;
                        typeName=value;
                    }
                });
    }
    public void showGroup()
    {
//        DropDownBoxTool tool = new DropDownBoxTool();
//        tool.showDialog("选选关联人", groupmembers, 1,
//                this, affiliated_person_tv,null);
    }

}
