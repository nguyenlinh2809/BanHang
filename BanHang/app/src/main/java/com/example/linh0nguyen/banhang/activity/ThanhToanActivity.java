package com.example.linh0nguyen.banhang.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThanhToanActivity extends AppCompatActivity {
    EditText edtTen, edtSoDT, edtEmail;
    Button btnGui, btnHuy;
    String urlThanhToan = "http://linhnguyenngoc.esy.es/banhang/donhang.php";
    String urlChiTietDonHang = "http://linhnguyenngoc.esy.es/banhang/chitietdonhang.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doThanhToan();
            }
        });
    }

    private void doThanhToan() {

        final String ten = edtTen.getText().toString();
        final String sdt = edtSoDT.getText().toString();
        final String email = edtEmail.getText().toString();
        if(ten.isEmpty() || sdt.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Bạn chưa nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlThanhToan, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                Log.d("madonhang", response.toString());
                if(response != null){
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlChiTietDonHang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("success")){
                                Toast.makeText(ThanhToanActivity.this, "Thêm giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ThanhToanActivity.this, MainActivity.class));
                                MainActivity.listGioHang.clear();
                                Toast.makeText(ThanhToanActivity.this, "Mời bạn tiếp tục mua hàng!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ThanhToanActivity.this, "Thêm đơn hàng thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            JSONArray jsonArray = new JSONArray();
                            for(int i = 0; i < MainActivity.listGioHang.size(); i++){
                                JSONObject jsonOject = new JSONObject();
                                try {
                                    jsonOject.put("madonhang", response);
                                    jsonOject.put("masanpham", MainActivity.listGioHang.get(i).getIdSp());
                                    jsonOject.put("tensanpham", MainActivity.listGioHang.get(i).getTenSp());
                                    jsonOject.put("soluong", MainActivity.listGioHang.get(i).getSoLuong()+"");
                                    jsonOject.put("giatien", MainActivity.listGioHang.get(i).getGiaSp()+"");
                                    jsonArray.put(jsonOject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            HashMap<String, String> param = new HashMap<>();
                            param.put("json", jsonArray.toString());
                            return param;
                        }
                    };

                    queue.add(stringRequest);

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ThanhToanActivity.this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("ten", ten);
                param.put("sodienthoai", sdt);
                param.put("email", email);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void addControls() {
        edtTen = (EditText) findViewById(R.id.edtTenKhachHang);
        edtSoDT = (EditText) findViewById(R.id.edtSdtKhachHang);
        edtEmail = (EditText) findViewById(R.id.edtEmailKhachHang);
        btnGui = (Button) findViewById(R.id.btnThanhToan);
        btnHuy = (Button) findViewById(R.id.btnTroVe);
    }
}
