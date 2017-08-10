package com.example.linh0nguyen.banhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linh0nguyen.banhang.MainActivity;
import com.example.linh0nguyen.banhang.R;
import com.example.linh0nguyen.banhang.activity.ChiTietSanPhamActivity;
import com.example.linh0nguyen.banhang.model.SanPham;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Linh(^0^)Nguyen on 7/4/2017.
 */

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    MainActivity context;
    int myLayout;
    ArrayList<SanPham> listSanPham;
    int pos=0;

    public SanPhamAdapter(MainActivity context, int myLayout, ArrayList<SanPham> listSanPham) {
        this.context = context;
        this.myLayout = myLayout;
        this.listSanPham = listSanPham;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(myLayout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        pos = position;
        SanPham sp = listSanPham.get(position);
        Picasso.with(context).load(sp.getHinhSp()).into(holder.imvHinh);
        holder.tvTen.setText(sp.getTenSp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGia.setText(decimalFormat.format(sp.getGiaSp())+" VNĐ");
    }

    @Override
    public int getItemCount() {
        return listSanPham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imvHinh;
        TextView tvTen, tvGia;

        public ViewHolder(View itemView) {
            super(itemView);
            imvHinh = (ImageView) itemView.findViewById(R.id.imvHinhSp);
            tvTen = (TextView) itemView.findViewById(R.id.tvTenSp);
            tvGia = (TextView) itemView.findViewById(R.id.tvGiaSp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SanPham sanPham = listSanPham.get(getAdapterPosition());
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("sanpham", sanPham);
                    context.startActivity(intent);
                }
            });
        }
    }
}
