package com.example.ripzery.traffisible;

/**
 * Created by visit on 8/31/14 AD.
 */
public class News {
    private String id;
    private String type;
    private String primarysource;
    private String secondarysource;
    private String starttime;
    private String endtime;
    private String title;
    private String description;

    public News(String id, String type, String primarysource, String secondarysource, String starttime, String endtime, String title, String description) {
        this.id = id;
        this.primarysource = primarysource;
        this.secondarysource = secondarysource;
        this.starttime = starttime;
        this.endtime = endtime;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getPrimarysource() {
        return primarysource;
    }

    public String getSecondarysource() {
        return secondarysource;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static class Media {
        private String type;
        private String path;


        public Media(String type, String path) {
            this.type = type;
            this.path = path;
        }

        public String getType() {
            return type;
        }

        public String getPath() {
            return path;
        }
    }

    public static class Location {
        private String type;

        public Location(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static class Point {
            private String name;
            private String latitude;
            private String longitude;

            public Point(String name, String latitude, String longitude) {
                this.name = name;
                this.latitude = latitude;
                this.longitude = longitude;
            }

            public String getName() {
                return name;
            }

            public String getLatitude() {
                return latitude;
            }

            public String getLongitude() {
                return longitude;
            }
        }
    }
}
