package com.wangjie.imageloadersample;

import android.graphics.Bitmap;
import com.wangjie.androidbucket.application.ABApplication;
import com.wangjie.imageloadersample.imageloader.CacheConfig;
import com.wangjie.imageloadersample.imageloader.ImageLoader;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-2-27
 * Time: 上午11:25
 */
public class MyApplication extends ABApplication {


    @Override
    protected void initImageLoader() {
        ImageLoader.init(getApplicationContext(),
                new CacheConfig()
                        .setDefRequiredSize(600) // 设置默认的加载图片尺寸（表示宽高任一不超过该值，默认是70px）
                        .setDefaultResId(R.drawable.ic_launcher) // 设置显示的默认图片（默认是0，即空白图片）
                        .setBitmapConfig(Bitmap.Config.ARGB_8888) // 设置图片位图模式（默认是Bitmap.CacheConfig.ARGB_8888）
                        .setMemoryCachelimit(Runtime.getRuntime().maxMemory() / 3) // 设置图片内存缓存大小（默认是Runtime.getRuntime().maxMemory() / 4）
//                    .setFileCachePath(Environment.getExternalStorageDirectory().toString() + "/mycache") // 设置文件缓存保存目录
        );
    }


}
