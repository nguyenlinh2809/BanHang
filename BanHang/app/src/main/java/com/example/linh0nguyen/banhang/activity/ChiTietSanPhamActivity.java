package com.example.linh0nguyen.banhang.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linh0nguyen.banhang.MainActivity;
import com.example.linh0nguyen.banhang.R;
import com.example.linh0nguyen.banhang.adapter.GioHangAdapter;
import com.example.linh0nguyen.banhang.model.GioHang;
import com.example.linh0nguyen.banhang.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    ImageView imvHinhChiTietSp;
    TextView tvTenChiTietSp, tvGiaChiTietSp, tvMoTaSp;
    Spinner spnSoLuong;
    Button btnNhapGioHang;
    Toolbar toolbar;
    Integer [] soluong;
    SanPham sanpham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        addControls();
        addEvents();
        actionBar();
        getDataChiTietSP();
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDataChiTietSP() {
        Intent intent = getIntent();
        sanpham = (SanPham) intent.getSerializableExtra("sanpham");
        Picasso.with(getApplicationContext()).load(sanpham.getHinhSp()).into(imvHinhChiTietSp);
        tvTenChiTietSp.setText(sanpham.getTenSp());
        DecimalFormat decimalformat = new DecimalFormat("###,###,###");
        tvGiaChiTietSp.setText(decimalformat.format(sanpham.getGiaSp())+" VNĐ");
        tvMoTaSp.setText(sanpham.getMoTa());
    }

    private void addEvents() {
        btnNhapGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.listGioHang.size() > 0){
                    boolean exist = false;
                    for(int i = 0; i<MainActivity.listGioHang.size(); i++){
                        Log.d("ABC", MainActivity.listGioHang.get(i).getIdSp()+MainActivity.listGioHang.get(i).getTenSp()+"\t");
                        if(MainActivity.listGioHang.get(i).getIdSp()==sanpham.getId()){
                            int soLuong = Integer.parseInt(spnSoLuong.getSelectedItem().toString());
                            int sl = soLuong + MainActivity.listGioHang.get(i).getSoLuong();
                            if(sl > 10){
                                MainActivity.listGioHang.get(i).setSoLuong(10);
                            }else {
                                MainActivity.listGioHang.get(i).setSoLuong(sl);
                            }
                            exist = true;
                        }
                    }
                    if(exist == false){
                        int soLuong = Integer.parseInt(spnSoLuong.getSelectedItem().toString());
                        GioHang gioHang = new GioHang(sanpham.getId(), sanpham.getTenSp(), sanpham.getHinhSp(), sanpham.getGiaSp(), soLuong);
                        MainActivity.listGioHang.add(gioHang);
                    }

                }else {
                    int soLuong = Integer.parseInt(spnSoLuong.getSelectedItem().toString());
                    GioHang gioHang = new GioHang(sanpham.getId(), sanpham.getTenSp(), sanpham.getHinhSp(), sanpham.getGiaSp(), soLuong);
                    MainActivity.listGioHang.add(gioHang);
                }
                startActivity(new Intent(ChiTietSanPhamActivity.this, GioHangActivity.class));
            }
        });
    }

    private void addControls() {
        imvHinhChiTietSp = (ImageView) findViewById(R.id.imvHinhChiTietSP);
        tvTenChiTietSp = (TextView) findViewById(R.id.tvTenChiTietSP);
        tvGiaChiTietSp = (TextView) findViewById(R.id.tvGiaChiTietSP);
        tvMoTaSp = (TextView) findViewById(R.id.tvMoTaChiTietSP);
        btnNhapGioHang = (Button) findViewById(R.id.btnThemGioHang);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        spnSoLuong = (Spinner) findViewById(R.id.spnSoLuong);
        soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getApplicationContext(), R.layout.spinner, R.id.tvspiner, soluong);
        adapter.setDropDownViewResource(R.layout.spinner);
        spnSoLuong.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_giohang, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menuGioHang){
            startActivity(new Intent(ChiTietSanPhamActivity.this, GioHangActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
