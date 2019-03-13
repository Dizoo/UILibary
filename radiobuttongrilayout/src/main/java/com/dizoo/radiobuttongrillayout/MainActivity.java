package com.dizoo.radiobuttongrillayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GrilAdapter.AdapterCallBack {

    private RecyclerView recycle;
    private List<SelecteBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list.add(new SelecteBean("条目1"));
        list.add(new SelecteBean("条目2...."));
        list.add(new SelecteBean("条目3...."));
        list.add(new SelecteBean("条目4...."));
        list.add(new SelecteBean("条目5...."));
        list.add(new SelecteBean("条目6....",true));
        list.add(new SelecteBean("条目7...."));
        list.add(new SelecteBean("条目8...."));
        list.add(new SelecteBean("条目9...."));
        recycle = findViewById(R.id.recycle);
        recycle.setLayoutManager(new GridLayoutManager(this,
                3, GridLayoutManager.VERTICAL, false));
        GrilAdapter adapter = new GrilAdapter(this, list, this);
        recycle.setAdapter(adapter);
    }

    @Override
    public void updata() {}
}
