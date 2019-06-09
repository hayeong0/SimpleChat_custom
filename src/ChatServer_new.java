import java.net.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class ChatServer_new {
    	public static void main(String[] args) {
		try{
			//ServerSocket 인스턴스 생성, 10001포트로 설정  
			ServerSocket server = new ServerSocket(10001); 
			System.out.println("Waiting connection...");

			/* 클라이언트 보낸 문자열을 접속한 모든 클라이언트에게 전송하기 위해  
			   스레드 간 OutputStream(송신)을 공유하기 위한 HashMap */
			HashMap hm = new HashMap();
			HashSet <String> set = new HashSet <String> ();
			/* 서버 시작하면 금지어 특정 파일에서 불러오기 */
			read_file(set);

			while(true){
				//클라이언트의 접속을 확인하고, 소켓 인스턴스 생성  
				Socket sock = server.accept();	
				//서버 프로그램의 스레드인 chatThread 생성  
				ChatThread chatthread = new ChatThread(sock, hm, set);
				chatthread.start();
			}
		}catch(Exception e){	
			System.out.println(e);
		}
	} // main
    	
    /*특정 파일에서 읽어오기  */
	public static void read_file(HashSet<String> spamset) {
		
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader("spamList.txt"));
			String line;
			
			while ((line = br.readLine()) != null) {
				spamset.add(line);
			}
			
			br.close();
		}catch(IOException e) {
			System.err.println(e);
		}finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {}
			}
	}//read_file
}

/* ChatThrad > Server의 일을 대신 해줌. 
   Server는 while문을 통해 접속하는 클라이언트를 받아주는 역할,
   동시에 server의 일을 대신해줄 수 있는 스레드 생성하여, 역할 위임 */

class ChatThread extends Thread{
	private Socket sock;	//클라이언트와 통신하기 위한 socket 
	private String id;
	private BufferedReader br;
	int ban_count = 0;
	private HashMap hm;	
	private boolean initFlag = false;
	private static HashSet<String> spamset = new HashSet<String>();
	
	public ChatThread(Socket sock, HashMap hm, HashSet<String> set){
		this.sock = sock;
		this.hm = hm;
		/* 파일에서 읽어온 데이터 넣어주기 */
		for (String it : set) {
			spamset.add(it);
		}
		try{ 
			//출력 스트림 객체를 리턴하는 PrintWriter 생성
			//클라이언트로 메세지를 보낼 수 있음
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			//클라이언트로부터 데이터를 읽어오기 위함  
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			id = br.readLine();
			
			broadcast(id + " entered.");
			System.out.println("[Server] User (" + id + ") entered.");
			
			//여러 스레드가 공유하는 해쉬 맵 동기화  
			synchronized(hm){	
				/* 사용자의 아이디를 key로, 출력 스트림을 value로 저장함
				  모든 클라이언트에 의해 공유된 메시지 broadcast하기 위해 출력 스트림을 해쉬 맵에 저장 */
				hm.put(this.id, pw);
			}
			initFlag = true;
		}catch(Exception ex){
			System.out.println(ex);
		}
	} // constructor

	/* 클라이언트로부터 수신받은  데이터를 클라이언트에게 송신 */
	public void run(){
		PrintWriter pw = null;
		try{
			
			String line = null; 
			while((line = br.readLine()) != null){
				
				/* 나가기 기능 */
				if(line.equals("/quit"))
					break;
				
				/* 금지어 필터링 (Lab7) */
				if(forbidden_check(line)) {
					pw = (PrintWriter)hm.get(id);
					pw.println("Forbidden command!!");
					pw.flush();
					ban_count++;
					
					/*금지어를 5번 입력하면 강제 퇴장 */
					if(ban_count == 4) {
						pw.println("You entered the prohibited word 5 times.");
						pw.println("== Block ==");
						pw.flush();
						pw.close();
					}
				}
				/* 귓속말 기능 */
				else if(line.indexOf("/to ") == 0) {
					sendmsg(line);
				}
				
				/* 현재 접속한 사용자 목록 보여주기 */
				else if(line.equals("/userlist")) {
					send_userlist();
				}
				
				/* 금지어 추가 기능 */
				else if(line.indexOf("/addspam ") == 0) {
					add_spam(line);
				}

				/* 금지어 필터링 기능 (Lab6) */
				else if(forbidden(line)) {
					pw = (PrintWriter)hm.get(id);
					pw.println("Forbidden command!!");
					pw.flush();
                }
				
				/* 금지어 목록 출력 기능 */
				else if(line.equals("/spamlist")) {
					print_spamlist();
				}
				/* broadcasting */
				else {
					broadcast(id + " : " + line);
				}
			}
		}catch(Exception ex){
			System.out.println(ex);
		}finally{ //클라이언트부터 quit이라는 종료 메시지 수신  
			synchronized(hm){	//HashMap 동기화  
				hm.remove(id);	//종료 메시지 보낸 클라이언트의 정보 HashMap에서 remove
			}
			broadcast(id + " exited.");	//broadcast로 접속 종료 알림  
			System.out.println(id + " exited."); //server에도 출력  
			try{
				if(sock != null)
					sock.close();	//나간 클라이언트 객체 close  
			}catch(Exception ex){}
		}

	} // run
	
