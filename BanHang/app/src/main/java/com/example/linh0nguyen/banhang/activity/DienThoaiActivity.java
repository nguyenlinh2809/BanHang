package com.example.linh0nguyen.banhang.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.example.linh0nguyen.banhang.model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {
    ListView lvDienThoai;
    ArrayList<SanPham> listDienThoai;
    LoadSanPhamAdapter adapterDienThoai;
    Toolbar toolbar;
    int page =1;
    String urlDienThoai = "http://linhnguyenngoc.esy.es/banhang/getSanPham.php/?page=";
    int idSanPham=0;
    View footerView;
    boolean isloading, limitdata = false;
    mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        addControls();
        addEvents();
        loadDataDienThoai(page);
        getIdSanPham();
    }

    private void getIdSanPham() {
        Intent intent = getIntent();
        idSanPham = intent.getIntExtra("idDienThoai", -1);
        //Log.d("A", idSanPham+"");
    }

    private void loadDataDienThoai(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDienThoai+page, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null && response.length() != 2){
                    lvDienThoai.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0; i< jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String ten = jsonObject.getString("ten");
                            int gia = jsonObject.getInt("gia");
                            String hinh = jsonObject.getString("hinh");
                            String mota = jsonObject.getString("mota");
                            int idloaisp = jsonObject.getInt("idloaisp");
                            listDienThoai.add(new SanPham(id, ten, gia, hinh, mota, idloaisp));
                            adapterDienThoai.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitdata = true;
                    lvDienThoai.removeFooterView(footerView);
                    Toast.makeText(DienThoaiActivity.this, "Đã hết dữ liệu", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DienThoaiActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("idloaisanpham", idSanPham+"");
                return param;
            }
        };
        requestQueue.add(stringRequest);
        Log.d("URL", urlDienThoai+page);
    }


    private void addEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        lvDienThoai.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //Log.d("AAA", firstVisibleItem+"-"+visibleItemCount+"-"+totalItemCount);
                //Toast.makeText(DienThoaiActivity.this, firstVisibleItem+"-"+visibleItemCount+"-"+totalItemCount, Toast.LENGTH_LONG).show();
                if((firstVisibleItem + visibleItemCount == totalItemCount) && (totalItemCount != 0) && (isloading == false) && (limitdata == false)){
                    //Log.d("AAA", true+"");
                    mThread mThread = new mThread();
                    mThread.start();
                    isloading = true;
                }
            }
        });
        lvDienThoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DienThoaiActivity.this, ChiTietSanPhamActivity.class);
                intent.putExtra("sanpham", listDienThoai.get(position));
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        lvDienThoai = (ListView) findViewById(R.id.lvDienThoai);
        listDienThoai = new ArrayList<>();
        adapterDienThoai = new LoadSanPhamAdapter(DienThoaiActivity.this, R.layout.dong_sanpham, listDienThoai);
        lvDienThoai.setAdapter(adapterDienThoai);
        toolbar = (Toolbar) findViewById(R.id.toolbarDienThoai);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.footerview, null);
        mHandler = new mHandler();

    }

    private class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0: lvDienThoai.addFooterView(footerView);
                        break;
                case 1: loadDataDienThoai(++page);
                        isloading = false;
                        break;
            }
            super.handleMessage(msg);
        }
    }
    private class mThread extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
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
            startActivity(new Intent(DienThoaiActivity.this, GioHangActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
