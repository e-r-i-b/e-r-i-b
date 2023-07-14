<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'ClientsEmployees'}"/>
	<tiles:put name="action" value="/persons/configure.do"/>
	<tiles:put name="text" value="Клиенты и сотрудники"/>
	<tiles:put name="title" value="Клиенты и сотрудники"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'Schemes'}"/>
	<tiles:put name="action" value="/schemes/configure.do"/>
	<tiles:put name="text" value="Права доступа сотрудника"/>
	<tiles:put name="title" value="Настройка прав доступа сотрудников по умолчанию"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'ClientTemplate'}"/>
	<tiles:put name="action" value="/policy/clientTemplate.do"/>
	<tiles:put name="text" value="Права доступа клиента"/>
	<tiles:put name="title" value="Настройка прав доступа клиентов по умолчанию"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="PersonPasswordCardManagment">
	<tiles:put name="enabled" value="${submenu != 'Cards'}"/>
	<tiles:put name="action" value="/passwordcards/configure.do"/>
	<tiles:put name="text" value="Карты ключей"/>
	<tiles:put name="title" value="Карты ключей"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ViewSmsSettingsOperation">
	<tiles:put name="enabled" value="${submenu != 'SmsService'}"/>
	<tiles:put name="action" value="/sms/configure.do"/>
	<tiles:put name="text" value="Sms-пароли"/>
	<tiles:put name="title" value="Sms-пароли"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ContactDictionariesSettingsManagement">
	<tiles:put name="enabled" value="${submenu != 'DictionariesContact'}"/>
	<tiles:put name="action" value="/dictionaries/configureContact.do"/>
	<tiles:put name="text" value="Справочники CONTACT"/>
	<tiles:put name="title" value="Справочники CONTACT"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="SetDictionariesConfigsOperation">
	<tiles:put name="enabled" value="${submenu != 'Dictionaries'}"/>
	<tiles:put name="action" value="/dictionaries/configure.do"/>
	<tiles:put name="text" value="Справочники"/>
	<tiles:put name="title" value="Справочники"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="Schedules">
	<tiles:put name="enabled" value="${submenu != 'SchedulesE'}"/>
	<tiles:put name="action" value="/schedules/list.do?kind=E"/>
	<tiles:put name="text" value="Расписания сотрудников"/>
	<tiles:put name="title" value="Расписания сотрудников"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ViewSystemSettingsOperation">
	<tiles:put name="enabled" value="${submenu != 'SysSettings'}"/>
	<tiles:put name="action" value="/systemSettings/configure.do"/>
	<tiles:put name="text" value="Параметры системы"/>
	<tiles:put name="title" value="Параметры системы"/>
</tiles:insert>
