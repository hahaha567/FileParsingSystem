USE `springdb`;

DROP TABLE IF EXISTS users;
CREATE TABLE users(
                       `id` bigint(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                       `username` varchar(30) NOT NULL COMMENT '用户名',
                       `password` varchar(60) NOT NULL COMMENT '密码',
                       `create_time` timestamp NOT NULL COMMENT '用户记录创建的时间',
                       `update_time` timestamp NOT NULL COMMENT '用户资料修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户基本信息表';

DROP TABLE IF EXISTS roles;
CREATE TABLE roles(
                        `id` bigint(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                        `name` varchar(30) NOT NULL COMMENT '角色名称'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '角色表';

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles(
                        `id` bigint(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                        `user_id` bigint(11) NOT NULL COMMENT '用户主键',
                        `role_id` bigint(11) NOT NULL COMMENT '角色主键',
                        foreign key (user_id) references users(id) ,
                        foreign key (role_id) references roles(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户角色连接表';

INSERT INTO `roles` values (1, 'admin');
INSERT INTO `roles` values (2, 'vip');
INSERT INTO `roles` values (3, 'normal');

DROP TABLE IF EXISTS files;
CREATE TABLE files(
                            `id` bigint(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                            `user_id` bigint(11) NOT NULL COMMENT '用户主键',
                            `filename` varchar(30) NOT NULL COMMENT '文件名',
                            `store_path` varchar(120) NOT NULL COMMENT '文件存储路径',
                            `create_time` timestamp NOT NULL COMMENT '文件创建时间',
                            `update_time` timestamp NOT NULL COMMENT '文件修改时间',
                            foreign key (user_id) references users(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '文件存储信息表';

DROP TABLE IF EXISTS download;
CREATE TABLE download(
                      `id` bigint(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                      `user_id` bigint(11) NOT NULL COMMENT '用户主键',
                      `file_id` bigint(11) NOT NULL COMMENT '文件主键',
                      `create_time` timestamp NOT NULL COMMENT '文件下载时间',
                      foreign key (user_id) references users(id),
                      foreign key (file_id) references files(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '文件下载记录表';

DROP TABLE IF EXISTS browse;
CREATE TABLE browse(
                         `id` bigint(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                         `user_id` bigint(11) NOT NULL COMMENT '用户主键',
                         `file_id` bigint(11) NOT NULL COMMENT '文件主键',
                         `create_time` timestamp NOT NULL COMMENT '文件浏览时间',
                         foreign key (user_id) references users(id),
                         foreign key (file_id) references files(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '文件浏览记录表';

DROP TABLE IF EXISTS likes;
CREATE TABLE likes(
                       `id` bigint(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                       `user_id` bigint(11) NOT NULL COMMENT '用户主键',
                       `file_id` bigint(11) NOT NULL COMMENT '文件主键',
                       `create_time` timestamp NOT NULL COMMENT '点赞文件的时间',
                       foreign key (user_id) references users(id),
                       foreign key (file_id) references files(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '文件点赞记录表';

DROP TABLE IF EXISTS comments;
CREATE TABLE comments(
                      `id` bigint(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                      `user_id` bigint(11) NOT NULL COMMENT '用户主键',
                      `file_id` bigint(11) NOT NULL COMMENT '文件主键',
                      `create_time` timestamp NOT NULL COMMENT '评论文件的时间',
                      `content` varchar(120) NOT NULL COMMENT '评论内容',
                      foreign key (user_id) references users(id),
                      foreign key (file_id) references files(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '文件评论记录表';