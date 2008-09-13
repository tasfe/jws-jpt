/*==============================================================*/
/* Table: t_constants                                           */
/*==============================================================*/
create table t_constants
(
   id                   integer not null auto_increment,
   predefined           tinyint not null default 0 comment '是否预定义。0-否、1-是。',
   name                 varchar(50) not null comment '常量名。创建后不能修改。',
   value                numeric(16,4) not null comment '常量值。',
   descr                varchar(200) comment '说明文字。',
   primary key (id)
) type=InnoDB;

alter table t_constants comment '常量';

/*==============================================================*/
/* Table: t_dictionary                                          */
/*==============================================================*/
create table t_dictionary
(
   id                   integer not null auto_increment,
   predefined           tinyint not null default 0 comment '是否预定义。0-否、1-是。',
   code                 varchar(50) comment '字典项代码，记录级联路径上的所有节点id，方便树型结构的查询。节点id之间用/连接。例如1/2/3/4。当该字典项为顶层节点时，code=id。',
   name                 varchar(20) comment '字典项名称。直接绑定到代码中的名称，不可维护，初始化数据时指定，主要用于生成客户端变量名。',
   assc_names           varchar(200) comment '关联的其他字典项的name，多个name间用,分隔。直接绑定到代码中的名称，不可维护，初始化数据时指定，用于下拉列表的联动显示。',
   text                 varchar(100) comment '字典项显示文字。',
   descr                varchar(200) comment '说明文字。',
   value                integer comment '字典项值。默认取值为该字典项的id，如果代码中需要判断，可根据需要手工指定为一个整数常量。通常预定义的字典项需要手工指定value。',
   parent_id            integer comment '父字典项。',
   layer                integer comment '字典项在级联树型结构中所处层数，当该字典项为顶层节点时，layer=0。事实上，layer等于code中/的数目。',
   display_order        integer not null default 99999999 comment '显示次序，升序排列。',
   primary key (id)
) type=InnoDB;

alter table t_dictionary comment '字典';

/*==============================================================*/
/* Index: idx_dictionary_parent                                 */
/*==============================================================*/
create index idx_dictionary_parent on t_dictionary
(
   parent_id
);

/*==============================================================*/
/* Table: t_menu_permis                                         */
/*==============================================================*/
create table t_menu_permis
(
   id                   integer not null auto_increment,
   menu_id              integer not null,
   permis_id            integer not null,
   primary key (id)
) type=InnoDB;

/*==============================================================*/
/* Index: idx_menu_permis_mp                                    */
/*==============================================================*/
create unique index idx_menu_permis_mp on t_menu_permis
(
   menu_id,
   permis_id
);

/*==============================================================*/
/* Index: idx_menu_permis_p                                     */
/*==============================================================*/
create index idx_menu_permis_p on t_menu_permis
(
   permis_id
);

/*==============================================================*/
/* Table: t_menus                                               */
/*==============================================================*/
create table t_menus
(
   id                   integer not null auto_increment,
   code                 varchar(50) comment '菜单代码，记录级联路径上的所有节点id，方便树型结构的查询。节点id之间用/连接。例如1/2/3/4。当该菜单为顶层节点时，code=id。',
   name                 varchar(50) not null comment '菜单名（显示文字）。',
   url                  varchar(200) comment '链接地址。',
   suburl               varchar(200) comment '用于获取子菜单的链接地址。',
   img                  varchar(200) comment '图片地址。',
   target               varchar(20) comment '目标窗口。',
   parent_id            integer comment '父菜单。',
   display_order        integer not null default 99999999 comment '显示顺序，升序排列。',
   primary key (id)
) type=InnoDB;

alter table t_menus comment '菜单';

/*==============================================================*/
/* Index: idx_menus_parent                                      */
/*==============================================================*/
create index idx_menus_parent on t_menus
(
   parent_id
);

/*==============================================================*/
/* Table: t_permis_res                                          */
/*==============================================================*/
create table t_permis_res
(
   id                   integer not null auto_increment,
   permis_id            integer not null,
   res_id               integer not null,
   primary key (id)
) type=InnoDB;

/*==============================================================*/
/* Index: idx_permis_res_prs                                    */
/*==============================================================*/
create unique index idx_permis_res_prs on t_permis_res
(
   permis_id,
   res_id
);

/*==============================================================*/
/* Index: idx_permis_res_rs                                     */
/*==============================================================*/
create index idx_permis_res_rs on t_permis_res
(
   res_id
);

/*==============================================================*/
/* Table: t_permissions                                         */
/*==============================================================*/
create table t_permissions
(
   id                   integer not null auto_increment,
   name                 varchar(50) not null comment '权限名。',
   authority            varchar(50) not null comment '权限(AUTH_开头的英文串，约定为大写)。',
   descr                varchar(200) comment '文字说明。',
   primary key (id)
) type=InnoDB;

alter table t_permissions comment '权限（控制对资源的访问）';

/*==============================================================*/
/* Index: idx_permissions_authority                             */
/*==============================================================*/
create unique index idx_permissions_authority on t_permissions
(
   authority
);

