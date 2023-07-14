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
		title="������� � ������ ��������">
	�������
</phiz:bookmark>
</c:if>
<phiz:bookmark
		action="/schemes/list"
		moduleId="Schemes"
		title="������ �� ������� ���� �������������">
	����� ����
</phiz:bookmark>
<phiz:bookmark
		action="/passwordcards/list"
		serviceId="PersonPasswordCardManagment"
		title="����� ������">
	����� ������
</phiz:bookmark>
<phiz:bookmark
		action="/employees/list"
		moduleId="Employees"
		title="������ �����������">
	����������
</phiz:bookmark>
<phiz:bookmark
		action="/selfpasswordchange"
		moduleId="Services"
		title="��������� ��������">
	������
</phiz:bookmark>

<phiz:bookmark
		action="/audit/businessDocument"
		param="status=admin"
		serviceId="ViewPaymentList"
		title="�����">
	�����
</phiz:bookmark>

<phiz:bookmark
		action="/pin/createRequest"
		serviceId="PINEnvelopeManagement"
		title="���-��������">
	���-��������
</phiz:bookmark>

<phiz:bookmark
		action="/private/dictionary/banks/national"
		moduleId="Dictionaries"
		title="�����������">
	�����������
</phiz:bookmark>


<phiz:bookmark
		action="/private/externalSystems"
        serviceId="ExternalSystemsManager"
		title="������� �������">
	������� �������
</phiz:bookmark>


<phiz:bookmark
		action="/persons/configure"
		moduleId="Options"
		title="���������">
	���������
</phiz:bookmark>
<phiz:bookmark
		action="/departments/list"
		moduleId="Departments"
		title="�������������">
	�������������
</phiz:bookmark>
<phiz:bookmark
		action="/certification/list"
		serviceId="CertDemandControl"
		title="�����������">
	�����������
</phiz:bookmark>
<phiz:bookmark action="/templates/templates"
               serviceId="BankTemplatesManagement"
               title="������ � ��������� ��������">
	������� ��������
</phiz:bookmark>
<phiz:bookmark action="/news/list"
               serviceId="NewsManagment"
               title="�������">
	�������
</phiz:bookmark>
<phiz:bookmark action="/mail/list"
               serviceId="MailManagment"
               title="������">
	������
</phiz:bookmark>
<phiz:bookmark action="/notifications/events"
               serviceId="SelfSubscriptions"
               title="����������">
	����������
</phiz:bookmark>
<phiz:bookmark action="/bankcells/presence"
               serviceId="Bankcells"
               title="�����">
	�����
</phiz:bookmark>

<phiz:bookmark action="/claims/list"
               serviceId="Claims"
               title="������">
	������
</phiz:bookmark>

<%--<phiz:bookmark action="/payments/list"
               serviceId="ViewPaymentList"
               title="�������">
	�������
</phiz:bookmark>--%>

<phiz:bookmark action="/deposits/list"
               serviceId="DepositsManagement"
               title="��������">
	��������
</phiz:bookmark>

<phiz:bookmark action="/loans/claims/list"
               moduleId="LoansManagement"
               title="�������">
	�������
</phiz:bookmark>
