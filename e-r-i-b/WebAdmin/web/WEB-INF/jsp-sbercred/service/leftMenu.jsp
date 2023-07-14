<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<!--��������� �������-->
<table width="93%" cellpadding="0" cellspacing="0">
    <tr><td class="infoTitle leftMenuHeader">
        ������
    </td></tr>
</table>

<tiles:insert definition="leftMenuLink">
	<tiles:put name="enabled" value="${submenu != 'Operation'}"/>
	<tiles:put name="action"  value="/log/operations.do"/>
	<tiles:put name="text"    value="������ �������� �������������"/>
	<tiles:put name="title"   value="������ �������� �������������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink">
	<tiles:put name="enabled" value="${submenu != 'Messages'}"/>
	<tiles:put name="action"  value="/log/system.do"/>
	<tiles:put name="text"    value="������ ��������� ��������"/>
	<tiles:put name="title"   value="������ ��������� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" operation="MessageLogOperation">
	<tiles:put name="enabled" value="${submenu != 'System'}"/>
	<tiles:put name="action"  value="/log/messages.do"/>
	<tiles:put name="text"    value="������ ���������"/>
	<tiles:put name="title"   value="������ ���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="ChangeEmployeePassword">
	<tiles:put name="enabled" value="${submenu != 'ChangePassword'}"/>
	<tiles:put name="action"  value="/selfpasswordchange.do"/>
	<tiles:put name="text"    value="������� ������"/>
	<tiles:put name="title"   value="������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" operation="ListEndOfDayTimeOperation">
	<tiles:put name="enabled" value="${submenu != 'ListEndOfDayTime'}"/>
	<tiles:put name="action"  value="/endofdaytimeedit/list.do"/>
	<tiles:put name="text"    value="����� ������ ����������"/>
	<tiles:put name="title"   value="������� ������� ������ ����������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" operation="GetPaymentFormListOperation">
	<tiles:put name="enabled" value="${submenu != 'Forms'}"/>
	<tiles:put name="action"  value="/forms/payment-forms.do"/>
	<tiles:put name="text"    value="����� ��������"/>
	<tiles:put name="title"   value="����� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="SynchronizeDictionaries">
	<tiles:put name="enabled" value="${submenu != 'Synchronize'}"/>
	<tiles:put name="action"  value="/dictionaries/synchronize.do"/>
	<tiles:put name="text"    value="�������� ������������"/>
	<tiles:put name="title"   value="�������� ������������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="ErrorMessagesDictionary">
	<tiles:put name="enabled" value="${submenu != 'ErrorMessages'}"/>
	<tiles:put name="action"  value="/errors/list.do"/>
	<tiles:put name="text"    value="���������� ������"/>
	<tiles:put name="title"   value="���������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink">
	<tiles:put name="enabled" value="${submenu != 'TemplatesPack'}"/>
	<tiles:put name="action"  value="/templates/package/list.do"/>
	<tiles:put name="text"    value="������ ��������"/>
	<tiles:put name="title"   value="������ �������� ���������� ���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink">
	<tiles:put name="enabled" value="${submenu != 'TemplatesDoc'}"/>
	<tiles:put name="action"  value="/templates/documents/list.do"/>
	<tiles:put name="text"    value="������� ����������"/>
	<tiles:put name="title"   value="������� ���������� ���������"/>
</tiles:insert>

<!--TODO ������� ��������� ������ ��� BanksDocumentsListOperation EditBanksDocumentOperation RemoveBanksDocumentOperation-->
<tiles:insert definition="leftMenuLink" operation="BanksDocumentsListOperation">
	<tiles:put name="enabled" value="${submenu != 'BanksDocuments'}"/>
	<tiles:put name="action"  value="/templates/banksDocuments/list.do"/>
	<tiles:put name="text"    value="��������� �����"/>
	<tiles:put name="title"   value="��������� �����"/>
</tiles:insert>

<!--TODO ������� ListCommissionOperation ViewCommissionOperation � EditCommissionOperation � ��������� ������-->
<tiles:insert definition="leftMenuLink" operation="ListCommissionOperation">
	<tiles:put name="enabled" value="${submenu != 'Commissions'}"/>
	<tiles:put name="action"  value="/commissions/list.do"/>
	<tiles:put name="text"    value="��������� ��������"/>
	<tiles:put name="title"   value="��������� �������� �����"/>
</tiles:insert>

