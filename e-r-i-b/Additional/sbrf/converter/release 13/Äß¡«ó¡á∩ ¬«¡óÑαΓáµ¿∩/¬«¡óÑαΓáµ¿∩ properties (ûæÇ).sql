-- Номер ревизии: 58064
-- Комментарий: ENH067465: Многоблочность - настройка для деловой среды в ЦСА Бэк 
UPDATE PROPERTIES 
SET PROPERTY_VALUE = SUBSTR(PROPERTY_VALUE, 0, INSTR(PROPERTY_VALUE, ':')) || '//%s/WSGateListener/services/CSABackRefServiceImpl'
WHERE PROPERTY_KEY = 'com.rssl.auth.csa.back.config.integration.erib.url'
/
