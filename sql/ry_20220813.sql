DROP TABLE IF EXISTS sys_ddns;
CREATE TABLE sys_ddns
(
    ddns_id           int          NOT NULL AUTO_INCREMENT COMMENT 'ddns ID',
    domain            varchar(500) NOT NULL COMMENT '域名',
    host_record       varchar(255) NOT NULL COMMENT '主机记录',
    parse_record_type varchar(255) NOT NULL COMMENT '解析记录类型',
    access_id         int          NOT NULL COMMENT 'Access ID',
    remark            varchar(500) NULL DEFAULT NULL COMMENT '备注',
    create_by         varchar(64)  NULL DEFAULT '' COMMENT '创建者',
    create_time       datetime     NULL DEFAULT NULL COMMENT '创建时间',
    update_by         varchar(64)  NULL DEFAULT '' COMMENT '更新者',
    update_time       datetime     NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (ddns_id) USING BTREE
) ENGINE = InnoDB
    COMMENT = 'ddns 解析配置表';

-- 菜单 SQL
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query,
                      is_frame, is_cache, menu_type, visible, status, perms, icon, create_by,
                      create_time, update_by, update_time, remark)
VALUES (4, '运维工具', 0, 4, 'ops', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'ops', 'admin', '2022-08-13 10:36:25', '',
        NULL, '');


insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('ddns 解析配置', '4', '1', 'ddns', 'ops/ddns/index', 1, 0, 'C', '0', '0', 'ops:ddns:list', '#', 'admin',
        sysdate(), '', null, 'ddns 解析配置菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('ddns 解析配置查询', @parentId, '1', '#', '', 1, 0, 'F', '0', '0', 'ops:ddns:query', '#', 'admin', sysdate(),
        '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('ddns 解析配置新增', @parentId, '2', '#', '', 1, 0, 'F', '0', '0', 'ops:ddns:add', '#', 'admin', sysdate(), '',
        null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('ddns 解析配置修改', @parentId, '3', '#', '', 1, 0, 'F', '0', '0', 'ops:ddns:edit', '#', 'admin', sysdate(), '',
        null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('ddns 解析配置删除', @parentId, '4', '#', '', 1, 0, 'F', '0', '0', 'ops:ddns:remove', '#', 'admin', sysdate(),
        '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('ddns 解析配置导出', @parentId, '5', '#', '', 1, 0, 'F', '0', '0', 'ops:ddns:export', '#', 'admin', sysdate(),
        '', null, '');

DROP TABLE IF EXISTS sys_third_access;
CREATE TABLE sys_third_access
(
    access_id         int          NOT NULL AUTO_INCREMENT COMMENT 'Access ID',
    access_key_secret varchar(255) NOT NULL COMMENT 'AccessKey Secret',
    access_key_id     varchar(255) NOT NULL COMMENT 'AccessKey ID',
    access_key_type   varchar(255) NULL DEFAULT NULL COMMENT 'AccessKey 平台',
    create_by         varchar(64)  NULL DEFAULT '' COMMENT '创建者',
    create_time       datetime     NULL DEFAULT NULL COMMENT '创建时间',
    update_by         varchar(64)  NULL DEFAULT '' COMMENT '更新者',
    update_time       datetime     NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (access_id) USING BTREE
) ENGINE = InnoDB
    COMMENT = '第三方AccessKey管理';

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('第三方AccessKey管理', '4', '1', 'access', 'system/access/index', 1, 0, 'C', '0', '0', 'ops:access:list', '#',
        'admin', sysdate(), '', null, '第三方AccessKey管理菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('第三方AccessKey管理查询', @parentId, '1', '#', '', 1, 0, 'F', '0', '0', 'ops:access:query', '#', 'admin',
        sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('第三方AccessKey管理新增', @parentId, '2', '#', '', 1, 0, 'F', '0', '0', 'ops:access:add', '#', 'admin',
        sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('第三方AccessKey管理修改', @parentId, '3', '#', '', 1, 0, 'F', '0', '0', 'ops:access:edit', '#', 'admin',
        sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('第三方AccessKey管理删除', @parentId, '4', '#', '', 1, 0, 'F', '0', '0', 'ops:access:remove', '#', 'admin',
        sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('第三方AccessKey管理导出', @parentId, '5', '#', '', 1, 0, 'F', '0', '0', 'ops:access:export', '#', 'admin',
        sysdate(), '', null, '');