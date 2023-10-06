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
	 private static String savefile = "C:\\Users\\admin\\Desktop\\肌电数据\\test.txt";

	 public static void main(String[] args) throws IOException, InterruptedException{
		int a=0,python=0;
		Process pr;
		 File file1 = new File("C:\\Users\\admin\\Desktop\\背背佳数据\\result.txt");
		 BufferedReader br = new BufferedReader(new FileReader(file1));// 构造一个BufferedReader类来读取文件
	     String s = null;
		 
		//建立服务端对象，绑定4574端口。
		 ServerSocket serv =new ServerSocket(4574);
		 System.out.println("服务器启动成功，等待用户接入");
		 int sign=0,sign1=0;
		 Socket sc=serv.accept();
		 	 InputStream in=sc.getInputStream();
		 //从Socket中得到网络输出流，将数据发送到网络上
		 	 DataOutputStream out;
		     out = new DataOutputStream(sc.getOutputStream());
		// OutputStream out=sc.getOutputStream();
		 System.out.println("有客户端接入，客户端ip:"+sc.getInetAddress());
		 //Time.sleep(1000);
		 System.out.println("数据训练准备开始");
		 for(;;) {
		 //从Socket中得到网络输入流，接收来自网络的数据
		 //接收客户端发来的数据
		 byte[] bs=new byte[1024];
		 //将数据存入bs数组中，返回值为数组的长度
		 int len=in.read(bs);
		 String str=new String(bs,4,len-2);
		 String start="start";
		 String stop="stop";
		 String close="close";
		 System.out.println("来自客户端的消息:"+str);
		 System.out.println(str);
		 if(start.equals(str)) {
			 System.out.println("数据存储开始");
			 sign=1;
		 }
		 if(stop.indexOf(str)!=-1) {
			 System.out.println("数据存储开始");
			 sign=0;
		 }
		 if(sign==1) {sign1++;}
		 out.writeUTF(str);
		 if(str.indexOf(stop)!=-1) {
	
			 out.writeUTF("进程停止了"); 
			 
		 }
		 out.flush();
		 System.out.println("服务端正常结束");
		 FileWriter fwriter = null;
	     try {
	    	 if(sign==1&&sign1>2) {
	    		 python=1;
	    		 System.out.println("已存入");
	     fwriter = new FileWriter(savefile,true);}
	    	 else {fwriter = new FileWriter(savefile,false);System.out.println("数据刷新");}
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
			pr = Runtime.getRuntime().exec("E:\\conda1\\python.exe C:/Users/admin/Desktop/背背佳数据/svm_train.py");
			if(pr.waitFor()==1) {
				System.out.println("训练结束");
				python=2;
			}
		}
		while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
			out.writeUTF(s); 
			}
			br.close();
	
		 if(close.equals(str)) {sc.close();	break;}
		
	}
		 
	
	}
}

