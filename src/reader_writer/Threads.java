package reader_writer;

public class Threads {
	private int number;
	private boolean wr;//RΪtrue
	private int begintime;
	private int lasttime;
	private int flag =0;//���ڼ�����ڶ���������
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public boolean isWr() {
		return wr;
	}
	public void setWr(boolean wr) {
		this.wr = wr;
	}
	public int getBegintime() {
		return begintime;
	}
	public void setBegintime(int begintime) {
		this.begintime = begintime;
	}
	public int getLasttime() {
		return lasttime;
	}
	public void setLasttime(int lasttime) {
		this.lasttime = lasttime;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
}
