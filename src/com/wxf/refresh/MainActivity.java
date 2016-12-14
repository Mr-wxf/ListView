package com.wxf.refresh;

import java.util.ArrayList;

import com.wxf.refresh.RefreshListView.OnRefreshListener;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

	private RefreshListView lv_my_listview;
	private ArrayList<String> arrayList;
	private MyAdapter myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initData();
		lv_my_listview = (RefreshListView) findViewById(R.id.lv_my_listview);
		myAdapter = new MyAdapter();
		lv_my_listview.setAdapter(myAdapter);
		
		lv_my_listview.setRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
               new Thread(){
            	   public void run() {
            		   try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
            		    arrayList.add(0,"我是刷新出来的数据");
            		    runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								myAdapter.notifyDataSetChanged();
								lv_my_listview.setOnRefresh();
							}
						});
            		    
            	   };
               }.start();				
			}

			@Override
			public void LoadMore() {
                 new Thread(){
                	 public void run() {
                		 try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
                		 
                		 arrayList.add("我是加载更多出来的数据");
                		 runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
                                 myAdapter.notifyDataSetChanged();	
                                 lv_my_listview.setOnRefresh();
							}
						});
                	 };
                	 
                 }.start();				
			}
		});
	}

	private void initData() {
		arrayList = new ArrayList<String>();

		for (int i = 0; i < 30; i++) {
			arrayList.add("我是第" + i + "条数据");
		}
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return arrayList.size();
		}

		@Override
		public Object getItem(int position) {
			return arrayList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(getApplicationContext());
			textView.setText(arrayList.get(position));
			textView.setTextColor(Color.BLACK);
			textView.setTextSize(20);
			return textView;
		}

	}

}
