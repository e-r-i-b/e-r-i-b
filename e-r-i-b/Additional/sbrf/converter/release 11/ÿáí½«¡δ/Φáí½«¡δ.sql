--временная таблица с id шаблонов для конвертации
create table TEMP_COV_TEMPLATE (
	LOGIN_ID             number, 
	BUSINESS_DOCUMENT_ID number, 
	CONVERT_STATE        varchar2(10), 
	STREAM               number
)
/

--наполнение таблицы
insert into temp_cov_template
  select 
    bd.login_id, res.business_document_id, null, 0
  from business_documents_res res
  inner join business_documents bd on bd.id= res.business_document_id
  group by bd.login_id, res.business_document_id
/
create index IDX_TEMP_COV on TEMP_COV_TEMPLATE (LOGIN_ID, BUSINESS_DOCUMENT_ID) 
/

begin
  --запуск потока
  convert_templates_11r.convertTemplates(0);
end;
