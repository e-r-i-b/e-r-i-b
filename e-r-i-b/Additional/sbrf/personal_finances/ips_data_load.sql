declare
   --------------------ВХОДНЫЕ ДАННЫЕ ДЛЯ ГЕНЕРАЦИИ ТРАНЗАКЦИЙ------------------------
   /*
    * Скрипт заполняет таблицу транзакций по картам тестовыми данными
    *
    */
   /*Схема для загрузки данных*/
   c_User constant varchar2(4000) := 'IPS';
   
   
   /*Общее кол-во транзакций*/
   c_TransactionCount  constant number:=0; /*10000 транзакций на весь период*/
   /*Дата начала генерации транзакций*/
   c_TransactionDateBegin constant date := to_date('01.08.2010','dd.mm.yyyy');
   
   /*Максимальное кол-во транзакций в день на карту. Не более*/
   c_CardTransacToDayCount constant number := 8;
   /*№ документа*/ 
   c_DocNum            constant varchar2(40) := '1234567890';
   /*Список карт через ","*/
   c_CardList          constant varchar2(4000) := '4200838701313161'||
                                                 ',5469400011375503'||
                                                 ',6062312474485283'||
                                                 ',7892961700358450'||
                                                 ',8892961700358450'||
                                                 ',9892961700358450';
   /*Список ISO кодов валюты счета через "," (длина списка д.б. такой же как и длина списка карт)*/
   c_ISOList           constant varchar2(4000) := '643,840,978,978,643,978';
   /*Список номеров счетов карт через "," (длина списка д.б. такой же как и длина списка карт)*/
   c_CardAccauntNumList constant varchar2(4000) := '4000567890123456780000001'||
                                                  ',4000567890123456780000002'||
                                                  ',4000567890123456780000003'||
                                                  ',4000567890123456780000004'||
                                                  ',4000567890123456780000005'||
                                                  ',4000567890123456780000006';
   /*Список ISO кодов валюты транзакции четез ","*/
   c_ISOTransList      constant varchar2(4000) := '643,840,978,978,643,840,944,764,818,414';
   /*Список описаний операций с местом ее совершения*/
   c_TransDescription constant varchar2(4000) := 'Перевод частному лицу. Интернет банк.'||
                                                ',Перевод со счета на счет. Интернет банк.'||
                                                ',Оплата сотового. Банкомат.';
   /*Список MCC-кодов через ",". Если операция расходная, то значение МСС-кода в списке должно быть с отрицательным знаком, иначе с положительным. 
     Значение МСС-кода при генерации данных выбирается случайным образом. Знак влияет только на тип операции (доходная/расходная), само значение 
     МСС-кода в сгенерированных данных будет положительным.*/
   c_MCCCodeList      constant varchar2(4000) := '-5532,3032';
   -----------------------------------------------------------------------------------
   
   l_CardCnt           number:=0;
   l_ISOCnt            number:=0;
   l_iso_trans_cnt     number:=0;
   l_mcc_cnt           number:=0;
   l_descr_cnt         number:=0;
   l_CardAccNumCnt     number:=0;
   l_Cnt               number:=0;
   l_TransId           number:=0;
   l_TransId_begin     number:=0;
   l_RandomDescription varchar2(100) :='';
   l_Random            number :=0;
   l_OperationDate     timestamp;
   l_NextOperationDate timestamp;
   l_MaxTransactionCount number;
   l_SumTransactionToDay number;
   l_SumTransactionToDayReq number;
   l_CardNum             varchar2(20);
   l_CardNumIndex        number;
   l_ISOCode             char(3);    
   l_CardAccNum          varchar2(25);
   l_ISOTransCode        char(3);
   l_MCCCode             integer;
   l_OperToDayCntBy_Card number:=0;
   l_TransactionDateBegin Date;
   l_Min                  number;
   l_RandomTime          timestamp;
   l_CardOperCnt         number;
   l_OperDate            varchar2(100);
   l_RandomCode          varchar2(6);
