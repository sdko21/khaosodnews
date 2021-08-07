import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class Khaosod extends Thread {

	private List<String> newsList;
	private List<String> newsLink;
	private List<String> newsTime;
	private List<String> covidNewsList;
	private List<String> covidNewsLink;
	private String[] pattern = { "udblock__permalink", ">", "<", "udblock__updated_at", "<p><strong>", ""};
	//																					  ^ for header news
	
	private KhaosodBreaking khaosodBreaking;
	
	private int success = 0;

	public Khaosod() {
		newsList = new ArrayList<>();
		newsLink = new ArrayList<>();
		newsTime = new ArrayList<>();

//		fetch();
//		run();
	}
	
	@Override
	public void run() {
		fetch();
	}
	
	public void setFrame(KhaosodBreaking khaosodBreaking) {
		this.khaosodBreaking = khaosodBreaking;
	}
	
	public Object[] getNews() {
		return newsList.toArray();
	}

	public Object[] getLink() {
		return newsLink.toArray();
	}
	
	public Object[] getTime() {
		return newsTime.toArray();
	}
	
	private void fetch() {
		JScapped webscape = new JScapped("https://www.khaosod.co.th/breaking-news");
		String text = webscape.getText();
		
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
						getNewsTime(text, end);
						
						try {
							sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						success++;
						khaosodBreaking.getLoadingNews().setValue(success);
					}
				}
			} else {
				khaosodBreaking.getLoadingNews().setVisible(false);
				khaosodBreaking.getBtnFetchNews().setEnabled(true);
				khaosodBreaking.getBtnShowNews().setEnabled(true);
				khaosodBreaking.setNewsList(getNews());
				khaosodBreaking.setNewsLink(getLink());
				khaosodBreaking.setNewsTime(getTime());
				break;
			}
		}
		
//		for (int i = 0; i < newsLink.size(); i++) {
//			getNewsContent(newsLink.get(i));
//		}

		
	}
	
	private void getNewsList() {

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
	
	private void getNewsTime(String src, int start) {
		int find = src.indexOf(pattern[3], start);
		if (find >= 0) {
			find = src.indexOf(pattern[1], find);
			if (find >= 0) {
				start = find + 1;
				find = src.indexOf(pattern[2], find);
				if (find >= 0) {
					newsTime.add(src.substring(start, find));
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Khaosod news = new Khaosod();
		Object[] newsList = news.getNews();
		Object[] newsLink = news.getLink();
		Object[] newsTime = news.getTime();

//		for (int i = 0; i < newsList.length; i++) {
//			System.out.println(newsList[i]);
//		}

		for (int i = 0; i < newsTime.length; i++) {
			System.out.println(newsTime[i]);
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