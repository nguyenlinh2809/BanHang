package com.example.linh0nguyen.banhang.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linh0nguyen.banhang.MainActivity;
import com.example.linh0nguyen.banhang.R;
import com.example.linh0nguyen.banhang.adapter.GioHangAdapter;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    Toolbar toolBar;
    public static TextView tvTongTien;
    Button btnThanhToan, btnTiepTuc;
    ListView lvGioHang;
    GioHangAdapter adapterGioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        addControls();
        addEvents();
        actionBar();
        setTongTien();
    }

    public static void setTongTien() {
        long tongTien = 0;
        for(int i = 0; i < MainActivity.listGioHang.size(); i++){
            tongTien += MainActivity.listGioHang.get(i).tongTien();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongTien.setText(decimalFormat.format(tongTien)+" VNĐ");
    }

    private void actionBar() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addEvents() {
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GioHangActivity.this, MainActivity.class));
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.listGioHang.size()>0){
                    startActivity(new Intent(GioHangActivity.this, ThanhToanActivity.class));
                }else{
                    Toast.makeText(GioHangActivity.this, "Bạn chưa nhập sản phẩm nào cho giỏ hàng!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Bạn có muốn xoá sản phẩm này?");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.listGioHang.remove(position);
                        adapterGioHang.notifyDataSetChanged();
                        setTongTien();
                    }
                });
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                return false;
            }
        });
    }

    private void addControls() {
        toolBar = (Toolbar) findViewById(R.id.toolBarGioHang);
        tvTongTien = (TextView) findViewById(R.id.tvGioHangTongTien);
        btnThanhToan = (Button) findViewById(R.id.btnGioHangThanhToan);
        btnTiepTuc = (Button) findViewById(R.id.btnGioHangTiepTucMuaHang);
        lvGioHang = (ListView) findViewById(R.id.lvGioHang);
        adapterGioHang = new GioHangAdapter(getApplicationContext(), MainActivity.listGioHang);
        lvGioHang.setAdapter(adapterGioHang);
    }
}
