package com.hqumath.map.ui.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.hqumath.map.R;
import com.hqumath.map.app.Constants;
import com.hqumath.map.bean.Waypoints;
import com.hqumath.map.utils.CommonUtil;

import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.TilesOverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2023/11/22 11:45
 * 文件描述: 地图相关
 * 注意事项:
 * ****************************************************************
 */
public class MapWidgetPresenter {
    public static float BigMapZoom = 18f;//大地图时缩放系数
    public static float SmallMapZoom = 17f;//小地图时缩放系数
    private IMapController mapController;
    private MapView mapView;
    private Polyline line;//航线
    private ItemizedIconOverlay itemizedIconOverlay;//航点
    private Marker myLocation2;//当前位置2

    public MapWidgetPresenter(MapView mapView) {
        this.mapView = mapView;
    }

    public void init(MapEventsReceiver mapEventsReceiver) {
        mapController = mapView.getController();
        //ui配置
        //mapView.setDestroyMode(false);//销毁模式。OnDetach时不销毁，便于修改父布局
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);//缩放按钮
        //setZoomController();
        mapView.setMultiTouchControls(true);//缩放手势
        /*mapView.setMaxZoomLevel((double) zoomMaxLevel);//缩放级别 可高于地图缩放级别，默认相同
        mapView.setMinZoomLevel((double) zoomMinLevel);
        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(mapView);//旋转手势
        mRotationGestureOverlay.setEnabled(true);
        mapView.getOverlays().add(mRotationGestureOverlay);*/
        //点击事件
        mapView.getOverlays().add(new MapEventsOverlay(mapEventsReceiver));

        //设置地图资源
        mapView.setTileSource(tileSourceImg);
        TilesOverlay tilesOverlay = new TilesOverlay(new MapTileProviderBasic(CommonUtil.getContext(), tileSourceCia), CommonUtil.getContext());
        mapView.getOverlayManager().add(tilesOverlay);
        //添加航线
        addLines();
        //添加航点
        addPoints();
        //添加标记
        initMarker2();

