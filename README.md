ImageLoaderSample
=================

android端用于异步加载图片，内存缓存，文件缓存，imageview显示图片时增加淡入淡出动画。

### 注意：
需要添加Library[AndroidBucket](https://github.com/wangjiegulu/AndroidBucket)项目的支持（日志、线程、util等）的依赖，ImageLoader使用的ThreadPool，修改配置文件代码

###Gradle
            compile 'com.github.wangjiegulu:ImageLoaderSample:1.0.1'
###Maven
            <dependency>
                <groupId>com.github.wangjiegulu</groupId>
                <artifactId>ImageLoaderSample</artifactId>
                <version>1.0.1</version>
            </dependency>

初始化配置
-----------------
### 创建MyApplication 继承Application，在onCreate中增加如下代码：<br/>
            /**
             * Created with IntelliJ IDEA.
             * Author: wangjie  email:tiantian.china.2@gmail.com
             * Date: 14-2-27
             * Time: 上午11:25
             */
            public class MyApplication extends ABApplication{

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
            ......
        }
<br/>
###然后再AndroidManifest.xml中添加：<br/>
        <application
                ......
                android:name="MyApplication">
                ......
        </application>

### 加载图片的调用方式如下：<br/>
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
                vhr.progress.setVisibility(View.GONE);
            }
        });
        或者：
        ImageLoader.getInstances().displayImage(url, imageIv);
        或者
        ImageLoader.getInstances().displayImage(url, imageIv, 100);

备注
------------
例子中，用到了一部分注解（与ImageLoader功能无关，但是可以简化代码的编写）
可以点下面连接进入AndroidInject
[AndroidInject](https://github.com/wangjiegulu/androidInject)<br />


License
=======

    Copyright 2013 Wang Jie

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

