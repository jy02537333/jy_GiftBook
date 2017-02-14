package com.zxw.giftbook;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.entity.User;
import pri.zxw.library.tool.AppConstantError;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ServicesTool;

import com.zxw.giftbook.Activity.menu.MyFragment;
import com.zxw.giftbook.Activity.menu.HomeFragment;
import com.zxw.giftbook.Activity.menu.ItemsFragment;
import com.zxw.giftbook.Activity.menu.SidekickerGroupFragment;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.utils.ExitTool;
import com.zxw.giftbook.utils.LoginUserInfoHandlerTool;

import java.util.HashMap;
import java.util.Map;

/**
 *  主页面
 * Created by lenovo on 2016-07-11.
 */
public class MainAct extends MyBaseActivity {
    private Activity mActivity;
    private Fragment mContent;
    private HomeFragment homeFragment;
    private SidekickerGroupFragment povertyObjectFragment;
    private ItemsFragment itemsFragment;
    private MyFragment myFragment;
    private ServicesTool mServicesTool;
    private LoginUserInfoHandlerTool loginUserInfoHandlerTool;
    public static final int USER_VALIDATE_CODE = 7345;
    public static final int USER_COLLECT_CODE = 1345;
    public static final int USER_PRAISE_CODE = 2345;
    public static final int TAB_MESSAGE_CODE=9362;
    public static final int USER_NOREAD_MSG = 2000;
    /**
     * 推送后切换消息
     */
    public static final String MESSAGE_TAB_KEY = "MESSAGE_TAB_KEY";
    /**
     * 推送后切换action
     */
    public static final String MESSAGE_TAB_ACTION = "MESSAGE_TAB_ACTION";
    public static final int MESSAGE_TAB_CODE = 8524;
    private FragmentManager mFragmentManager;
    private boolean radioSwitch = true;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case USER_VALIDATE_CODE:
                        if (msg.arg2 == AppConstantError.WEB_TIMEOUT)
                            loginUserInfoHandlerTool
                                    .loginVerifity(USER_VALIDATE_CODE,mHandler,MainAct.this,mServicesTool);
                        else
                        {
                         User user=loginUserInfoHandlerTool.loginedHandler(msg,FtpApplication.user.getLoginpassword());
                        }

