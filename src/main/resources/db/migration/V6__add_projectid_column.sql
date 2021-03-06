alter table TASK_GROUPS add column project_id int null;
alter table TASK_GROUPS add foreign key (project_id) references projects(id);