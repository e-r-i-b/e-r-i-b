begin
  --Создание новой схемы
  execute immediate
  'create user A_XBANK profile "DEFAULT" identified by A_XBANK account unlock default tablespace "USERS" temporary tablespace "TEMP" quota unlimited on "USERS"';
  --Права для схемы
  execute immediate
  'grant connect, resource,create session, delete any table, unlimited tablespace to A_XBANK';
  
  --Создание таблицы с примерами ответов xBank
  execute immediate
      'create table A_XBANK.XMLDATA
      (
        MESSAGE_TYPE VARCHAR2(100) not null,
        MESSAGE_DATA XMLTYPE
      )
      tablespace USERS
        pctfree 10
        initrans 1
        maxtrans 255
        storage
        (
          initial 128K
          minextents 1
          maxextents unlimited
        )';
  execute immediate 'comment on table A_XBANK.XMLDATA is ''Таблица содержит XML-ответы при получении выписки из XBank''';
  execute immediate 'comment on column A_XBANK.XMLDATA.MESSAGE_TYPE is ''Тип сообщения (xml_204, xml_297, xml_error)''';
  execute immediate 'comment on column A_XBANK.XMLDATA.MESSAGE_DATA is ''Содержание сообщения''';

  --вставка тестовых ответов xBank      
  execute immediate 
  'insert into A_XBANK.XMLDATA (MESSAGE_TYPE,MESSAGE_DATA)'||
         'select ''xml_204'''||
              ', xmltype(''<?xml version="1.0" encoding="windows-1251"?>'||
