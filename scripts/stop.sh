#!/usr/bin/env bash

#######################################
# 환경 설정
#######################################
TIME_NOW=$(TZ=Asia/Seoul date '+%Y%m%d_%H%M%S')

PROJECT_ROOT="/home/ubuntu/app"
JAR_FILE="$PROJECT_ROOT/spring-webapp.jar"

LOG_FOLDER="$PROJECT_ROOT/logging"
APP_LOG_BACKUP_FOLDER="$LOG_FOLDER/app_runtime"
DEPLOY_LOG="$LOG_FOLDER/deploy.log"

#######################################
# 폴더 생성
mkdir -p "$LOG_FOLDER"
mkdir -p "$APP_LOG_BACKUP_FOLDER"
mkdir -p "$LOG_FOLDER/app_error"
mkdir -p "$PROJECT_ROOT/jar_backup"
#######################################

#######################################
# 이전 로그 파일 처리
# - application_*.log를 app_runtime 폴더로 이동 후 삭제
#######################################
echo "$TIME_NOW > 이전 로그 파일 확인 및 백업" >> "$DEPLOY_LOG"

# 패턴: /home/ubuntu/app/application_*.log
for OLD_LOG in "$PROJECT_ROOT"/application_*.log
do
  # 실제 파일이 있는지 확인
  if [ -f "$OLD_LOG" ]; then
    echo "$TIME_NOW > 백업 후 삭제: $OLD_LOG -> $APP_LOG_BACKUP_FOLDER/" >> "$DEPLOY_LOG"
    cp "$OLD_LOG" "$APP_LOG_BACKUP_FOLDER/"
    rm "$OLD_LOG"
  fi
done

#######################################
# 현재 구동 중인 애플리케이션 종료
#######################################
CURRENT_PID=$(pgrep -f $JAR_FILE)

if [ -z $CURRENT_PID ]; then
  echo "$TIME_NOW > 현재 실행중인 애플리케이션이 없습니다" >> "$DEPLOY_LOG"
else
  echo "$TIME_NOW > 실행중인 애플리케이션 종료 [PID: $CURRENT_PID]" >> "$DEPLOY_LOG"
  kill -15 $CURRENT_PID
fi