                        break;
                    case TAB_MESSAGE_CODE:
                        getNoReadMsg();
                        break;
                    case USER_NOREAD_MSG:
                    {

                    }
                    break;
                    default:
                        loginUserInfoHandlerTool.messageHandler(msg);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                ProgressDialogTool.getInstance(mActivity).dismissDialog();
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        mActivity = this;
        mFragmentManager = getFragmentManager();
        initView();
        initTool();
        initData();


    }

    private void initTool() {
        mServicesTool = new ServicesTool(NetworkConfig.api_url, this, mHandler);
        loginUserInfoHandlerTool = new LoginUserInfoHandlerTool(mActivity,
                mServicesTool);
    }

    private void initData() {
        radioCanelCheck(R.id.a_main_one_lay, R.mipmap.tab_gift_give_p);
    }

    /**
     * 登陆验证
     */
    private void loginVerifity() {
      //  loginUserInfoHandlerTool.loginVerifity(USER_VALIDATE_CODE,mHandler,MainAct.this,mServicesTool);
    }

    /**
     * 初始化组件
     */
    private void initView() {
        homeFragment = new HomeFragment();
        mContent = homeFragment;
        getFragmentManager().beginTransaction()
                .replace(R.id.main_content, homeFragment).commit();

        getTabItem((LinearLayout) findViewById(R.id.a_main_one_lay),"送礼");
        getTabItem((LinearLayout)findViewById(R.id.a_main_two_lay),"收礼");
        getTabItem((LinearLayout)findViewById(R.id.a_main_center_lay),"金融超市");
       // getTabItemImage((LinearLayout) findViewById(R.id.a_main_center_lay));
        getTabItem((LinearLayout)findViewById(R.id.a_main_shree_lay),"亲友");
        getTabItem((LinearLayout)findViewById(R.id.a_main_four_lay),"我");
        //首页
        findViewById(R.id.a_main_one_lay).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int id = v.getId();
                radioCanelCheck(id, R.mipmap.tab_gift_give_p);
                if (homeFragment == null)
                {
                    homeFragment = new HomeFragment();
                }
                switchContent(mContent, homeFragment);
            }
        });
        //第二个菜单
        findViewById(R.id.a_main_two_lay).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int id = v.getId();
                radioCanelCheck(id, R.mipmap.tab_receive_p);
                if (povertyObjectFragment == null)
                {
                    povertyObjectFragment = new SidekickerGroupFragment();
                }
                switchContent(mContent, povertyObjectFragment);
            }
        });
        //中间按钮
        findViewById(R.id.a_main_center_lay).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int id = v.getId();
                radioCanelCheck(id, R.mipmap.tab_financial_p);
                if (povertyObjectFragment == null)
                {
                    povertyObjectFragment = new SidekickerGroupFragment();
                }
                switchContent(mContent, povertyObjectFragment);
            }
        });
        //亲友团
        findViewById(R.id.a_main_shree_lay).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int id = v.getId();
                radioCanelCheck(id, R.mipmap.tab_sidekicker_group_p);
                if (itemsFragment == null)
                {
                    itemsFragment = new ItemsFragment();
                }
                switchContent(mContent, itemsFragment);
            }
        });
        //我的
        findViewById(R.id.a_main_four_lay).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int id = v.getId();
                radioCanelCheck(id, R.mipmap.tab_my_p);
                if (myFragment == null)
                {
                    myFragment = new MyFragment();
                }
                switchContent(mContent, myFragment);
            }
        });
    }

    private void radioCanelCheck(int layoutId,int imageId)
    {
        setNormalStatus(R.id.a_main_one_lay, R.mipmap.tab_gift_give);
        setNormalStatus(R.id.a_main_two_lay, R.mipmap.tab_receive);
        setNormalStatus(R.id.a_main_center_lay, R.mipmap.tab_financial);
//        setNormalStatus(R.id.a_main_one_lay, R.mipmap.burst_l);
        setNormalStatus(R.id.a_main_shree_lay, R.mipmap.tab_sidekicker_group);
        setNormalStatus(R.id.a_main_four_lay, R.mipmap.tab_my);
        setSelectStatus(layoutId, imageId);

//		try {
//			LinearLayout rdo = (LinearLayout) findViewById(layoutId);
//			ImageView image = (ImageView)rdo.findViewById(R.id.imageview);
//			rdo.requestFocus();
//			if(layoutId==R.id.rbInfo)
//			{//切换到消息选项卡后
//				AppApplication.isTabInfoFragment=false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    }

    private void getTabItem(LinearLayout view,String title)
    {
        LayoutInflater layout = LayoutInflater.from(this);
        View item = layout.inflate(R.layout.act_tab_item, null);
        TextView text = (TextView)item.findViewById(R.id.textview);
        text.setText(title);
        view.addView(item);
    }

    private void getTabItemImage(LinearLayout view)
    {
        LayoutInflater layout = LayoutInflater.from(this);
        View item = layout.inflate(R.layout.act_tab_item, null);
        TextView text = (TextView)item.findViewById(R.id.textview);
        text.setVisibility(View.GONE);
        ImageView image = (ImageView)item.findViewById(R.id.imageview);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        image.setLayoutParams(params);
        view.addView(item);
    }

    private void setNormalStatus(int resID,int imageID)
    {
        LinearLayout rdo = (LinearLayout) findViewById(resID);
        ImageView image = (ImageView)rdo.findViewById(R.id.imageview);
        image.setBackgroundResource(imageID);
        TextView text = (TextView)rdo.findViewById(R.id.textview);
        text.setTextColor( //Color.rgb(0x9e, 0x9e, 0x9e));
        getResources().getColorStateList(R.color.com_tab_font_gray));
    }

    private void setSelectStatus(int resID,int imageID)
    {
        if(imageID == 0)
        {
            return;
        }
        LinearLayout rdo = (LinearLayout) findViewById(resID);
        ImageView image = (ImageView)rdo.findViewById(R.id.imageview);
        image.setBackgroundResource(imageID);
        TextView text = (TextView)rdo.findViewById(R.id.textview);
       text.setTextColor(//Color.rgb(0x42, 0xA5, 0xEF));
          getResources().getColorStateList(R.color.com_color1));
    }

    private void setHintNum(int resID,boolean show,int num)
    {
        LinearLayout rdo = (LinearLayout) findViewById(resID);
        TextView hint = (TextView)rdo.findViewById(R.id.texthint);
        if(show)
        {
            hint.setVisibility(View.VISIBLE);
            hint.setText(num+"");
        }
        else
        {
            hint.setVisibility(View.GONE);
        }
    }

    private boolean isHintShow(int resID)
    {
        LinearLayout rdo = (LinearLayout) findViewById(resID);
        TextView hint = (TextView)rdo.findViewById(R.id.texthint);
        return hint.getVisibility()==View.VISIBLE;
    }

    private void getNoReadMsg()
    {
        if(FtpApplication.getInstance().getUser() != null)
        {
            Map<String, String> param = new HashMap<String, String>();
            if(FtpApplication.getInstance().getUser()!=null)
                param.put("userId", FtpApplication.getInstance().getUser().getId());
            mServicesTool.doGetAndalysisData("/CommentNews/GetUnreadCount", param,
                    USER_NOREAD_MSG, 800);
        }
    }

    public void switchContent(Fragment from, Fragment to) {
        try {
            if (mContent != to) {
                mContent = to;
                FragmentTransaction transaction = mFragmentManager
                        .beginTransaction();
//                        .setCustomAnimations(
//                                android.R.anim.slide_in_left,
//                                R.anim.slide_left_out);
                System.gc();
                if (!to.isAdded()) { // 先判断是否被add过
                    transaction.hide(from).add(R.id.main_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Exit() {
        ExitTool.Exit(this);
    }

    @Override
    public void onBackPressed() {
        Exit();
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);


    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Exit();
            return false;
        }
        return false;
    }



    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
    }
}
