
--Скрипт изменяющий тип узла с обычного на тип SOFIA для VSB и VVB 

UPDATE nodes
    SET TYPE = 'SOFIA', PREFIX = 'phiz-gate-cod-vsb'
    WHERE URL like '%PhizGateCODVSB%' -- ID = 1
/

UPDATE nodes
    SET TYPE = 'SOFIA', PREFIX = 'phiz-gate-cod-vvb'
    WHERE URL like '%PhizGateCODVVB%' -- ID = 90
/

--------------------------------------------------------------------

--Скрипт, кторый перепривязвает адаптеры к одному узлу

--Перепривязываем адаптры VSB к узлу VSB типа SOFIA
--ID всех узлов VSB из NODES  1,2,3,4,5,6,7,8,24,25,26,37,38,39,40,92,93
--в NODE_ADAPTERS необходимо заменить все NODE_ID равные ID из NODES на ID нового шлюза VSB

update NODE_ADAPTERS
    set NODE_ID = '1'
    where NODE_ID in (1,2,3,4,5,6,7,8,24,25,26,37,38,39,40,92,93)
/

--Перепривязываем адаптры VVB к узлу VVB типа SOFIA
--ID всех узлов VVB из NODES за исключением "неживых" 
-- 75,103,83,55,104,50,88,44,105,18,9,86,53,89,76,59,56,57,58,77,66,78,47,13,81,100,79,80,67,60,108,19,36,61,52,46,84,106,20,21,22,109,101,14,33,49,15,16,102,17,82,10,107,68,43,32,99,98,45,54,90
--в NODE_ADAPTERS необходимо заменить все NODE_ID равные ID из NODES на ID нового шлюза VVB

update NODE_ADAPTERS
    set NODE_ID = '90'
    where NODE_ID in (75,103,83,55,104,50,88,44,105,18,9,86,53,89,76,59,56,57,58,77,66,78,47,13,81,100,79,80,67,60,108,19,36,61,52,46,84,106,20,21,22,109,101,14,33,49,15,16,102,17,82,10,107,68,43,32,99,98,45,54,90)
/
--------------------------------------------------------------------------------


--скрипт, который INSERTит
--в таблицу PROPERTIES
-- для категории phiz-gate-cod-vsb.codgate.properties
-- по ключу com.rssl.phisicgate.sbrf.ws.cod.url.prefix_adapter
-- значение url на ШЭСК

INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vsb', 'http://10.161.30.71/iBankSrvc161/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb180', 'http://10.161.30.71/iBankSrvc180/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb181', 'http://10.161.30.71/iBankSrvc181/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb2387', 'http://10.161.30.121/iBankSrvc2387/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb2402', 'http://10.161.30.121/iBankSrvc2402/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb2404', 'http://10.161.30.121/iBankSrvc2404/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb279', 'http://10.161.30.71/iBankSrvc279/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb6190', 'http://10.161.30.71/iBankSrvc6190/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb6917', 'http://10.161.30.71/iBankSrvc6917/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb7449', 'http://10.161.30.71/iBankSrvc7449/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb7701', 'http://10.161.30.71/iBankSrvc7701/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb7815', 'http://10.161.30.71/iBankSrvc7815/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb7864', 'http://10.161.30.121/iBankSrvc7864/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb8217', 'http://10.161.30.71/iBankSrvc8217/WEBBANKSERVICE.ASMX', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb8591', 'http://10.161.30.71/iBankSrvc8591/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb8602', 'http://10.161.30.71/iBankSrvc8602/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.cod-vsb-opero', 'http://10.161.30.71/iBankSrvc2/WebBankService.asmx', 'phiz-gate-cod-vsb.codgate.properties')
/


--------------------------------------------------------------------------------


--скрипт, который INSERTит
--в таблицу PROPERTIES
-- для категории phiz-gate-cod-vvb.codgate.properties
-- по ключу com.rssl.phisicgate.sbrf.ws.cod.url.prefix_adapter
-- значение url на ШЭСК

INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb', 'http://10.172.5.59/ibank9042/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb0007', 'http://10.172.5.60/ibank0007/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb1461', 'http://10.172.5.60/ibank1461/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb2484', 'http://10.172.5.60/ibank2484/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb2555', 'http://10.172.5.60/ibank2555/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb368', 'http://10.172.5.60/ibank0368/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4092', 'http://10.172.5.60/ibank4092/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4299', 'http://10.172.5.60/ibank4299/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4306', 'http://10.172.5.60/ibank4306/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4314', 'http://10.172.5.60/ibank4314/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4335', 'http://10.172.5.60/ibank4335/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4340', 'http://10.172.5.60/ibank4340/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4342', 'http://10.172.5.60/ibank4342/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4345', 'http://10.172.5.60/ibank4345/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4346', 'http://10.172.5.60/ibank4346/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4354', 'http://10.172.5.60/ibank4354/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4356', 'http://10.172.5.60/ibank4356/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4370', 'http://10.172.5.60/ibank4370/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4378', 'http://10.172.5.60/ibank4378/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4379', 'http://10.172.5.60/ibank4379/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4387', 'http://10.172.5.60/ibank4387/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4397', 'http://10.172.5.60/ibank4397/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4407', 'http://10.172.5.60/ibank4407/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4419', 'http://10.172.5.60/ibank4419/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4423', 'http://10.172.5.60/ibank4423/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4437', 'http://10.172.5.60/ibank4437/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4440', 'http://10.172.5.59/ibank4440/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4443', 'http://10.172.5.59/ibank4443/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4447', 'http://10.172.5.60/ibank4447/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4642', 'http://10.172.5.60/ibank4642/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4645', 'http://10.172.5.60/ibank4645/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4655', 'http://10.172.5.59/ibank4655/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4662', 'http://10.172.5.60/ibank4662/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4672', 'http://10.172.5.60/ibank4672/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4676', 'http://10.172.5.60/ibank4676/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4678', 'http://10.172.5.60/ibank4678/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4682', 'http://10.172.5.60/ibank4682/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4683', 'http://10.172.5.59/ibank4683/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4690', 'http://10.172.5.60/ibank4690/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4694', 'http://10.172.5.59/ibank4694/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb4698', 'http://10.172.5.60/ibank4698/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb5766', 'http://10.172.5.60/ibank5766/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb5836', 'http://10.172.5.60/ibank5836/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb6056', 'http://10.172.5.60/ibank6056/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb6281', 'http://10.172.5.60/ibank6281/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb6652', 'http://10.172.5.60/ibank6652/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb6669', 'http://10.172.5.60/ibank6669/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb6670', 'http://10.172.5.60/ibank6670/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb6672', 'http://10.172.5.60/ibank6672/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb7507', 'http://10.172.5.60/ibank7507/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb7508', 'http://10.172.5.59/ibank7508/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb7695', 'http://10.172.5.60/ibank7695/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb8102', 'http://10.172.5.60/ibank8102/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb8219', 'http://10.172.5.60/ibank8219/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb8589', 'http://10.172.5.59/ibank8589/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb8610', 'http://10.172.5.60/ibank8610/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb8611', 'http://10.172.5.60/ibank8611/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb8612', 'http://10.172.5.60/ibank8612/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb8613', 'http://10.172.5.60/ibank8613/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb8614', 'http://10.172.5.60/ibank8614/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/
INSERT INTO PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
    VALUES(S_PROPERTIES.nextval, 'com.rssl.phisicgate.sbrf.ws.cod.url.phiz-gate-cod-vvb93', 'http://10.172.5.60/ibank0093/webbankservice.asmx', 'phiz-gate-cod-vvb.codgate.properties')
/

--------------------------------------------------------------------------------
--скрипт, который удаляет старые узлы
-- из таблицы NODES удаляем старый узлы, по ID которые отвязали во втором скрипте
-- за исключением ID нового шлюза
-- PhizGateCODVSB -- ID = 1
-- PhizGateCODVVB -- ID = 90

-- удаляем узлы ВСБ
--delete from NODES where ID in (2,3,4,5,6,7,8,24,25,26,37,38,39,40,92,93)
--/

-- удаляем узлы ВВБ
--delete from NODES where ID in (75,103,83,55,104,50,88,44,105,18,9,86,53,89,76,59,56,57,58,77,66,78,47,13,81,100,79,80,67,60,108,19,36,61,52,46,84,106,20,21,22,109,101,14,33,49,15,16,102,17,82,10,107,68,43,32,99,98,45,54)
--/

