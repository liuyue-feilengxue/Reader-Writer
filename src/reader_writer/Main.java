package reader_writer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		List<Threads> threads = new ArrayList<Threads>();//存进程
		
		ReadersAndWriters randw = new ReadersAndWriters();
		
		try {
            String encoding="GBK";
            File file=new File("./test.txt");
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
//                    System.out.println(lineTxt);
                	String[] threadsStrings =lineTxt.split(" ");
                	
                	Threads threadstemp = new Threads();
                	threadstemp.setNumber(Integer.parseInt(threadsStrings[0]));
                	if (threadsStrings[1].equals("R")) {
                		threadstemp.setWr(true);
                	}else {
						threadstemp.setWr(false);
					}
                	threadstemp.setBegintime(Integer.parseInt(threadsStrings[2]));
                	threadstemp.setLasttime(Integer.parseInt(threadsStrings[3]));
                	threads.add(threadstemp);
                }
                read.close();
                }
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
		}
		int j=0;//检测队列
		for (int i = 0; i<=30;i++) {
			System.out.println("时间："+i);
			
			for (j=0;j<threads.size();j++) {
				if (threads.get(j).getBegintime()<=i&&//如果当前时间大于等于开始时间
						threads.get(j).isWr()&&//且为读者
						threads.get(j).getLasttime()>0) {//且他们持续时间还是大于0（未完成）
					if (threads.get(j).getBegintime()==i) {
						System.out.println("线程"+(j+1)+"开始读取");
						threads.get(j).setFlag(0);
					}
					threads.get(j).setFlag(randw.reader(threads.get(j)));  
					if (threads.get(j).getFlag() == 2) {
						//System.out.println("2");
					}
					else if(threads.get(j).getFlag() == 1) {
						System.out.println("线程"+(j+1)+"读取完毕");
					}
					else if (threads.get(j).getFlag() == 0) {
						System.out.println("error");
					}
				}
				else if(threads.get(j).getBegintime()<=i&&//如果当前时间大于等于开始时间
						threads.get(j).isWr()==false&&//是写者
						threads.get(j).getLasttime()>0) {//且他们持续时间还是大于0（未完成）
					
					if (randw.readcount==0&&threads.get(j).getFlag()==0) {
						System.out.println("线程"+(j+1)+"开始写入");
						threads.get(j).setFlag(0);
					}
					threads.get(j).setFlag(randw.writer(threads.get(j)));
					System.out.println("flag:"+threads.get(j).getFlag());
					if (threads.get(j).getFlag() == 2) {
//						System.out.println("2");
					}
					else if(threads.get(j).getFlag() == 1) {
						System.out.println("线程"+(j+1)+"写入完毕");
					}
					else if (threads.get(j).getFlag() == 0) {
						//System.out.println("error");
					}
					
				}
			}
			
		}
	}
}
