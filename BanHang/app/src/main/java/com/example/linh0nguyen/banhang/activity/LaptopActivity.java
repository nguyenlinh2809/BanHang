package com.example.linh0nguyen.banhang.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.linh0nguyen.banhang.MainActivity;
import com.example.linh0nguyen.banhang.R;
import com.example.linh0nguyen.banhang.adapter.LoadSanPhamAdapter;
import com.example.linh0nguyen.banhang.adapter.SanPhamAdapter;
import com.example.linh0nguyen.banhang.model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView lvLaptop;
    LoadSanPhamAdapter adapterLaptop;
    ArrayList<SanPham> listLaptop;
    int page =1;
    String urlLaptop = "http://linhnguyenngoc.esy.es/banhang/getSanPham.php/?page=";
    int idsanpham=0;
    View footerView;
    MyHandler myHandler;
    boolean isloading = false;
    boolean limitdata = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        addControls();
        addEvents();
        actionBar();
        getIdSanPham();
        getDataLaptop(page);
    }

    private void getIdSanPham() {
        Intent intent = getIntent();
        idsanpham = intent.getIntExtra("idLaptop", -1);
    }

    private void getDataLaptop(int pagelap) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlLaptop+pagelap, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null && response.length() != 2){
                    lvLaptop.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0; i<response.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String ten = jsonObject.getString("ten");
                            int gia = jsonObject.getInt("gia");
                            String hinh = jsonObject.getString("hinh");
                            String mota = jsonObject.getString("mota");
                            int idloaisp = jsonObject.getInt("idloaisp");
                            listLaptop.add(new SanPham(id, ten, gia, hinh, mota, idloaisp));
                            adapterLaptop.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitdata = true;
                    lvLaptop.removeFooterView(footerView);
                    Toast.makeText(LaptopActivity.this, "Đã hết dữ liệu", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LaptopActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("idloaisanpham", idsanpham+"");
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarLaptop);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addEvents() {
        lvLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if((firstVisibleItem + visibleItemCount == totalItemCount) && (totalItemCount != 0) && (isloading == false) && (limitdata == false)){
                    MyThread myThread = new MyThread();
                    myThread.start();
                    isloading = true;
                }
            }
        });
        lvLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LaptopActivity.this, ChiTietSanPhamActivity.class);
                intent.putExtra("sanpham", listLaptop.get(position));
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbarLaptop);
        lvLaptop = (ListView) findViewById(R.id.lvLaptop);
        listLaptop = new ArrayList<>();
        adapterLaptop = new LoadSanPhamAdapter(LaptopActivity.this, R.layout.dong_sanpham, listLaptop);
        lvLaptop.setAdapter(adapterLaptop);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.footerview, null);
        myHandler = new MyHandler();
    }
    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0: lvLaptop.addFooterView(footerView);
                        break;
                case 1: getDataLaptop(++page);
                        isloading = false;
                        break;
            }
        }
    }
    private class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1);
            myHandler.sendMessage(message);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_giohang, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menuGioHang){
            startActivity(new Intent(LaptopActivity.this, GioHangActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
