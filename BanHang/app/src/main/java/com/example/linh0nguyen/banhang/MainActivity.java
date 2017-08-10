package com.example.linh0nguyen.banhang;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.linh0nguyen.banhang.activity.DienThoaiActivity;
import com.example.linh0nguyen.banhang.activity.GioHangActivity;
import com.example.linh0nguyen.banhang.activity.LaptopActivity;
import com.example.linh0nguyen.banhang.activity.LienHeActivity;
import com.example.linh0nguyen.banhang.activity.ThongTinActivity;
import com.example.linh0nguyen.banhang.adapter.LoaiSanPhamAdapter;
import com.example.linh0nguyen.banhang.adapter.SanPhamAdapter;
import com.example.linh0nguyen.banhang.model.GioHang;
import com.example.linh0nguyen.banhang.model.LoaiSanPham;
import com.example.linh0nguyen.banhang.model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    RecyclerView recyclerView;
    SanPhamAdapter adapterSp;
    ArrayList<SanPham> listSp;
    String urlSanPhamMoi = "http://linhnguyenngoc.esy.es/banhang/getSpMoiNhat.php";


    ListView lvMenu;
    LoaiSanPhamAdapter adapterLoaiSp;
    ArrayList<LoaiSanPham> listLoaiSp;
    String urlLoaiSp = "http://linhnguyenngoc.esy.es/banhang/getLoaiSp.php";

    public static ArrayList<GioHang> listGioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
        actionBar();
        addImageToViewFiller();
        getDataLoaiSp();
        getSanPhamMoiNhat();
        /*Log.d("list", listGioHang.size()+"");
        Toast.makeText(this, listGioHang.size()+"", Toast.LENGTH_SHORT).show();*/
    }


    private void getSanPhamMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, urlSanPhamMoi, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonSanPham = response.getJSONObject(i);
                        int id = jsonSanPham.getInt("id");
                        String ten = jsonSanPham.getString("ten");
                        int gia = jsonSanPham.getInt("gia");
                        String hinh = jsonSanPham.getString("hinh");
                        String mota = jsonSanPham.getString("mota");
                        int idloaisp = jsonSanPham.getInt("idloaisp");
                        listSp.add(new SanPham(id, ten, gia, hinh, mota, idloaisp));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapterSp.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArray);
    }

    private void getDataLoaiSp() {
        listLoaiSp.add(0, new LoaiSanPham("Trang chủ", "http://linhnguyenngoc.esy.es/img/home.png"));
        adapterLoaiSp.notifyDataSetChanged();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, urlLoaiSp, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonOject;
                for(int i =0; i < response.length(); i++){
                    try {
                        jsonOject = response.getJSONObject(i);
                        listLoaiSp.add(new LoaiSanPham(jsonOject.getInt("id"), jsonOject.getString("ten"), jsonOject.getString("hinh")));
                        adapterLoaiSp.notifyDataSetChanged();
                        //Log.d("AAA", listLoaiSp.size()+"");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                listLoaiSp.add(3, new LoaiSanPham("Liên hệ", "http://linhnguyenngoc.esy.es/img/lienhe.jpg"));
                listLoaiSp.add(4, new LoaiSanPham("Thông tin", "http://linhnguyenngoc.esy.es/img/thongtin.png"));
                adapterLoaiSp.notifyDataSetChanged();
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        );
        requestQueue.add(jsonArray);
        //adapterLoaiSp.notifyDataSetChanged();
        //Log.d("SSS", listLoaiSp.size()+"");
    }

    private void addImageToViewFiller() {
        ArrayList<Integer> listHinhAnh = new ArrayList<>();
        listHinhAnh.add(R.drawable.iphone);
        listHinhAnh.add(R.drawable.samsung);
        listHinhAnh.add(R.drawable.sony);
        listHinhAnh.add(R.drawable.oppo);
        for(int i = 0 ; i< listHinhAnh.size(); i++){
            ImageView image = new ImageView(getApplicationContext());
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setImageResource(listHinhAnh.get(i));
            viewFlipper.addView(image);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flipperview_in);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flipperview_out);
        viewFlipper.setInAnimation(animation);
        viewFlipper.setOutAnimation(animation_out);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        Log.d("BBB", getSupportActionBar()+"");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void addEvents() {
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: startActivity(new Intent(MainActivity.this, MainActivity.class));
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                    case 1: Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("idLaptop", 1);
                            startActivity(intent);
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                    case 2: Intent intent1 = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent1.putExtra("idDienThoai", 2);
                            startActivity(intent1);
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                    case 3: Intent intent2 = new Intent(MainActivity.this, LienHeActivity.class);
                            startActivity(intent2);
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                    case 4: Intent intent3 = new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intent3);
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                }
            }
        });
    }

    private void addControls() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewFlipper = (ViewFlipper) findViewById(R.id.vfQuangCao);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        navigationView = (NavigationView) findViewById(R.id.navMenu);
        lvMenu = (ListView) findViewById(R.id.lvMenu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawbleLayout);
        listLoaiSp = new ArrayList<LoaiSanPham>();
        adapterLoaiSp = new LoaiSanPhamAdapter(MainActivity.this, R.layout.dong_loai_san_pham, listLoaiSp);
        lvMenu.setAdapter(adapterLoaiSp);

        listSp = new ArrayList<>();
        adapterSp = new SanPhamAdapter(MainActivity.this, R.layout.dong_sanphammoinhat, listSp);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterSp);

        if(listGioHang == null){
            listGioHang = new ArrayList<GioHang>();
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
            startActivity(new Intent(MainActivity.this, GioHangActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
