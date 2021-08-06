import java.util.*;

public class Khaosod {
	private List<String> newsList;
	private List<String> newsLink;

	public Khaosod() {
		newsList = new ArrayList<>();
		newsLink = new ArrayList<>();

		fetch();
	}

	public Object[] getNews() {
		return newsList.toArray();
	}

	public Object[] getLink() {
		return newsLink.toArray();
	}

	private void fetch() {
		JScapped webscape = new JScapped("https://www.khaosod.co.th/breaking-news");
		String text = webscape.getText();

		String[] pattern = { "udblock__permalink", ">", "<" };

		int start = 0;
		int end = 0;
		while (true) {
			int find = text.indexOf(pattern[0], end);
			if (find >= 0) {
				find = text.indexOf(pattern[1], find);
				start = find + 1;
				if (find >= 0) {
					find = text.indexOf(pattern[2], find);
					if (find >= 0) {
						end = find;
						String tmp = text.substring(start, end);
						tmp = tmp.replaceAll("&#039;", "\'");
						newsList.add(tmp);
						getNewsLink(text, start);
					}
				}
			} else {
				break;
			}
		}
	}

	private void getNewsLink(String src, int start) {
		start -= 92;
		int find = src.indexOf("http", start);
		if (find >= 0) {
			start = find;
			find = src.indexOf("\"", find);
			if (find >= 0) {
				newsLink.add(src.substring(start, find));
			}
		}
	}

	public static void main(String[] args) {
		Khaosod news = new Khaosod();
		Object[] newsList = news.getNews();
		Object[] newsLink = news.getLink();

//		for (int i = 0; i < newsList.length; i++) {
//			System.out.println(newsList[i]);
//		}

		for (int i = 0; i < newsLink.length; i++) {
			System.out.println(newsLink[i]);
		}

//		try {
//			Runtime rt = Runtime.getRuntime();
//			String url = "http://stackoverflow.com";
//			rt.exec("open " + url);
//		} catch (Exception e) {
//
//		}
	}

}