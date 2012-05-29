insert into container(uuid, name, root) values (0x0, 'root', true);

insert into note(uuid, name, contents) values (0x1, 'note1', 'Text');
insert into container_item(container_uuid, items_uuid) values (0x0, 0x1);