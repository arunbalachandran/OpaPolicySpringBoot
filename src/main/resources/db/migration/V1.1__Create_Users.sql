-- TODO: change role to be an enum
create table if not exists users (
    id uuid primary key,
    name text not null,
    email text not null,
    password text not null,
    role varchar(255) not null,
    created_time timestamp not null,
    updated_time timestamp not null
);
