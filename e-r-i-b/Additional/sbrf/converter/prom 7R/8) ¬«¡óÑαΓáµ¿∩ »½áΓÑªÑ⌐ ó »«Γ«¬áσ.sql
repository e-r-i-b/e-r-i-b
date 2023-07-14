create or replace procedure MOVE_PAYMENTS_FIELDS is
   docNumber    business_documents_copy.billing_document_number%type;
   extId        business_documents_copy.provider_external_id%type;
   recipientId  business_documents_copy.recipient_id%type;
   i number:=0;   
   j number:=0;
   st_date timestamp(0);
   end_date timestamp(0):=to_date('14.12.11 19:01:00', 'dd.mm.yy hh24:mi:ss');
   min_id number; 
   max_id number;
   cntr number;
   movsInterval number:=400000;
begin
    st_date:=sysdate;

    select s_payments_movs.nextval into cntr from dual;

    min_id:=(cntr)*movsInterval+1;
    max_id:=(cntr+1)*movsInterval;
    insert into MOVE_PAYMENTS_MONITOR values(cntr, min_id, max_id, 0, sysdate, null);
    commit;

    for doc in (
                select id, billing_document_number, provider_external_id , recipient_id, state_code, kind, length(d.payer_account) as acct_length
                from business_documents d 
                where id >=min_id and id<=max_id and kind in ('P', '3', 'Z', '2')
    )    
    loop
        begin
            extid:='';
            docnumber:='';
            recipientId:='';

            -- проверяем конвертировался ли документ ранее
            if (doc.billing_document_number is null and doc.provider_external_id is null and doc.recipient_id is null) then
                j:=j+1;
                begin
                    select df.value into extid from document_extended_fields df where df.payment_id = doc.id and df.name = 'receiverId' and rownum < 2;
                exception
                    when no_data_found then docnumber:='';
                end;

                if ( doc.kind='P' and doc.state_code in ('EXECUTED', 'ERROR', 'REFUSED') ) then
                    -- для города или биллингов с карт (кроме IQW) получаем billing_document_number
                    if ( extid like '%gorod%' or ((doc.acct_length >=15 or doc.acct_length<=18) and extid not like '%iqwave') )  then 
                        begin
                            select df.value into docnumber from document_extended_fields df where df.payment_id = doc.id and df.name = 'unp' and rownum < 2;
                        exception
                            when no_data_found then docnumber:='';
                        end;
                    else 
                        docnumber:='';
                    end if;
                else
                    begin
                        select df.value into docnumber from document_extended_fields df where df.payment_id = doc.id and df.name = 'unp' and rownum < 2;
                    exception
                        when no_data_found then docnumber:='';
                    end;
                end if;

                begin
                    select df.value into recipientId from document_extended_fields df where df.payment_id = doc.id and df.name = 'recipient' and rownum < 2;
                exception
                    when no_data_found then recipientId:='';
                end;
                -- для автоплатежей получаем код из таблицы поставщиков
                if ( doc.kind in ('3', 'Z', '2') ) then
                    begin
                        select external_id into extid from service_providers where id=recipientId;
                    exception
                        when no_data_found then null;
                    end;                    
                end if;

                update business_documents bd 
                    set bd.billing_document_number = docnumber, 
                        bd.provider_external_id = extid, 
                        bd.recipient_id = recipientId
                where bd.id = doc.id;

                st_date:=sysdate;
                i:=i+1;
            end if;

        exception 
            when no_data_found then null;
        end;
        -- комитим транзакцию если обновили 1000 записей, или транзакция висит уже более 5 минут
        if ((i>1000) or (st_date < sysdate - interval '5' minute)) then
            if (i>0) then
                i:=0;
                st_date:=sysdate;

                update MOVE_PAYMENTS_MONITOR set CNT=j where ID=cntr;

                commit;           
            else
                st_date:=sysdate;
            end if;
        end if;
        -- проверяем пора ли заканчивать конвертацию по расписанию
        if (end_date < sysdate ) then
            commit;
            exit;
        end if;
    end loop;
    -- выводим кол-во просмотренных (всех, сконвертированных и нет) документов скриптом
    update MOVE_PAYMENTS_MONITOR set TIME_STOP=sysdate where ID=cntr;
    dbms_output.put_line('Payments: '||j||', Time:'||to_char((sysdate - st_date), 'dd hh24:mi:ss'));
    commit;
end;