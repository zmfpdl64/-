# STANDER

 - http://www.stander-mcs.com
 - https://www.stander-mcs.herokuapp.com

# 기술 스택

## BackEnd
 - Jdk version: 11
 - Springboot version: '2.6.4'
 - Spring Data JPA : latest
 - MariaDB version: latest
 - Thymeleaf version: latest
 - Spring Security: version: 5

## Iot
 - Python version : 3.7
 - PigPio
 - cv2
 
<h1>프로젝트명: STANDER </h1>

<h2> 무인카페 시스템</h2>
<h3>무인카페 시스템 프로젝트를 진행하게 된 계기:</h3>
<h3>대학생이라 스터디 카페를 많이 이용하는데 이용하면서 불편한 점들이 있었다.</h3>
<h3>1. 퇴실할 때 사용자가 퇴실하기 버튼을 누르지 않는다면 시간을 날려버리는 경우가 존재합니다.
퇴실처리를 위하여 다시 카페를 가는 번거로움이 있습니다. 그래서 사용자가 사용을 다하고 퇴실을 눌러 퇴실을 할 수 있게 설계하였습니다.</h3>
<h3>2. 많은 스터디 카페가 자사 앱, 웹을 가지고 있지 않아 예약하는데 있어 번거로움이 존재합니다. </h3>
<h3>3. 앱, 웹을 같은 db를 연동하여 앱, 웹을 통합한 시스템을 구현하고 싶었습니다.</h3>
<br><br><br>
<hr>
<h2>무인 카페 시스템 원리</h2>

<h3>피지컬 아키텍처<h3>
    
![무인스터디카페_피지컬아키텍추](https://github.com/zmfpdl64/StudyCafe-AWS/assets/69797420/aa645ad6-7d6b-4ffc-a91d-2e39c3b91554)
  
  
<h3>웹, 앱</h3>
    <br>
     
 - 로그인
 - 회원가입
 - 예약하기
 - 결제하기
 - 마이페이지</h4>

<h3>IOT 파트 기능</h3>

 - 카메라 QR코드 인식 후 서버와 통신
 - Servo Motor Operate

![image](https://user-images.githubusercontent.com/69797420/170821953-00fb6f58-c560-4559-ab70-2354c46ab1e8.png)

<br><br>
<hr>

<h1> 무인 카페 시스템 메인 기능</h1>

<h3>1. 회원가입</h3>

![join](https://user-images.githubusercontent.com/69797420/170821105-ac4451cd-bbc1-42ca-a916-48cc8a11fc67.gif)


<br>
<h3>2. 로그인</h3>

![login_notime](https://user-images.githubusercontent.com/69797420/170821109-4c2132aa-c9da-4800-842c-78a116d5306e.gif)


<br>
<h3>3. 예약하기</h3>

![preserve](https://user-images.githubusercontent.com/69797420/170821111-60f42a37-bc4b-45e1-b04c-514631de6b8f.gif)


<br>
<h3>예약 전 마이 페이지</h3>
    
    
![GOMCAM 20220510_0154080891](https://user-images.githubusercontent.com/69797420/170823306-47ce36ac-7f7d-49ff-ad16-0e9c3c0dcac8.png)
    
    
<br><br>

<h3>예약 한 후 마이 페이지</h3>
    
    
![GOMCAM 20220507_2058420860](https://user-images.githubusercontent.com/69797420/170823324-a0336188-24d5-4ef9-a16c-9d0fa625ff22.png)

    
<br>

<br>
<h3>6. 예외 처리</h3>

![except_reserve](https://user-images.githubusercontent.com/69797420/170821103-3b416e1c-265d-425d-af32-cf8c566bbe96.gif)

<h3>IOT QR코드 동작</h3>

![프로젝트](https://github.com/user-attachments/assets/b2b6c4d2-ecef-4f29-ae98-c8606a381d7a)

QR코드를 카메라 모듈이 인식하면 Server에 API를 호출하여 정상적인 QR인지 확인후 출입문이 동작합니다.
 
## 겪었던 문제점
 
### Python의 싱글 스레드 처리
 
 Python에서 QR코드를 읽고 문을 열어주는 기능을 구현했어야 했습니다.
 qr코드를 인식하고 subo moter가 동작하는 동안에는 카메라가 이미지를 읽어오지 못했습니다.
 그래서 카메라를 읽어오고 subo motor가 동작할 때는 Thread를 생성해 동작하는 동안에도 프로그램이 멈추지 않게 문제를 해결했습니다.
 
 <details>
  <summary>코드보기/코드접기</summary>
  
  ```python
  
    import cv2  #이미지 처리를 위한 모듈이다
    from urllib .request import urlopen  #서버와 통신하기 위한 라이브러리
    import pigpio  #서브모터를 동작시키기 위한 라이브러리
    from time import sleep  #시간 관리하는 라이브러리
    import time
    import threading   #쓰레드 라이브러리

    start = time .time ()
    end = time .time ()
    pi = pigpio .pi()
    #while True:
    pi .set_servo_pulsewidth(18 ,0 )  #18번 핀을 이용한다
    sleep (1 )
    # initalize the cam
    cap = cv2 .VideoCapture(0 )    #카메라 모듈을 실시간 스트림으로 동작시킨다.
    # initialize the cv2 QRCode detector
    detector = cv2 .QRCodeDetector() #이미지를 디코딩한다
    while True :
       start = time .time () 
       _, img = cap .read()   #이미지를 읽어들인다.
       # detect and decode
       data , bbox , _ = detector .detectAndDecode(img )  #qr코드의 스트링을 읽어들인다.
       # check if there is a QRCode in the image
       if data and ((start - end )//1 >5 ):        #5초 동안 실행하지 않고 data가 존재할 때 실행된다.
         end = time .time ()
         try :
           response = urlopen (data ).read().decode('utf-8') #qr스트링을 읽어서 url로 이동한다.
           if response == "ok":   #해당 url로 이동했을 때 ok 신호가 왔을 때 실행된다.
             t = threading .Thread (target =servo , args =(500 , 1500 ))   #쓰레드를 생성한다. 
             t .start ()                        #쓰레드를 실행한다.
             print (response )
           else :
             print ("QR코드가 없습니다")
         except :
           response = "fail"
           print ('예외 발생')

       def servo (low , high ):    #문이 열리는 동작을 한다.
         for i in range (500 , 1500 ):
           pi .set_servo_pulsewidth(18 , i )
           sleep (0.0005 )
         sleep (5 )
         for i in range (1500 , 500 , -1 ):
           pi .set_servo_pulsewidth(18 , i )
           sleep (0.0005 )

         #break
       # display the result
       cv2 .imshow("QRCODEscanner", img )   #화면에 카메라 영상이 출력된다.
       if cv2 .waitKey(1 ) == ord ("q"):  #q를 누르면 프로그램이 종료된다.
         break

    cap .release()  
    cv2 .destroyAllWindows() #화면을 종료시킨다.
 ```
 
 </details>


