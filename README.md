# 🎁 GiftU-Back 🎁
### [EFUB 4기 sws 프로젝트] GiftU의 백엔드 레포지토리입니다.
![리드미 대표사진](https://github.com/user-attachments/assets/e23d2a0f-46c7-4c02-80a7-bfd12cf502fb)
<br><br>

## 🎀 서비스 설명
받고 싶은 선물을 직접 말하기 어려웠던 사람들과 매년 생일 선물 고민이 있었던 사람들을 위한 서비스입니다.<br>
고가의 선물이나 꼭 필요한 선물을 여러 친구들과 함께 펀딩하여 받을 수 있습니다. <br>
금액대별로 선물을 등록하여 펀딩을 개설하면 친구들이 펀딩에 참여합니다. <br>
펀딩에 성공하는 경우, 모인 금액에 맞는 선물이 배송됩니다. <br>
<br>

## 🗓️ 개발 기간
2024.06.29 ~ 2024.08.10
<br><br>

## ⚙️ 기술 스택
<div>
  <img alt="java" src="https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white" />
  <img alt="spring boot" src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat&logo=springboot&logoColor=white" />
  <img alt="jwt" src="https://img.shields.io/badge/JWT-282828?style=flat&logo=jsonwebtokens&logoColor=white" />
  <img alt="spring security" src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat&logo=springsecurity&logoColor=white" />
</div>
<div>
  <img alt="mysql" src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white" />
  <img alt="hibernate" src="https://img.shields.io/badge/Hibernate-59666C?style=flat&logo=hibernate&logoColor=white" />
</div>
<div>
  <img alt="nginx" src="https://img.shields.io/badge/NGINX-009639?style=flat&logo=nginx&logoColor=white" />
  <img alt="github actions" src="https://img.shields.io/badge/Github Actions-2088FF?style=flat&logo=githubactions&logoColor=white" />
</div>
<div>
  <img alt="EC2" src="https://img.shields.io/badge/AWS EC2-FF9900?style=flat&logo=amazonec2&logoColor=white" />
  <img alt="RDS" src="https://img.shields.io/badge/AWS RDS-527FFF?style=flat&logo=amazonrds&logoColor=white" />
  <img alt="S3" src="https://img.shields.io/badge/AWS S3-569A31?style=flat&logo=amazons3&logoColor=white" />
  <img alt="CodeDeploy" src="https://img.shields.io/badge/ AWS CodeDeploy-527FFF?style=flat&logo=Amazon AWS&logoColor=white"/>
</div>
<br>

## 🔧 시스템 구조
![image](https://github.com/user-attachments/assets/5a1a107e-3e2a-48b1-b728-ff4787d264c9)
<br>

## 📌 주요 기능 
- 카카오 소셜 로그인/회원가입 기능
- jwt 토큰(accessToken, refreshToken)을 이용한 사용자 인증
- 회원 조회, 정보 수정, 탈퇴 기능
- 펀딩 개설 및 조회
- 펀딩 검색 기능
- 펀딩 참여 및 조회
- 펀딩 종료 후 선물 후기 작성 기능 
- 친구 맺기 기능
- 친구 요청과 펀딩 마감 알림 기능
<br>
  
## ⭐ 팀원 소개 
|유효주|정지은|이여진|
|:------------:|:----------------:|:----------------:|
| ![image](https://github.com/user-attachments/assets/60fe7e6d-338f-40f6-9578-065f34ae199b) | ![image](https://github.com/user-attachments/assets/aa0a04a7-1952-4e71-a926-566e2f9a6885) |![image](https://github.com/user-attachments/assets/0905f04c-7175-4185-8a44-adce058f5a4f)|
|[@oohyj](https://github.com/oohyj)|[@stopsilver](https://github.com/stopsilver123)|[@yeojinLee1020](https://github.com/yeojinLee1020)|
|포트원 결제 API <br> 펀딩 상세,참여 API <br> 검색 API <br> 알림 API <br> 선물 후기 API|배포 - CI/CD (github actions), 도메인, 웹서버 설정 <br> 펀딩 개설 API <br> 친구 API <br> 사진 업로드 | 카카오 소셜 로그인/회원가입 API <br> 회원 API <br> 펀딩 리스트 API <br> 캘린더 API |
<br>

## 📜 ERD
![erd](https://github.com/user-attachments/assets/12eab85e-c946-4c9a-b24d-1006b15e20ef)
<br><br>

## ✏️ API 명세서   
### user, friend, funding, oauth(login), calender, review, notice, search, pay
<br>

![image](https://github.com/user-attachments/assets/43cc0b36-f809-4cd8-9c90-0ea8070462f8)
![image](https://github.com/user-attachments/assets/6ecefe92-7be0-4f58-9cbf-31cc6e5eed62)
![image](https://github.com/user-attachments/assets/25b828d0-0b7d-4c7a-97fe-1e56ef64657e)
![image](https://github.com/user-attachments/assets/2f341025-8772-48ed-ae1f-53841789f175)



