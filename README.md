# wanted-pre-onboarding-backend

## `docker compose`

```bash
# 1. 환경변수 설정
export JWT_SECRET_KEY=54124a91f59c44c9a1755cf7e4c9bccf80aff98b95b949119f42a243be15c2017a2310f0acc54d5c910de5530af15d82
export MYSQL_PASSWORD=mysqlpassword1234

# 2. docker compose 실행
docker compose pull
docker compose down
docker compose up -d
```
