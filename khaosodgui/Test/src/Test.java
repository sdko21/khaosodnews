
public class Test {

	static class CountEveryThing extends Thread {
		private int count = 0;

		public CountEveryThing(int count) {
			this.count = count;
		}

		@Override
		public void run() {
			// do something.

			countDown();

		}

		public void countDown() {
			while (count != 0) {
				count--;
				System.out.println("Count : " + count);
			}
		}
	}

	static class SayEveryThing extends Thread {
		private String message;
		private int time;

		public SayEveryThing(String message, int time) {
			this.message = message;
			this.time = time;
		}

		@Override
		public void run() {
			say();

		}

		private void say() {
			for (int i = 0; i < time; i++) {
				System.out.println(time + " " + message);
			}
		}

	}

	public static void main(String[] args) {
		Thread c = new CountEveryThing(10);
		Thread s = new SayEveryThing("Hi Nud!", 10);

		c.start();
		s.start();
	}
}
