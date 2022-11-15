#!/bin/sh
# ./bit.sh start 启动 stop 停止 restart 重启 status 状态
AppName=bit-admin.jar

# JVM参数
JVM_OPTS="-Dname=$AppName  -Duser.timezone=Asia/Shanghai -Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps  -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC"
APP_HOME=$(pwd)
# shellcheck disable=SC2034
LOG_PATH=$APP_HOME/logs/$AppName.log

Operation=""
IsLog=false

if [ "$1" = "" ];
then
    echo "-h \033[0;31m 查看帮助 \033[0m"
    exit 1
fi

if [ "$AppName" = "" ];
then
    echo "\033[0;31m 未输入应用名 \033[0m"
    exit 1
fi

start(){
    # shellcheck disable=SC2009
    PID=$(ps -ef |grep java|grep $AppName|grep -v grep|awk '{print $2}')

	if [ x"$PID" != x"" ]; then
	    echo "$AppName is running..."
	else
	  if [ "$IsLog" = true ]; then
      nohup java "$JVM_OPTS" -jar $AppName > "$LOG_PATH" 2>&1 &
    else
		  nohup java "$JVM_OPTS" -jar $AppName > /dev/null 2>&1 &
    fi

		echo "Start $AppName success..."
	fi
}

stop(){
    echo "Stop $AppName"

	PID=""
	query(){
		# shellcheck disable=SC2009
		PID=$(ps -ef |grep java|grep $AppName|grep -v grep|awk '{print $2}')
	}

	query
	if [ x"$PID" != x"" ]; then
		kill -TERM "$PID"
		echo "$AppName (pid:$PID) exiting..."
		while [ x"$PID" != x"" ]
		do
			sleep 1
			query
		done
		echo "$AppName exited."
	else
		echo "$AppName already stopped."
	fi
}

restart(){
    stop
    sleep 2
    start
}

status(){
    # shellcheck disable=SC2009
    # shellcheck disable=SC2126
    NUM=$(ps -ef |grep java|grep $AppName|grep -v grep|wc -l)
    # shellcheck disable=SC2009
    PID=$(ps -ef |grep java|grep $AppName|grep -v grep|awk '{print $2}')
    if [ "$NUM" != 0 ];then
        echo "$AppName is running , PID is $PID..."
    else
        echo "$AppName is not running..."
    fi
}

while getopts "h:e:o:l" opt
do
    case $opt in
        e)
            Operation=$OPTARG
            ;;
        l)
            IsLog=true
            ;;
        o)
            LOG_PATH=$OPTARG
            ;;
        h)
            echo "-e \033[0;31m 未输入操作名 \033[0m  \033[0;34m {start|stop|restart|status} \033[0m"
            echo "-l \033[0;31m 是否输出日志 \033[0m"
            echo "-o \033[0;31m 日志输出位置 \033[0m  \033[0;34m logs/app.log \033[0m"
            exit 1;;
        ?)
            echo "未知参数"
            exit 1;;
    esac
done
case $Operation in
    start)
      start
      ;;
    stop)
      stop
      ;;
    restart)
      restart
      ;;
    status)
      status
      ;;
    *)
    # shellcheck disable=SC2039
      echo -e "$2" "\033[0;31m 没找到此运行参数 \033[0m"
esac
