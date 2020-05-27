package reader_writer;

public class ReadersAndWriters {
	int readcount = 0;
	int writecount = 0;
	Semaphore wsem = new Semaphore(1);

	/**
	 * 一开始的时候flag是0，完成后返回1，正在执行返回2，正在进行写者返回3
	 * 
	 * @param threads
	 * @return
	 */
	public int reader(Threads threads) {
		if (threads.getFlag() == 3) {
			if (writecount == 1) {
				return 3;
			}
		}
		if (threads.getFlag() == 0) {
			readcount++;
			if (readcount == 1) {
				if (wsem.semWait(wsem) == false) {
					wsem.semSignal(wsem);
					return 3;
				}
			}
		}
		
		// READUNIT
		int lasttime = threads.getLasttime();
		lasttime--;
		threads.setLasttime(lasttime);
		if (lasttime != 0) {// 还在执行中
			return 2;
		}

		readcount--;
		if (readcount == 0) {
			wsem.count = 1;
		}
		return 1;
	}

	/**
	 * 如果是在阻塞，就返回0，如果不阻塞，并且写完了，就返回1， 
	 * 不阻塞，正在写返回2
	 * 
	 * @param threads
	 * @return
	 */
	public int writer(Threads threads) {
		
		if (wsem.semWait(wsem)// 第一次
				|| threads.getFlag() == 2) {// 正在读
			// WRITEUNIT
			int lasttime = threads.getLasttime();
			lasttime--;
			writecount = 1;
			threads.setLasttime(lasttime);
			if (lasttime != 0) {// 还在执行中
				wsem.count=0;
				return 2;
			}
			writecount = 0;
			wsem.count=1;
			return 1;
		} else {
			wsem.semSignal(wsem);
			return 0;
		}

	}
}
