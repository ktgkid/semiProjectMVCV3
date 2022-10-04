create procedure loop_insert1(in cnt int)
begin
    declare i int default 1;
    while (i <= cnt) do
        insert into board(title, userid, content) VALUES ('aaaa', '11', '가나다라마'), ('bbbb', '555', '제곧내~'), ('ㅠㅠㅠㅠ', '1111', '안녕~~?');
        set i = i + 1;
end while;
end;

delete from board;

call loop_insert1(221004);