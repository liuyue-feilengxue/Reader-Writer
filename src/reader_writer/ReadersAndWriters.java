package reader_writer;

public class ReadersAndWriters {
	int readcount = 0;
	int writecount = 0;
	Semaphore wsem = new Semaphore(1);

	/**
	 * һ��ʼ��ʱ��flag��0����ɺ󷵻�1������ִ�з���2�����ڽ���д�߷���3
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
		if (lasttime != 0) {// ����ִ����
			return 2;
		}

		readcount--;
		if (readcount == 0) {
			wsem.count = 1;
		}
		return 1;
	}

	/**
	 * ��������������ͷ���0�����������������д���ˣ��ͷ���1�� 
	 * ������������д����2
	 * 
	 * @param threads
	 * @return
	 */
	public int writer(Threads threads) {
		
		if (wsem.semWait(wsem)// ��һ��
				|| threads.getFlag() == 2) {// ���ڶ�
			// WRITEUNIT
			int lasttime = threads.getLasttime();
			lasttime--;
			writecount = 1;
			threads.setLasttime(lasttime);
			if (lasttime != 0) {// ����ִ����
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
