
-- type package declaration
create or replace package pdtypes  
as
    type ref_cursor is ref cursor;
end;
/

-- integrity package declaration
create or replace package integritypackage as
 procedure initnestlevel;
 function getnestlevel return number;
 procedure nextnestlevel;
 procedure previousnestlevel;
 end integritypackage;
/

-- integrity package definition
create or replace package body integritypackage as
 nestlevel number;

-- procedure to initialize the trigger nest level
 procedure initnestlevel is
 begin
 nestlevel := 0;
 end;


-- function to return the trigger nest level
 function getnestlevel return number is
 begin
 if nestlevel is null then
     nestlevel := 0;
 end if;
 return(nestlevel);
 end;

-- procedure to increase the trigger nest level
 procedure nextnestlevel is
 begin
 if nestlevel is null then
     nestlevel := 0;
 end if;
 nestlevel := nestlevel + 1;
 end;

-- procedure to decrease the trigger nest level
 procedure previousnestlevel is
 begin
 nestlevel := nestlevel - 1;
 end;

 end integritypackage;
/

drop trigger tib_t_constants
/

drop trigger tib_t_dictionary
/

drop trigger tib_t_menu_permis
/

drop trigger tib_t_menus
/

drop trigger tib_t_permis_res
/

drop trigger tib_t_permissions
/

drop trigger tib_t_resources
/

drop trigger tib_t_role_permis
/

drop trigger tib_t_roles
/

drop trigger tib_t_user_permis
/

drop trigger tib_t_user_role
/

drop trigger tib_t_users
/

alter table t_dictionary
   drop constraint fk_dictionary_parent
/

alter table t_menu_permis
   drop constraint fk_mp_m
/

alter table t_menu_permis
   drop constraint fk_mp_p
/

alter table t_menus
   drop constraint fk_menus_parent
/

alter table t_permis_res
   drop constraint fk_prs_p
/

alter table t_permis_res
   drop constraint fk_prs_rs
/

alter table t_role_permis
   drop constraint fk_rp_p
/

alter table t_role_permis
   drop constraint fk_rp_r
/

alter table t_user_permis
   drop constraint fk_up_p
/

alter table t_user_permis
   drop constraint fk_up_u
/

alter table t_user_role
   drop constraint fk_ur_r
/

alter table t_user_role
   drop constraint fk_ur_u
/

drop table t_constants cascade constraints
/

drop index idx_dictionary_parent
/

drop table t_dictionary cascade constraints
/

drop index idx_menu_permis_p
/

drop index idx_menu_permis_mp
/

drop table t_menu_permis cascade constraints
/

drop index idx_menus_parent
/

drop table t_menus cascade constraints
/

drop index idx_permis_res_rs
/

drop index idx_permis_res_prs
/

drop table t_permis_res cascade constraints
/

drop index idx_permissions_authority
/

drop table t_permissions cascade constraints
/

drop table t_resources cascade constraints
/

drop index idx_role_permis_p
/

drop index idx_role_permis_rp
/

drop table t_role_permis cascade constraints
/

drop table t_roles cascade constraints
/

drop index idx_user_permis_p
/

drop index idx_user_permis_up
/

drop table t_user_permis cascade constraints
/

drop index idx_user_role_r
/

drop index idx_user_role_ur
/

drop table t_user_role cascade constraints
/

drop index idx_users_name
/

drop table t_users cascade constraints
/

drop sequence s_t_constants
/

drop sequence s_t_dictionary
/

drop sequence s_t_menu_permis
/

drop sequence s_t_menus
/

drop sequence s_t_permis_res
/

drop sequence s_t_permissions
/

drop sequence s_t_resources
/

drop sequence s_t_role_permis
/

drop sequence s_t_roles
/

drop sequence s_t_user_permis
/

drop sequence s_t_user_role
/

drop sequence s_t_users
/

create sequence s_t_constants minvalue 10001
/

create sequence s_t_dictionary minvalue 10001
/

create sequence s_t_menu_permis minvalue 10001
/

create sequence s_t_menus minvalue 10001
/

create sequence s_t_permis_res minvalue 10001
/

