import java.net.*;
import java.io.*;
import java.util.*;

public class ChatClient_new {
	static String username;
	static String ip;
	

	public static void main(String[] args) { 
		ChatClient_new chat = new ChatClient_new();
		chat.start();
	}//main
	

	public void start(){
		Socket sock = null;	
		BufferedReader br = null;	//파일 read
		PrintWriter pw = null;	//파일 write
		boolean endflag = false;	
		try{
			String args[] = null;
			//서버 접속
			sock = new Socket(ip, 10001);
			//서버로 전송을 위한 OutputStream
			pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			//서버로부터 데이터를 받기 위한 InputStream
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			//서버가 보낸 데이터 출력
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		
				System.out.println("Usage : java ChatClient");	
				Scanner sc = new Scanner(System.in);
				boolean done = true;
				String msg = "";
				
				while(done) {
					System.out.println("your name >> ");
					username = sc.nextLine();
					
					System.out.println("server ip >> ");
					ip = sc.nextLine(); 
					
					// send username
					pw.println(username);
					pw.flush();
					
					msg = br.readLine();
					System.out.println(msg);
					
					if(msg.equals("Already exist!")) done = true;
					else done = false;
				}
					
			//inputThread를 start한다
			InputThread it = new InputThread(sock, br);
			it.start();
			//키보드 입력이 null이 아닐때까지 읽어온다
			String line = null;
			while((line = keyboard.readLine()) != null){
				//send message  
				pw.println(line);
				pw.flush();
				
				//입력이 quit이면, while문을 빠져나간다
				if(line.equals("/quit")){
					endflag = true;
					break;
				}
			}
			//연결이 끊김 ~
			System.out.println("Connection closed.");
		//에러상황처리
		}catch(Exception ex){
			if(!endflag)
				System.out.println(ex);
		}finally{
			try{
				if(pw != null)
					pw.close();
			}catch(Exception ex){}
			try{
				if(br != null)
					br.close();
			}catch(Exception ex){}
			try{
				if(sock != null)
					sock.close();
			}catch(Exception ex){}
		} // finally
	} //main
}//class

class InputThread extends Thread{
	private Socket sock = null;
	private BufferedReader br = null;

	//InputThread Structor
	public InputThread(Socket sock, BufferedReader br){
		this.sock = sock;
		this.br = br;
	}
	public void run(){
		try{
            String line = null;
            while((line = br.readLine()) != null){
                    System.out.println(line);
            }
            
   
			
		}catch(Exception ex){
		}finally{
			try{
				if(br != null)
					br.close();
			}catch(Exception ex){}
			try{
				if(sock != null)
					sock.close();
			}catch(Exception ex){}
		}
	} // InputThread
	
	
}



