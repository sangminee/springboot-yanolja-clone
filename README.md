# 야놀자 서버

<h3>1. 서버구축</h3>
- ec2 구축 <br>
- RDS 연결<br>  
- SSL 인증

<h3>2. ERD 설계</h3>
https://www.erdcloud.com/d/EDtBx2DH5YXPc8sLF

<h3>3. API 개발</h3>

|method    |      description          |                                 API                                        |
|----------|---------------------------|----------------------------------------------------------------------------|
|GET       |예약 - 취소 및 환불 상세 정보|/order-cancel/:userId/detail?bookingType=T&roomBookingId=20&userBookingId=34|
|GET       |예약 - 예약취소 요청 페이지|API /booking/time|
|POST       |대실 예약하기|/booking/day/time|
|GET       |대실 이용 시간 보여주기|/order-cancel/:userId/detail?bookingType=T&roomBookingId=20&userBookingId=34|
|GET       |취소 완료 영수증 조회 |/order-cancel/:userId/receipts/:cancelId|
|POST      |숙박 예약하기      |/booking/sleep|
|POST      | 숙박 예약하기    |/booking/sleep |
|POST      | 찜하기         |/like/:userId 
|PATCH     | 찜 삭제하기    |/like/delete?userId=1&likeId=1 
|POST      | 대실 장바구니 담기    | /cart/time 
|PATCH     | 장바구니 삭제 |/cart/:userId?roomCartType=T&roomCartId= 
|GET| 마이페이지 조회 |/mypage/:userId 
|GET| 예약 내역 조회  (전체)| /booking/:userId 
|GET| 찜하기 조회 | /like 
|GET| 장바구니 조회 | /cart /:userId  
|GET| 지역 검색 | /search?area=서울&userId=3&date=weekday
|GET| 최근 검색 기록 조회 | /search/list?userId=3 
|PATCH    | 최근 검색 기록 한 개 삭제 | /search/delete?userId=3&searchId=4 
|PATCH    | 최근 검색 기록 전체 삭제 | /search/delete/all?userId=3 
|GET| 예약내역 상세 API (하나만 조회) / 예약 확인서 조회  | /booking/:userId/bookingCheck?bookingType=S&roomBookingId=1 
|POST     | 예약 취소하기  | /order-cancel/:userId?bookingType=&roomBookingId= 
|POST     | 숙박 장바구니 담기 | /cart/sleep 
|POST     |(1:1 문의) 실시간 채팅 방 생성  | /chat  
|POST     | (1:1 문의) 실시간 채팅   | /ws/chat  



<h3>4. Postman 실행 </h3>
https://www.youtube.com/watch?v=jwLhU7ZiQ3I

