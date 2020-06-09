package reader_writer;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import com.sun.org.apache.bcel.internal.generic.IfInstruction;
/**
 * 
 * @author 六月飞冷雪
 * 本代码仅仅是模拟读者写者问题，没有用到多线程等方法，仅供参考
 * 觉得有用不如给我点个星呗~
 */
public class Main {
	
	public static void main(String[] args) throws Exception {
		List<Threads> threads = new ArrayList<Threads>();//存进程
		
		ReadersAndWriters randw = new ReadersAndWriters();
		System.out.println("自动读取目录下“线程.txt”文件");
		System.out.print("如果确认，请输入回车，如果退出请输入“0”");
		while(true) {
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			String str = bf.readLine();
			if (str.length()==0) {
				break;
			}
			if (str.charAt(0)=='0') {
				return;
			}
		}
		try {
            String encoding="GBK";
            File file=new File("./线程.txt");
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	
                	String[] threadsStrings =lineTxt.split(" ");//
                	
                	Threads threadstemp = new Threads();
                	//第几个进程
                	threadstemp.setNumber(Integer.parseInt(threadsStrings[0]));
                	//读者还是写者
                	if (threadsStrings[1].equals("R")) {
                		threadstemp.setWr(true);
                	}else {
						threadstemp.setWr(false);
					}
                	//开始时间
                	threadstemp.setBegintime(Integer.parseInt(threadsStrings[2]));
                	//持续时间
                	threadstemp.setLstime(Integer.parseInt(threadsStrings[3]));
                	threadstemp.setLasttime(Integer.parseInt(threadsStrings[3]));
                	threads.add(threadstemp);
                	System.out.println("线程"+threadstemp.getNumber()+"创建完成");
                }
                read.close();
                }
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
		}
		//导入完成
		
		int j=0;//检测队列
		for (int i = 0; i<=30;i++) {
			System.out.println("时间："+i);
			
			for (j=0;j<threads.size();j++) {
				if(threads.get(j).getBegintime()==i) {
					//读者
					if (threads.get(j).isWr()) {
						System.out.println("线程"+threads.get(j).getNumber()+"发出读申请");
					}
					else {
						System.out.println("线程"+threads.get(j).getNumber()+"发出写申请");
					}
				}
				if (threads.get(j).getBegintime()<=i&&//如果当前时间大于等于开始时间
						threads.get(j).isWr()&&//且为读者
						threads.get(j).getLasttime()>=0&&//且他们持续时间还是大于0（未完成）
						threads.get(j).isOver()==false) {//他们没读完
					if (randw.writecount>0) {
						continue;
					}
					threads.get(j).setFlag(randw.reader(threads.get(j))); 
//					System.out.println(threads.get(j).getNumber()+"  "+threads.get(j).getFlag());
					
					if (threads.get(j).getLstime() - threads.get(j).getLasttime()==1) {
						System.out.println("线程"+threads.get(j).getNumber()+"开始读取");
					}
					 
					if(threads.get(j).getFlag() == 1) {
						System.out.println("线程"+threads.get(j).getNumber()+"读取完毕");
						threads.get(j).setOver(true);
					}
					else if (threads.get(j).getFlag() == 0) {
						System.out.println(threads.get(j).getNumber()+"error");
					}
				}
				else if(threads.get(j).getBegintime()<=i&&//如果当前时间大于等于开始时间
						threads.get(j).isWr()==false&&//是写者
						threads.get(j).getLasttime()>=0&&//且他们持续时间还是大于0（未完成）
						threads.get(j).isOver()==false) {//没写完
					if (randw.readcount>0||(randw.writecount==1&&threads.get(j).getFlag()==0)) {
						continue;
					}
					if (randw.readcount==0&&threads.get(j).getFlag()==0) {
						System.out.println("线程"+threads.get(j).getNumber()+"开始写入");
					}
					threads.get(j).setFlag(randw.writer(threads.get(j)));
					
					if(threads.get(j).getFlag() == 1) {
						System.out.println("线程"+threads.get(j).getNumber()+"写入完毕");
						threads.get(j).setOver(true);
					}
					else if (threads.get(j).getFlag() == 0) {
						System.out.println("error");
					}
					
				}//else if
			}//for内部
			System.out.println();
			//结束进程
			boolean flag = false;
			for (j=0;j<threads.size();j++) {
				if (threads.get(j).isOver()==false) {//还有没做完的
					flag = false;
					break;
				}
				else {
					flag = true;
				}
			}
			if (flag) {//如果都做完了
				break;
			}
			//延迟一秒
			try {
				Robot  r   =   new   Robot();
				r.delay(1000);
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}//for 外部
	}
}
