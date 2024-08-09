#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/giftU"
JAR_FILE="$PROJECT_ROOT/build/libs/gift-u-0.0.1-SNAPSHOT.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/build/libs/*.jar $JAR_FILE

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
sudo nohup java -jar $JAR_FILE > /dev/null 2>&1 & #로그 버림

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스의 아이디는 $CURRENT_PID 입니다." >> $DEPLOY_LOG
