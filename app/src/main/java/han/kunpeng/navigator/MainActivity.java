package han.kunpeng.navigator;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CustomMapStyleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity implements AMap.OnMyLocationChangeListener {
    private MapView mMapView;
    private AMap mAMap;
    private MyLocationStyle mMyLocationStyle;
    private LatLonPoint mMyLatLonPoint = null;
    private CustomMapStyleOptions mapStyleOptions = new CustomMapStyleOptions();
    private TabLayout mTabLayout;
    private Timer mTimer = new Timer();
    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                startActivity(intent);
            }
        });
*/

        mMapView = findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState); // 此方法必须重写

        init();

/*
        startActivity(new Intent(this.getApplicationContext(),
                com.amap.api.maps.offlinemap.OfflineMapActivity.class));
*/
    }

    private void init() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            setUpMap();
        }

//        setMapCustomStyleFile(this);

        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (String tab : getResources().getStringArray(R.array.tabs)) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tab));
        }

    }

    /**
     * 设置一些 AMap 的属性
     */
    private void setUpMap() {
        // 设置默认定位状态
        mMyLocationStyle = new MyLocationStyle().myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        mAMap.setMyLocationStyle(mMyLocationStyle);

        // 设置默认定位按钮是否显示
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);

        // 默认为 false 表示隐藏定位层并不可触发定位
        // 设置为 true 表示显示定位层并可触发定位
        mAMap.setMyLocationEnabled(true);

        // 设置 SDK 自带定位消息监听
        mAMap.setOnMyLocationChangeListener(this);


        mAMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                Log.i(TAG, "onMapLoaded - zoom level: " + mAMap.getCameraPosition().zoom);
            }
        });
    }

    private void setMapCustomStyleFile(Context context) {
        String styleName = "style_new.data";
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(styleName);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            if (mapStyleOptions != null) {
                // 设置自定义样式
                mapStyleOptions.setStyleData(b);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "MainActivity -onResume");
        mMapView.onResume();
//        Log.i(TAG, "MainActivity -onResume - Timer.schedule - begin");
//        mTimer.schedule(mTimerTask, 3000, 3000);
//        Log.i(TAG, "MainActivity -onResume - Timer.schedule - end");
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mTimer.cancel();
    }

    @Override
    public void onMyLocationChange(Location location) {
        // 定位回调监听
        if (location != null) {
            Log.i(TAG, "onMyLocationChange - Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
/*
            // 首次定位
            if (null == mMyLatLonPoint) {
                mMyLatLonPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());
                // 选择移动到地图中心点并修改级别到 16 级
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMyLatLonPoint.getLatitude(), mMyLatLonPoint.getLongitude()), 16));
            } else {
                mMyLatLonPoint.setLatitude(location.getLatitude());
                mMyLatLonPoint.setLongitude(location.getLongitude());
            }
            Bundle bundle = location.getExtras();
            if (bundle != null) {
                int errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE);
                String errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO);
                // 定位类型，可能为 GPS、WIFI 等，具体可以参考官网的定位 SDK 介绍。
                int locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE);

                */
/*
                errorCode
                errorInfo
                locationType
                *//*

                Log.e(TAG, "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType);
            } else {
                Log.e(TAG, "定位信息， bundle is null ");
            }
*/

        } else {
            Log.e(TAG, "定位失败");
        }
    }
}
