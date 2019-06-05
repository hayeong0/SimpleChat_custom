# SimpleChat
Simple Chatting Program (java)

## Client Compile 방법
 javac ChatClient.java

## Server Compile 방법
 javac ChatServer.java

## Test 방법
### Terminal #1
  java ChatServer
### Terminal #2
  java ChatClient \<username1> \<server--ip-address>
### Terminal #3
  java ChatClient \<username2> \<server--ip-address>

## Lab5: Customizing 1
- 1. ChatClient 실행 구문 변경하기
- 2. broadcast(), sendmsg()에서 클라이언트에게 보내는 메시지 앞부분에 현재시간을 보여주는 기능 추가

## Lab6: Customizing 2
- 1. 현재 접속한 사용자 목록 보기 기능
- 2. 자신이 보낸 채팅 문장은 자신에게는 나타나지 않도록 할 것
- 3. 금지어 경고 기능

## Lab7: Customizing 3
- 1. 클라이언트에서 '/spamlist' 를 입력하면 현재 서버에 등록된 금지어의 목록 출력 기능 구현 (미리 금지어가 등록되어 있을 필요 없음)
- 2. 클라이언트에서 '/addspam 단어'를 입력하면 해당 <단어>가 서버에 금지어로 추가되도록 하는 기능 구현
- 3. 금지어 파일 관리 기능 구현 - 서버를 시작하면 금지어 리스트는 특정 파일에서 불러오고, 서버가 종료되면 새로 추가된 금지어를 포함한 현재 리스트가 파일에 저장되도록 기능 구현

> Q&A: 21400564@handong.edu || KakaoTalk open chat
