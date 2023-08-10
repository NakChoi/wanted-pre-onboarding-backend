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


## `애플리케이션의 실행 방법(API 엔드포인트 포함)`
<br/><br>

1. 회원가입

    Method: POST
    - URL: http://43.201.235.107:8080/v1/api/members
    - 이메일과 비밀번호로 회원가입을 할 수 있습니다.
    - 이메일에 @를 포함해야 합니다.
    - 비밀번호는 8자 이상으로 작성해야합니다.
    - 회원가입이 성공한 경우 응답 헤더의 Location에서 회원의 ID를 알 수 있습니다.

<br/><br>

2. 로그인

    Method: POST
    - URL: http://43.201.235.107:8080/auth/login
    - 회원가입한 이메일과 비밀번호로 로그인을 할 수 있습니다.
    - 게시글을 생성, 수정, 삭제하기 위해서는 로그인을 할 필요가 있습니다.
    - 로그인이 성공한 경우 응답 헤더의 Authorization에서 JWT AccessToken을 확인할 수 있습니다.

<br/><br>

3. 게시글 생성

     Method: POST
     - URL: http://43.201.235.107:8080/v1/api/posts
     - Request body 에 제목과 본문 내용을 작성해야합니다.
     - 게시글 생성을 하기 위해서는 요청 헤더에 발급받은 JWT AccessToken을 포함시켜주세요.

<br/><br>

4. 게시글 목록 조회

     Method: GET
     - URL: http://43.201.235.107:8080/v1/api/posts
     - Pagination이 적용되어 있습니다.
     - page와 size를 변경하고 싶다면, 쿼리 파라미터에 page와 size를 붙여서 요청을 보내주세요.
     - ex. http://43.201.235.107:8080/v1/api/posts?page=1&size=5 

<br/><br>

5. 특정 게시글 조회

     Method: GET
     - URL: http://43.201.235.107:8080/v1/api/posts/{post-id}
     - 조회하고 싶은 게시글의 ID를 URL 마지막에 붙여서 요청을 보내주세요.
     - 응답 본문에서 대상 게시글의 ID, 제목, 내용을 확인할 수 있습니다.
     - ex. http://43.201.235.107:8080/v1/api/posts/1

<br/><br>

6. 특정 게시글 수정

     Method: PATCH
     - URL: http://43.201.235.107:8080/v1/api/posts/{post-id}
     - 수정하고 싶은 게시글의 ID를 URL 마지막에 붙여서 요청을 보내주세요.
     - 제목이나 내용을 선택적으로 수정할 수도 있고, 한번에 모두 수정할 수도 있습니다.
     - 요청 본문에 수정하고 싶은 내용을 포함하여 요청을 보내주세요.
     - 게시글 수정은 게시글 작성자만 할 수 있습니다.
     - 게시글 수정을 하기 위해서는 요청 헤더에 발급받은 JWT AccessToken을 포함시켜주세요.

<br/><br>

7. 특정 게시글 삭제

     Method: DELETE
     - URL: http://43.201.235.107:8080/v1/api/posts/{post-id}
     - 삭제하고 싶은 게시글의 ID를 URL 마지막에 붙여서 요청을 보내주세요.
     - 게시글 삭제는 게시글 작성자만 할 수 있습니다.
     - 게시글 삭제를 하기 위해서는 요청 헤더에 발급받은 JWT AccessToken을 포함시켜주세요.

<br/><br>

## `API 명세서 입니다.`

[PostMan API 명세서 링크입니다!](https://documenter.getpostman.com/view/24689222/2s9XxyRt9V)  

<br/><br>

## `데이터베이스 테이블 구조`

![image](https://github.com/NakChoi/wanted-pre-onboarding-backend/assets/92242517/c637592c-9f99-4403-908c-99e388e398c5)


<br/><br>

## `구현 방법 및 이유`
Spring Boot와 Spring Security를 활용하여 빠르고 보안적으로 안정적인 웹 애플리케이션을 개발하려 노력했습니다. 또 MySQL을 사용하여 데이터를 효율적으로 관리하고, Github actions, Docker Container와 Docker Compose를 채택하여 애플리케이션과 DB를 가상화하고, AWS EC2를 통해 클라우드 환경에서 배포하여 안정성과 확장성을 보장하였습니다. 

또한, Git Flow 전략을 통해 Main, Deploy, feat/member, feat/post 등을 각각의 브랜치로 분리하여 개발과정을 관리하는 전략으로, 병렬 개발과 안정적인 배포를 하려고 노력했습니다. 이러한 기술 스택과 방법을 통해 프로젝트를 구현하고 배포하여 비용과 시간을 절감하고 단위 테스트 코드도 작성하여 더 높은 안정성을 보장할 수 있었습니다!



<br/><br>
## `구현한 API의 동작을 촬영한 데모 영상 링크`

[최낙준 -  API의 동작을 촬영한 데모 영상 링크 (YouTube)](https://www.youtube.com/watch?v=vDQ1pdOQeU8&ab_channel=%EC%B5%9C%EB%82%99%EC%A4%80)




<br/><br>



### `Docker Image, Git Actions, ghcr.io, Docekr Compose, AWS EC2 로 CI/CD,구축하였습니다. `


![제목 없는 다이어그램 (1)](https://github.com/NakChoi/wanted-pre-onboarding-backend/assets/92242517/2bc7e459-08f4-4f22-91cb-74320359ef5c)

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

<br/><br>




