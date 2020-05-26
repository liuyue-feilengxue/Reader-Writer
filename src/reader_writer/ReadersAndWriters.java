package reader_writer;

public class ReadersAndWriters {
	int readcount = 0;
	Semaphore x = new Semaphore(1);
	Semaphore wsem = new Semaphore(1);

	/**
	 * ����������У���᷵��0����ɺ󷵻�1�����������ִ�з���2
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
		if (lasttime != 0) {// ����ִ����
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
	 * ��������������ͷ���0�����������������д���ˣ��ͷ���1�� ������������д����2
	 * 
	 * @param threads
	 * @return
	 */
	public int writer(Threads threads) {
		if (threads.getFlag() == 0) {
//			System.out.println(wsem.count);
			if (wsem.semWait(wsem)) {// ������
				// WRITEUNIT
				int lasttime = threads.getLasttime();
				lasttime--;
				threads.setLasttime(lasttime);
				if (lasttime != 0) {// ����ִ����
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
