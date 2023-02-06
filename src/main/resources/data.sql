use stander;
show tables;
select * from member;
 insert into member
value (3, 25, null, 'test2@mail.com', 'male', 'test2', '!@#$asdf1234', null, 0, 'test2', null);
update member
set time = 10000000, password = '1234'
where id = 3;



