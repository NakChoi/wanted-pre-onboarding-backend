<br/><br>
# wanted-pre-onboarding-backend

 #### 안녕하세요 이번에 wanted-pre-onboarding-be에 지원하게 된 최낙준이라고 합니다.

<br/><br>
## `docker compose 실행 방법`  

```bash
# 1. 환경변수 설정
export JWT_SECRET_KEY=54124a91f59c44c9a1755cf7e4c9bccf80aff98b95b949119f42a243be15c2017a2310f0acc54d5c910de5530af15d82
export MYSQL_PASSWORD=mysqlpassword1234

# 2. docker compose 실행
docker compose pull
docker compose down
docker compose up -d
```

<br/><br>

## `API 명세서 입니다.`

[PostMan API 명세서 링크입니다!](https://documenter.getpostman.com/view/24689222/2s9XxyRt9V)  

<br/><br>

## `데이터베이스 테이블 구조`

![image](https://github.com/NakChoi/wanted-pre-onboarding-backend/assets/92242517/c637592c-9f99-4403-908c-99e388e398c5)


<br/><br>

## `구현 방법 및 이유`
Spring Boot와 Spring Security를 활용하여 빠르고 보안적으로 안정적인 웹 애플리케이션을 개발하려 노력했습니다. 또 MySQL을 사용하여 데이터를 효율적으로 관리하고, ㅎGithub actions, Docker Container와 Docker Compose를 채택하여 애플리케이션과 DB를 가상화하고, AWS EC2를 통해 클라우드 환경에서 배포하여 안정성과 확장성을 보장하였습니다. 

또한, Git Flow 전략을 통해 Main, Deploy, feat/member, feat/post 등을 각각의 브랜치로 분리하여 개발과정을 관리하는 전략으로, 병렬 개발과 안정적인 배포를 하려고 노력했습니다. 이러한 기술 스택과 방법을 통해 프로젝트를 구현하고 배포하여 비용과 시간을 절감하고 안정성을 보장할 수 있었습니다.




<br/><br>
## `구현한 API의 동작을 촬영한 데모 영상 링크`

[최낙준 -  API의 동작을 촬영한 데모 영상 링크 (YouTube)](https://www.youtube.com/watch?v=vDQ1pdOQeU8&ab_channel=%EC%B5%9C%EB%82%99%EC%A4%80)




<br/><br>



### `Docker Image, Git Actions, ghcr.io, Docekr Compose, AWS EC2 로 CI/CD,구축하였습니다. `

        +-----------------------------------------+
        |           GitHub Repository            |
        |               (Source)                  |
        +-----------------------------------------+
                                |
                                | git push
                                |
        +-----------------------------------------+
        |         GitHub Actions (CI)             |
        +-----------------------------------------+
                    |              |
                    |              | CI Success
                    |              |
        +-----------v--------------v----------+
        |   ghcr.io / Docker Hub / public Repo  |
        |       (Docker Images)                 |
        +------------------------------------+
                    |              |
                    | docker pull  |
                    |              |
        +-----------v--------------v----------+
        |           Docker Compose             |
        |            (Deployment)              |
        +------------------------------------+
                    |              |
                    |   docker     | Deployment Success
                    |   compose    |
        +-----------v--------------v----------+
        |          Application                |
        |         (Running Service)            |
        +------------------------------------+

## AWS EC2 배포 주소입니다.
`http://43.201.235.107:8080`

[http://43.201.235.107:8080](http://43.201.235.107:8080)