/*==============================================================*/
/* Table: t_resources                                           */
/*==============================================================*/
create table t_resources
(
   id                   integer not null auto_increment,
   name                 varchar(50) not null comment '资源名。',
   url                  varchar(200) not null comment '资源地址（依程序中设置不同，可使用ANT通配符或正则表达式，程序默认为ANT通配符）。',
   descr                varchar(200) comment '文字说明。',
   primary key (id)
) type=InnoDB;

alter table t_resources comment '资源（以URL标识）';

/*==============================================================*/
/* Table: t_role_permis                                         */
/*==============================================================*/
create table t_role_permis
(
   id                   integer not null auto_increment,
   role_id              integer not null,
   permis_id            integer not null,
   primary key (id)
) type=InnoDB;

/*==============================================================*/
/* Index: idx_role_permis_rp                                    */
/*==============================================================*/
create unique index idx_role_permis_rp on t_role_permis
(
   role_id,
   permis_id
);

/*==============================================================*/
/* Index: idx_role_permis_p                                     */
/*==============================================================*/
create index idx_role_permis_p on t_role_permis
(
   permis_id
);

/*==============================================================*/
/* Table: t_roles                                               */
/*==============================================================*/
create table t_roles
(
   id                   integer not null auto_increment,
   name                 varchar(50) not null comment '角色名。',
   descr                varchar(200) comment '说明文字。',
   primary key (id)
) type=InnoDB;

alter table t_roles comment '角色（权限的集合）';

/*==============================================================*/
/* Table: t_user_permis                                         */
/*==============================================================*/
create table t_user_permis
(
   id                   integer not null auto_increment,
   user_id              integer not null,
   permis_id            integer not null,
   primary key (id)
) type=InnoDB;

/*==============================================================*/
/* Index: idx_user_permis_up                                    */
/*==============================================================*/
create unique index idx_user_permis_up on t_user_permis
(
   user_id,
   permis_id
);

/*==============================================================*/
/* Index: idx_user_permis_p                                     */
/*==============================================================*/
create index idx_user_permis_p on t_user_permis
(
   permis_id
);

/*==============================================================*/
/* Table: t_user_role                                           */
/*==============================================================*/
create table t_user_role
(
   id                   integer not null auto_increment,
   user_id              integer not null comment '用户。',
   role_id              integer not null comment '角色。',
   role_level           tinyint comment '该角色对该用户的优先级。0-默认角色。',
   primary key (id)
) type=InnoDB;

/*==============================================================*/
/* Index: idx_user_role_ur                                      */
/*==============================================================*/
create unique index idx_user_role_ur on t_user_role
(
   user_id,
   role_id
);

/*==============================================================*/
/* Index: idx_user_role_r                                       */
/*==============================================================*/
create index idx_user_role_r on t_user_role
(
   role_id
);

/*==============================================================*/
/* Table: t_users                                               */
/*==============================================================*/
create table t_users
(
   id                   integer not null auto_increment,
   predefined           tinyint not null default 0 comment '是否预定义。0-否、1-是。',
   enabled              tinyint not null default 1 comment '是否启用。0-否、1-是。',
   name                 varchar(20) not null comment '登录名。',
   fullname             varchar(50) comment '全名（真实姓名）。',
   password             varchar(200) comment '密码。',
   title                varchar(50) comment '头衔/职务。',
   phone                varchar(50) comment '办公电话。',
   mobile               varchar(50) comment '手机。',
   email                varchar(50) comment '电子邮箱。',
   descr                varchar(200) comment '说明文字。',
   primary key (id)
) type=InnoDB;

alter table t_users comment '用户';

/*==============================================================*/
/* Index: idx_users_name                                        */
/*==============================================================*/
create unique index idx_users_name on t_users
(
   name
);

alter table t_dictionary add constraint fk_dictionary_parent foreign key (parent_id)
      references t_dictionary (id) on delete cascade;

alter table t_menu_permis add constraint fk_mp_m foreign key (menu_id)
      references t_menus (id) on delete cascade;

alter table t_menu_permis add constraint fk_mp_p foreign key (permis_id)
      references t_permissions (id) on delete cascade;

alter table t_menus add constraint fk_menus_parent foreign key (parent_id)
      references t_menus (id) on delete cascade;

alter table t_permis_res add constraint fk_prs_p foreign key (permis_id)
      references t_permissions (id) on delete cascade;

alter table t_permis_res add constraint fk_prs_rs foreign key (res_id)
      references t_resources (id) on delete cascade;

alter table t_role_permis add constraint fk_rp_p foreign key (permis_id)
      references t_permissions (id) on delete cascade;

alter table t_role_permis add constraint fk_rp_r foreign key (role_id)
      references t_roles (id) on delete cascade;

alter table t_user_permis add constraint fk_up_p foreign key (permis_id)
      references t_permissions (id) on delete cascade;

alter table t_user_permis add constraint fk_up_u foreign key (user_id)
      references t_users (id) on delete cascade;

alter table t_user_role add constraint fk_ur_r foreign key (role_id)
      references t_roles (id) on delete cascade;

alter table t_user_role add constraint fk_ur_u foreign key (user_id)
      references t_users (id) on delete cascade;