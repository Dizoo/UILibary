package com.dizoo.bottontablaout;


public class MineFragment extends BaseFragment{

    @Override
    protected int onCreateViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }
}
