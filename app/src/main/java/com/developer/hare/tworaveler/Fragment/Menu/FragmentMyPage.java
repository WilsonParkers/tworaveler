package com.developer.hare.tworaveler.Fragment.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.hare.tworaveler.Activity.SignIn;
import com.developer.hare.tworaveler.Fragment.BaseFragment;
import com.developer.hare.tworaveler.Fragment.Page.FragmentMyPageHome;
import com.developer.hare.tworaveler.R;
import com.developer.hare.tworaveler.UI.FragmentManager;
import com.developer.hare.tworaveler.UI.Layout.MenuTopTitle;
import com.developer.hare.tworaveler.Data.SessionManager;
import com.developer.hare.tworaveler.UI.UIFactory;
import com.developer.hare.tworaveler.Util.FontManager;
import com.developer.hare.tworaveler.Util.Log_HR;

public class FragmentMyPage extends BaseFragment {
    private static FragmentMyPage instance = new FragmentMyPage();
    private ImageView IV_login;
    private MenuTopTitle menuTopTitle;
    private TextView textView;

    public static FragmentMyPage newInstance() {
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log_HR.log(Log_HR.LOG_INFO, FragmentFeed.class, "onCreateView()", "onCreateView");
        return inflater.inflate(R.layout.fragment_my_page, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        loginAction();
        Log_HR.log(Log_HR.LOG_INFO, FragmentFeed.class, "onResum()", "resume");
    }

    @Override
    protected void init(View view) {
        Log_HR.log(Log_HR.LOG_INFO, FragmentFeed.class, "init()", "init");
        UIFactory uiFactory = UIFactory.getInstance(getActivity());
        IV_login = uiFactory.createView(R.id.fragment_mypage$IV_nologin);
        IV_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SignIn.class));
            }
        });
        menuTopTitle = uiFactory.createView(R.id.fragment_mypage$topbar);
        menuTopTitle.getIB_left().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SignIn.class));
            }
        });
        menuTopTitle.getIB_right().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager.getInstance().setFragmentContent(FragmentMyPageHome.newInstance());
            }
        });
        textView = uiFactory.createView(R.id.fragment_mypage$TV);
        FontManager.getInstance().setFont(textView, "NotoSansCJKkr-Regular.otf");
    }

    private void loginAction() {
        if (SessionManager.getInstance().isLogin())
            FragmentManager.getInstance().setFragmentContent(FragmentMyPageHome.newInstance());
    }

}