begin
      --схема где находится таблица транзакций
      execute immediate 'alter session set current_schema = '||c_User;      
      --Чистка таблицы перед новым заполнением
      --execute immediate 'truncate table ips_transaction';
      /*Проверки входных данных*/
      execute immediate 'select count(*) from table (cast (str2tbl('''||c_CardList||''','','') as STRING_LIST_TYPE))' into l_CardCnt;
      execute immediate 'select count(*) from table (cast (str2tbl('''||c_ISOList||''','','') as STRING_LIST_TYPE))' into l_ISOCnt;
      execute immediate 'select count(*) from table (cast (str2tbl('''||c_CardAccauntNumList||''','','') as STRING_LIST_TYPE))' into l_CardAccNumCnt;
      
      if (l_CardCnt + l_ISOCnt + l_CardAccNumCnt)/3 <> l_CardAccNumCnt then
        RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных!'); 
      end if;

      --Временная таблица с входными данными по картам на переиод жизни транзакции
      execute immediate 
      'create global temporary table card_data_table 
      (num number, 
       card_num varchar2(20), 
       iso_code varchar(20), 
       acc_num varchar2(25)) on commit preserve rows';
      execute immediate 
      'insert into card_data_table
           SELECT cn.num, cn.card_num, iso.iso_code, acc.acc_num
             FROM (
                   select rownum num, t.column_value as card_num 
                     from table (cast (str2tbl('''||c_CardList||''','','') as STRING_LIST_TYPE)) t
                  ) cn,
                  (
                   select rownum num, t.column_value as iso_code
                     from table (cast (str2tbl('''||c_ISOList||''','','') as STRING_LIST_TYPE)) t
                  ) iso,
                  (
                   select rownum num, t.column_value as acc_num
                     from table (cast (str2tbl('''||c_CardAccauntNumList||''','','') as STRING_LIST_TYPE)) t
                  ) acc
            WHERE cn.num = iso.num
              and acc.num = cn.num';
      
      --Временная таблица с ISO кодами валют транзакций на переиод жизни транзакции
      execute immediate 'create global temporary table iso_code_table (num number, iso_trans_code varchar(20)) on commit preserve rows';
      execute immediate
      'insert into iso_code_table
           SELECT itl.num, itl.iso_trans_code
             FROM (
                   select rownum num, t.column_value as iso_trans_code 
                     from table (cast (str2tbl('''||c_ISOTransList||''','','') as STRING_LIST_TYPE)) t
                  ) itl';
      l_iso_trans_cnt := SQL%ROWCOUNT;
      
      --Временная таблица с MCC кодами на переиод жизни транзакции 
      execute immediate 'create global temporary table mcc_code_table (num number, mcc_code integer) on commit preserve rows';
      execute immediate
      'insert into mcc_code_table
           SELECT mcc.num, mcc.mcc_code
             FROM (
                   select rownum num, t.column_value as mcc_code 
                     from table (cast (str2tbl('''||c_MCCCodeList||''','','') as STRING_LIST_TYPE)) t
                  ) mcc';
      l_mcc_cnt := SQL%ROWCOUNT;
      
      --Временная таблица с описаниями операций и местами их совершения на переиод жизни транзакции
      execute immediate 'create global temporary table trans_descript_table (num number, descript varchar(100)) on commit preserve rows';
      execute immediate
      'insert into trans_descript_table
           SELECT td.num, td.descript
              FROM (
                    select rownum num, t.column_value as descript 
                      from table (cast (str2tbl('''||c_TransDescription||''','','') as STRING_LIST_TYPE)) t
                   ) td';
      l_descr_cnt := SQL%ROWCOUNT;
      
      if l_iso_trans_cnt = 0 or l_mcc_cnt = 0 or l_descr_cnt = 0 then
        RAISE_APPLICATION_ERROR(-20900,'Ошибка обработки входных данных '||l_iso_trans_cnt||' '||l_mcc_cnt||' '||l_descr_cnt);
      end if;
      
      --Временная таблица с кол-ом операций на каждую карту за сутки
      execute immediate 'create global temporary table tmp_cart_operation_count (card_num varchar2(20), oper_count number) on commit preserve rows';
      
      /*Если указано максимальное кол-во операций*/
      if nvl(c_TransactionCount,0) > 0 then
        /*Максимальное кол-во транзакций за день по всем картам*/
        l_SumTransactionToDay := l_CardCnt*c_CardTransacToDayCount;--------
        /*Определение даты начала генерации операций*/
        l_TransactionDateBegin := trunc(sysdate - ceil(c_TransactionCount/l_SumTransactionToDay));        
      /*Если указана дата начала генерации операций*/
      elsif c_TransactionDateBegin is not null then
        l_TransactionDateBegin := c_TransactionDateBegin;
        /*Определение максимального кол-ва операций за весь период*/
        l_MaxTransactionCount := trunc((sysdate - l_TransactionDateBegin)*c_CardTransacToDayCount*l_CardCnt);
        /*Кол-во транзакций за день*/
        l_SumTransactionToDay := greatest(floor(l_MaxTransactionCount/(trunc(sysdate - l_TransactionDateBegin)+1)),1);
      else
        RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных. Укажите дату начала генерации операций или общее кол-во операций!');
      end if;
      /*интервал времени в течении которого должна появиться операция, чтобы было равномерное распределение 
        в течении операционного дня*/
      l_Min := ceil(24*60/l_SumTransactionToDay);--каждые l_Min минут должна появляться одна операция

      l_SumTransactionToDayReq := l_SumTransactionToDay;

      l_OperationDate := l_TransactionDateBegin;
      loop
        l_NextOperationDate := l_OperationDate+1;
        l_Cnt := 0;
        loop --транзакции за день
          --Случайное число для сумм
          select trunc(dbms_random.value(1,10),4) into l_Random from dual;
          --Случайная строка для кода авторизации
          select dbms_random.string('a',6) into l_RandomCode from dual;
          --Случайное время в диапазоне [1...l_Min] минут
          select l_OperationDate+(dbms_random.value(1,l_Min)/(24*60)) into l_RandomTime from dual;
          
          if l_RandomTime <= sysdate then
            --циклом по всем доступным картам и генерим транзакцию
            l_Cnt := l_Cnt + 1;
            select to_char(trunc(cast(l_RandomTime as date)),'dd.mm.yyyy') into l_OperDate from dual;
            
            --очередная карта для вставки транзакции по ней 
            execute immediate 
            ' select max(card_num), max(iso_code), max(acc_num), max(iso_trans_cod), min(mcc_cod), max(description) '
            ||'  from ( '
            ||' select t.card_num, t.iso_code, t.acc_num '
                 ||', (select t.iso_trans_code from iso_code_table t where t.num = (select trunc(dbms_random.value(1,'||l_iso_trans_cnt||'+0.5)) from dual)) iso_trans_cod '
                 ||', (select t.mcc_code from mcc_code_table t where t.num = (select trunc(dbms_random.value(1,'||l_mcc_cnt||'+0.5)) from dual)) mcc_cod'
                 ||', (select t.descript from trans_descript_table t where t.num = (select trunc(dbms_random.value(1,'||l_descr_cnt||'+0.5)) from dual)) description'
             ||' from (SELECT rownum num, t.card_num, t.iso_code, t.acc_num, count(*) over() rec_cnt '
                    ||'  FROM card_data_table t '
                    ||' WHERE (select coalesce(max(tr.oper_count),0) from tmp_cart_operation_count tr where tr.card_num = t.card_num) < '||c_CardTransacToDayCount||') t '
             ||' where t.num = (select trunc(dbms_random.value(1,t.rec_cnt)) from dual))' 
            into l_CardNum, l_ISOCode, l_CardAccNum, l_ISOTransCode, l_MCCCode, l_RandomDescription;

            if l_CardNum is not null then
              if l_TransId = 0 then l_TransId_begin := -1; end if;
              
              execute immediate 'select sq_ips_transaction.nextval from dual' into l_TransId;
              
              if l_TransId_begin < 0 then l_TransId_begin := l_TransId; end if;
              --Вставка транзакции по карте
              execute immediate
              'insert into ips_transaction '
                 ||'select to_char(:TransId)' /*Идентификатор транзакции*/
                      ||', 2000' /*Код типа операции*/
                      ||', :RandomTime' /*Дата совершения операции*/
                      ||', :CardNum' /*Номер карты*/
                      ||', :RandomDescription' /*Описание операции с местом её совершения*/
                      ||', (case when :MCCCode < 0 then -1 else 1 end) * (select trunc(dbms_random.value(200,10000)) from dual)' /*Сумма в валюте счета*/
                      ||', :ISOCode' /*ISO код валюты счета*/
                      ||', (case when :MCCCode < 0 then -1 else 1 end) * (select trunc(dbms_random.value(200,10000)) from dual)' /*Сумма транзакции*/
                      ||', :ISOTransCode' /*ISO код валюты транзакции*/
                      ||', abs(:MCCCode)' /*MCC код*/
                      ||', :CardAccNum' /*Номер счета карты*/
                      ||', rpad(trunc(:Random),4,''0'')||''567890''' /*Ид устройства в котором проведена операция*/
                      ||', :DocNum'
                      ||', :RandomCode' /*Код авторизации операции*/
                  ||' from dual'
                using l_TransId, l_RandomTime, l_CardNum, l_RandomDescription, l_MCCCode, l_ISOCode, l_MCCCode, l_ISOTransCode, l_MCCCode, l_CardAccNum, l_Random, c_DocNum, l_RandomCode;
              
              --Обновление информации по кол-ву операций за день для данной карты
              execute immediate
                'update tmp_cart_operation_count c '
                ||' set c.oper_count = coalesce(c.oper_count,-1)+1 '
              ||' where c.card_num = :CardNum'
              using l_CardNum;
              
              if SQL%ROWCOUNT = 0 then
                execute immediate 'insert into tmp_cart_operation_count (card_num,oper_count) values (:CardNum,1)' using l_CardNum;
              end if;
              
            end if;
            l_OperationDate := l_OperationDate + l_Min/(24*60);
          end if;
          exit when l_RandomTime >= sysdate or 
                    l_Cnt >= l_SumTransactionToDay or 
                    l_CardNum is null or 
                    l_TransId-l_TransId_begin >= l_MaxTransactionCount;
        end loop;

        l_OperationDate := l_NextOperationDate;
        execute immediate 'truncate table tmp_cart_operation_count';

        exit when l_RandomTime >= sysdate or
                  l_TransId-l_TransId_begin >= l_MaxTransactionCount;
      end loop;
      
      --Создание операций возврата (5% от всех неоткаченных)
      execute immediate
      'insert into ips_transaction '
        ||' select sq_ips_transaction.nextval, '
               ||' tr.operationtype '
             ||' , cast ((tr.operationdate+ (5/(24*60))) as timestamp) operationdate '
             ||' , tr.cardid '
             ||' , tr.description||''(возврат)'' description '
             ||' , tr.accsum*-1 accsum '
             ||' , tr.curaccsum '
             ||' , tr.sum*-1 sum '
             ||' , tr.cursum '
             ||' , tr.mcccode '
             ||' , tr.cardaccount '
             ||' , tr.deviceid '
             ||' , tr.persondoc '
             ||' , tr.authCode '
          ||' from (select case when mod(rownum, trunc(count(*) over() / (count(*) over() * 0.05))) = 0 then tr1.id end otkat_oper_id '
                     ||' , tr1.* '
                  ||' from ips_transaction tr1 '
                 ||' where not exists (select 1 from ips_transaction tr2 '
                                    ||' where tr2.cardid = tr1.cardid '
                                      ||' and tr2.cardaccount = tr1.cardaccount '
                                      ||' and tr2.mcccode = tr1.mcccode '
                                      ||' and tr2.id <> tr1.id '
                                      ||' and abs(tr2.accsum) = abs(tr1.accsum) '
                                      ||' and abs(tr2.sum) = abs(tr1.sum) '
                                      ||' ) '
               ||' ) tr '
         ||' where tr.otkat_oper_id is not null';
         
      commit;
      execute immediate 'truncate table card_data_table';
      execute immediate 'truncate table iso_code_table';
      execute immediate 'truncate table mcc_code_table';
      execute immediate 'truncate table trans_descript_table';
      execute immediate 'truncate table tmp_cart_operation_count';
      execute immediate 'drop table card_data_table';
      execute immediate 'drop table iso_code_table';
      execute immediate 'drop table mcc_code_table';
      execute immediate 'drop table trans_descript_table';
      execute immediate 'drop table tmp_cart_operation_count';
end; 
