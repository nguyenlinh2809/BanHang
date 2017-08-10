package com.example.linh0nguyen.banhang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linh0nguyen.banhang.R;
import com.example.linh0nguyen.banhang.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Linh(^0^)Nguyen on 7/6/2017.
 */

public class LoadSanPhamAdapter extends BaseAdapter {
    Context context;
    int myLayout;
    ArrayList<SanPham> listSanPham;

    public LoadSanPhamAdapter(Context context, int myLayout, ArrayList<SanPham> listSanPham) {
        this.context = context;
        this.myLayout = myLayout;
        this.listSanPham = listSanPham;
    }

    @Override
    public int getCount() {
        return listSanPham.size();
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
        private ImageView imvHinhSanPham;
        private TextView tvTenSanPham, tvGiaSanPham, tvMoTaSanPham;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_sanpham, null);
            holder.imvHinhSanPham = (ImageView) convertView.findViewById(R.id.imvSanPham);
            holder.tvTenSanPham = (TextView) convertView.findViewById(R.id.tvTenSanPham);
            holder.tvGiaSanPham = (TextView) convertView.findViewById(R.id.tvGiaSanPham);
            holder.tvMoTaSanPham = (TextView) convertView.findViewById(R.id.tvMotaSanPham);
            convertView.setTag(holder);
        }else holder = (ViewHolder) convertView.getTag();
        SanPham sp = listSanPham.get(position);
        Picasso.with(context).load(sp.getHinhSp()).into(holder.imvHinhSanPham);
        holder.tvTenSanPham.setText(sp.getTenSp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGiaSanPham.setText(decimalFormat.format(sp.getGiaSp())+" VNĐ");
        holder.tvMoTaSanPham.setMaxLines(2);
        holder.tvMoTaSanPham.setEllipsize(TextUtils.TruncateAt.END);
        holder.tvMoTaSanPham.setText(sp.getMoTa());

        return convertView;
    }
}