'<message> '||
   '<messageId>(messageId)</messageId>'||
   '<messageDate>(messageDate)</messageDate>'||
   '<fromAbonent>(fromAbonent)</fromAbonent>'||
     '<billing_a>'||
        '<formType>204</formType>'||
        '<bankName>ЦЕНТРАЛЬНОЕ  ОСБ N 8641 СБЕРБАНКА РОССИИ Г.МОСКВА</bankName>'||
        '<branch>8637</branch>'||
        '<office>136</office>'||
        '<account>(account)</account>'||
        '<startDate>(startDate)</startDate>'||
        '<endDate>(endDate)</endDate>'||
        '<depositorName>БУРМАКОВА ГАЛИНА МАКСИМОВНА</depositorName>'||
        '<depositType>До востребования Сбербанка России</depositType>'||
        '<currencyCode>RUR</currencyCode>'||
        '<openingDate>2000-03-02+03:00</openingDate>'||
        '<openingSum>10.00</openingSum>'||
        '<interestRate>0.3600</interestRate>'||
        '<EndingDate>2010-01-01+03:00</EndingDate>'||
        '<beginningBalance>10.00</beginningBalance>'||
        '<outBalance>7990.39</outBalance>'||
        '<prevOperDate>2000-03-02+03:00</prevOperDate>'||
        '<row>'||
            '<date>2012-07-19+04:00</date>'||
            '<name>Капитализация вклада.</name>'||
            '<aspect>09</aspect>'||
            '<debit>false</debit>'||
            '<sum>0.02</sum>'||
            '<balance>10.02</balance>'||
        '</row>'||
        '<row>'||
            '<date>2012-07-20+04:00</date>'||
            '<name>Премия за службу.</name>'||
            '<aspect>09</aspect>'||
            '<debit>false</debit>'||
            '<sum>12000.05</sum>'||
            '<balance>12010.07</balance>'||
        '</row>'||
        '<row>'||
            '<date>2000-06-30+04:00</date>'||
            '<name>Капитализация вклада.</name>'||
            '<aspect>09</aspect>'||
            '<debit>false</debit>'||
            '<sum>0.05</sum>'||
            '<balance>10.07</balance>'||
        '</row>'||
        '<row>'||
            '<date>2000-06-30+04:00</date>'||
            '<name>Победа на WSOP.</name>'||
            '<aspect>09</aspect>'||
            '<debit>false</debit>'||
            '<sum>1000000.00</sum>'||
            '<balance>1012010.07</balance>'||
        '</row>'||
        '<row>'||
            '<date>2000-09-30+04:00</date>'||
            '<name>Капитализация вклада.</name>'||
            '<aspect>09</aspect>'||
            '<debit>false</debit>'||
            '<sum>0.05</sum>'||
            '<balance>1012010.12</balance>'||
        '</row>'||
        '<row>'||
            '<date>2000-09-30+04:00</date>'||
            '<name>Купил машину.</name>'||
            '<aspect>09</aspect>'||
            '<debit>true</debit>'||
            '<sum>500000.00</sum>'||
            '<balance>512010.12</balance>'||
        '</row>'||
         '<row>'||
            '<date>2000-09-30+04:00</date>'||
            '<name>Купил дом.</name>'||
            '<aspect>09</aspect>'||
            '<debit>true</debit>'||
            '<sum>500000.00</sum>'||
            '<balance>12010.12</balance>
        </row>
        <row>
            <date>2004-09-30+04:00</date>
            <name>Капитализация вклада.</name>
            <aspect>09</aspect>
            <debit>false</debit>
            <sum>0.00</sum>
            <balance>10.65</balance>
        </row>
        <row>
            <date>2004-12-31+03:00</date>
            <name>Капитализация вклада.</name>
            <aspect>09</aspect>
            <debit>false</debit>
            <sum>0.00</sum>
            <balance>10.65</balance>
        </row>
        <row>
            <date>2005-03-31+04:00</date>
            <name>Капитализация вклада.</name>
            <aspect>09</aspect>
            <debit>false</debit>
            <sum>0.00</sum>
            <balance>10.65</balance>
        </row>
        <row>
            <date>2005-06-30+04:00</date>
            <name>Капитализация вклада.</name>
            <aspect>09</aspect>
            <debit>false</debit>
            <sum>0.00</sum>
            <balance>10.65</balance>
        </row>
        <row>
            <date>2005-09-30+04:00</date>
            <name>Капитализация вклада.</name>
            <aspect>09</aspect>
            <debit>false</debit>
            <sum>0.00</sum>
            <correspondent>47411810138360000000</correspondent>
            <balance>10.65</balance>
        </row>
        <row>
            <date>2005-12-31+03:00</date>
            <name>Капитализация вклада.</name>
            <aspect>09</aspect>
            <debit>false</debit>
            <sum>0.00</sum>
            <balance>10.65</balance>
        </row>
         <Proxy>
             <date>2010-08-30+04:00</date>
             <name>Иванов Иван Иванович</name>
         </Proxy>
         <Proxy>
             <date>2010-08-30+04:00</date>
             <name>Сидоров Петр Васильевич</name>
         </Proxy>
         <Proxy>
             <date>2010-08-30+04:00</date>
             <name>Петров Иван Григорьевич</name>
         </Proxy>         
     </billing_a>
