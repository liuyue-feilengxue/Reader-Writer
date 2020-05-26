package reader_writer;

import java.util.LinkedList;
import java.util.Queue;

public class Semaphore {
	int count;
	public Semaphore() {
		
	}
	public Semaphore(int count) {
		this.count = count;
	}
	/**
	 * semWait操作，如果count<0就阻塞（返回false）
	 * @param s
	 * @return
	 */
	public boolean semWait(Semaphore s) {
		s.count--;
		if (s.count<0) {
			return false;
		}
		return true;
	}
	/**
	 * semSignal操作，如果count<=0，不阻塞，就返回true
	 * @param s
	 * @return
	 */
	public boolean semSignal(Semaphore s) {
		s.count++;
		if (s.count<=0) {
			return true;
		}
		return false;
	}
}
