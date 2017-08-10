package com.example.linh0nguyen.banhang.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linh0nguyen.banhang.R;
import com.example.linh0nguyen.banhang.model.LoaiSanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Linh(^0^)Nguyen on 7/3/2017.
 */

public class LoaiSanPhamAdapter extends BaseAdapter {
    Context context;
    int myLayout;
    ArrayList<LoaiSanPham> listLoaiSp;

    public LoaiSanPhamAdapter(Context context, int myLayout, ArrayList<LoaiSanPham> listLoaiSp) {
        this.context = context;
        this.myLayout = myLayout;
        this.listLoaiSp = listLoaiSp;
    }

    @Override
    public int getCount() {
        return listLoaiSp.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        private ImageView imvHinh;
        private TextView tvTen;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_loai_san_pham, null);
            holder.imvHinh = (ImageView) convertView.findViewById(R.id.imvLoaiSp);
            holder.tvTen = (TextView) convertView.findViewById(R.id.tvTenLoaiSp);
            convertView.setTag(holder);
        }else holder = (ViewHolder) convertView.getTag();
        LoaiSanPham loaiSp = listLoaiSp.get(position);
        Picasso.with(context).load(loaiSp.getHinhLoaiSp()).into(holder.imvHinh);
        holder.tvTen.setText(loaiSp.getTenLoaiSp());

        return convertView;
    }
}
