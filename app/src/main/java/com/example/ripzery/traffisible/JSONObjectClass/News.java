package com.example.ripzery.traffisible.JSONObjectClass;

public class News {
    private String id;
    private String type;
    private String primarysource;
    private String secondarysource;
    private String starttime;
    private String endtime;
    private String title;
    private String description;
    private Location location = new Location();
    private Media media = new Media();

    public News(String id, String type, String primarysource, String secondarysource, String starttime, String endtime, String title, String description) {
        this.id = id;
        this.type = type;
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

    public Location getLocation() {
        return location;
    }

    public Media getMedia() {
        return media;
    }

    public static class Media {
        private String type;
        private String path;

        public Media() {

        }

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
        private Point point = new Point();
        private Road road = new Road();
        private StartPoint startPoint = new StartPoint();
        private EndPoint endPoint = new EndPoint();

        public Location() {

        }

        public Location(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public Point getPoint() {
            return point;
        }

        public Road getRoad() {
            return road;
        }

        public StartPoint getStartPoint() {
            return startPoint;
        }

        public EndPoint getEndPoint() {
            return endPoint;
        }

        public static class Point {
            private String name;
            private String latitude;
            private String longitude;

            public Point() {

            }

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

        public static class Road {
            private String name;

            public Road() {

            }

            public Road(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }

        public static class StartPoint {
            private String name, latitude, longitude;

            public StartPoint() {

            }

            public StartPoint(String name, String latitude, String longitude) {
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

        public static class EndPoint {
            private String name, latitude, longitude;

            public EndPoint() {

            }

            public EndPoint(String name, String latitude, String longitude) {
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
