package com.wangjie.imageloadersample.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.thread.Runtask;
import com.wangjie.androidbucket.thread.ThreadPool;
import com.wangjie.androidbucket.utils.ABIOUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABImageProcess;
import com.wangjie.imageloadersample.customviews.FadeImageView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author wangjie email:tiantian.china.2@gmail.com
 * @version 创建时间：2012-10-14 下午7:30:07
 */
public class ImageLoader {

    /**
     * 图片加载监听器
     */
    public interface OnImageLoaderListener {
        /**
         * 用于在加载过程中回调更新UI进度
         * @param imageView
         * @param currentSize
         * @param totalSize
         */
        public void onProgressImageLoader(ImageView imageView, int currentSize, int totalSize);
        /**
         * 完成加载后回调
         * @param imageView 要加载ImageView
         * @param bitmap 加载的图片
         */
        public void onFinishedImageLoader(ImageView imageView, Bitmap bitmap);
    }


    private static final String TAG = ImageLoader.class.getSimpleName();

    private static ImageLoader imageLoader;

    public MemoryCache memoryCache; // 内存缓存
    public FileCache fileCache; // 文件缓存
    private Map<ImageView, String> imageViews = Collections
            .synchronizedMap(new WeakHashMap<ImageView, String>()); // 为每个imageview设置对应的url
    // 线程池
//    ExecutorService executorService;

    /*******************************配置信息BEGIN*******************************/
    // applicationContext
    private Context context;

    /**
     * 配置文件
     */
    private CacheConfig config;

    /*******************************配置信息END*******************************/

//    private ImageLoader() {
//        executorService = Executors.newFixedThreadPool(5);
//    }

    /**
     * 初始化方法（初始化各种缓存配置），推荐在Application中调用
     * @param context
     */
    public static void init(Context context, CacheConfig config){
        imageLoader = new ImageLoader();
        imageLoader.context = context;
        /**
         * 如果配置了CacheConfig，则使用配置的Config；否则，使用默认的Config
         */
        imageLoader.config = null != config ? config : new CacheConfig();
        imageLoader.memoryCache = new MemoryCache(config.getMemoryCachelimit());
        /**
         * 如果在config中配置了缓存目录，则使用之；否则，使用默认的缓存路径
         */
        imageLoader.fileCache = new FileCache(imageLoader.context, null != config.getFileCachePath() ? config.getFileCachePath() : Environment.getExternalStorageDirectory().toString() + "/Android/data/" + context.getPackageName() + "/cache");


    }

    /**
     * 获取ImageLoader实例
     * @return
     */
    public static ImageLoader getInstances() {
        if(null == imageLoader){
            Log.e(TAG, "imageLoader had not be initialized");
        }
        return imageLoader;
    }

