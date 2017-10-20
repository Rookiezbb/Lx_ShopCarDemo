package com.bawei.lx_shopcardemo;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Zhang on 2017/10/20.
 */

public class MyApp  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoder();
    }
    /**
     * 图片加载
      */
    private void initImageLoder() {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true)
                    .cacheInMemory(true)
                    .showImageForEmptyUri(R.mipmap.ic_launcher)
                    .showImageOnFail(R.mipmap.ic_launcher)
                    .showImageOnLoading(R.mipmap.ic_launcher)
                    .build();
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                    .defaultDisplayImageOptions(options)
                    .build();

            ImageLoader.getInstance().init(configuration);
        }
}
