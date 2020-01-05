create database blog default charset 'utf8mb4';

use blog;

create table user(
	id int(11) primary key auto_increment,
	email varchar(50) comment '邮箱',
	name varchar(50) comment '用户名',
	password varchar(50),
	role int comment'身份，1为普通用户',
	photo varchar(50) comment'用户头像名称',
	introduction varchar(800) comment '个人简介',
	created_at timestamp default current_timestamp comment '创建时间',
	lasttime timestamp default current_timestamp on update current_timestamp comment '最近登录的时间'
);

create table type(
	id int(11) primary key auto_increment,
	name varchar(50) comment '分类名称',
	created_at timestamp default current_timestamp
);

create table article(
	id int(11) primary key auto_increment comment '文章的id',
	title varchar(100) comment '文章的标题',
	introduction varchar(600) comment '文章的简介',
	content text comment '文章内容',
	created_at timestamp default current_timestamp comment '文章创建时间',
	photo varchar(50) comment'文章配图名称',
	uid int(11) comment'文章作者id',
	tid int(11) comment'分类名称'
);

create table message(
	id int(11) primary key auto_increment,
	uId int(11) comment'留言者id',
	content varchar(300) comment'留言内容',
	aId int(11) comment'被留言的文章id',
	mId int(11) comment'被留言的留言id',
	created_at timestamp default current_timestamp comment'留言添加时间'
);

create table photo(
	id int(11) primary key auto_increment,
	name varchar(50) comment'照片名称',
	created_at timestamp comment'照片上传时间',
	uid int(11)
);

--视图
create view articleInfo as
select a.*,u.name as author,t.name as typeName from article a 
left join user u on a.uid=u.id 
left join type t on a.tid=t.id; 

--users
insert into user values(default,'123456@qq.com','admin','123456',0,'gsm.jpg','第一次想做这么一个网站，去记录自己的生活和学习，前行的脚步太过匆忙，不如停下来好好整理整理，自己选择的路，不论如何都要走完。',default,default);


--types
insert into type values(default,'java',default);
insert into type values(default,'mysql',default);
insert into type values(default,'php',default);
insert into type values(default,'oracle',default);
insert into type values(default,'生活',default);

--articles

insert into article values(default,'mysql中文乱码','mysql中文乱码','1.创建数据库时就用default charset 'utf8'设置编码
  2.通过show variables like '%char%';查看数据库默认字符集
  3.编辑my.cnf文件，在文件的[client],[mysqld_safe],[mysqld],[mysql]下新加 default-character-set=utf8',default,'11.jpg',1,2);
insert into article values(default,'mysql单表多个查询','mysql单表多个查询','通过select *from 表名 where locate(?,concat(列名,列名,列名))查询',default,'22.jpg',1,2);
insert into article values(default,'php得到数据库结果','php得到数据库结果','1.fetch() 返回结果集的下一行，结果指针下移，到头返回false；
2.fetchAll() 通过一次调用返回所有结果，结果是以数组形式保存；
3.fetchColumn()返回结果集中下一行某个列的值；',default,'555.jpg',1,3);

insert into article values
	(default,'关于我的介绍','第1次尝试制作php个人博客网站',
	'第一次想做这么一个网站，去记录自己的生活和学习，前行的脚步太过匆忙，不如停下来好好整理整理，自己选择的路，不论如何都要走完。',default,'a.jpg',1,5);

--photos
insert into photo values(default,'a.jpg',default,1);
insert into photo values(default,'b.jpg',default,1);
insert into photo values(default,'c.jpg',default,1);
insert into photo values(default,'d.jpg',default,1);
insert into photo values(default,'e.jpg',default,1);
insert into photo values(default,'f.jpg',default,1);
insert into photo values(default,'g.jpg',default,1);
insert into photo values(default,'h.jpg',default,1);
insert into photo values(default,'i.jpg',default,1);