create table t_standalone_config
(
    id              bigint auto_increment
        primary key,
    code            varchar(100) not null,
    name            varchar(100) not null,
    log_group       char(32)     not null comment '日志组',
    log_snapshot_id char(32)     not null comment '业务表关联的ID',
    if_log          tinyint      not null comment '1日志，0非日志。同一个日志组里，0状态的数据只有一个',
    creator         varchar(32)  not null,
    creator_name    varchar(32)  not null,
    create_time     datetime(3)  not null,
    modifier        varchar(32)  not null,
    modifier_name   varchar(32)  not null,
    modify_time     datetime(3)  not null
);


create table t_cascade_parent_config
(
    id              bigint auto_increment
        primary key,
    code            varchar(100) not null,
    name            varchar(100) not null,
    log_group       char(32)     not null comment '日志组',
    log_snapshot_id char(32)     not null comment '快照ID：业务表关联的ID',
    if_log          tinyint      not null comment '1日志，0非日志。同一个日志组里，0状态的数据只有一个',
    parent_id       bigint       null comment '父亲',
    creator         varchar(32)  not null,
    creator_name    varchar(32)  not null,
    create_time     datetime(3)  not null,
    modifier        varchar(32)  not null,
    modifier_name   varchar(32)  not null,
    modify_time     datetime(3)  not null
);



create table t_cascade_parent_config_his_state
(
    id                       bigint auto_increment
        primary key,
    state_id                 char(32)     not null,
    cascade_parent_config_id bigint       not null,
    code                     varchar(100) not null,
    name                     varchar(100) not null,
    log_group                char(32)     not null comment '日志组',
    log_snapshot_id          char(32)     not null comment '快照ID：业务表关联的ID',
    if_log                   tinyint      not null comment '1日志，0非日志。同一个日志组里，0状态的数据只有一个',
    parent_id                bigint       null comment '父亲',
    creator                  varchar(32)  not null,
    creator_name             varchar(32)  not null,
    create_time              datetime(3)  not null,
    modifier                 varchar(32)  not null,
    modifier_name            varchar(32)  not null,
    modify_time              datetime(3)  not null
);



create table t_cascade_current_config
(
    id                      bigint auto_increment
        primary key,
    code                    varchar(100) not null,
    name                    varchar(100) not null,
    log_group               char(32)     not null comment '日志组',
    log_snapshot_id         char(32)     not null comment '业务表关联的ID',
    if_log                  tinyint      not null comment '1日志，0非日志。同一个日志组里，0状态的数据只有一个',
    foreign_id              bigint       not null comment '外键表ID',
    foreign_log_snapshot_id char(32)     not null comment '外键表快照ID',
    creator                 varchar(32)  not null,
    creator_name            varchar(32)  not null,
    create_time             datetime(3)  not null,
    modifier                varchar(32)  not null,
    modifier_name           varchar(32)  not null,
    modify_time             datetime(3)  not null
);



create table t_cascade_current_config_his_state
(
    id                        bigint auto_increment
        primary key,
    state_id                  char(32)     not null,
    cascade_current_config_id bigint       not null,
    code                      varchar(100) not null,
    name                      varchar(100) not null,
    log_group                 char(32)     not null comment '日志组',
    log_snapshot_id           char(32)     not null comment '业务表关联的ID',
    if_log                    tinyint      not null comment '1日志，0非日志。同一个日志组里，0状态的数据只有一个',
    foreign_id                bigint       not null comment '外键表ID',
    foreign_log_snapshot_id   char(32)     not null comment '外键表快照ID',
    creator                   varchar(32)  not null,
    creator_name              varchar(32)  not null,
    create_time               datetime(3)  not null,
    modifier                  varchar(32)  not null,
    modifier_name             varchar(32)  not null,
    modify_time               datetime(3)  not null
);



create table t_cascade_child_config
(
    id                      bigint auto_increment
        primary key,
    code                    varchar(100) not null,
    name                    varchar(100) not null,
    log_group               char(32)     not null comment '日志组',
    log_snapshot_id         char(32)     not null comment '业务表关联的ID',
    if_log                  tinyint      not null comment '1日志，0非日志。同一个日志组里，0状态的数据只有一个',
    foreign_id              bigint       not null comment '外键表ID',
    foreign_log_snapshot_id char(32)     not null comment '外键表快照ID',
    creator                 varchar(32)  not null,
    creator_name            varchar(32)  not null,
    create_time             datetime(3)  not null,
    modifier                varchar(32)  not null,
    modifier_name           varchar(32)  not null,
    modify_time             datetime(3)  not null
);



create table t_cascade_child_config_his_state
(
    id                      bigint auto_increment
        primary key,
    state_id                char(32)     not null,
    cascade_child_config_id bigint       not null,
    code                    varchar(100) not null,
    name                    varchar(100) not null,
    log_group               char(32)     not null comment '日志组',
    log_snapshot_id         char(32)     not null comment '业务表关联的ID',
    if_log                  tinyint      not null comment '1日志，0非日志。同一个日志组里，0状态的数据只有一个',
    foreign_id              bigint       not null comment '外键表ID',
    foreign_log_snapshot_id char(32)     not null comment '外键表快照ID',
    creator                 varchar(32)  not null,
    creator_name            varchar(32)  not null,
    create_time             datetime(3)  not null,
    modifier                varchar(32)  not null,
    modifier_name           varchar(32)  not null,
    modify_time             datetime(3)  not null
);



delete from t_standalone_config;
delete from t_cascade_parent_config;
delete from t_cascade_parent_config_his_state;
delete from t_cascade_current_config;
delete from t_cascade_current_config_his_state;
delete from t_cascade_child_config;
delete from t_cascade_child_config_his_state;

