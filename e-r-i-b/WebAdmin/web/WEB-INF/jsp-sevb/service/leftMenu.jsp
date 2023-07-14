<%--
  Created by IntelliJ IDEA.
  User: Pakhomova
  Date: 08.09.2008
  Time: 14:01:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<!--��������� �������-->
<tiles:insert definition="leftMenuInset" service="LogsService" operation="DownloadUserLogOperation">
	<tiles:put name="enabled" value="${submenu != 'Operation'}"/>
	<tiles:put name="action"  value="/log/operations.do"/>
	<tiles:put name="text"    value="������ �������� �������������"/>
	<tiles:put name="title"   value="������ �������� �������������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LogsService" operation="DownloadSystemLogOperation">
	<tiles:put name="enabled" value="${submenu != 'Messages'}"/>
	<tiles:put name="action"  value="/log/system.do"/>
	<tiles:put name="text"    value="������ ��������� ��������"/>
	<tiles:put name="title"   value="������ ��������� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="MessageLogService" operation="MessageLogOperation">
	<tiles:put name="enabled" value="${submenu != 'System'}"/>
	<tiles:put name="action"  value="/log/messages.do"/>
	<tiles:put name="text"    value="������ ���������"/>
	<tiles:put name="title"   value="������ ���������"/>
</tiles:insert>

<c:set var="avaliable" value="${phiz:impliesOperation('ArchiveSystemLogOperation','DownloadLog')or phiz:impliesOperation('ArchiveOperationsLogOperation','DownloadLog') or phiz:impliesOperation('ArchiveMessagesLogOperation','ArchiveMessagesLogService')}"/>
<c:if test="${avaliable == true}">
	<tiles:insert definition="leftMenuInset">
		<tiles:put name="enabled" value="${submenu != 'Archive'}"/>
		<tiles:put name="action"  value="/log/archive.do"/>
		<tiles:put name="text"    value="��������� ��������"/>
		<tiles:put name="title"   value="��������� ��������"/>
	</tiles:insert>
</c:if>

<tiles:insert definition="leftMenuInset" service="*" operation="ChangeEmployeePasswordOperation">
	<tiles:put name="enabled" value="${submenu != 'ChangePassword'}"/>
	<tiles:put name="action"  value="/selfpasswordchange.do"/>
	<tiles:put name="text"    value="������� ������"/>
	<tiles:put name="title"   value="������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditEndOfDayTimeOperation">
	<tiles:put name="enabled" value="${submenu != 'EditDocumentsReceptionTime'}"/>
	<tiles:put name="action"  value="/endofdaytimeedit.do"/>
	<tiles:put name="text"    value="����� ������ ����������"/>
	<tiles:put name="title"   value="������� ������� ������ ����������"/>
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
	<tiles:put name="text"    value="������ ��������"/>
	<tiles:put name="title"   value="������ �������� ���������� ���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="TemplateDocumentsListOperation">
	<tiles:put name="enabled" value="${submenu != 'TemplatesDoc'}"/>
	<tiles:put name="action"  value="/templates/documents/list.do"/>
	<tiles:put name="text"    value="������� ����������"/>
	<tiles:put name="title"   value="������� ���������� ���������"/>
</tiles:insert>

<!--TODO ������� ��������� ������ ��� BanksDocumentsListOperation EditBanksDocumentOperation RemoveBanksDocumentOperation-->
<tiles:insert definition="leftMenuInset" operation="BanksDocumentsListOperation">
	<tiles:put name="enabled" value="${submenu != 'BanksDocuments'}"/>
	<tiles:put name="action"  value="/templates/banksDocuments/list.do"/>
	<tiles:put name="text"    value="��������� �����"/>
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

