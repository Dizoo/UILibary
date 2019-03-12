package com.dizoo.bottontablaout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabClickListener{

    private TabLayout layoutTab;
    private TabLayoutView tab;
    private TabFragment fragment;
    private String[] titles = {"首页", "我的"};
    private int[] imgs = {R.drawable.main_loan_tab_selector, R.drawable.main_mine_tab_selector};
    private List<Fragment> fragmentList = new ArrayList<> ();
    private HotFragment hotFragment;
    private MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutTab = findViewById(R.id.llayout_tab);
        tab = findViewById(R.id.tab);
        initTab();
        initFragment();
//        setUpBottomTab();
    }

    private void initTab() {
        tab.setDataSource ( titles, imgs, 0 );
        tab.setImageStyle ( 20, 20 );
        tab.setTextStyle ( 10, R.color.colorPrimary, R.color.colorAccent );
        tab.initDatas ();
        tab.setSelectStyle ( 0 );
        tab.setNeedCheck(false);
        selectedFragment ( 0 );
        tab.setOnItemOnclickListener ( new TabLayoutView.OnItemOnclickListener () {
            @Override
            public void onItemClick(int index) {
                selectedFragment(index);
            }

            @Override
            public void onNeedOhterMether(int index) {
                Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
            }
        } );
    }

    private void setUpBottomTab() {
        ArrayList<TabLayout.Tab> tabs = new ArrayList<>();
        tabs.add(new TabLayout.Tab(R.drawable.main_loan_tab_selector, R.string.hot, HotFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.main_mine_tab_selector, R.string.mine,MineFragment.class));
        layoutTab.setUpData(tabs, this);
        layoutTab.setCurrentTab(0);
    }

    @Override
    public void onTabClick(TabLayout.Tab tab) {
        try {
            TabFragment tmpFragment = (TabFragment) getSupportFragmentManager().findFragmentByTag(
                    tab.targetFragmentClz.getSimpleName());
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (tmpFragment == null) {
                try {
                    tmpFragment = tab.targetFragmentClz.newInstance();
                    transaction.add(R.id.fragment_container, tmpFragment.getFragment(),
                            tab.targetFragmentClz.getSimpleName());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (fragment != null) {
                    transaction.hide(fragment.getFragment());
                }
                transaction.commit();
            } else {
                transaction.show(tmpFragment.getFragment());
                if (fragment != null) {
                    transaction.hide(fragment.getFragment());
                }
                transaction.commit();
            }
            fragment = tmpFragment;
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void selectedFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager ().beginTransaction ();
//        hideFragment ( transaction );
        switch (index) {
            case 0:
                if (hotFragment == null) {
                    hotFragment = new HotFragment ();
                }
                addFragment(hotFragment);
                showFragment(hotFragment);
                break;
            case 1:
                if (mineFragment == null) {
                    mineFragment = new MineFragment ();
                }
                addFragment(mineFragment);
                showFragment(mineFragment);
                break;
            default:
                break;
        }
        transaction.commit ();
    }

    private void hideFragment(FragmentTransaction transaction) {
        for (Fragment fragment : fragmentList) {
            transaction.hide(fragment);
        }
    }

    private void addFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
            fragmentList.add(fragment);
        }
    }

    private void addToList(Fragment fragment) {
        if (fragment != null) {
            fragmentList.add(fragment);
        }
    }

    private void initFragment() {
        hotFragment = new HotFragment ();
        addFragment(hotFragment);
        showFragment(hotFragment);
    }

    private void showFragment(Fragment fragment) {
        for (Fragment frag : fragmentList) {
            if (frag != fragment) {
                getSupportFragmentManager().beginTransaction().hide(frag).commit();
            }
        }
        getSupportFragmentManager().beginTransaction().show(fragment).commit();

    }
}
