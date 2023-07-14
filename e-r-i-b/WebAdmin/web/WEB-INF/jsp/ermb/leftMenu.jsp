
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"  %>

<tiles:importAttribute/>

<tiles:insert definition="leftMenuInset" service="ErmbProfileManagment">
    <tiles:put name="enabled" value="${submenu != 'ErmbTariff'}"/>
    <tiles:put name="action"  value="/ermb/tariff/list.do"/>
    <tiles:put name="text"    value="���������� �������� ����"/>
    <tiles:put name="title"   value="���������� �������� ����"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ErmbProfileManagment">
    <tiles:put name="enabled" value="${submenu != 'ErmbPersonSearch'}"/>
    <tiles:put name="action"  value="/ermb/person/search.do"/>
    <tiles:put name="text"    value="����� �������"/>
    <tiles:put name="title"   value="����� �������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ErmbProfileManagment">
	<tiles:put name="enabled" value="${submenu != 'ErmbBankrollProductRules'}"/>
	<tiles:put name="action"  value="/ermb/rules/list.do"/>
	<tiles:put name="text"    value="��������� �� ���������"/>
	<tiles:put name="title"   value="��������� �� ���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ErmbProfileManagment">
	<tiles:put name="enabled" value="${submenu != 'ErmbSubscribeFeeSettings'}"/>
	<tiles:put name="action"  value="/ermb/settings/subscribeFee.do"/>
	<tiles:put name="text"    value="��������� ����������� �����"/>
	<tiles:put name="title"   value="��������� ����������� �����"/>
</tiles:insert>

