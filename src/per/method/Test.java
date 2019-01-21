package per.method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		try {
			Socket sock  = new Socket("39.108.97.64",9000);
			OutputStream os = sock.getOutputStream();
			Scanner scan = new Scanner(System.in);
			new Thread(new Test(). new Myclient(sock)).start();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while(true) {
				String msg = scan.nextLine();
				byte[] b = msg.getBytes();
				os.write(b);
				os.flush();
				System.out.println(df.format(new Date())+"  发送:"+msg);
			}
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	class Myclient implements Runnable{
		Socket sock ;
		public Myclient(Socket sock) {
			this.sock = sock;
		}

		@Override
		public void run() {
			InputStream is = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				is = sock.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr);
			String msg = null;
			try {
				while((msg = reader.readLine()) != null) {
					System.out.println(df.format(new Date())+" 收到："+msg);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
