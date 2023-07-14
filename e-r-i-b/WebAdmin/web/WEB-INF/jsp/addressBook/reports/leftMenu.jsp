<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>

<tiles:insert definition="leftMenuInset" service="AddressBookContactsCountReportManagement">
    <tiles:put name="enabled" value="${submenu != 'ContactsCount'}"/>
    <tiles:put name="action"  value="/addressBook/reports/contactsCount"/>
    <tiles:put name="text"><bean:message bundle="addressBookReportsBundle" key="label.menu.left.contacts.count"/></tiles:put>
    <tiles:put name="title"><bean:message bundle="addressBookReportsBundle" key="label.menu.left.contacts.count"/></tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="AddressBookRequestsCountReportManagement">
    <tiles:put name="enabled" value="${submenu != 'RequestsCount'}"/>
    <tiles:put name="action"  value="/addressBook/reports/requestsCount"/>
    <tiles:put name="text"><bean:message bundle="addressBookReportsBundle" key="label.menu.left.request.count"/></tiles:put>
    <tiles:put name="title"><bean:message bundle="addressBookReportsBundle" key="label.menu.left.request.count"/></tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="AddressBookSyncCountExceedReportManagement">
    <tiles:put name="enabled" value="${submenu != 'SyncCountExceed'}"/>
    <tiles:put name="action"  value="/addressBook/reports/syncCountExceed"/>
    <tiles:put name="text"><bean:message bundle="addressBookReportsBundle" key="label.menu.left.sync.count.exceed"/></tiles:put>
    <tiles:put name="title"><bean:message bundle="addressBookReportsBundle" key="label.menu.left.sync.count.exceed"/></tiles:put>
</tiles:insert>


<tiles:insert definition="leftMenuInset" service="BusinessSettingsManagement">
    <tiles:put name="enabled" value="${submenu != 'RequestByPhone'}"/>
    <tiles:put name="action"  value="/addressBook/reports/requestByPhone"/>
    <tiles:put name="text"><bean:message bundle="addressBookReportsBundle" key="label.menu.left.request.by.phone.count"/></tiles:put>
    <tiles:put name="title"><bean:message bundle="addressBookReportsBundle" key="label.menu.left.request.by.phone.count"/></tiles:put>
</tiles:insert>

