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

public class Timeactivity extends Activity {
    private static final String TAG = "Timeactivity";
    private GridView gv_content;
    private ImageButton ib_finish;
    private Button bt_choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeactivity);

        ib_finish = (ImageButton) findViewById(R.id.ib_time_finish);

        gv_content = (GridView) findViewById(R.id.gv_time_content);
        gv_content.setAdapter(new MyAdapter());
        gv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: +++++++++");
                String data = choices[position];
                Intent intent = new Intent(getApplicationContext(), LoadOneActivity.class);
                intent.putExtra("time",data);
                startActivity(intent);
                finish();
            }
        });
    }

    public void over(View view) {
        finish();

    }
    String[] choices={"<5分钟","<10分钟","<15分钟","<30分钟","<90分钟","<2小时",
            "2小时以上"};
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
