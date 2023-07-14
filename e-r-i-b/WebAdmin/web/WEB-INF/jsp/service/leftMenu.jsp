<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<!--��������� �������-->
<tiles:insert definition="leftMenuInset" service="DownloadLog">
	<tiles:put name="enabled" value="${submenu != 'Operation'}"/>
	<tiles:put name="action"  value="/log/operations.do"/>
	<tiles:put name="text"    value="������ �������� �������������"/>
	<tiles:put name="title"   value="������ �������� �������������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="DownloadLog">
	<tiles:put name="enabled" value="${submenu != 'Messages'}"/>
	<tiles:put name="action"  value="/log/system.do"/>
	<tiles:put name="text"    value="������ ��������� ��������"/>
	<tiles:put name="title"   value="������ ��������� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="DownloadLog">
	<tiles:put name="enabled" value="${submenu != 'System'}"/>
	<tiles:put name="action"  value="/log/messages.do"/>
	<tiles:put name="text"    value="������ ���������"/>
	<tiles:put name="title"   value="������ ���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="DownloadLog">
	<tiles:put name="enabled" value="${submenu != 'Archive'}"/>
	<tiles:put name="action"  value="/log/archive.do"/>
	<tiles:put name="text"    value="��������� ��������"/>
	<tiles:put name="title"   value="��������� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="*" operation="ChangeEmployeePasswordOperation">
	<tiles:put name="enabled" value="${submenu != 'ChangePassword'}"/>
	<tiles:put name="action"  value="/selfpasswordchange.do"/>
	<tiles:put name="text"    value="������� ������"/>
	<tiles:put name="title"   value="������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ListCalendarOperation">
	<tiles:put name="enabled" value="${submenu != 'ListCalendar'}"/>
	<tiles:put name="action"  value="/listcalendar.do"/>
	<tiles:put name="text"    value="������� ���������"/>
	<tiles:put name="title"   value="�������� ������� ����������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="GetPaymentFormListOperation">
	<tiles:put name="enabled" value="${submenu != 'Forms'}"/>
	<tiles:put name="action"  value="/forms/payment-forms.do"/>
	<tiles:put name="text"    value="����� ��������"/>
	<tiles:put name="title"   value="����� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ErrorMessagesDictionary">
	<tiles:put name="enabled" value="${submenu != 'ErrorMessages'}"/>
	<tiles:put name="action"  value="/errors/list.do"/>
	<tiles:put name="text"    value="���������� ������"/>
	<tiles:put name="title"   value="���������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="TemplatePackageListOperation">
	<tiles:put name="enabled" value="${submenu != 'TemplatesPack'}"/>
	<tiles:put name="action"  value="/templates/package/list.do"/>
	<tiles:put name="text"    value="������ �������� ���������� �����"/>
	<tiles:put name="title"   value="������ �������� ���������� ���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="TemplateDocumentsListOperation">
	<tiles:put name="enabled" value="${submenu != 'TemplatesDoc'}"/>
	<tiles:put name="action"  value="/templates/documents/list.do"/>
	<tiles:put name="text"    value="������� ���������� �����"/>
	<tiles:put name="title"   value="������� ���������� ���������"/>
</tiles:insert>

<!--TODO ������� ��������� ������ ��� BanksDocumentsListOperation EditBanksDocumentOperation RemoveBanksDocumentOperation-->
<tiles:insert definition="leftMenuInset" operation="BanksDocumentsListOperation">
	<tiles:put name="enabled" value="${submenu != 'BanksDocuments'}"/>
	<tiles:put name="action"  value="/templates/banksDocuments/list.do"/>
	<tiles:put name="text"    value="�������� �����"/>
	<tiles:put name="title"   value="��������� �����"/>
</tiles:insert>

<!--TODO ������� ListCommissionOperation ViewCommissionOperation � EditCommissionOperation � ��������� ������-->
<tiles:insert definition="leftMenuInset" operation="ListCommissionOperation">
	<tiles:put name="enabled" value="${submenu != 'Commissions'}"/>
	<tiles:put name="action"  value="/commissions/list.do"/>
	<tiles:put name="text"    value="��������� ��������"/>
	<tiles:put name="title"   value="��������� �������� �����"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="CheckPaymentSignatureOperation">
	<tiles:put name="enabled" value="${submenu != '�heckSignature'}"/>
	<tiles:put name="action"  value="/documents/checkSignature"/>
	<tiles:put name="text"    value="�������� ���"/>
	<tiles:put name="title"   value="�������� ���"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="BankTemplatesManagement">
	<tiles:put name="enabled" value="${submenu != 'ListBankSmsTemplates'}"/>
	<tiles:put name="action"  value="/templates/smstemplates.do"/>
	<tiles:put name="text"    value="SMS-�������"/>
	<tiles:put name="title"   value="SMS-�������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditLoggingLevelOperation" module="Services">
	<tiles:put name="enabled" value="${submenu != 'EditSystemLogLevel'}"/>
	<tiles:put name="action"  value="/logging/systemLog/edit.do"/>
	<tiles:put name="text"    value="��������� ������� ��������� ��������"/>
	<tiles:put name="title"   value="��������� ������� ��������� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditLoggingLevelOperation" module="Services">
	<tiles:put name="enabled" value="${submenu != 'EditMesagesLoggingLevel'}"/>
	<tiles:put name="action"  value="/logging/messagesLog/edit.do"/>
	<tiles:put name="text"    value="��������� ������� ���������"/>
	<tiles:put name="title"   value="��������� ������� ���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditLoggingLevelOperation" module="Services">
	<tiles:put name="enabled" value="${submenu != 'EditOperationsLoggingLevel'}"/>
	<tiles:put name="action"  value="/logging/operationsLog/edit.do"/>
	<tiles:put name="text"    value="��������� �������<br> �������� �������������"/>
	<tiles:put name="title"   value="��������� ������� �������� �������������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'About'}"/>
	<tiles:put name="action"  value="/about"/>
	<tiles:put name="text"    value="� ���������"/>
	<tiles:put name="title"   value="� ���������"/>
</tiles:insert>