	/* 귓속말 기능 */
	public void sendmsg(String msg){
		String time = curTime();	//current time 
		int start = msg.indexOf(" ") +1; 	//처음 공백 문자 다음부터  
		int end = msg.indexOf(" ", start); 	//두번째 공백 문자 전까지 

		if(end != -1){ 
			String to = msg.substring(start, end);	//ID
			String msg2 = msg.substring(end+1);	//message
			Object obj = hm.get(to);	//HashMap에서 ID로 출력 스트림을 얻어옴 
			if(obj != null){
				PrintWriter pw = (PrintWriter)obj;
				pw.println(time);
				pw.println(id + " whisphered : " + msg2);
				pw.flush();
			}
		}
	} // sendmsg

	/* Broadcasting */
	public void broadcast(String msg){
		String time = curTime();

		synchronized(hm){
			Set<String> set = hm.keySet();
			Iterator<String> iterator = set.iterator();

			while(iterator.hasNext()){
				String key = (String)iterator.next();
				if(id != key) {
					PrintWriter pw = (PrintWriter)(hm.get(key));
					pw.println(time);
					pw.println(msg);
					pw.flush();
				}
			}
		}
	} // broadcast

	/* 현재 시각 출력 기능  */
	public String curTime() {
		long time = System.currentTimeMillis(); 
		SimpleDateFormat curtime = new SimpleDateFormat("[aaa] hh:mm");
		String str = curtime.format(new Date(time));
		return str;
	}//curtime

	/* 금지어 경고 기능 */ 
	public boolean forbidden(String msg) {
		if(msg.contains("oss")) return true;
		if(msg.contains("carrot")) return true;
		if(msg.contains("mango")) return true;
		if(msg.contains("java")) return true;
		if(msg.contains("bad")) return true;

		else return false;
	}//forbidden
	
	/* 금지어 경고 기능 (Lab 7) */
	public boolean forbidden_check(String msg) {
		for(String str : spamset) {
			if(msg.contains(str)) return true;
		}
		return false;
	}
	
	/* 사용자 목록 보여주기 */
	public void send_userlist() {
		PrintWriter pw = (PrintWriter)hm.get(id);
		pw.println(hm.keySet());
		pw.println("Total : " + hm.size());
		pw.flush();
	} //send_userlist
	
	/* 금지어 등록 기능 */
	public void add_spam(String msg) {
		int word = msg.indexOf(" ") + 1;

		String spam = msg.substring(word);
		spamset.add(spam);
		
		save_spamlist(spam);
		
		PrintWriter pw = (PrintWriter)hm.get(id);
		pw.println("Resigster forbidden word");
		pw.flush();
		
	}//add_spam
	
	/* 금지어 목록 출력 기능 */ 
	public void print_spamlist() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("spamList.txt"));
			
			for(String x : spamset) {
				PrintWriter pw = (PrintWriter)hm.get(id);
				pw.println(x);
				pw.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {}
		}
	}//print_spamlist
	
	/* 금지어 파일 관리 기능 */
	public void save_spamlist(String spam) { 
		BufferedWriter fbw = null;
		System.out.println("Save SpamList");
		try {
			fbw = new BufferedWriter(new FileWriter("spamList.txt", true));
			fbw.write(spam);;
			fbw.newLine();
			fbw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fbw != null)
				try {
					fbw.close();
				} catch (IOException e) {}
		}
	}//sava_spamlist
}

	