</message>'')
           from dual';

  execute immediate 
  'insert into A_XBANK.XMLDATA (MESSAGE_TYPE,MESSAGE_DATA)'||
  'select ''xml_297''
       , xmltype(''<?xml version="1.0" encoding="windows-1251"?>'||
'<message>'||
   '<messageId>(messageId)</messageId>'||
   '<messageDate>(messageDate)</messageDate>'||
   '<fromAbonent>(fromAbonent)</fromAbonent>'||
     '<billing_a>'||
        '<formType>297</formType>'||
        '<bankName>ЦЕНТРАЛЬНОЕ  ОСБ N 8641 СБЕРБАНКА РОССИИ Г.МОСКВА</bankName>'||
        '<branch>8637</branch>'||
        '<office>136</office>'||
        '<account>(account)</account>'||
        '<startDate>(startDate)</startDate>'||
        '<endDate>(endDate)</endDate>'||
        '<depositorName>Акакий Акакиевич Акакиев</depositorName>'||
        '<depositType>До востребования Сбербанка России</depositType>'||
        '<currencyCode>RUR</currencyCode>'||
        '<openingDate>2000-03-02+03:00</openingDate>'||
        '<openingSum>10.00</openingSum>'||
        '<interestRate>0.3600</interestRate>'||
        '<beginningBalance>10.00</beginningBalance>'||
        '<outBalance>7990.39</outBalance>'||
        '<prevOperDate>2000-03-02+03:00</prevOperDate>'||
        '<row>'||
            '<date>2012-07-19+04:00</date>'||
            '<name>Капитализация вклада.</name>'||
            '<aspect>09</aspect>'||
            '<debit>false</debit>'||
            '<sum>0.02</sum>'||
            '<balance>10.02</balance>'||
        '</row>'||
        '<row>'||
            '<date>2012-07-20+04:00</date>'||
            '<name>Премия за службу.</name>'||
            '<aspect>09</aspect>'||
            '<debit>false</debit>'||
            '<sum>12000.05</sum>'||
            '<balance>12010.07</balance>'||
        '</row>'||
        '<row>'||
            '<date>2000-06-30+04:00</date>'||
            '<name>Капитализация вклада.</name>'||
            '<aspect>09</aspect>'||
            '<debit>false</debit>'||
            '<sum>0.05</sum>'||
            '<balance>10.07</balance>'||
        '</row>'||
        '<row>'||
            '<date>2000-06-30+04:00</date>'||
            '<name>Победа на WSOP.</name>'||
            '<aspect>09</aspect>'||
            '<debit>false</debit>'||
            '<sum>1000000.00</sum>'||
            '<balance>1012010.07</balance>'||
        '</row>'||
        '<row>'||
            '<date>2000-09-30+04:00</date>'||
            '<name>Капитализация вклада.</name>'||
            '<aspect>09</aspect>'||
            '<debit>false</debit>'||
            '<sum>0.05</sum>'||
            '<balance>1012010.12</balance>'||
        '</row>'||
        '<row>'||
            '<date>2000-09-30+04:00</date>'||
            '<name>Купил машину.</name>'||
            '<aspect>09</aspect>'||
            '<debit>true</debit>'||
            '<sum>500000.00</sum>'||
            '<balance>512010.12</balance>'||
        '</row>'||
         '<row>'||
            '<date>2000-09-30+04:00</date>'||
            '<name>Купил дом.</name>'||
            '<aspect>09</aspect>'||
            '<debit>true</debit>'||
            '<sum>500000.00</sum>'||
            '<balance>12010.12</balance>'||
        '</row>'||
        '<row>
            <date>2000-12-31+03:00</date>
            <name>Капитализация вклада.</name>
            <aspect>09</aspect>
            <debit>false</debit>
            <sum>0.05</sum>
            <balance>12010.17</balance>
        </row>
        <row>
            <date>2002-06-30+04:00</date>
            <name>Капитализация вклада.</name>
            <aspect>09</aspect>
            <debit>false</debit>
            <sum>0.05</sum>
            <balance>10.47</balance>
        </row>
        <row>
            <date>2002-09-30+04:00</date>
            <name>Капитализация вклада.</name>
            <aspect>09</aspect>
            <debit>false</debit>
            <sum>0.05</sum>
            <balance>10.52</balance>
        </row>
        <row>
            <date>2002-12-31+03:00</date>
            <name>Капитализация вклада.</name>
            <aspect>09</aspect>
            <debit>false</debit>
            <sum>0.03</sum>
            <balance>10.55</balance>
        </row>
        <row>
            <date>2003-03-31+04:00</date>
            <name>Капитализация вклада.</name>
            <aspect>09</aspect>
            <debit>false</debit>
            <sum>0.03</sum>
            <balance>10.58</balance>
        </row>
         <Proxy>
             <date>2010-08-30+04:00</date>
             <name>Чацкий</name>
         </Proxy>
         <Proxy>
             <date>2010-08-30+04:00</date>
             <name>Чичиков</name>
         </Proxy>
         <Proxy>
             <date>2010-08-30+04:00</date>
             <name>Петров Иван Григорьевич</name>
         </Proxy>
     </billing_a>
</message>'')  
            from dual';

  execute immediate
  'insert into A_XBANK.XMLDATA (MESSAGE_TYPE,MESSAGE_DATA)'||
  'select ''xml_error''
       , xmltype(''<?xml version="1.0" encoding="windows-1251"?>
<message>
  <messageId>(messageId)</messageId>
  <messageDate>(messageDate)</messageDate>
  <fromAbonent>(fromAbonent)</fromAbonent>
  <error_a>
    <code>ERROR_VALIDATE</code>
    <message>Сообщение</message>
  </error_a>
</message>'')
    from dual';
  
