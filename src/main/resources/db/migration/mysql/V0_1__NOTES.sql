create table notes 
(
    id varchar(36)  not null primary key,
    content varchar(300)   not null,
    created_at datetime default now() not null
)