create sequence s_t_permissions minvalue 10001
/

create sequence s_t_resources minvalue 10001
/

create sequence s_t_role_permis minvalue 10001
/

create sequence s_t_roles minvalue 10001
/

create sequence s_t_user_permis minvalue 10001
/

create sequence s_t_user_role minvalue 10001
/

create sequence s_t_users minvalue 10001
/

create table t_constants  (
   id                   integer                       not null,
   predefined           smallint                       default 0 not null,
   name                 varchar2(50)                    not null,
   value                number(16,4)                    not null,
   descr                varchar2(200),
   constraint pk_t_constants primary key (id)
)
/

comment on table t_constants is
'常量'
/

comment on column t_constants.predefined is
'是否预定义。0-否、1-是。'
/

comment on column t_constants.name is
'常量名。创建后不能修改。'
/

comment on column t_constants.value is
'常量值。'
/

comment on column t_constants.descr is
'说明文字。'
/

create table t_dictionary  (
   id                   integer                       not null,
   predefined           smallint                       default 0 not null,
   code                 varchar2(50),
   name                 varchar2(20),
   asscnames           varchar2(200),
   text                 varchar2(100),
   descr	            varchar2(200),
   value                integer,
   parent_id            integer,
   layer                integer,
   display_order        integer                        default 99999999 not null,
   constraint pk_t_dictionary primary key (id)
)
/

comment on table t_dictionary is
'字典'
/

comment on column t_dictionary.predefined is
'是否预定义。0-否、1-是。'
/

comment on column t_dictionary.code is
'字典项代码，记录级联路径上的所有节点id，方便树型结构的查询。节点id之间用/连接。例如1/2/3/4。当该字典项为顶层节点时，code=id。'
/

comment on column t_dictionary.name is
'字典项名称。直接绑定到代码中的名称，不可维护，初始化数据时指定，主要用于生成客户端变量名。'
/

comment on column t_dictionary.asscnames is
'关联的其他字典项的name，多个name间用,分隔。直接绑定到代码中的名称，不可维护，初始化数据时指定，用于下拉列表的联动显示。'
/

comment on column t_dictionary.text is
'字典项显示文字。'
/

comment on column t_dictionary.descr is
'说明文字。'
/

comment on column t_dictionary.value is
'字典项值。默认取值为该字典项的id，如果代码中需要判断，可根据需要手工指定为一个整数常量。通常预定义的字典项需要手工指定value。'
/

comment on column t_dictionary.parent_id is
'父字典项。'
/

comment on column t_dictionary.layer is
'字典项在级联树型结构中所处层数，当该字典项为顶层节点时，layer=0。事实上，layer等于code中/的数目。'
/

comment on column t_dictionary.display_order is
'显示次序，升序排列。'
/

create index idx_dictionary_parent on t_dictionary (
   parent_id asc
)
/

create table t_menu_permis  (
   id                   integer                       not null,
   menu_id              integer                         not null,
   permis_id            integer                         not null,
   constraint pk_t_menu_permis primary key (id)
)
/

create unique index idx_menu_permis_mp on t_menu_permis (
   menu_id asc,
   permis_id asc
)
/

create index idx_menu_permis_p on t_menu_permis (
   permis_id asc
)
/

create table t_menus  (
   id                   integer                       not null,
   code                 varchar2(50),
   name                 varchar2(50)                  not null,
   url                  varchar2(200),
   suburl               varchar2(200),
   img                  varchar2(200),
   target               varchar2(20),
   parent_id            integer,
   display_order        integer                        default 99999999 not null,
   constraint pk_t_menus primary key (id)
)
/

comment on table t_menus is
'菜单'
/

comment on column t_menus.code is
'菜单代码，记录级联路径上的所有节点id，方便树型结构的查询。节点id之间用/连接。例如1/2/3/4。当该菜单为顶层节点时，code=id。'
/

comment on column t_menus.name is
'菜单名（显示文字）。'
/

comment on column t_menus.url is
'链接地址。'
/

comment on column t_menus.suburl is
'用于获取子菜单的链接地址。'
/

comment on column t_menus.img is
'图片地址。'
/

