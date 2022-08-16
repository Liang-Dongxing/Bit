# 基础镜像
FROM  openjdk:11-jre
# author
MAINTAINER dongxing

# 添加时区环境变量，亚洲，上海
ENV TIME_ZONE=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TIME_ZONE /etc/localtime && echo $TIME_ZONE > /etc/timezone

EXPOSE 8080

# 挂载目录
VOLUME /home/ruoyi
# 创建目录
RUN mkdir -p /home/ruoyi
# 指定路径
WORKDIR /home/ruoyi
# 复制jar文件到路径
COPY ./ruoyi-admin/target/ruoyi-admin.jar /home/ruoyi/ruoyi-admin.jar
# 启动认证服务
ENTRYPOINT ["java","-jar","ruoyi-admin.jar"]