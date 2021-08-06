public class JString {
	private char[] text;
	private int size = 0;
	private int capacity = 16;
	
	public JString() {
		text = new char[capacity];
	}
	
	public JString(String s) {
		text = s.toCharArray();
		capacity = Math.max(capacity, 2 * text.length);
		size = text.length;
	}
	
	public int length() {
		return size;
	}
	
	public char charAt(int i) {
		checkIndexAt(i);
		return text[i];
	}
	
	public void append(String s) {
		if (s == null) throw new IllegalArgumentException();

		ensureCapacity(s.length() + 1);
		for (int i = 0; i < s.length(); i++) {
			text[size] = s.charAt(i);
			++size;
		}
	}
	
	private void ensureCapacity(int minimumCapacity) {
		if (minimumCapacity >= capacity) {
			int newSize = Math.max(minimumCapacity, text.length * 2);
			char[] newText = new char[newSize];
			for (int i = 0; i < text.length; i++) {
				newText[i] = text[i];
			}
			text = newText;
			capacity = newSize;
		}
	}
	
	private void checkIndexAt(int i) {
		if (i < 0 || i > capacity) {
			throw new ArrayIndexOutOfBoundsException("Out of length. : " + size);
		}
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public String toString() {
		return new String(text);
	}
	
	public char[] toCharArray() {
		return text;
	}
}