comment on column t_menus.target is
'目标窗口。'
/

comment on column t_menus.parent_id is
'父菜单。'
/

comment on column t_menus.display_order is
'显示顺序，升序排列。'
/

create index idx_menus_parent on t_menus (
   parent_id asc
)
/

create table t_permis_res  (
   id                   integer                       not null,
   permis_id            integer                         not null,
   res_id               integer                         not null,
   constraint pk_t_permis_res primary key (id)
)
/

create unique index idx_permis_res_prs on t_permis_res (
   permis_id asc,
   res_id asc
)
/

create index idx_permis_res_rs on t_permis_res (
   res_id asc
)
/

create table t_permissions  (
   id                   integer                       not null,
   name                 varchar2(50)                    not null,
   authority            varchar2(50)                    not null,
   descr                varchar2(200),
   constraint pk_t_permissions primary key (id)
)
/

comment on table t_permissions is
'权限（控制对资源的访问）'
/

comment on column t_permissions.name is
'权限名。'
/

comment on column t_permissions.authority is
'权限(AUTH_开头的英文串，约定为大写)。'
/

comment on column t_permissions.descr is
'文字说明。'
/

create unique index idx_permissions_authority on t_permissions (
   authority asc
)
/

create table t_resources  (
   id                   integer                       not null,
   name                 varchar2(50)                    not null,
   url                  varchar2(200)                   not null,
   descr                varchar2(200),
   constraint pk_t_resources primary key (id)
)
/

comment on table t_resources is
'资源（以URL标识）'
/

comment on column t_resources.name is
'资源名。'
/

comment on column t_resources.url is
'资源地址（依程序中设置不同，可使用ANT通配符或正则表达式，程序默认为ANT通配符）。'
/

comment on column t_resources.descr is
'文字说明。'
/

create table t_role_permis  (
   id                   integer                       not null,
   role_id              integer                         not null,
   permis_id            integer                         not null,
   constraint pk_t_role_permis primary key (id)
)
/

create unique index idx_role_permis_rp on t_role_permis (
   role_id asc,
   permis_id asc
)
/

create index idx_role_permis_p on t_role_permis (
   permis_id asc
)
/

create table t_roles  (
   id                   integer                       not null,
   name                 varchar2(50)                    not null,
   descr                varchar2(200),
   constraint pk_t_roles primary key (id)
)
/

comment on table t_roles is
'角色（权限的集合）'
/

comment on column t_roles.name is
'角色名。'
/

comment on column t_roles.descr is
'说明文字。'
/

create table t_user_permis  (
   id                   integer                       not null,
   user_id              integer                         not null,
   permis_id            integer                         not null,
   constraint pk_t_user_permis primary key (id)
)
/

create unique index idx_user_permis_up on t_user_permis (
   user_id asc,
   permis_id asc
)
/

create index idx_user_permis_p on t_user_permis (
   permis_id asc
)
/

create table t_user_role  (
   id                   integer                       not null,
   user_id              integer                         not null,
   role_id              integer                         not null,
   role_level           smallint,
   constraint pk_t_user_role primary key (id)
)
/

comment on column t_user_role.user_id is
'用户。'
/

comment on column t_user_role.role_id is
'角色。'
/

comment on column t_user_role.role_level is
'该角色对该用户的优先级。0-默认角色。'
/

create unique index idx_user_role_ur on t_user_role (
   user_id asc,
   role_id asc
)
/

create index idx_user_role_r on t_user_role (
   role_id asc
)
/

create table t_users  (
   id                   integer                       not null,
   predefined           smallint                       default 0 not null,
   enabled              smallint                       default 1 not null,
   name                 varchar2(20)                    not null,
   fullname             varchar2(50),
   password             varchar2(200),
   title                varchar2(50),
   phone                varchar2(50),
   mobile               varchar2(50),
   email                varchar2(50),
   descr                varchar2(200),
   constraint pk_t_users primary key (id)
)
/

comment on table t_users is
'用户'
/

comment on column t_users.predefined is
'是否预定义。0-否、1-是。'
/

comment on column t_users.enabled is
'是否启用。0-否、1-是。'
/

