package nwsuaf.com.exam.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.squareup.leakcanary.LeakCanary;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.util.VolleyUtil;

public class MainApplication extends Application
{
    /**
     * 单例对象
     */
    private static MainApplication instance;

    private static PackageInfo packInfo;

    @Override
    public void onCreate()
    {
        super.onCreate();
        //initRequestQueue();
        ZXingLibrary.initDisplayOpinion(this);
        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);*/
        //initImageLoader();
        instance = this;
    }

    public static MainApplication getInstance()
    {
        return instance;
    }

    /**
     * 获取apk包名路径
     */
    @SuppressLint("Override")
    public String getDataDir()
    {
        if (packInfo == null)
            getAppInfo();
        return packInfo != null && packInfo.applicationInfo != null ? packInfo.applicationInfo.dataDir : "";
    }

    private void getAppInfo()
    {
        PackageManager packageManager = getPackageManager();
        try
        {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        }
        catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    //初始化请求队列
    private void initRequestQueue(){
        //初始化 volley
        VolleyUtil.initialize(getApplicationContext());
    }

    /**
     * 初始化ImageLoader
     */
    /*private void initImageLoader() {
        DisplayImageOptions defaultOptions;
        defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.drawable.imgloading)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .defaultDisplayImageOptions(defaultOptions)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCache(new UnlimitedDiskCache(StorageUtils.getOwnCacheDirectory(this, AppConstants.APP_IMAGE)))
                .diskCacheSize(100 * 1024 * 1024).tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)).memoryCacheSize(2 * 1024 * 1024)
                .threadPoolSize(3)
                .build();
        ImageLoader.getInstance().init(config);
    }*/
}
