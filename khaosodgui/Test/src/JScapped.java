import java.io.*;
import java.net.*;
import java.util.*;


public class JScapped {
	private URL url;

	public JScapped(String url) {
		try {
			this.url = new URL(url);
		} catch (Exception e) {

		}
	}

	public void setURL(String url) {
		try {
			this.url = new URL(url);
		} catch (Exception e) {
			
		}
	}
	
	public String getText() {
		StringBuilder out = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out.toString();
	}
	
	public void ensureURL() {
		if (url == null) {

		}
	}
	
	public void toHTML(String outPath) {
		String src = getText();
		if (src != null) {
			try {
				String outFile = outPath;
				BufferedWriter write = new BufferedWriter(new FileWriter(new File(outFile)));
				write.append(src);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public static Object[] getData(String src) {
		String[] pattern = {"udblock__permalink", ">", "<"};

		List<String> newsList = new ArrayList<>();
		int start = 0;
		int end = 0;
		while (true) {
			int find = src.indexOf(pattern[0], end);
			if (find >= 0) {
				find = src.indexOf(pattern[1], find);
				start = find + 1;
				if (find >= 0) {
					find = src.indexOf(pattern[2], find);
					if (find >= 0) {
						end = find;
						newsList.add(src.substring(start, end));
					}
				}
			} else {
				break;
			}
		}
		
		return newsList.toArray();
	}


//	public static void main(String[] args) {
//		long startTime = System.currentTimeMillis();
//		
//		JScapped webscape = new JScapped("https://www.khaosod.co.th/breaking-news");
//		String text = webscape.getText();
//		
//		Object[] news = getData(text);
//		
//
//		long endTime = System.currentTimeMillis();
//		long timeProcessing = (endTime - startTime) / 1000L;
//		System.out.println("Program end in : " + timeProcessing);
//	}
	
}