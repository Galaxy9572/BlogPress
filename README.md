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

## 4、建表语句

### 4.1、User表

```sql
create table if not exists "user"
(
	user_id bigint not null constraint user_pk primary key,
	nick varchar(20) not null,
	password_hash varchar(128) not null,
	salt varchar(64) not null,
	gender varchar(1) not null,
	birthday date not null,
	phone varchar(20),
	email varchar(100),
	country varchar(100),
	province varchar(100),
	city varchar(100),
	street varchar(100),
	create_time timestamp default CURRENT_TIMESTAMP,
	update_time timestamp default CURRENT_TIMESTAMP,
	user_status varchar(20) default 'NORMAL'::character varying,
	is_logic_deleted boolean default false,
    version bigint default 0
);
comment on column "user".user_id is '主键';
comment on column "user".nick is '昵称';
comment on column "user".password_hash is '密码哈希';
comment on column "user".salt is '密码盐值';
comment on column "user".gender is '性别';
comment on column "user".birthday is '生日';
comment on column "user".phone is '手机号';
comment on column "user".email is '邮箱';
comment on column "user".country is '国家';
comment on column "user".province is '省份';
comment on column "user".city is '城市';
comment on column "user".street is '街道';
comment on column "user".create_time is '注册时间';
comment on column "user".update_time is '信息更新时间';
comment on column "user".user_status is '用户状态';
comment on column "user".is_logic_deleted is '是否已经逻辑删除';
comment on column "user".version is '版本号';
alter table "user" owner to postgres;
```

### 4.2、用户角色表

```sql
create table user_role
(
    role_id bigint not null constraint user_role_pk primary key,
	user_id bigint not null,
	code varchar(20) not null,
	create_time timestamp default CURRENT_TIMESTAMP,
	update_time timestamp default CURRENT_TIMESTAMP,
	is_logic_deleted boolean default false,
    version bigint default 0
);
comment on column user_role.role_id is '主键';
comment on column user_role.user_id is '用户ID';
comment on column user_role.code is '角色编码';
comment on column user_role.create_time is '注册时间';
comment on column user_role.update_time is '信息更新时间';
comment on column user_role.is_logic_deleted is '是否已经逻辑删除';
comment on column user_role.version is '版本号';
alter table user_role owner to postgres;
```

### 4.3、文章表

```sql
create table if not exists article
(
	article_id bigint not null constraint article_pk primary key,
	user_id bigint not null,
	title text not null,
	content text not null,
	create_time timestamp default CURRENT_TIMESTAMP not null,
	update_time timestamp default CURRENT_TIMESTAMP not null,
	is_logic_deleted boolean default false,
    version bigint default 0
);
comment on table article is '文章表';
comment on column article.article_id is '主键';
comment on column article.user_id is '作者ID';
comment on column article.title is '文章标题';
comment on column article.content is '文章内容';
comment on column article.create_time is '文章创建时间';
comment on column article.update_time is '文章修改时间';
comment on column article.is_logic_deleted is '是否已经逻辑删除';
comment on column article.version is '版本号';
alter table article owner to postgres;
```

### 4.4、统计表

```sql
create table count
(
	count_id bigint not null constraint count_pk primary key,
	content_type varchar(20) not null,
    content_id bigint not null,
	like_count int default 0 not null,
	collect_count int default 0 not null,
	comment_count int default 0 not null,
	is_logic_deleted bool default false not null,
	version bigint default 0
);
comment on table count is '统计表';
comment on column count.count_id is '主键';
comment on column count.content_type is '内容分类';
comment on column count.content_id is '内容主键';
comment on column count.like_count is '点赞数';
comment on column count.collect_count is '收藏数';
comment on column count.comment_count is '评论数';
comment on column count.is_logic_deleted is '是否已经逻辑删除';
comment on column count.version is '版本号';
```

## 5、Elastic Search建索引

```
PUT {{host}}:9200/index_user?pretty

{
    "settings": {
        "number_of_shards": 3,
        "number_of_replicas": 2
    },
    "mappings": {
        "properties": {
            "user_id": {
                "type": "long"
            },
            "nick": {
                "type": "keyword"
            },
            "gender": {
                "type": "keyword"
            },
            "birthday": {
                "type": "keyword"
            },
            "country": {
                "type": "keyword"
            },
            "province": {
                "type": "keyword"
            },
            "city": {
                "type": "keyword"
            },
            "user_status": {
                "type": "keyword"
            },
            "is_logic_deleted": {
                "type": "keyword"
            }
        }
    }
}
```