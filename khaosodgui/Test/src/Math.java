import java.util.*;

public class Math {
	public static void main(String[] args) {
//		toScientific(1234.1234);
//		testInsert();
		demo();
	}

	public static void toScientific(double n) {
		// case 1 : 1234 <-- pure integer. result 1.234 * 10^3
		// case 2 : 123.1234 <-- mix. result 1.231234 * 10^2 
		// case 3 : 0.1234 <-- zero. result 1.234 * 10^-1

		// solve case 1
		// 1 234
		// ^

		
		
		StringBuilder tmp = new StringBuilder(Double.toString(n));
		int findDot = tmp.indexOf(".");
		if (findDot >= 0) {
			tmp.append("HLE");
		} else {
			int count = tmp.length() - 1;
			tmp.insert(1, ".");
			System.out.println(tmp.toString() + " count : " + count);
			System.out.println("result = " + tmp.toString() + " * 10^" + count);
		}

	}

	public static void shift(Object src, int offset, Object v) {

	}

	public static void copyarray(int[] src, int srcStart, int[] dest, int destStart, int length) {

	}
	
	static int size = 16;
	static char[] text = new char[size];

	public static void testInsert() {
		text = "SAD".toCharArray();
		size = text.length;

		append("RUN");
		append("RUN");
		append("RUN");
		append("RUN");
		append("RUN");
		append("RUN");
		
	}
	
	public static void ensure(int capacity) {
		if (capacity >= text.length) {
			int who = max(capacity, 2*text.length);
			char[] arr = new char[who];
			for (int i = 0; i < size; i++)
				arr[i] = text[i];
			text = arr;
		}
	}
	
	public static void append(String s) {
		ensure(text.length + s.length() + 1);
		for (int i = 0; i < s.length(); i++) {
			text[size - 1] = s.charAt(i);
			++size;
		}
	}
	
	public static int max(int a, int b) {
		return a > b ? a : b;
	}
	
	public static void demo() {
		JString s = new JString();

		s.append("HI");
		System.out.println(s);
		s.append(" Hello World!");
		System.out.println(s);
		String s1 = new String("1234");
	}
}