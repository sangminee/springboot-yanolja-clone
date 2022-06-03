# 야놀자 서버

<h3>1. 서버구축</h3>
- ec2 구축 <br>
- RDS 연결<br>  
- SSL 인증

<h3>2. ERD 설계</h3>
https://www.erdcloud.com/d/EDtBx2DH5YXPc8sLF

<h3>3. API 개발</h3>
[GET] /order-cancel/:userId/detail?bookingType=T&roomBookingId=20&userBookingId=34 ((예약 - 취소 및 환불 상세 정보))<br>
[GET] /order-cancel/:userId?bookingType=&roomBookingId= ((예약 - 예약취소 요청 페이지 API))<br>
[POST] /booking/time ((대실 예약하기 API))<br>
[GET] /booking/day/time ((대실 이용 시간 보여주기 API))<br>
[GET] /order-cancel/:userId/receipts/:cancelId ((취소 완료 영수증 조회))<br>
[POST] /booking/sleep ((숙박 예약하기 API))<br>
[POST] /like/:userId ((찜하기 API))<br>
[PATCH] /like/delete?userId=1&likeId=1 ((찜 삭제하기  API))<br>
[POST] /cart/time ((대실 장바구니 담기 API))<br>
[PATCH] /cart/:userId?roomCartType=T&roomCartId= ((장바구니 삭제 API))<br>
[GET] /mypage/:userId ((마이페이지 조회 API))<br>
[GET] /booking/:userId ((예약 내역 조회 API (전체)))<br>
[GET] /like ((찜하기 조회 API))<br>
[GET] /cart /:userId ((장바구니 조회 API)) <br>
[GET] /search?area=서울&userId=3&date=weekday ((지역 검색 API))<br>
[GET] /search/list?userId=3 ((최근 검색 기록 조회 API))<br>
[PATCH] /search/delete?userId=3&searchId=4 ((최근 검색 기록 한 개 삭제 API))<br>
[PATCH] /search/delete/all?userId=3 ((최근 검색 기록 전체 삭제 API))<br>
[GET] /booking/:userId/bookingCheck?bookingType=S&roomBookingId=1 ((예약내역 상세 API (하나만 조회) / 예약 확인서 조회 API))<br>
[POST] /order-cancel/:userId?bookingType=&roomBookingId= ((예약 취소하기 API))<br>
[POST] /cart/sleep ((숙박 장바구니 담기 API))<br>
[POST] /chat   ((1:1 문의) 실시간 채팅 방 생성))  <br>
[POST] /ws/chat  ((1:1 문의) 실시간 채팅)) 

<h3>4. Postman 실행 </h3>
https://www.youtube.com/watch?v=jwLhU7ZiQ3I