comment on column t_users.name is
'登录名。'
/

comment on column t_users.fullname is
'全名（真实姓名）。'
/

comment on column t_users.password is
'密码。'
/

comment on column t_users.title is
'头衔/职务。'
/

comment on column t_users.phone is
'办公电话。'
/

comment on column t_users.mobile is
'手机。'
/

comment on column t_users.email is
'电子邮箱。'
/

comment on column t_users.descr is
'说明文字。'
/

create unique index idx_users_name on t_users (
   name asc
)
/

alter table t_dictionary
   add constraint fk_dictionary_parent foreign key (parent_id)
      references t_dictionary (id)
      on delete cascade
/

alter table t_menu_permis
   add constraint fk_mp_m foreign key (menu_id)
      references t_menus (id)
      on delete cascade
/

alter table t_menu_permis
   add constraint fk_mp_p foreign key (permis_id)
      references t_permissions (id)
      on delete cascade
/

alter table t_menus
   add constraint fk_menus_parent foreign key (parent_id)
      references t_menus (id)
      on delete cascade
/

alter table t_permis_res
   add constraint fk_prs_p foreign key (permis_id)
      references t_permissions (id)
      on delete cascade
/

alter table t_permis_res
   add constraint fk_prs_rs foreign key (res_id)
      references t_resources (id)
      on delete cascade
/

alter table t_role_permis
   add constraint fk_rp_p foreign key (permis_id)
      references t_permissions (id)
      on delete cascade
/

alter table t_role_permis
   add constraint fk_rp_r foreign key (role_id)
      references t_roles (id)
      on delete cascade
/

alter table t_user_permis
   add constraint fk_up_p foreign key (permis_id)
      references t_permissions (id)
      on delete cascade
/

alter table t_user_permis
   add constraint fk_up_u foreign key (user_id)
      references t_users (id)
      on delete cascade
/

alter table t_user_role
   add constraint fk_ur_r foreign key (role_id)
      references t_roles (id)
      on delete cascade
/

alter table t_user_role
   add constraint fk_ur_u foreign key (user_id)
      references t_users (id)
      on delete cascade
/

create trigger tib_t_constants before insert
on t_constants for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  column "id" uses sequence s_t_constants
    select s_t_constants.nextval into :new.id from dual;

--  errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger tib_t_dictionary before insert
on t_dictionary for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  column "id" uses sequence s_t_dictionary
    select s_t_dictionary.nextval into :new.id from dual;

--  errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger tib_t_menu_permis before insert
on t_menu_permis for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  column "id" uses sequence s_t_menu_permis
    select s_t_menu_permis.nextval into :new.id from dual;

--  errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger tib_t_menus before insert
on t_menus for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  column "id" uses sequence s_t_menus
    select s_t_menus.nextval into :new.id from dual;

--  errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger tib_t_permis_res before insert
on t_permis_res for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  column "id" uses sequence s_t_permis_res
    select s_t_permis_res.nextval into :new.id from dual;

--  errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger tib_t_permissions before insert
on t_permissions for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  column "id" uses sequence s_t_permissions
    select s_t_permissions.nextval into :new.id from dual;

--  errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger tib_t_resources before insert
on t_resources for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  column "id" uses sequence s_t_resources
    select s_t_resources.nextval into :new.id from dual;

--  errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger tib_t_role_permis before insert
on t_role_permis for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  column "id" uses sequence s_t_role_permis
    select s_t_role_permis.nextval into :new.id from dual;

--  errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger tib_t_roles before insert
on t_roles for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  column "id" uses sequence s_t_roles
    select s_t_roles.nextval into :new.id from dual;

--  errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger tib_t_user_permis before insert
on t_user_permis for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  column "id" uses sequence s_t_user_permis
    select s_t_user_permis.nextval into :new.id from dual;

--  errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger tib_t_user_role before insert
on t_user_role for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  column "id" uses sequence s_t_user_role
    select s_t_user_role.nextval into :new.id from dual;

--  errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger tib_t_users before insert
on t_users for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  column "id" uses sequence s_t_users
    select s_t_users.nextval into :new.id from dual;

--  errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/