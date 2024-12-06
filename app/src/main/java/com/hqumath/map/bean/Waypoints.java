package com.hqumath.map.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2024/12/6 18:34
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
public class Waypoints {

    @SerializedName("waypoint")
    private List<WaypointDTO> waypoint;

    public List<WaypointDTO> getWaypoint() {
        return waypoint;
    }

    public void setWaypoint(List<WaypointDTO> waypoint) {
        this.waypoint = waypoint;
    }

    public static class WaypointDTO {
        @SerializedName("coordinate")
        private List<Double> coordinate;

        public List<Double> getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(List<Double> coordinate) {
            this.coordinate = coordinate;
        }
    }
}
