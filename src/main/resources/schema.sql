CREATE TABLE TEST_TABLE_ENTITY1(
    ID            NUMBER(22)          NOT NULL,
    VAR1          NVARCHAR2(22)       NOT NULL,
    VAR2      NVARCHAR2(22)       NOT NULL
);

ALTER TABLE TEST_TABLE_ENTITY1 ADD CONSTRAINT PK_TTBE1_ID PRIMARY KEY (ID);

CREATE SEQUENCE SEQ_TTBE1 NOCACHE MAXVALUE 9223372036854770000 START WITH 1;

COMMIT;

-----------------------------------------------------------------------


CREATE TABLE T_SYS_USER(
                           USER_ID NVARCHAR2(22) NOT NULL PRIMARY KEY,
                           USER_NAME NVARCHAR2(22) NOT NULL,
                           UNIT_ID NVARCHAR2(22) NOT NULL
);

CREATE TABLE T_TMD_TASK_TYPE(
                                TASK_TYPE_ID NVARCHAR2(50) NOT NULL PRIMARY KEY,
                                TASK_TYPE_NAME NVARCHAR2(50) NOT NULL
);

CREATE TABLE T_TMD_TASK_ITEM(
                                TASK_ITEM_ID NVARCHAR2(50) NOT NULL PRIMARY KEY,
                                TASK_ITEM_NAME NVARCHAR2(50) NOT NULL
);

CREATE TABLE T_TMD_TASK_ITEM_TASK_TYPE(
                                          ID NVARCHAR2(50) NOT NULL PRIMARY KEY,
                                          TASK_ITEM_ID NVARCHAR2(50) NOT NULL,
                                          TASK_TYPE_ID NVARCHAR2(50) NOT NULL
);

CREATE TABLE T_TMD_DAILY_PLAN(
                                 ID NVARCHAR2(50) NOT NULL PRIMARY KEY,
                                 USER_ID NVARCHAR2(22) NOT NULL,
                                 PLAN_DATE DATE default sysdate NOT NULL
);

create table T_TMD_TASKS(
                            ID NVARCHAR2(50) NOT NULL PRIMARY KEY,
                            TASK_NAME NVARCHAR2(100) NOT NULL,
                            ASSINGEE_ID NVARCHAR2(22) NOT NULL,
                            TASK_MAPPING_ID NVARCHAR2(55) NOT NULL,
                            CREATE_DATE TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL
);

create table T_TMD_TASK_DAILY_PLAN(
                                      ID NVARCHAR2(50) NOT NULL PRIMARY KEY,
                                      TASK_ID NVARCHAR2(50) NOT NULL,
                                      DAILY_PLAN_ID NVARCHAR2(50) NOT NULL,
                                      WORKING_HOURS NUMBER(3,1) NOT NULL
);

COMMIT;

insert into T_SYS_USER values('test1', 'test1Name', 'unitId1');
insert into T_SYS_USER values('test2', 'test1Name', 'unitId1');
insert into T_SYS_USER values('test3', 'test1Name', 'unitId2');
insert into T_SYS_USER values('test4', 'test1Name', 'unitId2');

insert into T_TMD_TASK_TYPE values('taskTypeId001', 'taskType001');
insert into T_TMD_TASK_TYPE values('taskTypeId002', 'taskType002');
insert into T_TMD_TASK_TYPE values('taskTypeId003', 'taskType003');
insert into T_TMD_TASK_TYPE values('taskTypeId004', 'taskType004');

insert into T_TMD_TASK_ITEM values('taskItemId001', 'taskItem001');
insert into T_TMD_TASK_ITEM values('taskItemId002', 'taskItem002');
insert into T_TMD_TASK_ITEM values('taskItemId003', 'taskItem003');
insert into T_TMD_TASK_ITEM values('taskItemId004', 'taskItem004');

insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping001', 'taskItemId001', 'taskTypeId001');
insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping002', 'taskItemId002', 'taskTypeId001');
insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping003', 'taskItemId003', 'taskTypeId002');
insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping004', 'taskItemId004', 'taskTypeId002');

insert into T_TMD_DAILY_PLAN values('d001', 'test1', to_date('2021-12-01', 'yyyy-MM-dd'));
insert into T_TMD_DAILY_PLAN values('d002', 'test1', to_date('2021-12-02', 'yyyy-MM-dd'));
insert into T_TMD_DAILY_PLAN values('d003', 'test1', to_date('2021-12-03', 'yyyy-MM-dd'));
insert into T_TMD_DAILY_PLAN values('d004', 'test1', to_date('2021-12-04', 'yyyy-MM-dd'));

INSERT INTO T_TMD_TASKS VALUES( 't001', 'taskName1', 'test1', 'mapping001',  systimestamp);
INSERT INTO T_TMD_TASKS VALUES( 't002', 'taskName2', 'test1', 'mapping002', systimestamp);
INSERT INTO T_TMD_TASKS VALUES( 't003', 'taskName3', 'test1', 'mapping003', systimestamp);
INSERT INTO T_TMD_TASKS VALUES( 't004', 'taskName4', 'test1', 'mapping004', systimestamp);

INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't001', 'd001', 1.0);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't002', 'd001', 1.5);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't003', 'd002', 2.0);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't004', 'd002', 2.5);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't003', 'd003', 3.0);

COMMIT;