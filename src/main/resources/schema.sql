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

CREATE TABLE T_SYS_UNIT(
    UNIT_ID NVARCHAR2(22) NOT NULL PRIMARY KEY,
    UNIT_NAME NVARCHAR2(50) NOT NULL,
    SUPERIOR_UNIT_ID NVARCHAR2(22)
);

CREATE TABLE T_SYS_USER_ROLE(
  ID NVARCHAR2(50) NOT NULL PRIMARY KEY,
  USER_ID NVARCHAR2(22) NOT NULL,
  ROLE_ID NVARCHAR2(22)
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
                                 PLAN_DATE DATE default sysdate NOT NULL,
                                 TOTAL_HOURS NUMBER(3,1) NOT NULL,
                                 OVER_WORK_HOURS NUMBER(3,1)
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
insert into T_SYS_USER values('test2', 'test2Name', 'unitId1');
insert into T_SYS_USER values('test3', 'test3Name', 'unitId2');
insert into T_SYS_USER values('test4', 'test4Name', 'unitId2');
insert into T_SYS_USER values('test5', 'test5Name', 'unitId3');
insert into T_SYS_USER values('test6', 'test6Name', 'unitId4');
insert into T_SYS_USER values('test7', 'test7Name', 'unitId1');

insert into T_SYS_UNIT values('unitId1', 'IT一科', 'superiorId1');
insert into T_SYS_UNIT values('unitId2', 'IT二科', 'superiorId1');
insert into T_SYS_UNIT values('unitId3', 'Test一科', 'superiorId2');
insert into T_SYS_UNIT values('unitId4', 'Test二科', 'superiorId2');
insert into T_SYS_UNIT values('unitId5', 'Test系統維運一科', 'superiorId3');
insert into T_SYS_UNIT values('superiorId1', 'IT部', '');
insert into T_SYS_UNIT values('superiorId2', 'Test部', '');
insert into T_SYS_UNIT values('superiorId3', 'Test系統維運部', '');

insert into T_SYS_USER_ROLE values('001', 'test1', 'ROLE_EMPLOYEE');
insert into T_SYS_USER_ROLE values('002', 'test2', 'ROLE_EMPLOYEE');
insert into T_SYS_USER_ROLE values('003', 'test7', 'ROLE_SUPERVISOR');

-- insert into T_TMD_TASK_TYPE values('taskTypeId001', 'taskType001');
-- insert into T_TMD_TASK_TYPE values('taskTypeId002', 'taskType002');
-- insert into T_TMD_TASK_TYPE values('taskTypeId003', 'taskType003');
-- insert into T_TMD_TASK_TYPE values('taskTypeId004', 'taskType004');
--
-- insert into T_TMD_TASK_ITEM values('taskItemId001', 'taskItem001');
-- insert into T_TMD_TASK_ITEM values('taskItemId002', 'taskItem002');
-- insert into T_TMD_TASK_ITEM values('taskItemId003', 'taskItem003');
-- insert into T_TMD_TASK_ITEM values('taskItemId004', 'taskItem004');
--
-- insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping001', 'taskItemId001', 'taskTypeId001');
-- insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping002', 'taskItemId002', 'taskTypeId001');
-- insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping003', 'taskItemId003', 'taskTypeId002');
-- insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping004', 'taskItemId004', 'taskTypeId002');

insert into T_TMD_TASK_TYPE values('SYS_MAINTEN', '系統為運');
insert into T_TMD_TASK_TYPE values('PROJECT_DEV', '專案開發');
insert into T_TMD_TASK_TYPE values('OFF_HOURS', '公出');
insert into T_TMD_TASK_TYPE values('DAILY', '日常行政');
insert into T_TMD_TASK_TYPE values('OTHER', '其他');

insert into T_TMD_TASK_ITEM values('taskItemId001', 'taskItem001');
insert into T_TMD_TASK_ITEM values('taskItemId002', 'taskItem002');
insert into T_TMD_TASK_ITEM values('taskItemId003', 'taskItem003');
insert into T_TMD_TASK_ITEM values('taskItemId004', 'taskItem004');
insert into T_TMD_TASK_ITEM values('taskItemId005', 'taskItem005');
insert into T_TMD_TASK_ITEM values('taskItemId006', 'taskItem006');
insert into T_TMD_TASK_ITEM values('taskItemId007', 'taskItem007');

insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping001', 'taskItemId001', 'SYS_MAINTEN');
insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping002', 'taskItemId002', 'SYS_MAINTEN');
insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping003', 'taskItemId003', 'PROJECT_DEV');
insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping004', 'taskItemId004', 'PROJECT_DEV');
insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping005', 'taskItemId005', 'OFF_HOURS');
insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping006', 'taskItemId006', 'DAILY');
insert into T_TMD_TASK_ITEM_TASK_TYPE values('mapping007', 'taskItemId007', 'OTHER');


insert into T_TMD_DAILY_PLAN values('d001', 'test1', to_date('2021-12-01', 'yyyy-MM-dd'), 2.5, 0);
insert into T_TMD_DAILY_PLAN values('d002', 'test1', to_date('2021-12-02', 'yyyy-MM-dd'), 4.5, 0);
insert into T_TMD_DAILY_PLAN values('d003', 'test1', to_date('2021-12-03', 'yyyy-MM-dd'), 3.0, 0);
insert into T_TMD_DAILY_PLAN values('d004', 'test1', to_date('2021-12-04', 'yyyy-MM-dd'), 0.0, 0);
insert into T_TMD_DAILY_PLAN values('d005', 'test3', to_date('2021-12-01', 'yyyy-MM-dd'), 3.0, 0);
insert into T_TMD_DAILY_PLAN values('d006', 'test5', to_date('2021-12-01', 'yyyy-MM-dd'), 3.0, 0);
insert into T_TMD_DAILY_PLAN values('d007', 'test6', to_date('2021-12-01', 'yyyy-MM-dd'), 3.0, 0);

INSERT INTO T_TMD_TASKS VALUES( 't001', 'taskName1', 'test1', 'mapping001',  systimestamp);
INSERT INTO T_TMD_TASKS VALUES( 't002', 'taskName2', 'test1', 'mapping002', systimestamp);
INSERT INTO T_TMD_TASKS VALUES( 't003', 'taskName3', 'test1', 'mapping003', systimestamp);
INSERT INTO T_TMD_TASKS VALUES( 't004', 'taskName4', 'test1', 'mapping004', systimestamp);
INSERT INTO T_TMD_TASKS VALUES( 't005', 'taskName5', 'test3', 'mapping001', systimestamp);
INSERT INTO T_TMD_TASKS VALUES( 't006', 'taskName6', 'test5', 'mapping002', systimestamp);
INSERT INTO T_TMD_TASKS VALUES( 't007', 'taskName7', 'test6', 'mapping003', systimestamp);
INSERT INTO T_TMD_TASKS VALUES( 't008', 'taskName8', 'test3', 'mapping004', systimestamp);
INSERT INTO T_TMD_TASKS VALUES( 't009', 'taskName9', 'test3', 'mapping005', systimestamp);
INSERT INTO T_TMD_TASKS VALUES( 't0010', 'taskName10', 'test3', 'mapping006', systimestamp);
INSERT INTO T_TMD_TASKS VALUES( 't0011', 'taskName11', 'test3', 'mapping007', systimestamp);

INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't001', 'd001', 1.0);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't002', 'd001', 1.5);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't003', 'd002', 2.0);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't004', 'd002', 2.5);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't003', 'd003', 3.0);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't005', 'd005', 3.0);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't006', 'd006', 3.0);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't007', 'd007', 3.0);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't008', 'd005', 3.0);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't009', 'd005', 4.0);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't0010', 'd005', 5.0);
INSERT INTO T_TMD_TASK_DAILY_PLAN VALUES (SYS_GUID(), 't0011', 'd005', 6.0);

COMMIT;