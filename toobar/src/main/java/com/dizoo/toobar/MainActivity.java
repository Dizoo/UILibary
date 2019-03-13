package com.dizoo.toobar;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private Toolbar toobar;
    private TextView toobarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toobar = findViewById(R.id.toobar);
        toobarText = findViewById(R.id.toobar_text);
        toobar.setBackgroundColor(ContextCompat.getColor(this,R.color.color_toobar));
        toobar.getMenu().clear();
        toobar.inflateMenu(R.menu.main);
        toobar.setOnMenuItemClickListener(this);
        toobarText.setText("Toobar");
        setRedPointMessage(toobar);
    }

    private void setRedPointMessage(Toolbar toolbar) {
        Menu menu = toolbar.getMenu();
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = menu.getItem(i);
            if (item.getItemId() == R.id.menu_message) {
                RedPointDrawable redPointDrawable = RedPointDrawable.wrap(this, item.getIcon());
                redPointDrawable.setGravity(Gravity.BOTTOM);
                redPointDrawable.setShowRedPoint(true);
                item.setIcon(redPointDrawable);
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_message){
            Toast.makeText(this, "message", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
