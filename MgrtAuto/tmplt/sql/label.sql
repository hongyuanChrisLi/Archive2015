--Source and Target
select 
  b.SUBJ_NAME || ',' || 
   a.OBJECT_NAME  RES
from INFOMETA.OPB_VERSION_PROPS a
inner join INFOMETA.OPB_SUBJECT b on b.subj_id = a.subject_id
inner join INFOMETA.REP_USERS c on a.user_id = c.user_id
where UTC_LAST_SAVED >= $start_ts
and c.user_name = $user
and a.object_type in (1, 2, 23, 24, 25)
-- END
UNION
-- Mapping and Workflow
select 
  b.SUBJ_NAME || ',' || 
  a.OBJECT_NAME  RES
from INFOMETA.OPB_VERSION_PROPS a
inner join INFOMETA.OPB_SUBJECT b on b.subj_id = a.subject_id
inner join INFOMETA.REP_USERS c on a.user_id = c.user_id
where 
a.UTC_LAST_SAVED >= $start_ts 
and a.object_type in (21, 44, 71)
and c.user_name = $user
UNION
-- transformation
select 
  SUBJ_NAME || ',' || 
  OBJECT_NAME RES
from 
(
  select c.subj_name, a.object_id, a.object_type, a.object_name, max(b.VERSION_NUMBER) from 
  INFOMETA.OPB_VERSION_PROPS a
  inner join
  INFOMETA.OPB_WIDGET b on a.object_id = b.WIDGET_id
  inner join INFOMETA.OPB_SUBJECT c on c.subj_id = a.subject_id
  inner join INFOMETA.REP_USERS d on a.user_id = d.user_id
  where a.UTC_LAST_SAVED >= $start_ts
  and a.object_type in (4, 5, 7, 11)
  and b.IS_REUSABLE = 1
  and d.user_name = $user
  group by
  c.subj_name, a.object_id, a.object_type, a.object_name
)
UNION
-- Tasks
select 
  SUBJ_NAME || ',' || 
  OBJECT_NAME  RES
from 
(
  select c.subj_name, a.object_id, a.object_type, a.object_name, max(b.VERSION_NUMBER) from 
  INFOMETA.OPB_VERSION_PROPS a
  inner join
  INFOMETA.OPB_TASK b on a.object_id = b.task_id
  inner join INFOMETA.OPB_SUBJECT c on c.subj_id = a.subject_id
  inner join INFOMETA.REP_USERS d on a.user_id = d.user_id
  where a.UTC_LAST_SAVED >= $start_ts 
  and a.object_type in (58, 59, 65, 66, 67, 68, 69, 70)
  and b.IS_REUSABLE = 1
  and d.user_name = $user
  group by
  c.subj_name, a.object_id, a.object_type, a.object_name
)