    /**
     * 最主要的方法
     * @param url
     * @param imageView
     * @param requiredSize 裁剪图片大小尺寸（一直裁剪到图片宽或高 至少有一个小与requiredSize的时候）
     * @param listener
     * @param defaultPicResId
     */
    public void displayImage(String url, ImageView imageView, int requiredSize, OnImageLoaderListener listener, int defaultPicResId) {
//        String identityCode = url + "_" + requiredSize;
        url = getIdentityCode(url, requiredSize);
        imageViews.put(imageView, url);
        // 先从内存缓存中查找
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
            if(null != listener){
                listener.onFinishedImageLoader(imageView, bitmap); // 通知完成加载
            }
        } else {
            /**
             * 如果defaultPicResId小于0，则不设置默认图片
             */
            if(defaultPicResId < 0){
                queuePhoto(url, imageView, requiredSize, listener);
                return;
            }
            /**
             * 如果defaultPicResId等于0，则设置默认图片为config中的默认图片，并开启新线程加载真实需要的图片
             * 如果defaultPicResId大于0，则设置默认图片为指定的默认图片，并开启新线程加载真实需要的图片
             */
            if(defaultPicResId == 0){
                defaultPicResId = config.getDefaultResId();
            }
            imageView.setImageResource(defaultPicResId);

            queuePhoto(url, imageView, requiredSize, listener);
        }
    }

    public void displayImage(String url, ImageView imageView) {
        displayImage(url, imageView, config.getDefRequiredSize(), null, 0);
    }

    public void displayImage(String url, ImageView imageView, int requiredSize) {
        displayImage(url, imageView, requiredSize, null, 0);
    }

    public void displayImage(String url, ImageView imageView, OnImageLoaderListener listener) {
        displayImage(url, imageView, config.getDefRequiredSize(), listener, 0);
    }

    public void displayImage(String url, ImageView imageView, OnImageLoaderListener listener, int defaultPicResId) {
        displayImage(url, imageView, config.getDefRequiredSize(), listener, defaultPicResId);
    }



    /**
     * 启动线程加载图片
     * @param url
     * @param imageView
     * @param requiredSize
     * @param listener
     */
    private void queuePhoto(String url, ImageView imageView, int requiredSize, OnImageLoaderListener listener) {
        PhotoToLoad p = new PhotoToLoad(url, imageView, listener);
//        executorService.submit(new PhotosLoader(p, requiredSize));
        ThreadPool.go(new PhotosLoader(p, requiredSize));

    }

    /**
     * 执行网络请求加载图片
     * @param url
     * @param requiredSize
     * @return
     */
    private Bitmap getBitmap(String url, int requiredSize, PhotoToLoad photoToLoad) {
        File f = fileCache.getFile(url);
        // 先从文件缓存中查找是否有
        Bitmap b = decodeFile(f, requiredSize);
        if (b != null)
            return b;

        InputStream is = null;
        OutputStream os = null;
        try {
            os = new FileOutputStream(f);
            String realUri = getUriFromIdentityCode(url);

            // 如果是本地文件
            if(!realUri.startsWith("http")){
                Bitmap bm = ABImageProcess.getSmallBitmap(realUri, requiredSize, requiredSize);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, os);
                return bm;
            }

            // 如果是网络图片，从指定的url中下载图片
            Bitmap bitmap = null;
            URL imageUrl = new URL(realUri);
            HttpURLConnection conn = (HttpURLConnection) imageUrl
                    .openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            is = conn.getInputStream();

//            CopyStream(is, os, conn.getContentLength(), photoToLoad);

            photoToLoad.totalSize = conn.getContentLength();
            int buffer_size = 1024;
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);

                if (count == -1){
                    break;
                }
                os.write(bytes, 0, count);

                if(null != photoToLoad.onImageLoaderListener){ // 如果设置了图片加载监听，则回调
                    Message msg = loaderHandler.obtainMessage();
                    photoToLoad.currentSize += count;
                    msg.arg1 = IMAGE_LOADER_PROCESS;
                    msg.obj = photoToLoad;
                    loaderHandler.sendMessage(msg);
                }

            }

            bitmap = decodeFile(f, requiredSize);
            return bitmap;
        } catch (Exception ex) {
            Logger.w(TAG, ex);
            return null;
        } finally {
            ABIOUtil.closeIO(is, os);
        }
    }

    /**
     * decode这个图片并且按比例缩放以减少内存消耗，虚拟机对每张图片的缓存大小也是有限制的
     * @param f
     * @param requiredSize
     * @return
     */
    private Bitmap decodeFile(File f, int requiredSize) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // 计算合理的缩放指数（2的整幂数）
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;

            while (true) {
//                        if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize){
//                            break;
//                        }
//                        width_tmp /= 2;
//                        height_tmp /= 2;
//                        scale *= 2;
                if(width_tmp <= requiredSize || height_tmp <= requiredSize){
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;

            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
//            o2.inSampleSize = (int) scale;
            o2.inSampleSize = scale;
            o2.inPreferredConfig = config.getBitmapConfig();
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);

        } catch (FileNotFoundException e) {
        }
        return null;
    }

    /**
     * 图片加载对象的封装
     */
    private class PhotoToLoad {
        public String url;
        public ImageView imageView;
        public OnImageLoaderListener onImageLoaderListener;
        public int currentSize;
        public int totalSize;
        public Bitmap bitmap;

        public PhotoToLoad(String u, ImageView i, OnImageLoaderListener listener) {
            url = u;
            imageView = i;
            this.onImageLoaderListener = listener;
        }

    }

    /**
     * 异步加载图片
     */
    class PhotosLoader extends Runtask{
        PhotosLoader(Object... objs) {
            super(objs);
        }

        @Override
        public Object runInBackground() {
            PhotoToLoad photoToLoad = (PhotoToLoad)objs[0];
            int requiredSize = (Integer)objs[1];

            if (imageViewReused(photoToLoad)){ // 防止图片错位（如果加载的图片不是当前需要加载的图片，则不做任何处理）
                return null;
            }
            Bitmap bmp = getBitmap(photoToLoad.url, requiredSize, photoToLoad); // 网络加载图片

            memoryCache.put(photoToLoad.url, bmp);
            if (imageViewReused(photoToLoad)){ // 防止图片错位（如果加载的图片不是当前需要加载的图片，则不做任何处理）
                return null;
            }

            // 加载结束，更新UI
            Message msg = rHandler.obtainMessage();
            msg.arg1 = IMAGE_LOADER_FINISHED;
//            photoToLoad.bitmap = bmp;
            msg.obj = photoToLoad;
            loaderHandler.sendMessage(msg);

            return null;
        }
    }

    /**
     * 防止图片错位
     *
     * @param photoToLoad
     * @return
     */
    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
