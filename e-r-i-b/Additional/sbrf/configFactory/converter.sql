update PROPERTIES set CATEGORY = 'cod-bajkal' where CATEGORY like 'cod-bajkal.%'
go
update PROPERTIES set CATEGORY = 'cod-yzb' where CATEGORY like 'cod-yzb.%'
go
update PROPERTIES set CATEGORY = 'cod-vsb-union' where CATEGORY like 'cod-vsb-union.%'
go
update PROPERTIES set CATEGORY = 'cod-vvb-union' where CATEGORY like 'cod-vvb-union.%'
go
update PROPERTIES set CATEGORY = 'phiz-gate-cod-vvb' where CATEGORY like 'phiz-gate-cod-vvb.%'
go
update PROPERTIES set CATEGORY = 'phiz-gate-iqwave' where CATEGORY like 'phiz-gate-iqwave.%'
go
update PROPERTIES set CATEGORY = 'phiz-gate-cod' where CATEGORY like 'phiz-gate-cod.%'
go
update PROPERTIES set CATEGORY = 'cod-dvb' where CATEGORY like 'cod-dvb.%'
go
update PROPERTIES set CATEGORY = 'cod-moscow38' where CATEGORY like 'cod-moscow38.%'
go
update PROPERTIES set CATEGORY = 'cod-moscow99' where CATEGORY like 'cod-moscow99.%'
go
update PROPERTIES set CATEGORY = 'cod-cchb' where CATEGORY like 'cod-cchb.%'
go
update PROPERTIES set CATEGORY = 'cod-pb' where CATEGORY like 'cod-pb.%'
go
update PROPERTIES set CATEGORY = 'cod-skb' where CATEGORY like 'cod-skb.%'
go
update PROPERTIES set CATEGORY = 'cod-ub' where CATEGORY like 'cod-ub.%'
go
update PROPERTIES set CATEGORY = 'cod-zsb' where CATEGORY like 'cod-zsb.%'
go
update PROPERTIES set CATEGORY = 'phiz-gate-cod-altaj' where CATEGORY like 'phiz-gate-cod-altaj.%'
go
update PROPERTIES set CATEGORY = 'phiz-gate-cod-sb' where CATEGORY like 'phiz-gate-cod-sb.%'
go
update PROPERTIES set CATEGORY = 'phiz-gate-cod-svb' where CATEGORY like 'phiz-gate-cod-svb.%'
go
update PROPERTIES set CATEGORY = 'phiz-gate-cod-yzb' where CATEGORY like 'phiz-gate-cod-yzb.%'
go
update PROPERTIES set CATEGORY = 'phiz-gate-gorod' where CATEGORY like 'phiz-gate-gorod.%'
go
update PROPERTIES set CATEGORY = 'phiz-gate-sofia' where CATEGORY like 'phiz-gate-sofia.%'
go
update PROPERTIES set CATEGORY = 'phiz-gate-cpfl' where CATEGORY like 'phiz-gate-cpfl.%'
go
update PROPERTIES set CATEGORY = 'csa-auth-gate' where CATEGORY like 'csa-auth-gate.%'
go
update PROPERTIES set PROPERTY_KEY='com.rssl.gate.connection.timeout.esb' where CATEGORY = 'esb.gate.properties'
go
update PROPERTIES set PROPERTY_KEY='com.rssl.gate.connection.timeout.ipas' where CATEGORY = 'ipas.gate.properties'
go
update PROPERTIES set CATEGORY = 'phiz' where CATEGORY not in ('cod-dvb','cod-moscow38','cod-moscow99','cod-cchb',
'cod-pb','cod-skb','cod-ub','cod-zsb','phiz-gate-cod-altaj','phiz-gate-cod-sb','phiz-gate-cod-svb','phiz-gate-cod-yzb',
'phiz-gate-cod-vvb','cod-vvb-union','cod-vsb-union','cod-bajkal','cod-yzb','phiz-gate-iqwave','phiz-gate-cod','phiz-gate-cpfl',
'phiz-gate-sofia', 'phiz-gate-gorod', 'csa-auth-gate')
go
delete from PROPERTIES where PROPERTY_VALUE is null
go

--ÄÁ ÖÑÀ
update PROPERTIES set CATEGORY = 'csa-back' where CATEGORY like 'CSABack.%'
go
update PROPERTIES set CATEGORY = 'phiz-csa' where CATEGORY <> 'csa-back'
go
delete from PROPERTIES where PROPERTY_VALUE is null
go
