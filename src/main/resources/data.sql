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


insert into linknetwork(
                    id, fnode, tnode,
                    geojson, link_length, link_slope,
                    link_cost, link_type, drink_toilet
)
select
    linkid, fnode, tnode,
    geom, link_len, slope_angle,
    tobler_sec, link_type, drink_toilet
    from baselink;

ALTER TABLE linknetwork ALTER COLUMN geojson
    TYPE geometry
    USING ST_GeomFromEWKB(decode(geojson, 'hex'));

insert into nodenetwork(id, geojson)
select
    nodeid,geom
from basenode;

ALTER TABLE nodenetwork ALTER COLUMN geojson
    TYPE geometry
    USING ST_GeomFromEWKB(decode(geojson, 'hex'));