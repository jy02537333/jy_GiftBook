package com.zxw.giftbook.Activity.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxw.giftbook.R;

/**
 * 扶贫项目
 * Created by lenovo on 2016-07-15.
 */
public class ItemsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_sidekicker_group, container, false);
    }
}
