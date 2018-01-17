/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     2018/1/17 15:58:39                           */
/*==============================================================*/


drop index RAIN_MENU_PK;

drop table RAIN_MENU;

drop index RAIN_ROLE_PK;

drop table RAIN_ROLE;

drop index Relationship_2_FK;

drop index Relationship_1_FK;

drop index ROLE_MENU_PK;

drop table ROLE_MENU;

/*==============================================================*/
/* Table: RAIN_MENU                                             */
/*==============================================================*/
create table RAIN_MENU (
   MENU_ID              INT4                 not null,
   MENU_NAME            VARCHAR(100)         null,
   MENU_URL             VARCHAR(200)         null,
   MENU_ICON            VARCHAR(200)         null,
   PARENT_ID            INT4                 null,
   constraint PK_RAIN_MENU primary key (MENU_ID)
);

/*==============================================================*/
/* Index: RAIN_MENU_PK                                          */
/*==============================================================*/
create unique index RAIN_MENU_PK on RAIN_MENU (
MENU_ID
);

/*==============================================================*/
/* Table: RAIN_ROLE                                             */
/*==============================================================*/
create table RAIN_ROLE (
   ROLE_ID              INT4                 not null,
   ROLE_NAME            VARCHAR(50)          null,
   CREATE_TIME          DATE                 null,
   UPDATE_TIME          DATE                 null,
   constraint PK_RAIN_ROLE primary key (ROLE_ID)
);

/*==============================================================*/
/* Index: RAIN_ROLE_PK                                          */
/*==============================================================*/
create unique index RAIN_ROLE_PK on RAIN_ROLE (
ROLE_ID
);

/*==============================================================*/
/* Table: ROLE_MENU                                             */
/*==============================================================*/
create table ROLE_MENU (
   ROLE_MENU_ID         INT4                 not null,
   ROLE_ID              INT4                 null,
   MENU_ID              INT4                 null,
   constraint PK_ROLE_MENU primary key (ROLE_MENU_ID)
);

/*==============================================================*/
/* Index: ROLE_MENU_PK                                          */
/*==============================================================*/
create unique index ROLE_MENU_PK on ROLE_MENU (
ROLE_MENU_ID
);

/*==============================================================*/
/* Index: Relationship_1_FK                                     */
/*==============================================================*/
create  index Relationship_1_FK on ROLE_MENU (
MENU_ID
);

/*==============================================================*/
/* Index: Relationship_2_FK                                     */
/*==============================================================*/
create  index Relationship_2_FK on ROLE_MENU (
ROLE_ID
);

alter table ROLE_MENU
   add constraint FK_ROLE_MEN_RELATIONS_RAIN_MEN foreign key (MENU_ID)
      references RAIN_MENU (MENU_ID)
      on delete restrict on update restrict;

alter table ROLE_MENU
   add constraint FK_ROLE_MEN_RELATIONS_RAIN_ROL foreign key (ROLE_ID)
      references RAIN_ROLE (ROLE_ID)
      on delete restrict on update restrict;