        //缺省的位置
        //公司        117.14477 , 36.66407
        //济南融创乐园 117.18868 , 36.66837
        mapController.setZoom(SmallMapZoom);//缺省缩放级别
        mapController.setCenter(new GeoPoint(36.665133680118, 117.14632436503));
    }

    public void onResume() {
        mapView.onResume();
    }

    public void onPause() {
        mapView.onPause();
    }

    //刷新地图
    public void refresh() {
        mapView.invalidate();
    }

    //设置缩放级别
    public void setZoom(boolean isBigMap) {
        mapController.setZoom(isBigMap ? BigMapZoom : SmallMapZoom);
    }

    /**
     * 放大
     */
    public void zoomIn() {
        mapController.zoomIn();
    }

    /**
     * 缩小
     */
    public void zoomOut() {
        mapController.zoomOut();
    }

    //更新中心位置
    public void setCenter(double lat, double lng) {
        mapController.setCenter(new GeoPoint(lat, lng));
        //mapController.animateTo(new GeoPoint(lat, lng));
    }

    public void setCenter(double lat, double lng, float rotate) {
        //updateMarker(lat, lng, rotate);
        mapController.setCenter(new GeoPoint(lat, lng));
        //mapController.animateTo(new GeoPoint(lat, lng));
    }

    //添加航线
    public void addLines() {
        line = new Polyline(mapView);
        //line.setTitle("龙门山航线");
        //line.setSubDescription("2km");
        Paint paint = line.getOutlinePaint();
        paint.setStrokeWidth(CommonUtil.dp2px(mapView.getContext(), 2));
        paint.setColor(Color.YELLOW);
        /*List<GeoPoint> points = new ArrayList<>();
        for (WayPointInfo.WaypointDTO waypoint : Constants.waypoints) {
            points.add(new GeoPoint(waypoint.getCoordinate().get(1), waypoint.getCoordinate().get(0)));
        }
        line.setPoints(points);*/
        line.setGeodesic(false);
        line.setInfoWindow(null);
        //line.setOnClickListener(new Polyline.OnClickListener() {
        mapView.getOverlayManager().add(line);
    }

    //更新航线
    public void updateLines(List<Waypoints.WaypointDTO> waypoints) {
        List<GeoPoint> points = new ArrayList<>();
        for (Waypoints.WaypointDTO waypoint : waypoints) {
            points.add(new GeoPoint(waypoint.getCoordinate().get(1), waypoint.getCoordinate().get(0)));
        }
        line.setPoints(points);
    }

    //添加航点
    public void addPoints() {
        ArrayList<OverlayItem> items = new ArrayList<>();
        itemizedIconOverlay = new ItemizedIconOverlay(items, new ItemizedIconOverlay.OnItemGestureListener() {
            @Override
            public boolean onItemSingleTapUp(int index, Object item) {
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, Object item) {
                return false;
            }
        }, CommonUtil.getContext());
        mapView.getOverlays().add(itemizedIconOverlay);
    }

    //更新航点
    public void updatePoints(List<Waypoints.WaypointDTO> waypoints) {
        ArrayList<OverlayItem> items = new ArrayList<>();
        for (Waypoints.WaypointDTO waypoint : waypoints) {
            OverlayItem item = new OverlayItem("", "", new GeoPoint(waypoint.getCoordinate().get(1), waypoint.getCoordinate().get(0)));
            Drawable marker = textToDrawable(items.size() + 1 + "");//01H 20km/h
            //Drawable marker = CommonUtil.getContext().getResources().getDrawable(R.drawable.icon_marker);
            item.setMarker(marker);
            items.add(item);
        }
        itemizedIconOverlay.removeAllItems();
        itemizedIconOverlay.addItems(items);
    }

    //添加标记
    private void initMarker2() {
        myLocation2 = new Marker(mapView);
        //startMarker.setPosition(startPoint);
        myLocation2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        Drawable drawable = CommonUtil.getContext().getResources().getDrawable(R.drawable.location_vehicle);
        myLocation2.setIcon(drawable);
        myLocation2.setInfoWindow(null);
        mapView.getOverlays().add(myLocation2);
    }

    /**
     * 更新标记
     *
     * @param lat 坐标
     * @param lng
     */
    public void updateMarker2(double lat, double lng) {
        GeoPoint startPoint = new GeoPoint(lat, lng);
        myLocation2.setPosition(startPoint);
    }

    //文字转图片
    private Drawable textToDrawable(String text) {
        //图像大小
        Paint paint = new Paint();
        paint.setTextSize(CommonUtil.dp2px(8));//设置字体大小
        Rect bounds = new Rect();//包围所有字符的最小矩形
        paint.getTextBounds(text, 0, text.length(), bounds);
        bounds.right = bounds.right + 4;
        Bitmap bitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);//画布
        //绘制矩形
        paint.setColor(0x80000000);
        canvas.drawRect(0, 0, bounds.width(), bounds.height(), paint);
        //绘制文字
        paint.setColor(Color.WHITE);
        canvas.drawText(text, 0, bounds.height(), paint); //绘制文字

        Drawable drawable = new BitmapDrawable(CommonUtil.getContext().getResources(), bitmap);
        return drawable;
    }

    //设置缩放按钮
    private void setZoomController() {
        Drawable drawableIn = CommonUtil.getContext().getResources().getDrawable(org.osmdroid.library.R.drawable.zoom_in);
        Drawable drawableOut = CommonUtil.getContext().getResources().getDrawable(org.osmdroid.library.R.drawable.zoom_out);
        Bitmap bitmapIn = ((BitmapDrawable) drawableIn).getBitmap();
        Bitmap bitmapOut = ((BitmapDrawable) drawableOut).getBitmap();
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);//缩放按钮
        mapView.getZoomController().getDisplay().setBitmaps(bitmapIn, bitmapIn, bitmapOut, bitmapOut);
    }

    //影像地图 _W是墨卡托投影  _c是国家2000的坐标系
    private OnlineTileSourceBase tileSourceImg = new OnlineTileSourceBase("Tian Di Tu Img", Constants.zoomMinLevel, Constants.zoomMaxLevel, Constants.tileSizePixels, "",
            new String[]{Constants.tileSourceImgUrl}) {
        @Override
        public String getTileURLString(final long pMapTileIndex) {
            return getBaseUrl() + "&TILECOL=" + MapTileIndex.getX(pMapTileIndex) + "&TILEROW=" + MapTileIndex.getY(pMapTileIndex) + "&TILEMATRIX=" + MapTileIndex.getZoom(pMapTileIndex);
        }
    };

    //影像标注 _W是墨卡托投影  _c是国家2000的坐标系
    private OnlineTileSourceBase tileSourceCia = new OnlineTileSourceBase("Tian Di Tu Cia", Constants.zoomMinLevel, Constants.zoomMaxLevel, Constants.tileSizePixels, "",
            new String[]{Constants.tileSourceCiaUrl}) {
        @Override
        public String getTileURLString(final long pMapTileIndex) {
            return getBaseUrl() + "&TILECOL=" + MapTileIndex.getX(pMapTileIndex) + "&TILEROW=" + MapTileIndex.getY(pMapTileIndex) + "&TILEMATRIX=" + MapTileIndex.getZoom(pMapTileIndex);
        }
    };
}
