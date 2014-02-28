package com.wangjie.imageloadersample.imageloader;

import android.graphics.Bitmap;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-2-28
 * Time: 下午1:00
 */
public class CacheConfig {

    /**
     * 默认的加载图片尺寸（表示宽高任一不超过该值，默认是70px）
     */
    private int defRequiredSize = 70;

    /**
     * 显示的默认图片（默认是0，即空白图片）
     */
    private int defaultResId;

    /**
     * 图片位图模式（默认是Bitmap.CacheConfig.ARGB_8888）
     */
    private Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;

    /**
     * 图片内存缓存大小（默认是Runtime.getRuntime().maxMemory() / 4）
     */
    private long memoryCachelimit;

    /**
     * 文件缓存保存目录
     */
    private String fileCachePath;




    public int getDefRequiredSize() {
        return defRequiredSize;
    }

    public CacheConfig setDefRequiredSize(int defRequiredSize) {
        this.defRequiredSize = defRequiredSize;
        return this;
    }

    public int getDefaultResId() {
        return defaultResId;
    }

    public CacheConfig setDefaultResId(int defaultResId) {
        this.defaultResId = defaultResId;
        return this;
    }

    public Bitmap.Config getBitmapConfig() {
        return bitmapConfig;
    }

    public CacheConfig setBitmapConfig(Bitmap.Config bitmapConfig) {
        this.bitmapConfig = bitmapConfig;
        return this;
    }

    public String getFileCachePath() {
        return fileCachePath;
    }

    public CacheConfig setFileCachePath(String fileCachePath) {
        this.fileCachePath = fileCachePath;
        return this;
    }

    public long getMemoryCachelimit() {
        return memoryCachelimit;
    }

    public CacheConfig setMemoryCachelimit(long memoryCachelimit) {
        this.memoryCachelimit = memoryCachelimit;
        return this;
    }



}
