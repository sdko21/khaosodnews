package worker;

public class News {
    private String newsContent;
    private String newsTime;
    private String newsLink;

    public News() {}

    public News(String newsContent, String newsTime, String newsLink) {
        this.newsContent = newsContent;
        this.newsTime = newsTime;
        this.newsLink = newsLink;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    @Override
    public String toString() {
        return "worker.News{" +
                "newsContent='" + newsContent + '\'' +
                ", newsTime='" + newsTime + '\'' +
                ", newsLink='" + newsLink + '\'' +
                '}';
    }
}
