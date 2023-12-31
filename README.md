## backend-api
* working port 8081a
* is main application
* it has user registration, verification, jwt for authentication
* implements role based authorization
* uses rabit mq to produce mail event

## send-mail-consume
* working port 8082
* used for sending mail
* uses rabit mq for sending mail only