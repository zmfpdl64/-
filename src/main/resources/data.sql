use stander;
show tables;
select * from member;
 insert into member
value (3, 25, null, 'test2@mail.com', 'male', 'test2', '!@#$asdf1234', null, 0, 'test2', null);
update member
set time = 10000000, password = '1234'
where id = 3;
select * from timeboard;
insert into timeboard
value (1, date_format('2023-02-06'));

update timeboard
set date = date_format('2023-02-07 10:20:50', '%Y-%m-%d %h:%m:%s')
where id = 1;

delete timeboard from timeboard where id = 1;