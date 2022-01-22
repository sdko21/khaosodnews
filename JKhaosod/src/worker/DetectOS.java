package worker;

public class DetectOS {
	private static String os = System.getProperty("os.name").toLowerCase();
	
    /**
     * Checks if is windows.
     *
     * @return true, if is windows
     */
	public static boolean isWindows() {
		return (os.indexOf("win") >= 0);
	}
	
    /**
     * Checks if is mac.
     *
     * @return true, if is mac
     */
	public static boolean isMac() {
		return (os.indexOf("mac") >= 0);
	}
	
    /**
     * Checks if is unix.
     *
     * @return true, if is unix
     */
	public static boolean isUnix() {
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") >= 0);
	}
	
    /**
     * Checks if is solaris.
     *
     * @return true, if is solaris
     */
	public static boolean isSolaris() {
		return (os.indexOf("sunos") >= 0);
	}
}
