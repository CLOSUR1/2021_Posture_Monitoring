package fwd;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;

public class Mysever {
	 private static String savefile = "C:\\Users\\admin\\Desktop\\��������\\test.txt";

	 public static void main(String[] args) throws IOException, InterruptedException{
		int a=0,python=0;
		Process pr;
		 File file1 = new File("C:\\Users\\admin\\Desktop\\����������\\result.txt");
		 BufferedReader br = new BufferedReader(new FileReader(file1));// ����һ��BufferedReader������ȡ�ļ�
	     String s = null;
		 
		//��������˶��󣬰�4574�˿ڡ�
		 ServerSocket serv =new ServerSocket(4574);
		 System.out.println("�����������ɹ����ȴ��û�����");
		 int sign=0,sign1=0;
		 Socket sc=serv.accept();
		 	 InputStream in=sc.getInputStream();
		 //��Socket�еõ�����������������ݷ��͵�������
		 	 DataOutputStream out;
		     out = new DataOutputStream(sc.getOutputStream());
		// OutputStream out=sc.getOutputStream();
		 System.out.println("�пͻ��˽��룬�ͻ���ip:"+sc.getInetAddress());
		 //Time.sleep(1000);
		 System.out.println("����ѵ��׼����ʼ");
		 for(;;) {
		 //��Socket�еõ������������������������������
		 //���տͻ��˷���������
		 byte[] bs=new byte[1024];
		 //�����ݴ���bs�����У�����ֵΪ����ĳ���
		 int len=in.read(bs);
		 String str=new String(bs,4,len-2);
		 String start="start";
		 String stop="stop";
		 String close="close";
		 System.out.println("���Կͻ��˵���Ϣ:"+str);
		 System.out.println(str);
		 if(start.equals(str)) {
			 System.out.println("���ݴ洢��ʼ");
			 sign=1;
		 }
		 if(stop.indexOf(str)!=-1) {
			 System.out.println("���ݴ洢��ʼ");
			 sign=0;
		 }
		 if(sign==1) {sign1++;}
		 out.writeUTF(str);
		 if(str.indexOf(stop)!=-1) {
	
			 out.writeUTF("����ֹͣ��"); 
			 
		 }
		 out.flush();
		 System.out.println("�������������");
		 FileWriter fwriter = null;
	     try {
	    	 if(sign==1&&sign1>2) {
	    		 python=1;
	    		 System.out.println("�Ѵ���");
	     fwriter = new FileWriter(savefile,true);}
	    	 else {fwriter = new FileWriter(savefile,false);System.out.println("����ˢ��");}
	     fwriter.write(str+"\n");
	     } catch (IOException ex) {
	     ex.printStackTrace();
	     } finally {
	     try {
	     fwriter.flush();
	     fwriter.close();
	     } catch (IOException ex) {
	     ex.printStackTrace();
	     }
	     }
		if(python==1) {
			pr = Runtime.getRuntime().exec("E:\\conda1\\python.exe C:/Users/admin/Desktop/����������/svm_train.py");
			if(pr.waitFor()==1) {
				System.out.println("ѵ������");
				python=2;
			}
		}
		while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
			out.writeUTF(s); 
			}
			br.close();
	
		 if(close.equals(str)) {sc.close();	break;}
		
	}
		 
	
	}
}

