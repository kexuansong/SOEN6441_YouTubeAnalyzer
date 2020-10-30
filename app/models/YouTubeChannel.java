package models;

public class YouTubeChannel {
    // Api_Key = AIzaSyCdDdyagRPBqUwYQrR1fgBo7_kHpyhaGkU

    private String imgLink;
    private String channelName;
    private int totalViews;
    private int totalSubscribers;
    private int totalVideos;

    public YouTubeChannel(String img, String name, int views, int subscribers, int videos){
        this.imgLink = img;
        this.channelName = name;
        this.totalViews = views;
        this.totalSubscribers = subscribers;
        this.totalVideos = videos;
    }

    public YouTubeChannel(){};

    public int getTotalSubscribers() {
        return totalSubscribers;
    }

    public int getTotalVideos() {
        return totalVideos;
    }

    public int getTotalViews() {
        return totalViews;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public void setTotalSubscribers(int totalSubscribers) {
        this.totalSubscribers = totalSubscribers;
    }

    public void setTotalVideos(int totalVideos) {
        this.totalVideos = totalVideos;
    }

    public void setTotalViews(int totalViews) {
        this.totalViews = totalViews;
    }
}
