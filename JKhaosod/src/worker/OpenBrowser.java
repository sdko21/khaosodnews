package worker;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;

public class OpenBrowser {
	private static final String WINDOWS_COMMAND = "rundll32 url.dll,FileProtocolHandler ";
	private static final String MAC_COMMAND = "open ";
	private static final String UNIX_COMMAND = "xdg-open ";
	private static final String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror", "netscape", "opera", "links", "lynx" };
	
	public static void open(String url) throws OperationNotSupportedException {
		Runtime rt = Runtime.getRuntime();
		String command = "";
		
		if (DetectOS.isWindows()) {
			command = WINDOWS_COMMAND; 
		} else if (DetectOS.isMac()) {
			command = MAC_COMMAND;
		} else if (DetectOS.isUnix()) {
			command = UNIX_COMMAND;
		} else if (DetectOS.isSolaris()) {

		} else {
			throw new OperationNotSupportedException("");
		}
		
		try {
			rt.exec(command + url);
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
}