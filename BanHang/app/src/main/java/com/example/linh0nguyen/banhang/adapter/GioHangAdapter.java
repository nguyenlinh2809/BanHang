package com.example.linh0nguyen.banhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linh0nguyen.banhang.MainActivity;
import com.example.linh0nguyen.banhang.R;
import com.example.linh0nguyen.banhang.activity.GioHangActivity;
import com.example.linh0nguyen.banhang.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Linh(^0^)Nguyen on 7/14/2017.
 */

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> listGioHangAdapter;

    public GioHangAdapter(Context context, ArrayList<GioHang> listGioHangAdapter) {
        this.context = context;
        this.listGioHangAdapter = listGioHangAdapter;
    }

    @Override
    public int getCount() {
        return listGioHangAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        private ImageView imvHinh;
        private TextView tvTen, tvGia, tvSoLuong;
        private Button btnTru, btnCong;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giohang, null);
            holder = new ViewHolder();
            holder.imvHinh = (ImageView) convertView.findViewById(R.id.imvHinhGioHang);
            holder.tvTen = (TextView) convertView.findViewById(R.id.tvTenSPGioHang);
            holder.tvGia = (TextView) convertView.findViewById(R.id.tvGiaSpGioHang);
            holder.tvSoLuong = (TextView) convertView.findViewById(R.id.tvGioHangSoLuong);
            holder.btnTru = (Button) convertView.findViewById(R.id.btnGioHangTru);
            holder.btnCong = (Button) convertView.findViewById(R.id.btnGioHangCong);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        final GioHang gioHang = listGioHangAdapter.get(position);
        Picasso.with(context).load(gioHang.getHinhSp()).into(holder.imvHinh);
        holder.tvTen.setText(gioHang.getTenSp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGia.setText(decimalFormat.format(gioHang.getGiaSp()) + " VNĐ");
        holder.tvSoLuong.setText(gioHang.getSoLuong() + "");

        int soLuong = Integer.parseInt(holder.tvSoLuong.getText().toString());
        checkSoLuong(soLuong, holder.btnCong, holder.btnTru);
        holder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioHang.setSoLuong(gioHang.getSoLuong() + 1);
                checkSoLuong(gioHang.getSoLuong(), holder.btnCong, holder.btnTru);
                holder.tvSoLuong.setText(gioHang.getSoLuong() + "");
                GioHangActivity.setTongTien();
            }
        });
        holder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioHang.setSoLuong(gioHang.getSoLuong() - 1);
                checkSoLuong(gioHang.getSoLuong(), holder.btnCong, holder.btnTru);
                holder.tvSoLuong.setText(gioHang.getSoLuong() + "");
                GioHangActivity.setTongTien();
            }
        });
        return convertView;

    }

    public void checkSoLuong(int sl, Button btnCong, Button btnTru) {
        if (sl <= 1) {
            btnTru.setEnabled(false);
            btnCong.setEnabled(true);
        } else if (sl >= 10) {
            btnCong.setEnabled(false);
            btnTru.setEnabled(true);
        } else {
            btnTru.setEnabled(true);
            btnCong.setEnabled(true);
        }
    }


}
