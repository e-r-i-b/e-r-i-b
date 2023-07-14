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

<tiles:insert definition="leftMenuInset" service="CommonLogService">
	<tiles:put name="enabled" value="${submenu != 'Common'}"/>
	<tiles:put name="action"  value="/log/common.do"/>
	<tiles:put name="text"    value="����������� ������"/>
	<tiles:put name="title"   value="����������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="CommonLogServiceEmployee">
	<tiles:put name="enabled" value="${submenu != 'Common'}"/>
	<tiles:put name="action"  value="/log/common.do"/>
	<tiles:put name="text"    value="����������� ������"/>
	<tiles:put name="title"   value="����������� ������"/>
</tiles:insert>

<!--��������� �������-->
<tiles:insert definition="leftMenuInset" service="LogsService">
	<tiles:put name="enabled" value="${submenu != 'Operation'}"/>
	<tiles:put name="action"  value="/log/operations.do"/>
	<tiles:put name="text"    value="������ �������� �������������"/>
	<tiles:put name="title"   value="������ �������� �������������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LogsServiceEmployee">
	<tiles:put name="enabled" value="${submenu != 'Operation'}"/>
	<tiles:put name="action"  value="/log/operations.do"/>
	<tiles:put name="text"    value="������ �������� �������������"/>
	<tiles:put name="title"   value="������ �������� �������������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LogsService">
	<tiles:put name="enabled" value="${submenu != 'Messages'}"/>
	<tiles:put name="action"  value="/log/system.do"/>
	<tiles:put name="text"    value="������ ��������� ��������"/>
	<tiles:put name="title"   value="������ ��������� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LogsServiceEmployee">
	<tiles:put name="enabled" value="${submenu != 'Messages'}"/>
	<tiles:put name="action"  value="/log/system.do"/>
	<tiles:put name="text"    value="������ ��������� ��������"/>
	<tiles:put name="title"   value="������ ��������� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="MessageLogService">
	<tiles:put name="enabled" value="${submenu != 'System'}"/>
	<tiles:put name="action"  value="/log/messages.do"/>
	<tiles:put name="text"    value="������ ���������"/>
	<tiles:put name="title"   value="������ ���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="MessageLogServiceEmployee">
	<tiles:put name="enabled" value="${submenu != 'System'}"/>
	<tiles:put name="action"  value="/log/messages.do"/>
	<tiles:put name="text"    value="������ ���������"/>
	<tiles:put name="title"   value="������ ���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LoggingJournalServiceEmployee">
	<tiles:put name="enabled" value="${submenu != 'LogEntries'}"/>
	<tiles:put name="action"  value="/log/entries.do"/>
	<tiles:put name="text"    value="������ ����������� ������"/>
	<tiles:put name="title"   value="������ ����������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LoggingJournalService">
	<tiles:put name="enabled" value="${submenu != 'LogEntries'}"/>
	<tiles:put name="action"  value="/log/entries.do"/>
	<tiles:put name="text"    value="������ ����������� ������"/>
	<tiles:put name="title"   value="������ ����������� ������"/>
</tiles:insert>

<c:if test="${phiz:impliesService('CSALogMangement') or phiz:impliesService('CSALogMangementEmployee')}">
    <tiles:insert definition="leftMenuInsetGroup" flush="false" >
        <tiles:put name="text"    value="������� ���"/>
        <tiles:put name="name"    value="CSAJournals"/>
        <tiles:put name="enabled" value="${submenu != 'CSASystemLog' && submenu != 'CSAMessgeLog' && submenu!='CSAMessageDictionary'
                                            && submenu!='CSAOperationLog' && submenu != 'CSAGuestEntryLog'}"/>
        <tiles:put name="data">
            <tiles:insert definition="leftMenuInset" operation="ViewCSASystemLogOperation" flush="false">
                <tiles:put name="enabled"       value="${submenu != 'CSASystemLog'}"/>
                <tiles:put name="action"        value="/log/csa/system.do"/>
                <tiles:put name="text"          value="������ ��������� ��������"/>
                <tiles:put name="title"         value="������ ��������� ��������"/>
                <tiles:put name="parentName"    value="CSAJournals"/>
            </tiles:insert>
            <tiles:insert definition="leftMenuInset" operation="ViewCSAMessageLogOperation" flush="false">
                <tiles:put name="enabled"       value="${submenu != 'CSAMessgeLog'}"/>
                <tiles:put name="action"        value="/log/csa/messages.do"/>
                <tiles:put name="text"          value="������ ���������"/>
                <tiles:put name="title"         value="������ ���������"/>
                <tiles:put name="parentName"    value="CSAJournals"/>
            </tiles:insert>
            <tiles:insert definition="leftMenuInset" service="CSALogMangement" operation="ViewCSAOperationLogOperation" flush="false">
                <tiles:put name="enabled"       value="${submenu != 'CSAOperationLog'}"/>
                <tiles:put name="action"        value="/log/csa/operations.do"/>
                <tiles:put name="text"          value="������ �������� �������������"/>
                <tiles:put name="title"         value="������ �������� �������������"/>
                <tiles:put name="parentName"    value="CSAJournals"/>
            </tiles:insert>
            <tiles:insert definition="leftMenuInset" service="CSAMesageDictionaryManagement" operation="MessageTranslateCSAListOperation" flush="false">
                <tiles:put name="enabled" value="${submenu != 'CSAMessageDictionary'}"/>
                <tiles:put name="action"  value="/messageTranslate/list.do?isCSA=true"/>
                <tiles:put name="text"    value="���������� �������� � ������� ���"/>
                <tiles:put name="title"   value="���������� �������� � ������� ���"/>
                <tiles:put name="parentName"    value="CSAJournals"/>
            </tiles:insert>
            <tiles:insert definition="leftMenuInset" service="CSALogManagement" operation="ViewCSAOperationLogOperation" flush="false">
                <tiles:put name="enabled"    value="${submenu != 'CSAGuestEntryLog'}"/>
                <tiles:put name="action"     value="/log/csa/action/guest.do"/>
                <tiles:put name="text"       value="������ �������� ������"/>
                <tiles:put name="title"      value="������ �������� ������"/>
                <tiles:put name="parentName" value="CSAJournals"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</c:if>

