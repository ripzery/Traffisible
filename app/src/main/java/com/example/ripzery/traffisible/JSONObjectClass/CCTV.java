package com.example.ripzery.traffisible.JSONObjectClass;

/**
 * Created by visit on 9/7/14 AD.
 */
public class CCTV {
    private String name;
    private String name_th;
    private String available;
    private String lastupdate;
    private String url;
    private String src;
    private String desc;
    private String list;
    private Point point = new Point();

    public CCTV() {

    }

    public CCTV(String name, String name_th, String available, String lastupdate, String url, String src, String desc, String list) {
        this.name = name;
        this.name_th = name_th;
        this.available = available;
        this.lastupdate = lastupdate;
        this.url = url;
        this.src = src;
        this.desc = desc;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public String getName_th() {
        return name_th;
    }

    public String getAvailable() {
        return available;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public String getUrl() {
        return url;
    }

    public String getSrc() {
        return src;
    }

    public String getDesc() {
        return desc;
    }

    public String getList() {
        return list;
    }

    public Point getPoint() {
        return point;
    }

    public static class Point {
        private String lat;
        private String lng;

        public Point() {

        }

        public Point(String lat, String lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public String getLat() {
            return this.lat;
        }

        public String getLng() {
            return this.lng;
        }
    }
}
