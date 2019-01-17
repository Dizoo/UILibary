package com.dizoo.uilibary.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dizoo.uilibary.R;

import java.util.List;

public class HorizontalRvAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public HorizontalRvAdapter(int horizontal_rv_item, List<String> horizontalRvList) {
        super(horizontal_rv_item, horizontalRvList);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.horizontal_tv,item);
    }
}
