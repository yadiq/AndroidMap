package com.hqumath.map.ui.main;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.hqumath.map.app.Constants;
import com.hqumath.map.base.BaseActivity;
import com.hqumath.map.bean.Waypoints;
import com.hqumath.map.databinding.ActivityMainBinding;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2023/10/25 9:35
 * 文件描述: 主界面
 * 注意事项:
 * ****************************************************************
 */
public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private MapWidgetPresenter mapPresenter;

    @Override
    protected View initContentView(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.ivZoomIn.setOnClickListener(v -> {
            mapPresenter.zoomIn();
        });
        binding.ivZoomOut.setOnClickListener(v -> {
            mapPresenter.zoomOut();
        });
    }

    @Override
    protected void initData() {
        mapPresenter = new MapWidgetPresenter(binding.mapView);
        mapPresenter.init(new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
//                if (binding.cbMapPoint.isChecked()) {
//                    double lat = p.getLatitude();
//                    double lng = p.getLongitude();
//                    double alt = 150;
//                    /*if (BuildConfig.DEBUG) {
//                        lat = 36.44353842;
//                        lng = 117.17613932;
//                        alt = 246.3;
//                    }*/
//                    mapPresenter.updateMarker2(lat, lng);
//                    mapPresenter.refresh();
//                    mqttClient.sendDataCommand("aircraft_follow_gps", lat, lng, alt);
//                    return false;
//                }
//                binding.cvMap.performClick();//切换地图
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapPresenter != null)
            mapPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapPresenter != null)
            mapPresenter.onPause();
    }

    /**
     * 更新地图
     */
    public void updateMap() {
        Waypoints waypoints = new Gson().fromJson(Constants.waypoints, Waypoints.class);
        mapPresenter.updateLines(waypoints.getWaypoint());//添加航线
        mapPresenter.updatePoints(waypoints.getWaypoint());//添加航点
//        //更新中心位置
//        if (Constants.waypoints.size() > 0) {
//            WayPointInfo.WaypointDTO waypoint = Constants.waypoints.get(0);
//            mapPresenter.setCenter(waypoint.getCoordinate().get(1), waypoint.getCoordinate().get(0));
//        }
    }
}
