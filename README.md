# Blog Press博客系统

## 1、项目介绍
本项目为BlogPress博客系统的后端项目部分，包括以下几个模块

### 1.1、用户模块

- 用户登录
- 用户注册
- 关注用户
- 角色与权限

### 1.2、文章模块

- 文章列表
- 文章详情
- 编写文章
- 文章收藏
- 文章点赞
- 文章评论

### 1.3、搜索模块

- 搜索用户
- 搜索文章

### 1.4、消息模块

- 站内信
- 私信

## 2、环境信息

### 2.1、开发环境

- JDK 1.8
- Maven 3.8.1

### 2.2、部署环境

- CentOS 7
- Docker 20.10.7

### 2.3、数据库以及中间件

- PostgreSQL 12.1
- Redis 6.2.4
- ElasticSearch 7.13.3
- Elastic Kibana 7.13.3
- RabbitMQ 3.8.19

## 3、环境搭建(基于CentOS 7)

### 3.1、Docker安装

```
# 安装yum-utils
sudo yum install -y yum-utils

# 添加docker安装仓库
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

# 安装最新版docker
sudo yum install docker-ce docker-ce-cli containerd.io
```

### 3.2、使用Docker安装PostgreSQL

```
# 拉取镜像
docker pull postgres:12.1

# 在后台启动名为postgres的容器，密码为123456，将本机/data/pgdata映射到容器内的/var/lib/postgresql/data
docker run -d --name postgres -e POSTGRES_PASSWORD=123456 -p 5432:5432 postgres:12.1 -v /data/pgdata:/var/lib/postgresql/data
```

### 3.3、使用Docker安装Redis

```
# 拉取镜像
docker pull redis:6.2.4

# 在后台启动名为redis的容器，密码为123456
docker run -d --name redis -p 6379:6379 redis:6.2.4 --requirepass "123456"
```

### 3.4、使用Docker安装ElasticSearch & Kibana

```
# 拉取镜像
docker pull docker.elastic.co/elasticsearch/elasticsearch:7.13.3
docker pull docker.elastic.co/kibana/kibana:7.13.3

# 启动容器(单节点)
# 创建名为elastic的网络
docker network create elastic

# 在后台启动名字为elasticsearch的容器
docker run -d --name elasticsearch --net elastic -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.13.3

# 在后台启动名字为kibana的容器
docker run -d --name kibana --net elastic -p 5601:5601 -e "ELASTICSEARCH_HOSTS=http://elasticsearch:9200" docker.elastic.co/kibana/kibana:7.13.3
```

### 3.5、使用Docker安装RabbitMQ

```
# 拉取镜像
docker pull rabbitmq:management

# 在后台启动容器，默认用户和密码均为admin
docker run -dit --name rabbitmq -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin -p 15672:15672 -p 5672:5672 rabbitmq:management
```