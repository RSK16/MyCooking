package com.example.mycooking.activity.shangchuangcaipu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.mycooking.R;

public class KouweiActivity extends Activity {
    private static final String TAG = "KouweiActivity";
    private GridView gv_content;
    private ImageButton ib_finish;
    private Button bt_choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kouwei);

        ib_finish = (ImageButton) findViewById(R.id.ib_kouwei_finish);

        gv_content = (GridView) findViewById(R.id.gv_kouwei_content);
        gv_content.setAdapter(new MyAdapter());
        gv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: +++++++++");
                String data = choices[position];
                Intent intent = new Intent(getApplicationContext(), LoadOneActivity.class);
                intent.putExtra("kouwei",data);
                startActivity(intent);
                finish();
            }
        });
    }

    public void over(View view) {
        finish();
    }
    String[] choices={"家常味","香辣味","咸鲜味","酸辣味","麻辣味","酱香味","奶香味",
            "蒜香味","鱼香味","果味","五香味","葱香味","咖喱味","茄汁味","糊辣味",
            "红油味","黑椒味","豆瓣味","麻酱味","椒麻味","芥末味","怪味","其他"};
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return choices.length;
        }

        @Override
        public Object getItem(int position) {
            return choices[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = View.inflate(getApplicationContext(), R.layout.item_gongyi_gridview, null);
            bt_choice = (Button) inflate.findViewById(R.id.bt_choice);
            bt_choice.setText(choices[position]);
            return inflate;
        }
    }
}
