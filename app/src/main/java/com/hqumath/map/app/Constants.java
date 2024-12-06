package com.hqumath.map.app;

import static com.hqumath.map.app.Constant.TOKEN;

import com.hqumath.map.utils.SPUtil;

import java.util.HashMap;

/**
 * ****************************************************************
 * 文件名称: AppNetConfig
 * 作    者: Created by gyd
 * 创建时间: 2019/1/22 14:30
 * 文件描述: 网络地址
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class Constants {
    public static String baseUrl = "https://api.github.com/"; //API服务器
    public final static String baseUrl_TDT = "http://t1.tianditu.gov.cn/";//天地图API

    //天地图key http://lbs.tianditu.gov.cn/
    public final static String TDT_KEY = "f0208c6587580095818c28b4ec698950"; //天地图key 私人账号，会限制流量，请替换为自己的key
    public final static String tileSourceImgUrl = baseUrl_TDT
            + "img_w/wmts?img_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=img&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&tk="
            + TDT_KEY;//天地图 影像底图 球面墨卡托投影
    public final static String tileSourceCiaUrl = baseUrl_TDT
            + "cia_w/wmts?cia_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cia&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&tk="
            + TDT_KEY;//天地图 影像注记 球面墨卡托投影
    public final static int zoomMinLevel = 1;//缩放级别，显示最大区域
    public final static int zoomMaxLevel = 18;
    public final static int tileSizePixels = 256;//瓦片的大小为256*256

    public final static String waypoints = "{\"waypoint\":[{\"coordinate\":[117.16489805305,36.45287048297]},{\"coordinate\":[117.16639475435,36.45439396908]},{\"coordinate\":[117.15995901542,36.450949861745]},{\"coordinate\":[117.1592966774,36.444753824435]},{\"coordinate\":[117.15432698688,36.446267164199]},{\"coordinate\":[117.15483514123,36.4447635279]},{\"coordinate\":[117.15244892701,36.443831209825]},{\"coordinate\":[117.14928334069,36.444194479151]},{\"coordinate\":[117.14836767475,36.446071085431]},{\"coordinate\":[117.15055375998,36.447718322403]},{\"coordinate\":[117.15124185453,36.447123225624]},{\"coordinate\":[117.15245989413,36.445919422351]},{\"coordinate\":[117.15360360396,36.447274142181]}]}";


    //请求通用参数
    public static HashMap<String, String> getBaseMap() {
        String token = SPUtil.getInstance().getString(TOKEN);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

}
