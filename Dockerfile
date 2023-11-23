FROM azul/zulu-openjdk-alpine:11

ENV TZ=Asia/Seoul
ENV LANG=ko_KR.UTF-8

RUN apk add --no-cache bash

ADD ./server/target/universal/stage /app/

WORKDIR /app
EXPOSE 8080

ENTRYPOINT /app/bin/server \
    -Dapplication.home=/app \
    -Dconfig.file=/app/conf/application.conf \
    -Dlogger.file=/app/conf/logback.xml \
    -Dplay.http.secret.key=f4175469aec955dc23c054e41c4bbe48 \
    -Dpidfile.path=/app/server.pid \
    -Dhttp.port=8080

# docker build --tag demo .
# docker run --rm -p 9000:8080 demo
