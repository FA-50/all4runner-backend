# All 4 Runner

> 서울 시내에서 방해물과 경사를 최소화한 최적 러닝 코스를 추천하는 웹 기반 지도 애플리케이션

All 4 Runner는 사용자가 선택한 출발지와 도착지를 기준으로 서울시 보행 네트워크, 경사도, 편의시설 데이터를 활용해 러닝에 적합한 경로를 추천하는 졸업작품 프로젝트입니다. 공공 공간데이터를 전처리하고 PostGIS/pgRouting 기반 경로 탐색 API를 구성하여, 지도 위에서 러닝 코스를 시각적으로 확인할 수 있도록 설계했습니다.

본 저장소는 All 4 Runner의 Spring Boot 백엔드 애플리케이션 저장소입니다.

## 프로젝트 정보

- 프로젝트명: All 4 Runner
- 주제: 최적 러닝코스를 추천하는 웹 기반 지도 애플리케이션
- 소속: 서울시립대학교 공간정보공학과
- 행사: 제39회 도시과학대학 공동작품전
- 기간: 2025.03 ~ 2025.06
- 목표: 서울 시내에서 방해물을 최소화하고 경사를 고려한 러닝 경로 추천 서비스 개발

## 주요 기능

- 출발지/도착지 기반 러닝 경로 탐색
- 경사도와 보행 네트워크를 고려한 경로 비용 산정
- PostGIS 및 pgRouting 기반 최단/최적 경로 안내
- OpenLayers 기반 지도 및 추천 경로 시각화
- JWT 기반 회원 인증 및 로그인
- REST API 설계 및 Swagger 기반 API 문서화
- 단위 테스트와 통합 테스트를 통한 배포 전 오류 검증

## 주요 성과

- 국토교통부, 서울 열린데이터 광장, VWorld 데이터를 활용한 공간데이터 처리 파이프라인 구성
- QGIS와 R을 활용해 DEM, 보행 네트워크, 편의시설 데이터를 전처리
- PostGIS extension을 활용해 공간 질의와 경로 안내 기능 구현
- 경로 도메인 중심으로 Controller, Service, Repository 계층 설계
- Spring Security와 JWT를 활용한 인증 구조 구현
- GitHub Issue/PR 기반 협업과 GitHub Actions 기반 CI 흐름 구성

## 기술 스택

### Frontend

- React
- OpenLayers
- VWorld WMTS/Search API

### Backend

- Java 17
- Spring Boot 3.5.x
- Spring Data JPA
- QueryDSL
- Spring Security
- JWT
- PostgreSQL
- PostGIS
- pgRouting
- GeoServer

### GIS/Data

- QGIS
- R
- DEM
- 서울시 보행 네트워크
- 공공화장실/음수대 위치정보

### Tool / CI/CD

- Docker
- Swagger
- Postman
- ERDCloud
- GitHub Issue / Pull Request
- GitHub Actions

## 시스템 구조

```text
Client(React, OpenLayers)
        |
        | REST API
        v
Spring Boot Backend
        |
        | JPA / Spatial Query / Routing Query
        v
PostgreSQL + PostGIS + pgRouting
        |
        | Spatial Layer / Tile / GIS Data
        v
GeoServer / VWorld API / Public GIS Data
```

## 데이터 처리 흐름

```text
공공데이터 수집
   -> QGIS/R 기반 좌표계 정리 및 공간데이터 전처리
   -> DEM 기반 경사도 산정
   -> 보행 네트워크와 장애/편의 요소 결합
   -> PostgreSQL(PostGIS) 적재
   -> pgRouting 기반 경로 탐색
   -> REST API 응답
   -> OpenLayers 지도 시각화
```

## Backend 실행 방법

### 요구사항

- Java 17
- PostgreSQL
- PostGIS extension
- pgRouting extension
- Gradle Wrapper

### 실행

```bash
./gradlew bootRun
```

Windows 환경에서는 다음 명령을 사용할 수 있습니다.

```bash
gradlew.bat bootRun
```

### 테스트

```bash
./gradlew test
```

Windows 환경에서는 다음 명령을 사용할 수 있습니다.

```bash
gradlew.bat test
```

## 현재 저장소 구성

```text
all4runner-backend
├── .github
│   └── ISSUE_TEMPLATE
├── gradle
│   └── wrapper
├── src
│   ├── main
│   │   ├── java/com/uos/all4runner
│   │   └── resources
│   └── test
│       └── java/com/uos/all4runner
├── build.gradle
├── settings.gradle
└── README.md
```

## 사용 데이터

- 국토교통부. (2024). 수치지형도 v2.0(1:5000) [데이터 세트]. 브이월드.  
  https://www.vworld.kr/dtmk/dtmk_ntads_s002.do?dsId=30205
- 서울특별시. (2025.03.13). 서울시 자치구별 도보 네트워크 공간정보 [데이터 세트]. 서울 열린데이터 광장.  
  https://data.seoul.go.kr/dataList/OA-21208/S/1/datasetView.do
- 서울특별시. (2024.12.03). 서울시 공중화장실 위치정보(좌표계: WGS1984) [데이터 세트]. 서울 열린데이터 광장.  
  https://data.seoul.go.kr/dataList/OA-1370/S/1/datasetView.do
- 서울특별시. (2025.03.12). 서울시 공원음수대 정보 조회 [데이터 세트]. 서울 열린데이터 광장.  
  https://data.seoul.go.kr/dataList/OA-20884/S/1/datasetView.do
- VWorld Search API 2.0
- VWorld WMTS API

## 참고문헌

- 이소희, 구신회, 전영우, 박영진. (2015). 도로경사와 연령별 보행속도 차이를 고려한 자연재난 대피소의 입지분석. 대한공간정보학회지, 23(2), 69-77.
- 우제윤, 구지희, 홍창희, 김태훈. (2001). 수치표고모형(DEM) 구축을 위한 지형별 보간 방법 및 격자크기에 관한 연구. 한국공간정보시스템학회 논문지, 3(2), 5-19.
- 김대호, 정인범. (2012). 고도를 고려한 정밀도 높은 운동거리 측정시스템. 한국정보통신학회논문지, 16(3), 615-625.
- Tobler, Waldo. (1993). Three presentations on geographical analysis and modeling: Non-isotropic geographic modeling speculations on the geometry of geography global spatial analysis. National Center for Geographic Information and Analysis Technical Report, 93(1).
- Lippincott Williams & Wilkins. (2009). ACSM's Guidelines for Exercise Testing and Prescription.

## License

This project is licensed under the MIT License. See [LICENSE](./LICENSE) for details.
