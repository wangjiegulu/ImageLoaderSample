package com.wangjie.imageloadersample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.wangjie.imageloadersample.imageloader.ImageLoader;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-2-27
 * Time: 下午1:05
 */
public class MyAdapter extends BaseAdapter{
    List<String> list;
    Context context;

    public MyAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(null == convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.image = (FadeImageView) convertView.findViewById(R.id.item_image);
            Point point = ((MainActivity)context).screenSize;
            ViewGroup.LayoutParams lp = holder.image.getLayoutParams();
            lp.width = point.x;
            lp.height = point.x;
            holder.image.setLayoutParams(lp);

            holder.progress = (TextView) convertView.findViewById(R.id.item_progress);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.progress.setText("0%");
        holder.progress.setVisibility(View.VISIBLE);
        final ViewHolder vhr = holder;
        ImageLoader.getInstances().displayImage(list.get(position), holder.image, new ImageLoader.OnImageLoaderListener() {
            @Override
            public void onProgressImageLoader(ImageView imageView, int currentSize, int totalSize) {
                vhr.progress.setText(currentSize * 100 / totalSize + "%");
            }

            @Override
            public void onFinishedImageLoader(ImageView imageView, Bitmap bitmap) {
//                System.out.println(imageView + ": load finished!");
                vhr.progress.setVisibility(View.GONE);
            }
        });

        return convertView;
    }

    class ViewHolder{
        FadeImageView image;
        TextView progress;
    }

}