--Создание пакета (спецификация)
execute immediate
  'create or replace package A_XBANK.XBANK_PRS is '||
    '/*Используется при имитации взаимодействия с xBank*/ '||
    'procedure AccountPRS_P(iXMLDocument_IN XMLTYPE, iXMLDocument_OUT OUT XMLTYPE); '||
  'end XBANK_PRS;';
--Создание пакета (тело)
execute immediate
  'create or replace package body A_XBANK.XBANK_PRS is '||
  '/*Используется при имитации взаимодействия с xBank*/
  procedure AccountPRS_P(iXMLDocument_IN XMLTYPE, iXMLDocument_OUT OUT XMLTYPE) is
    c_xml_eror_type constant varchar2(10) := ''xml_error'';
    c_xml_297_type  constant varchar2(10) := ''xml_297'';
    c_xml_204_type  constant varchar2(10) := ''xml_204'';
    l_messageId   varchar2(32); 
    l_messageDate varchar2(50);
    l_fromAbonent varchar2(5);
    l_copying     varchar2(10);
    l_account     varchar2(20);
    l_startDate   varchar2(50);
    l_endDate     varchar2(50);
    l_randomVal   number;
    l_xml_type    varchar2(100);
    ansverXML     varchar2(32000);  
  begin
      --Входящая XML-ка имеет вид:
      /*<?xml version="1.0" encoding="windows-1251"?>
        <message>
            <messageId>392601f84f2826b669fab8107d95ba58</messageId>
            <messageDate>2012-07-16+04:00</messageDate>
            <fromAbonent>ESK</fromAbonent>
            <billingDemand_q>
                <clientId>0</clientId>
                <copying>true</copying>
                <account>42307840903821049485</account>
                <startDate>2012-06-16</startDate>
                <endDate>2012-07-16</endDate>
            </billingDemand_q>
        </message>*/
    if iXMLDocument_IN is not null then
      l_messageId := iXMLDocument_IN.extract(''message/messageId/text()'').getStringVal();
      l_messageDate := iXMLDocument_IN.extract(''message/messageDate/text()'').getStringVal();
      l_fromAbonent := iXMLDocument_IN.extract(''message/fromAbonent/text()'').getStringVal();
      l_account := iXMLDocument_IN.extract(''message/billingDemand_q/account/text()'').getStringVal();
      l_startDate := iXMLDocument_IN.extract(''message/billingDemand_q/startDate/text()'').getStringVal();
      l_endDate := iXMLDocument_IN.extract(''message/billingDemand_q/endDate/text()'').getStringVal();
      l_copying := iXMLDocument_IN.extract(''message/billingDemand_q/copying/text()'').getStringVal();
      
      --Случайное число для генерации ответа с ошибкой
      l_randomVal := trunc(dbms_random.value(1,50));
      
      --Данные для ответа
      select d.message_data, d.message_type
        into iXMLDocument_OUT, l_xml_type
        from A_XBANK.xmldata d
       where d.message_type = (case 
                                 when l_randomVal = 10 then c_xml_eror_type/*вернет ошибку*/
                                 when l_copying = ''true'' then c_xml_297_type/*выписка с типом 297*/
                               else c_xml_204_type end/*выписка с типом 204*/);
      
      --Формирование ответа
      ansverXML := replace(
                           replace(
                                   replace(iXMLDocument_OUT.getClobVal(),''(messageId)'',l_messageId)/*номер сообщения*/
                                  ,''(messageDate)'',l_messageDate /*дата сообщения*/
                                  )
                          ,''(fromAbonent)'',l_fromAbonent /*абонент-отправитель сообщения*/
                          );
      if l_xml_type in (c_xml_204_type,c_xml_297_type) then
        ansverXML := replace(
                             replace(
                                     replace(ansverXML,''(account)'',l_account /*номер счета*/) 
                                     ,''(startDate)'',l_startDate /*начало периода (включительно)*/
                                    )
                             ,''(endDate)'',l_endDate /*окончание периода (включительно)*/
                             );
      end if;
      iXMLDocument_OUT := new XMLType(ansverXML);
    end if;
  end; '||
  'end XBANK_PRS;';
end;
