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
	 * semWait���������count<0������������false��
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
	 * semSignal���������count<=0�����������ͷ���true
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
