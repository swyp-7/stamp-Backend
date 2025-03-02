#!/usr/bin/env bash

#######################################
# 환경 설정
#######################################
TIME_NOW=$(TZ=Asia/Seoul date '+%Y%m%d_%H%M%S')

PROJECT_ROOT="/home/ubuntu/app"
JAR_FILE="$PROJECT_ROOT/spring-webapp.jar"

# JAR 백업 파일명(시간이 포함되어야 하므로 TIME_NOW 뒤에 선언)
JAR_BACKUP="$PROJECT_ROOT/jar_backup/spring-webapp-$TIME_NOW.jar"

# 로그 경로
LOG_FOLDER="$PROJECT_ROOT/logging"
APP_LOG="$PROJECT_ROOT/application_$TIME_NOW.log"     # 표준출력 로그
APP_ERROR_LOG="$LOG_FOLDER/app_error/$TIME_NOW.log"  # 표준에러 로그
ERROR_LOG="$LOG_FOLDER/error.log"                    # 스크립트 실행 중 에러 로그
DEPLOY_LOG="$LOG_FOLDER/deploy.log"                  # 배포 과정 로그

#######################################
# 환경 변수 로드
#######################################
if [ -f "$PROJECT_ROOT/scripts/.env" ]; then
  source "$PROJECT_ROOT/scripts/.env"
else
  echo "$TIME_NOW > 환경 변수 파일(.env)을 찾을 수 없습니다." >> "$ERROR_LOG"
  echo "현재 디렉토리: $(pwd)" >> "$ERROR_LOG"
  ls -al >> "$ERROR_LOG"
  exit 1
fi

#######################################
# jar 파일을 프로젝트 루트로 복사
#######################################
echo "$TIME_NOW > $JAR_FILE 파일 복사" >> "$DEPLOY_LOG"
cp "$PROJECT_ROOT/build/libs/"*.jar "$JAR_FILE"

#######################################
# 새 애플리케이션 구동
#######################################
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> "$DEPLOY_LOG"
echo "Run command: java -D$JASYPT_SECRET -jar $JAR_FILE" >> "$DEPLOY_LOG"

# 백그라운드 실행 & 로그 리다이렉트
nohup java -D"$JASYPT_SECRET" -jar "$JAR_FILE" > "$APP_LOG" 2> "$APP_ERROR_LOG" &

# PID 확인
CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 프로세스 실행완료. [PID: $CURRENT_PID]" >> "$DEPLOY_LOG"

#######################################
# JAR 백업
#######################################
cp "$PROJECT_ROOT/build/libs/"*.jar "$JAR_BACKUP"
echo "$TIME_NOW > JAR 파일 백업 완료: $JAR_BACKUP" >> "$DEPLOY_LOG"
