
-- 초기 SUPERUSER 계정 생성
insert into account(
    id, email, name,
    password, role, status,
    address_dong, address_gu, gender,
    avg_speed, weight, created_at,
    updated_at
)
values(
'1cd40046-6dcf-4172-99eb-2b6f7377bc80','wjdtn747@naver.com', '이정수',
'$2a$10$m/BzNm4PYtv7MrxuSzNg5.DYxPIpOCSB4LSh4JbrdA4HRDmgwJdwi','SUPERADMIN', 'ACTIVATED',
'Hwigyeong', 'Dondaemun', 'MALE',
15, 80, '2025-12-25 18:16:33.329914+09',
'2025-12-25 18:16:33.329914+09'
);



-- 초기 링크데이터 구성
insert into linknetwork(
    id, fnode, tnode,
    geom, link_length, link_slope,
    link_cost, link_type, drink_toilet
)
select
linkid, fnode, tnode,
    geom, link_len, slope_angle,
tobler_sec, link_type, drink_toilet
    from baselink;

ALTER TABLE linknetwork ALTER COLUMN geom
    TYPE geometry
    USING ST_GeomFromEWKB(decode(geom, 'hex'));


-- 초기 노드데이터 구성
insert into nodenetwork(id, geom)
select nodeid,geom
from basenode;

ALTER TABLE nodenetwork ALTER COLUMN geom
    TYPE geometry
    USING ST_GeomFromEWKB(decode(geom, 'hex'));

update nodenetwork
set geom = ST_SetSRID(geom,5174);


-- PostgreSQL 확장
CREATE EXTENSION IF NOT EXISTS pgrouting;