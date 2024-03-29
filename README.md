## 개요
https://github.com/vmunier/play-scalajs.g8 템플릿을 사용해 web UI 를 개발해 본다.
개발에 대한 설명은 https://wefree.tistory.com/324 블로그에서 확인할 수 있다.

## 데모 페이지
https://wefree-scalajs.fly.dev/

## Tech Stack
* Require Java 17
* [playframework-3.x](https://www.playframework.com/)
* [laminar-16.0.0](https://laminar.dev/)
* [frontroute-0.18.x](https://frontroute.dev/)
* [bootstrap-5.3](https://getbootstrap.com/)

## fly.io 배포
개인 Local PC 에서 Dockerfile 로 이미지를 만들 수 있는 준비되어야 `fly` command 로 배포할 수 있다.
* Local PC 에서 sbt build  
  `java-17` 과 `sbt` 가 설치되어 있어야 한다.

  ```shell
  sbt server/clean server/stage
  
  # docker build --tag demo .
  # docker run --rm -p 9000:8080 demo
  ```
* `fly` command 로 배포 [참고](https://github.com/windbird123/dash-admin#flyio-%EB%B0%B0%ED%8F%AC)  
  `fly deploy` 를 실행하면 Local 에서 Dockerfile 로 이미지를 생성 후 배포한다.
  ```shell
  fly auth login
  fly launch
  fly deploy
  ```

* (optional) [Deploying a full stack Scala application on fly.io](https://medium.com/itnext/deploying-a-full-stack-scala-application-on-fly-io-f80ca9de9b13) 을 참고해 github actions 로 관리할 수 있다.
  
* 참고 문서
  * [fly.io 공식 가이드](https://fly.io/docs/languages-and-frameworks/dockerfile/)
  * [medium doc](https://medium.com/itnext/deploying-a-full-stack-scala-application-on-fly-io-f80ca9de9b13)
