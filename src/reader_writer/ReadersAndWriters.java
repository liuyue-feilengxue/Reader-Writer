package reader_writer;

public class ReadersAndWriters {
	int readcount = 0;
	Semaphore x = new Semaphore(1);
	Semaphore wsem = new Semaphore(1);

	/**
	 * 如果是阻塞中，则会返回0，完成后返回1，如果是正在执行返回2
	 * 
	 * @param threads
	 * @return
	 */
	public int reader(Threads threads) {

		if (threads.getFlag() == 0) {
			if (x.semWait(x)) {
				readcount++;
				if (readcount == 1) {
					wsem.semWait(wsem);
				}
			} else {
				return 0;
			}
			x.semSignal(x);
		}
		// System.out.println(wsem.count);
		// READUNIT
		int lasttime = threads.getLasttime();
		lasttime--;
		threads.setLasttime(lasttime);
		if (lasttime != 0) {// 还在执行中
			return 2;
		}

		if (x.semWait(x)) {
			readcount--;
			if (readcount == 0) {
				wsem.semSignal(wsem);
			}
		} else {
			return 0;
		}
		x.semSignal(x);
		return 1;
	}

	/**
	 * 如果是在阻塞，就返回0，如果不阻塞，并且写完了，就返回1， 不阻塞，正在写返回2
	 * 
	 * @param threads
	 * @return
	 */
	public int writer(Threads threads) {
		if (threads.getFlag() == 0) {
//			System.out.println(wsem.count);
			if (wsem.semWait(wsem)) {// 不阻塞
				// WRITEUNIT
				int lasttime = threads.getLasttime();
				lasttime--;
				threads.setLasttime(lasttime);
				if (lasttime != 0) {// 还在执行中
					return 2;
				}

				wsem.semSignal(wsem);

				return 1;
			} else {
				wsem.semSignal(wsem);
				return 0;
			}
		} else {
			return 0;
		}
	}
}
