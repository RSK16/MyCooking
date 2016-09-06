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

public class GongyiActivity extends Activity {
    private static final String TAG = "GongyiActivity";
    private GridView gv_content;
    private ImageButton ib_finish;
    private Button bt_choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gongyi);

        ib_finish = (ImageButton) findViewById(R.id.ib_gongyi_finish);

        gv_content = (GridView) findViewById(R.id.gv_gongyi_content);
        gv_content.setAdapter(new MyAdapter());
        gv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: +++++++++");
                String data = choices[position];
                Intent intent = new Intent(getApplicationContext(), LoadOneActivity.class);
                intent.putExtra("gongyi",data);
                startActivity(intent);
                finish();
            }
        });
    }

    public void over(View view) {
        finish();

    }
    String[] choices={"炒","蒸","煮","炖","拌","烧","煎",
            "炸","烘焙","微波","煲","烤","糖蘸","干煸","榨汁","拔丝","酱",
            "腌","熏","干锅","腊","灼","焗","爆","煨","泡","其他"};
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
            Log.i(TAG, "getView: "+bt_choice);
            bt_choice.setText(choices[position]);
            return inflate;
        }
    }
}
