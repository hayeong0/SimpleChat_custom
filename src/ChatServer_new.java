import java.net.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class ChatServer_new {

	public static void main(String[] args) {
		try{
			ServerSocket server = new ServerSocket(10001); 
			System.out.println("Waiting connection...");
			HashMap hm = new HashMap();	
			
			while(true){
				Socket sock = server.accept();	
				ChatThread chatthread = new ChatThread(sock, hm);
				chatthread.start();
			} // while
		}catch(Exception e){	
			System.out.println(e);
		}
	} // main
}

class ChatThread extends Thread{
	private Socket sock;	
	private String id;
	private BufferedReader br;
	private HashMap hm;
	private boolean initFlag = false;
	public ChatThread(Socket sock, HashMap hm){
		this.sock = sock;
		this.hm = hm;
		try{
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			id = br.readLine();
			
			broadcast(id + " entered.");
			System.out.println("[Server] User (" + id + ") entered.");
			synchronized(hm){	
				hm.put(this.id, pw);
			}
			initFlag = true;
		}catch(Exception ex){
			System.out.println(ex);
		}
	} // constructor
	
	public void run(){
		PrintWriter pw = null;
		try{
			String line = null;
			while((line = br.readLine()) != null){
				
				if(line.equals("/quit"))
					break;
				
				if(line.indexOf("/to ") == 0) 
					sendmsg(line);
				/*** customizing ***/
				//현재 접속한 사용자 목록 보기   
				if(line.equals("/userlist")) 
					send_userlist();
				//스레드가 실행되면서 else 메시지 들어올 경우 broadcast로 보내기 
				else {
					//금지어 경고 기능 추가 + broadcast로 보내지 않는
					if(line.contains("lol")) {
						avoid();
					}
					if(line.contains("BAD")) {
						avoid();
					}
					if(line.contains("OSS")) {
						avoid();
					}
					if(line.contains("/java")) {
						avoid();
					}
					if(line.contains("/test")) {
						avoid();
					}
					else broadcast(id + " : " + line);
				}//else
			}//while
		}catch(Exception ex){
			System.out.println(ex);
			
		}finally{ 
			synchronized(hm){
				hm.remove(id);
			}
			broadcast(id + " exited.");
			try{
				if(sock != null)
					sock.close();
			}catch(Exception ex){}
		}
	} // run
	
	public void sendmsg(String msg){
		long time = System.currentTimeMillis(); 
		SimpleDateFormat curtime = new SimpleDateFormat("[aaa] hh:mm");
		String str = curtime.format(new Date(time));

		int start = msg.indexOf(" ") +1; 
		int end = msg.indexOf(" ", start); 
		if(end != -1){
			String to = msg.substring(start, end);
			String msg2 = msg.substring(end+1);
			Object obj = hm.get(to);
			if(obj != null){
				PrintWriter pw = (PrintWriter)obj;
				pw.println(str);
				pw.println(id + " whisphered. : " + msg2);
				pw.flush();
			} // if
		}
	} // sendmsg
	
	public void broadcast(String msg){
		long time = System.currentTimeMillis(); 
		SimpleDateFormat curtime = new SimpleDateFormat("[aaa] hh:mm");
		String str = curtime.format(new Date(time));
		
		synchronized(hm){
			Set<String> set = hm.keySet();
			Iterator iterator = set.iterator();
			
			while(iterator.hasNext()){
				String key = (String)iterator.next();
				if(id != key) {
					PrintWriter pw = (PrintWriter)(hm.get(key));
					pw.println(str);
					pw.println(msg);
					pw.flush();
				}
			}
		}
	} // broadcast
	
	public void send_userlist() {
		PrintWriter pw = (PrintWriter)hm.get(id);
		pw.println(hm.keySet());
		pw.println("Total : " + hm.size());
		pw.flush();
	} //send_userlist
	
	public void avoid() {
		synchronized(hm){
			Set<String> set = hm.keySet();
			Iterator iterator = set.iterator();
			
			while(iterator.hasNext()){
				String key = (String)iterator.next();
				if(id == key) {
					PrintWriter pw = (PrintWriter)(hm.get(key));
					pw.println("Forbidden command!!");
					pw.flush();
				}
			}
		}
	}
}
