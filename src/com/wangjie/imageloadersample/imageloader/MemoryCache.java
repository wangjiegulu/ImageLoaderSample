package com.wangjie.imageloadersample.imageloader;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author wangjie
 * @version 创建时间：2012-10-14 下午7:28:39
 */
public class MemoryCache {

    private static final String TAG = "MemoryCache";
    // 放入缓存时是个同步操作
    // LinkedHashMap构造方法的最后一个参数true代表这个map里的元素将按照最近使用次数由少到多排列，即LRU
    // 这样的好处是如果要将缓存中的元素替换，则先遍历出最近最少使用的元素来替换以提高效率
    private Map<String, Bitmap> cache = Collections
                    .synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));
    // 缓存中图片所占用的字节，初始0，将通过此变量严格控制缓存所占用的堆内存
    private long size = 0;// current allocated size
    /**
     * 缓存占用的最大堆内存
     */
    private long limit = 1000000;// max memory in bytes

    public MemoryCache(long limit) {
            // use 25% of available heap size
        this.limit = limit > 1000000l ? limit : Runtime.getRuntime().maxMemory() / 4;
        Log.i(TAG, "MemoryCache limit 初始化为 " + this.limit / 1024. / 1024. + "MB");
    }

    /**
     * 从缓存中取出图片
     * @param id
     * @return
     */
    public Bitmap get(String id) {
            try {
                    if (!cache.containsKey(id)){
                        return null;
                    }
                    return cache.get(id);
            } catch (NullPointerException ex) {
                    return null;
            }
    }

    /**
     * 把图片放入缓存
     * @param id
     * @param bitmap
     */
    public void put(String id, Bitmap bitmap) {
            try {
                    if (cache.containsKey(id)){
                        size -= getSizeInBytes(cache.get(id));
                    }
                    cache.put(id, bitmap);
                    size += getSizeInBytes(bitmap);
                    checkSize();
            } catch (Throwable th) {
                    th.printStackTrace();
            }
    }

    /**
     * 严格控制堆内存，如果超过将首先替换最近最少使用的那个图片缓存
     * 
     */
    private synchronized void checkSize() {
            Log.i(TAG, "cache size=" + size + "bytes, length=" + cache.size());
            if (size > limit) {
                    // 先遍历最近最少使用的元素
                    Iterator<Entry<String, Bitmap>> iter = cache.entrySet().iterator();
                    while (iter.hasNext()) {
                            Entry<String, Bitmap> entry = iter.next();
                            size -= getSizeInBytes(entry.getValue());
                            iter.remove();
                            if (size <= limit)
                                    break;
                    }
                    Log.i(TAG, "Clean cache. New size " + cache.size());
            }
    }

    /**
     * 清清除内存缓存
     */
    public void clear() {
            cache.clear();
    }

    /**
     * 获取图片占用的内存大小
     * 
     * @param bitmap
     * @return
     */
    long getSizeInBytes(Bitmap bitmap) {
        if (bitmap == null){
            return 0;
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

}
