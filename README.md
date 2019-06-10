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
  java ChatClient
  > 실행시 username과 ip address 입력하도록 나옴
### Terminal #3
  java ChatClient
  > 실행시 username과 ip address 입력하도록 나옴

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

## Lab8: Customizing 4
- 서버에 등록되어 있는 금지어를 5번이상 입력할 시, 강제 퇴장 기능

# Result

## Lab5: Customizing 1
- 1. ChatClient 실행 구문 변경하기
> Client
<img src="https://user-images.githubusercontent.com/47182864/59143841-6167a800-8a0a-11e9-89ac-b7b4eb308564.png">
<img src="https://user-images.githubusercontent.com/47182864/59143842-63316b80-8a0a-11e9-8118-ac6cdeaa4d8e.png">

> Server
<img src="https://user-images.githubusercontent.com/47182864/59143845-6b89a680-8a0a-11e9-84e8-e2f52cb63290.png">

- 2. broadcast(), sendmsg()에서 클라이언트에게 보내는 메시지 앞부분에 현재시간을 보여주는 기능 추가
> broadcast()
<img src="https://user-images.githubusercontent.com/47182864/59143846-6c223d00-8a0a-11e9-9d20-4c161bf85626.png">

> sendmsg()
<img src="https://user-images.githubusercontent.com/47182864/59143847-6c223d00-8a0a-11e9-93d4-ebc8badd403c.png">

## Lab6: Customizing 2
- 1. 현재 접속한 사용자 목록 보기 기능
<img src="https://user-images.githubusercontent.com/47182864/59143853-75aba500-8a0a-11e9-8ccb-677ec6c8ca74.png">

- 2. 자신이 보낸 채팅 문장은 자신에게는 나타나지 않도록 할 것

> testuser2에서 입력
<img src="https://user-images.githubusercontent.com/47182864/59143854-75aba500-8a0a-11e9-9d84-bb54864289c9.png">

> testuser1에서만 출력됨
<img src="https://user-images.githubusercontent.com/47182864/59143855-75aba500-8a0a-11e9-8b65-1d514b4fa3ad.png">

- 3. 금지어 경고 기능
<img src="https://user-images.githubusercontent.com/47182864/59143856-76443b80-8a0a-11e9-8628-813be24c3084.png">

> 해당 단어 입력시 “Forbidden command!!” 경고
<img src="https://user-images.githubusercontent.com/47182864/59143857-76443b80-8a0a-11e9-88e9-9e072cb598df.png">

>	채팅 문장에 금지어가 포함되어 있으면 다른 사용자에게 전송하지 않고 해당 사용자에게만 적절한 경고 메시지
<img src="https://user-images.githubusercontent.com/47182864/59143858-76443b80-8a0a-11e9-9ada-cffc6f48ed6b.png">

## Lab7: Customizing 3
- 1. 클라이언트에서 '/spamlist' 를 입력하면 현재 서버에 등록된 금지어의 목록 출력 기능 구현 (미리 금지어가 등록되어 있을 필요 없음)
<img src="https://user-images.githubusercontent.com/47182864/59143861-7e9c7680-8a0a-11e9-9798-f606b0ed4ea6.png">

- 2. 클라이언트에서 '/addspam 단어'를 입력하면 해당 <단어>가 서버에 금지어로 추가되도록 하는 기능 구현
<img src="https://user-images.githubusercontent.com/47182864/59143862-7e9c7680-8a0a-11e9-8136-a394065f594d.png">
<img src="https://user-images.githubusercontent.com/47182864/59143863-7f350d00-8a0a-11e9-8aa3-99bb4228228c.png">

- 3. 금지어 파일 관리 기능 구현 - 서버를 시작하면 금지어 리스트는 특정 파일에서 불러오고, 서버가 종료되면 새로 추가된 금지어를 포함한 현재 리스트가 파일에 저장되도록 기능 구현
<img src="https://user-images.githubusercontent.com/47182864/59159096-a588a480-8aff-11e9-87a0-6296fe2f709d.png">

## Lab8: Customizing 4
- 1. 서버에 등록되어 있는 금지어를 5번이상 입력할 시, 강제 퇴장 기능
> testuser1에게 금지어를 5번 입력하여 차단당했다는 메시지 전송
<img src="https://user-images.githubusercontent.com/47182864/59159198-ea610b00-8b00-11e9-9e39-38422fae134a.png">

> testuser2(다른 사용자)에게 user가 나갔다는 메시지 전송
<img src="https://user-images.githubusercontent.com/47182864/59159200-ec2ace80-8b00-11e9-9cb0-e6617f090d89.png">

- 2. 클라이언트가 입력한 username이 중복될 경우, 중복되었다는 메시지 전해주고, 
중복되지 않은 아이디 입력할 때까지 받기
> 클라이언트 1 (choihayeong 이라는 username으로 들어옴. 중복 X)
<img src="https://user-images.githubusercontent.com/47182864/59203730-24521000-8bda-11e9-803e-7286509e2acc.png">

> 클라이언트 2 (hayeong 이라는 username으로 들어옴 . 중복 X)
<img src="https://user-images.githubusercontent.com/47182864/59203740-2916c400-8bda-11e9-98ba-d3575c02c718.png">

> 클라이언트 3 (hayeong이라는 이름으로 접속 시도 -> 이미 있다는 경고 -> 재입력 -> 접속)
<img src="https://user-images.githubusercontent.com/47182864/59203743-2caa4b00-8bda-11e9-8b95-c8e83496b2f2.png">

> 서버 화면 
<img src="https://user-images.githubusercontent.com/47182864/59203742-2c11b480-8bda-11e9-9bfb-3bfc2fe10f08.png">
