create table oportunidade(
	id bigint auto_increment not null,
	nome_prospecto varchar(80) not null,
	descricao varchar(300) not null,
	valor decimal(10,2),
	
	primary key (id)

);