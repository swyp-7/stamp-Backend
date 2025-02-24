#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/app"
JAR_FILE="$PROJECT_ROOT/spring-webapp.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

if [ -f $PROJECT_ROOT/scripts/.env ]; then
  source $PROJECT_ROOT/scripts/.env
else
  echo "$TIME_NOW > 환경 변수 파일을 찾을 수 없습니다." >> $ERROR_LOG
  echo "현재 디렉토리: $(pwd)" >> $ERROR_LOG
  ls -al >> $ERROR_LOG
  exit 1
fi


# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/build/libs/*.jar $JAR_FILE

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
echo "Run command: java -D$JASYPT_SECRET -jar $JAR_FILE" >> $DEPLOY_LOG
nohup java -D$JASYPT_SECRET -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 프로세스 실행완료. [PID: $CURRENT_PID]" >> $DEPLOY_LOG
