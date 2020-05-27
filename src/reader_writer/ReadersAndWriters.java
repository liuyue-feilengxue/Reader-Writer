package reader_writer;

public class ReadersAndWriters {
	int readcount = 0;
	int writecount = 0;
	Semaphore x = new Semaphore(1);
	Semaphore wsem = new Semaphore(1);

	/**
	 * ����������У���᷵��0����ɺ󷵻�1�����������ִ�з���2
	 * ����3������д
	 * @param threads
	 * @return
	 */
	public int reader(Threads threads) {

		if (threads.getFlag() == 0) {
			if (x.semWait(x)) {
				readcount++;
				if (readcount == 1) {
					wsem.semWait(wsem);
//					if(wsem.semWait(wsem)==false) {
//						x.semSignal(x);
//						return 3;
//					}
				}
			} else {
				return 0;
			}
			x.semSignal(x);
		}
		
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
		//System.out.println(writecount);
		if (wsem.semWait(wsem)//��һ��
				||threads.getFlag()==2) {//���ڶ�
			// WRITEUNIT
			
			int lasttime = threads.getLasttime();
			lasttime--;
			
			threads.setLasttime(lasttime);
			if (lasttime != 0) {// ����ִ����
				return 2;
			}
			//writecount --;
			//wsem.semSignal(wsem);//�⿪if����wait
			wsem.semSignal(wsem);//�⿪�������ź���
			return 1;
		} else {
			wsem.semSignal(wsem);
			return 0;
		}
		
	}
}
