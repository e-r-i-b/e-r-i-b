<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute name="mainmenu" ignore="true"/>

<c:set var="mode" scope="request" value="${mainmenu}"/>
<c:if test="${phiz:impliesOperation('GetPersonsListOperation','*')}">

<phiz:bookmark
		action="/persons/list"
		moduleId="Users"
		title="Перейти к списку клиентов">
	Клиенты
</phiz:bookmark>
</c:if>
<phiz:bookmark
		action="/schemes/list"
		moduleId="Schemes"
		title="Работа со схемами прав пользователей">
	Схемы прав
</phiz:bookmark>
<phiz:bookmark
		action="/passwordcards/list"
		serviceId="PersonPasswordCardManagment"
		title="Карты ключей">
	Карты ключей
</phiz:bookmark>
<phiz:bookmark
		action="/employees/list"
		moduleId="Employees"
		title="Список сотрудников">
	Сотрудники
</phiz:bookmark>
<phiz:bookmark
		action="/selfpasswordchange"
		moduleId="Services"
		title="Сервисные операции">
	Сервис
</phiz:bookmark>

<phiz:bookmark
		action="/audit/businessDocument"
		param="status=admin"
		serviceId="ViewPaymentList"
		title="Аудит">
	Аудит
</phiz:bookmark>

<phiz:bookmark
		action="/pin/createRequest"
		serviceId="PINEnvelopeManagement"
		title="ПИН-конверты">
	ПИН-конверты
</phiz:bookmark>

<phiz:bookmark
		action="/private/dictionary/banks/national"
		moduleId="Dictionaries"
		title="Справочники">
	Справочники
</phiz:bookmark>


<phiz:bookmark
		action="/private/externalSystems"
        serviceId="ExternalSystemsManager"
		title="Внешние системы">
	Внешние системы
</phiz:bookmark>


<phiz:bookmark
		action="/persons/configure"
		moduleId="Options"
		title="Настройки">
	Настройки
</phiz:bookmark>
<phiz:bookmark
		action="/departments/list"
		moduleId="Departments"
		title="Подразделения">
	Подразделения
</phiz:bookmark>
<phiz:bookmark
		action="/certification/list"
		serviceId="CertDemandControl"
		title="Сертификаты">
	Сертификаты
</phiz:bookmark>
<phiz:bookmark action="/templates/templates"
               serviceId="BankTemplatesManagement"
               title="Работа с шаблонами платежей">
	Шаблоны платежей
</phiz:bookmark>
<phiz:bookmark action="/news/list"
               serviceId="NewsManagment"
               title="Новости">
	Новости
</phiz:bookmark>
<phiz:bookmark action="/mail/list"
               serviceId="MailManagment"
               title="Письма">
	Письма
</phiz:bookmark>
<phiz:bookmark action="/notifications/events"
               serviceId="SelfSubscriptions"
               title="Оповещения">
	Оповещения
</phiz:bookmark>
<phiz:bookmark action="/bankcells/presence"
               serviceId="Bankcells"
               title="Сейфы">
	Сейфы
</phiz:bookmark>

<phiz:bookmark action="/claims/list"
               serviceId="Claims"
               title="Заявки">
	Заявки
</phiz:bookmark>

<%--<phiz:bookmark action="/payments/list"
               serviceId="ViewPaymentList"
               title="Платежи">
	Платежи
</phiz:bookmark>--%>

<phiz:bookmark action="/deposits/list"
               serviceId="DepositsManagement"
               title="Депозиты">
	Депозиты
</phiz:bookmark>

<phiz:bookmark action="/loans/claims/list"
               moduleId="LoansManagement"
               title="Кредиты">
	Кредиты
</phiz:bookmark>
