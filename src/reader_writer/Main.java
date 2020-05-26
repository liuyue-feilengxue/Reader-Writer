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
		List<Threads> threads = new ArrayList<Threads>();//�����
		
		ReadersAndWriters randw = new ReadersAndWriters();
		
		try {
            String encoding="GBK";
            File file=new File("./test.txt");
            if(file.isFile() && file.exists()){ //�ж��ļ��Ƿ����
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//���ǵ������ʽ
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
		int j=0;//������
		for (int i = 0; i<=30;i++) {
			System.out.println("ʱ�䣺"+i);
			
			for (j=0;j<threads.size();j++) {
				if (threads.get(j).getBegintime()<=i&&//�����ǰʱ����ڵ��ڿ�ʼʱ��
						threads.get(j).isWr()&&//��Ϊ����
						threads.get(j).getLasttime()>0) {//�����ǳ���ʱ�仹�Ǵ���0��δ��ɣ�
					if (threads.get(j).getBegintime()==i) {
						System.out.println("�߳�"+(j+1)+"��ʼ��ȡ");
						threads.get(j).setFlag(0);
					}
					threads.get(j).setFlag(randw.reader(threads.get(j)));  
					if (threads.get(j).getFlag() == 2) {
						//System.out.println("2");
					}
					else if(threads.get(j).getFlag() == 1) {
						System.out.println("�߳�"+(j+1)+"��ȡ���");
					}
					else if (threads.get(j).getFlag() == 0) {
						System.out.println("error");
					}
				}
				else if(threads.get(j).getBegintime()<=i&&//�����ǰʱ����ڵ��ڿ�ʼʱ��
						threads.get(j).isWr()==false&&//��д��
						threads.get(j).getLasttime()>0) {//�����ǳ���ʱ�仹�Ǵ���0��δ��ɣ�
					
					if (randw.readcount==0&&threads.get(j).getFlag()==0) {
						System.out.println("�߳�"+(j+1)+"��ʼд��");
						threads.get(j).setFlag(0);
					}
					threads.get(j).setFlag(randw.writer(threads.get(j)));
					System.out.println("flag:"+threads.get(j).getFlag());
					if (threads.get(j).getFlag() == 2) {
//						System.out.println("2");
					}
					else if(threads.get(j).getFlag() == 1) {
						System.out.println("�߳�"+(j+1)+"д�����");
					}
					else if (threads.get(j).getFlag() == 0) {
						//System.out.println("error");
					}
					
				}
			}
			
		}
	}
}
