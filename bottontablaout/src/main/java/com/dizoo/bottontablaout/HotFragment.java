package com.dizoo.bottontablaout;


public class HotFragment extends BaseFragment{

    @Override
    protected int onCreateViewId() {
        return R.layout.fragment_hot;
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }
}
