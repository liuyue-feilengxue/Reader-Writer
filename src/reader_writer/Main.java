package reader_writer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/**
 * 
 * @author ���·���ѩ
 *�����������ģ�����д�����⣬û���õ����̵߳ȷ����������ο�
 *�������ò�����ҵ������~
 */
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
                	
                	String[] threadsStrings =lineTxt.split(" ");//
                	
                	Threads threadstemp = new Threads();
                	//�ڼ������̣�����û��ʲô�ã�����������i�Ϳ����ˣ�
                	threadstemp.setNumber(Integer.parseInt(threadsStrings[0]));
                	//���߻���д��
                	if (threadsStrings[1].equals("R")) {
                		threadstemp.setWr(true);
                	}else {
						threadstemp.setWr(false);
					}
                	//��ʼʱ��
                	threadstemp.setBegintime(Integer.parseInt(threadsStrings[2]));
                	//����ʱ��
                	threadstemp.setLstime(Integer.parseInt(threadsStrings[3]));
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
		//�������
		
		int j=0;//������
		for (int i = 0; i<=30;i++) {
			System.out.println("ʱ�䣺"+i);
			
			for (j=0;j<threads.size();j++) {
				if (threads.get(j).getBegintime()<=i&&//�����ǰʱ����ڵ��ڿ�ʼʱ��
						threads.get(j).isWr()&&//��Ϊ����
						threads.get(j).getLasttime()>=0&&//�����ǳ���ʱ�仹�Ǵ���0��δ��ɣ�
						threads.get(j).isOver()==false) {//����û����
					if (randw.writecount>0) {
						continue;
					}
					threads.get(j).setFlag(randw.reader(threads.get(j))); 
//					System.out.println(threads.get(j).getNumber()+"  "+threads.get(j).getFlag());
					if (threads.get(j).getLstime() - threads.get(j).getLasttime()==1) {
						System.out.println("�߳�"+threads.get(j).getNumber()+"��ʼ��ȡ");
					}
					 
					if (threads.get(j).getFlag() == 2) {
						
					}
					else if(threads.get(j).getFlag() == 1) {
						System.out.println("�߳�"+threads.get(j).getNumber()+"��ȡ���");
						threads.get(j).setOver(true);
					}
					else if (threads.get(j).getFlag() == 0) {
						System.out.println(threads.get(j).getNumber()+"error");
					}
					else if(threads.get(j).getFlag() == 3) {
//						threads.get(j).setFlag(3);
					}
				}
				else if(threads.get(j).getBegintime()<=i&&//�����ǰʱ����ڵ��ڿ�ʼʱ��
						threads.get(j).isWr()==false&&//��д��
						threads.get(j).getLasttime()>=0&&//�����ǳ���ʱ�仹�Ǵ���0��δ��ɣ�
						threads.get(j).isOver()==false) {//ûд��
					if (randw.readcount>0) {
						continue;
					}
					if (randw.readcount==0&&threads.get(j).getFlag()==0) {
						System.out.println("�߳�"+threads.get(j).getNumber()+"��ʼд��");
						
					}
					threads.get(j).setFlag(randw.writer(threads.get(j)));
					
					if (threads.get(j).getFlag() == 2) {
//						
					}
					else if(threads.get(j).getFlag() == 1) {
						System.out.println("�߳�"+threads.get(j).getNumber()+"д�����");
						threads.get(j).setOver(true);
					}
					else if (threads.get(j).getFlag() == 0) {
						//System.out.println("error");
					}
					
				}//else if
			}//for�ڲ�
			
		}//for �ⲿ
	}
}
