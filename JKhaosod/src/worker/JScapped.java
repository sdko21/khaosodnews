package worker;

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
	
	public URL getURL() {
		return url;
	}
	
	/**
	 *  return source code from your website.
	 *  
	 *  @return string, if can access
	 */
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
		return new String(out);
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

//	public static void main(String[] args) {
//		long startTime = System.currentTimeMillis();
//		
//		worker.JScapped webscape = new worker.JScapped("https://www.khaosod.co.th/breaking-news");
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