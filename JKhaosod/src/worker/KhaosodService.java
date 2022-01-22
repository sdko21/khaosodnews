package worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KhaosodService {
    private static String[] pattern = { "udblock__permalink", ">", "<", "udblock__updated_at", "<p><strong>", ""};
    public static final String HOMEPAGE = "https://www.khaosod.co.th/";
    public static final String BREAKING_NEWS = "breaking-news";
    public static final String COVID19_NEWS = "covid-19";
    public static final String TRENDING_ONLINE_NEWS = "special-stories";
    public static final String ENTERTRAINMENT_NEWS = "entertainment";
    public static final String SPORT_NEWS = "sports";
    public static final String MAGIC_NEWS = "ดวง";
    
    private static final Map<Integer, String> memo = new HashMap<>();
    
    static {
    	memo.put(1, HOMEPAGE + BREAKING_NEWS);
    	memo.put(2, HOMEPAGE + COVID19_NEWS);
    	memo.put(3, HOMEPAGE + TRENDING_ONLINE_NEWS);
    	memo.put(4, HOMEPAGE + ENTERTRAINMENT_NEWS);
    	memo.put(5, HOMEPAGE + SPORT_NEWS);
    	memo.put(6, HOMEPAGE + MAGIC_NEWS);
    }
    
    private KhaosodService() {}

    public static List<News> getNews(String url) {
        // store news and datetime
        List<News> newsList = new ArrayList<>();
        JScapped webscape = new JScapped(url);
        String text = webscape.getText();

        int start = 0;
        int end = 0;
        while (true) {
            int find = text.indexOf(pattern[0], end);
            if (find >= 0) {
                find = text.indexOf(pattern[1], find);
                if (find >= 0) {
                    start = find + 1;
                    find = text.indexOf(pattern[2], find);
                    if (find >= 0) {
                        end = find;
                        String newsContent = text.substring(start, end);
                        newsContent = newsContent.replaceAll("&#039;", "\'");
                        String newsTime = getNewsTime(text, end);
                        String newsLink = getNewsLink(text, start);
                        News newNews = new News(newsContent, newsTime, newsLink);
                        newsList.add(newNews);
                    }
                }
            } else {
                break;
            }
        }
        return newsList;
    }

    private static String getNewsLink(String src, int start) {
        String link = "";
        start -= 130;
        int find = src.indexOf("http", start);
        if (find >= 0) {
            start = find;
            find = src.indexOf("\"", find);
            if (find >= 0) {
                link = src.substring(start, find);
            }
        }
        return link;
    }

    private static String getNewsTime(String src, int start) {
        String time = "";
        int find = src.indexOf(pattern[3], start);
        if (find >= 0) {
            find = src.indexOf(pattern[1], find);
            if (find >= 0) {
                start = find + 1;
                find = src.indexOf(pattern[2], find);
                if (find >= 0) {
                    time = src.substring(start, find);
                }
            }
        }
        return time;
    }
    
    public static String getWebsiteNewsLink(int pick) {
    	return memo.get(pick);
    }

    public static void main(String[] args) {
        List<News> n = getNews("https://www.khaosod.co.th/breaking-news");
        for (var k : n) {
            System.out.println(k.getNewsContent() + " - " + k.getNewsTime() + " - " + k.getNewsLink());
        }
    }
}