//        if (tag == null || !tag.equals(photoToLoad.url))
//            return true;
//        return false;
        return null == tag || !tag.equals(photoToLoad.url);
    }


    /**
     * 清空文件缓存或内存缓存
     */
    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }
    public void clearMemoryCache() {
        memoryCache.clear();
    }
    public void clearFileCache() {
        fileCache.clear();
    }


    static final int IMAGE_LOADER_PROCESS = 0x01;
    static final int IMAGE_LOADER_FINISHED = 0x02;

    Handler loaderHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PhotoToLoad photoToLoad = (PhotoToLoad)msg.obj;
            if(null == photoToLoad){
                return;
            }
            switch(msg.arg1){
                case IMAGE_LOADER_PROCESS: // 更新加载进度
                    photoToLoad.onImageLoaderListener.onProgressImageLoader(photoToLoad.imageView, photoToLoad.currentSize, photoToLoad.totalSize);

                    break;
                case IMAGE_LOADER_FINISHED: // 加载完毕
                    if (imageViewReused(photoToLoad)){ // 防止图片错位（如果加载的图片不是当前需要加载的图片，则不做任何处理）
                        return;
                    }
                    Bitmap bitmap = memoryCache.get(photoToLoad.url);
                    if (null != bitmap){
                        if(photoToLoad.imageView instanceof FadeImageView){
                            ((FadeImageView)photoToLoad.imageView).setImageBitmapAnim(bitmap);
                        }else{
                            photoToLoad.imageView.setImageBitmap(bitmap);
                        }
                    }
                    // 如果设置了监听器
                    if(null != photoToLoad.onImageLoaderListener){
                        // 通知观察者完成
                        photoToLoad.onImageLoaderListener.onFinishedImageLoader(photoToLoad.imageView, bitmap);

                    }


                    break;
            }


        }
    };


    private static final String DIVIDER = "_";
    private String getIdentityCode(String uri, int requiredSize){
        return uri + DIVIDER + requiredSize;
    }
    private String getUriFromIdentityCode(String indentityCode){
        return indentityCode.substring(0, indentityCode.lastIndexOf(DIVIDER));
    }
    private int getRequiredSizeFromIdentityCode(String indentityCode){
        return Integer.valueOf(indentityCode.substring(indentityCode.lastIndexOf(DIVIDER)));
    }

}
