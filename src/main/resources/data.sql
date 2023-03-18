use stander;
show tables;
select * from member;
 insert into member(member_id, age, check_in, email, gender, name, password, qr, time, username, seat_id)
value (1, 25, null, 'test@mail.com', 'male', 'test', '!@#$asdf1234', null, 0, 'test', null);
update member
set time = 10000000, password = '1234'
where member_id = 1;
select * from timeboard;
insert into timeboard (id, date)
values (1, date_format('2023-02-06')), (2, date_format('2023-02-09')), (3, date_format('2023-02-10'));
insert into stander.timeboard (id, date)
values
    (1, current_time()),
    (2, adddate(current_time(), 1)),
    (3, adddate(current_time(), 2)),
    (4, adddate(current_time(), 3)),
    (5, adddate(current_time(), 4)),
    (6, adddate(current_time(), 5)),
    (7, adddate(current_time(), 6)),
    (8, adddate(current_time(), 7));

update timeboard
set date = current_time
where id = 1;

delete timeboard from timeboard
where id > 0;

update timeboard
set date = date_format('2023-02-07 10:20:50', '%Y-%m-%d %h:%m:%s')
where id = 1;

delete timeboard from timeboard where id = 1;
delete member from member where username = '이우진';
delete seat from seat where seat.member_id > 0;

drop table member;
drop table seat;
drop table timeboard;
