package com.dizoo.radiobuttongrillayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GrilAdapter extends RecyclerView.Adapter<GrilAdapter.GrilHHolder> {

    //实现单选  方法二，变量保存当前选中的position
    private int mSelectedPos = -1;
    private Context context;
    private AdapterCallBack callBack;
    private List<SelecteBean> list;

    public GrilAdapter(Context context, List<SelecteBean> list,AdapterCallBack callback) {
        this.context = context;
        this.list = list;
        this.callBack = callback;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()){
                mSelectedPos = i;
            }
        }
    }

    @NonNull
    @Override
    public GrilHHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GrilHHolder(LayoutInflater.from(context).inflate(R.layout.item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GrilHHolder grilHHolder, final int i) {
        String content = list.get(i).getName();
        grilHHolder.tv.setText(content);
        if (list.get(i).isSelected()){
            //选中效果
            grilHHolder.tv.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
        }else {
            grilHHolder.tv.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));
        }
        grilHHolder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedPos != i){
                    list.get(mSelectedPos).setSelected(false);
                    notifyItemChanged(mSelectedPos);
                    mSelectedPos = i;
                    callBack.updata();
                    list.get(mSelectedPos).setSelected(true);
                    notifyItemChanged(mSelectedPos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class GrilHHolder extends RecyclerView.ViewHolder{

        private TextView tv;

        public GrilHHolder(@NonNull View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    public interface AdapterCallBack{
        void updata();
    }
}