<tiles:insert definition="leftMenuInset" service="BannedAccountManagment">
	<tiles:put name="enabled" value="${submenu != 'BannedAccount'}"/>
	<tiles:put name="action"  value="/bannedaccount/list.do"/>
	<tiles:put name="text"    value="��������� ����� ������"/>
	<tiles:put name="title"   value="��������� ����� ������"/>
</tiles:insert>


<c:if test="${phiz:impliesService('ArchiveMessagesLogService')}">
	<tiles:insert definition="leftMenuInset" service="ArchiveMessagesLogService">
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

<tiles:insert definition="leftMenuInset" operation="EditLoggingLevelOperation" >
	<tiles:put name="enabled" value="${submenu != 'EditSystemLogLevel'}"/>
	<tiles:put name="action"  value="/logging/systemLog/edit.do"/>
	<tiles:put name="text"    value="��������� ������� ��������� ��������"/>
	<tiles:put name="title"   value="��������� ������� ��������� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditLoggingLevelOperation">
	<tiles:put name="enabled" value="${submenu != 'EditMesagesLoggingLevel'}"/>
	<tiles:put name="action"  value="/logging/messagesLog/edit.do"/>
	<tiles:put name="text"    value="��������� ������� ���������"/>
	<tiles:put name="title"   value="��������� ������� ���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditLoggingLevelOperation">
	<tiles:put name="enabled" value="${submenu != 'EditOperationsLoggingLevel'}"/>
	<tiles:put name="action"  value="/logging/operationsLog/edit.do"/>
	<tiles:put name="text"    value="��������� ������� �������� �������������"/>
	<tiles:put name="title"   value="��������� ������� �������� �������������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditLoggingLevelOperation">
	<tiles:put name="enabled" value="${submenu != 'EditEntriesLoggingLevel'}"/>
	<tiles:put name="action"  value="/logging/entriesLog/edit.do"/>
	<tiles:put name="text"    value="��������� ������� ����������� ������"/>
	<tiles:put name="title"   value="��������� ������� ����������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="PFRTemplateOperation">
	<tiles:put name="enabled" value="${submenu != 'PFRTemplate'}"/>
	<tiles:put name="action"  value="/pfr/pfrTemplate.do"/>
	<tiles:put name="text"    value="������ ������� ���"/>
	<tiles:put name="title"   value="������ ������� ���"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="MessageTranslateListOperation">
	<tiles:put name="enabled" value="${submenu != 'SettingMessageTranslate'}"/>
	<tiles:put name="action"  value="/messageTranslate/list.do"/>
	<tiles:put name="text"    value="���������� �������� � �������"/>
	<tiles:put name="title"   value="���������� �������� � �������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="BlockingRulesListOperation">
	<tiles:put name="enabled" value="${submenu != 'BlockingRules'}"/>
	<tiles:put name="action"  value="/blockingrules/list.do"/>
	<tiles:put name="text"    value="����������� �����"/>
	<tiles:put name="title"   value="����������� �����"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ErmbSmsHistoryOperation">
	<tiles:put name="enabled" value="${submenu != 'ErmbSmsLog'}"/>
	<tiles:put name="action"  value="/log/ermb/sms.do"/>
	<tiles:put name="text"    value="������ ����"/>
	<tiles:put name="title"   value="������ ����"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ExceptionEntryListOperation">
	<tiles:put name="enabled" value="${submenu != 'ExceptionEntryList'}"/>
	<tiles:put name="action"  value="/configure/exceptions/list.do"/>
	<tiles:put name="text"    value="������� ��������� ������"/>
	<tiles:put name="title"   value="������� ��������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ExceptionEntryListOperation">
	<tiles:put name="enabled" value="${submenu ne 'ExternalExceptionEntryList' and submenu ne 'ExternalExceptionEntryEdit'}"/>
	<tiles:put name="action"  value="/configure/exceptions/external/list.do"/>
	<tiles:put name="text"    value="������� ������� ������"/>
	<tiles:put name="title"   value="������� ������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ViewNodesAvailabilityInfoOperation">
    <tiles:put name="enabled" value="${submenu != 'NodesAvailabilityInfo'}"/>
    <tiles:put name="action"  value="/service/nodes"/>
    <tiles:put name="text"><bean:message key="menu.left.title" bundle="nodesAvailabilityInfoBundle"/></tiles:put>
    <tiles:put name="title"><bean:message key="menu.left.title" bundle="nodesAvailabilityInfoBundle"/></tiles:put>
</tiles:insert>

